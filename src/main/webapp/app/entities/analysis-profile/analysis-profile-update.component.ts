import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import { IAnalysisProfile, AnalysisProfile } from '@/shared/model/analysis-profile.model';
import AnalysisProfileService from './analysis-profile.service';

const validations: any = {
  analysisProfile: {},
};

@Component({
  validations,
})
export default class AnalysisProfileUpdate extends Vue {
  @Inject('analysisProfileService') private analysisProfileService: () => AnalysisProfileService;
  @Inject('alertService') private alertService: () => AlertService;

  public analysisProfile: IAnalysisProfile = new AnalysisProfile();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.analysisProfileId) {
        vm.retrieveAnalysisProfile(to.params.analysisProfileId);
      }
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.analysisProfile.id) {
      this.analysisProfileService()
        .update(this.analysisProfile)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('stanchionApp.analysisProfile.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.analysisProfileService()
        .create(this.analysisProfile)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('stanchionApp.analysisProfile.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public retrieveAnalysisProfile(analysisProfileId): void {
    this.analysisProfileService()
      .find(analysisProfileId)
      .then(res => {
        this.analysisProfile = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
