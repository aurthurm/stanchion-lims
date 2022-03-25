import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAnalysisService } from '@/shared/model/analysis-service.model';
import AnalysisServiceService from './analysis-service.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class AnalysisServiceDetails extends Vue {
  @Inject('analysisServiceService') private analysisServiceService: () => AnalysisServiceService;
  @Inject('alertService') private alertService: () => AlertService;

  public analysisService: IAnalysisService = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.analysisServiceId) {
        vm.retrieveAnalysisService(to.params.analysisServiceId);
      }
    });
  }

  public retrieveAnalysisService(analysisServiceId) {
    this.analysisServiceService()
      .find(analysisServiceId)
      .then(res => {
        this.analysisService = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
