import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAnalysisProfile } from '@/shared/model/analysis-profile.model';
import AnalysisProfileService from './analysis-profile.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class AnalysisProfileDetails extends Vue {
  @Inject('analysisProfileService') private analysisProfileService: () => AnalysisProfileService;
  @Inject('alertService') private alertService: () => AlertService;

  public analysisProfile: IAnalysisProfile = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.analysisProfileId) {
        vm.retrieveAnalysisProfile(to.params.analysisProfileId);
      }
    });
  }

  public retrieveAnalysisProfile(analysisProfileId) {
    this.analysisProfileService()
      .find(analysisProfileId)
      .then(res => {
        this.analysisProfile = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
