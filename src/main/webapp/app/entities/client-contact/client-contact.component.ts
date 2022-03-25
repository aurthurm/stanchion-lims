import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IClientContact } from '@/shared/model/client-contact.model';

import ClientContactService from './client-contact.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class ClientContact extends Vue {
  @Inject('clientContactService') private clientContactService: () => ClientContactService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public clientContacts: IClientContact[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllClientContacts();
  }

  public clear(): void {
    this.retrieveAllClientContacts();
  }

  public retrieveAllClientContacts(): void {
    this.isFetching = true;
    this.clientContactService()
      .retrieve()
      .then(
        res => {
          this.clientContacts = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
          this.alertService().showHttpError(this, err.response);
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IClientContact): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeClientContact(): void {
    this.clientContactService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('stanchionApp.clientContact.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllClientContacts();
        this.closeDialog();
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
