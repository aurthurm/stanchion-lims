import { Component, Vue, Inject } from 'vue-property-decorator';

import { IDistrict } from '@/shared/model/district.model';
import DistrictService from './district.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class DistrictDetails extends Vue {
  @Inject('districtService') private districtService: () => DistrictService;
  @Inject('alertService') private alertService: () => AlertService;

  public district: IDistrict = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.districtId) {
        vm.retrieveDistrict(to.params.districtId);
      }
    });
  }

  public retrieveDistrict(districtId) {
    this.districtService()
      .find(districtId)
      .then(res => {
        this.district = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
