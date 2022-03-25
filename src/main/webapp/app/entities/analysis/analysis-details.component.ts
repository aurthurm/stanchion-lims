import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAnalysis } from '@/shared/model/analysis.model';
import AnalysisService from './analysis.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class AnalysisDetails extends Vue {
  @Inject('analysisService') private analysisService: () => AnalysisService;
  @Inject('alertService') private alertService: () => AlertService;

  public analysis: IAnalysis = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.analysisId) {
        vm.retrieveAnalysis(to.params.analysisId);
      }
    });
  }

  public retrieveAnalysis(analysisId) {
    this.analysisService()
      .find(analysisId)
      .then(res => {
        this.analysis = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
