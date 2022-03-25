import { Component, Vue, Inject } from 'vue-property-decorator';

import { IProvince } from '@/shared/model/province.model';
import ProvinceService from './province.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class ProvinceDetails extends Vue {
  @Inject('provinceService') private provinceService: () => ProvinceService;
  @Inject('alertService') private alertService: () => AlertService;

  public province: IProvince = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.provinceId) {
        vm.retrieveProvince(to.params.provinceId);
      }
    });
  }

  public retrieveProvince(provinceId) {
    this.provinceService()
      .find(provinceId)
      .then(res => {
        this.province = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
