import { Component, Vue, Inject } from 'vue-property-decorator';

import { ISample } from '@/shared/model/sample.model';
import SampleService from './sample.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class SampleDetails extends Vue {
  @Inject('sampleService') private sampleService: () => SampleService;
  @Inject('alertService') private alertService: () => AlertService;

  public sample: ISample = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.sampleId) {
        vm.retrieveSample(to.params.sampleId);
      }
    });
  }

  public retrieveSample(sampleId) {
    this.sampleService()
      .find(sampleId)
      .then(res => {
        this.sample = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
