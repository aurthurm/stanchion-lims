import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import { IAnalysis, Analysis } from '@/shared/model/analysis.model';
import AnalysisService from './analysis.service';

const validations: any = {
  analysis: {},
};

@Component({
  validations,
})
export default class AnalysisUpdate extends Vue {
  @Inject('analysisService') private analysisService: () => AnalysisService;
  @Inject('alertService') private alertService: () => AlertService;

  public analysis: IAnalysis = new Analysis();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.analysisId) {
        vm.retrieveAnalysis(to.params.analysisId);
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
    if (this.analysis.id) {
      this.analysisService()
        .update(this.analysis)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('stanchionApp.analysis.updated', { param: param.id });
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
      this.analysisService()
        .create(this.analysis)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('stanchionApp.analysis.created', { param: param.id });
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

  public retrieveAnalysis(analysisId): void {
    this.analysisService()
      .find(analysisId)
      .then(res => {
        this.analysis = res;
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
