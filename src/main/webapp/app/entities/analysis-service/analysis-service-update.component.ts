import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import { IAnalysisService, AnalysisService } from '@/shared/model/analysis-service.model';
import AnalysisServiceService from './analysis-service.service';

const validations: any = {
  analysisService: {},
};

@Component({
  validations,
})
export default class AnalysisServiceUpdate extends Vue {
  @Inject('analysisServiceService') private analysisServiceService: () => AnalysisServiceService;
  @Inject('alertService') private alertService: () => AlertService;

  public analysisService: IAnalysisService = new AnalysisService();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.analysisServiceId) {
        vm.retrieveAnalysisService(to.params.analysisServiceId);
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
    if (this.analysisService.id) {
      this.analysisServiceService()
        .update(this.analysisService)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('stanchionApp.analysisService.updated', { param: param.id });
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
      this.analysisServiceService()
        .create(this.analysisService)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('stanchionApp.analysisService.created', { param: param.id });
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

  public retrieveAnalysisService(analysisServiceId): void {
    this.analysisServiceService()
      .find(analysisServiceId)
      .then(res => {
        this.analysisService = res;
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
