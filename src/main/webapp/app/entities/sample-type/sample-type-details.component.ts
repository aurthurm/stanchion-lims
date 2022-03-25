import { Component, Vue, Inject } from 'vue-property-decorator';

import { ISampleType } from '@/shared/model/sample-type.model';
import SampleTypeService from './sample-type.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class SampleTypeDetails extends Vue {
  @Inject('sampleTypeService') private sampleTypeService: () => SampleTypeService;
  @Inject('alertService') private alertService: () => AlertService;

  public sampleType: ISampleType = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.sampleTypeId) {
        vm.retrieveSampleType(to.params.sampleTypeId);
      }
    });
  }

  public retrieveSampleType(sampleTypeId) {
    this.sampleTypeService()
      .find(sampleTypeId)
      .then(res => {
        this.sampleType = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
