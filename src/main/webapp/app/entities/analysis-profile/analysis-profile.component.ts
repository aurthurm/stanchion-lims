import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IAnalysisProfile } from '@/shared/model/analysis-profile.model';

import AnalysisProfileService from './analysis-profile.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class AnalysisProfile extends Vue {
  @Inject('analysisProfileService') private analysisProfileService: () => AnalysisProfileService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public analysisProfiles: IAnalysisProfile[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllAnalysisProfiles();
  }

  public clear(): void {
    this.retrieveAllAnalysisProfiles();
  }

  public retrieveAllAnalysisProfiles(): void {
    this.isFetching = true;
    this.analysisProfileService()
      .retrieve()
      .then(
        res => {
          this.analysisProfiles = res.data;
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

  public prepareRemove(instance: IAnalysisProfile): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeAnalysisProfile(): void {
    this.analysisProfileService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('stanchionApp.analysisProfile.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllAnalysisProfiles();
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
