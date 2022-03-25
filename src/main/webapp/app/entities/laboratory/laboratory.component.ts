import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ILaboratory } from '@/shared/model/laboratory.model';

import LaboratoryService from './laboratory.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Laboratory extends Vue {
  @Inject('laboratoryService') private laboratoryService: () => LaboratoryService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public laboratories: ILaboratory[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllLaboratorys();
  }

  public clear(): void {
    this.retrieveAllLaboratorys();
  }

  public retrieveAllLaboratorys(): void {
    this.isFetching = true;
    this.laboratoryService()
      .retrieve()
      .then(
        res => {
          this.laboratories = res.data;
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

  public prepareRemove(instance: ILaboratory): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeLaboratory(): void {
    this.laboratoryService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('stanchionApp.laboratory.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllLaboratorys();
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
