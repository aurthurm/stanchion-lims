import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IAnalysisService } from '@/shared/model/analysis-service.model';

import AnalysisServiceService from './analysis-service.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class AnalysisService extends Vue {
  @Inject('analysisServiceService') private analysisServiceService: () => AnalysisServiceService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public analysisServices: IAnalysisService[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllAnalysisServices();
  }

  public clear(): void {
    this.retrieveAllAnalysisServices();
  }

  public retrieveAllAnalysisServices(): void {
    this.isFetching = true;
    this.analysisServiceService()
      .retrieve()
      .then(
        res => {
          this.analysisServices = res.data;
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

  public prepareRemove(instance: IAnalysisService): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeAnalysisService(): void {
    this.analysisServiceService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('stanchionApp.analysisService.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllAnalysisServices();
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
