import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ILaboratoryRequest } from '@/shared/model/laboratory-request.model';

import LaboratoryRequestService from './laboratory-request.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class LaboratoryRequest extends Vue {
  @Inject('laboratoryRequestService') private laboratoryRequestService: () => LaboratoryRequestService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public laboratoryRequests: ILaboratoryRequest[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllLaboratoryRequests();
  }

  public clear(): void {
    this.retrieveAllLaboratoryRequests();
  }

  public retrieveAllLaboratoryRequests(): void {
    this.isFetching = true;
    this.laboratoryRequestService()
      .retrieve()
      .then(
        res => {
          this.laboratoryRequests = res.data;
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

  public prepareRemove(instance: ILaboratoryRequest): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeLaboratoryRequest(): void {
    this.laboratoryRequestService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('stanchionApp.laboratoryRequest.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllLaboratoryRequests();
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
