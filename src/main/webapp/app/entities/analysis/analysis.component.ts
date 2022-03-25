import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IAnalysis } from '@/shared/model/analysis.model';

import AnalysisService from './analysis.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Analysis extends Vue {
  @Inject('analysisService') private analysisService: () => AnalysisService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public analyses: IAnalysis[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllAnalysiss();
  }

  public clear(): void {
    this.retrieveAllAnalysiss();
  }

  public retrieveAllAnalysiss(): void {
    this.isFetching = true;
    this.analysisService()
      .retrieve()
      .then(
        res => {
          this.analyses = res.data;
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

  public prepareRemove(instance: IAnalysis): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeAnalysis(): void {
    this.analysisService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('stanchionApp.analysis.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllAnalysiss();
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
