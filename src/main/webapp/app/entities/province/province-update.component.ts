import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import DistrictService from '@/entities/district/district.service';
import { IDistrict } from '@/shared/model/district.model';

import { IProvince, Province } from '@/shared/model/province.model';
import ProvinceService from './province.service';

const validations: any = {
  province: {
    name: {},
  },
};

@Component({
  validations,
})
export default class ProvinceUpdate extends Vue {
  @Inject('provinceService') private provinceService: () => ProvinceService;
  @Inject('alertService') private alertService: () => AlertService;

  public province: IProvince = new Province();

  @Inject('districtService') private districtService: () => DistrictService;

  public districts: IDistrict[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.provinceId) {
        vm.retrieveProvince(to.params.provinceId);
      }
      vm.initRelationships();
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
    if (this.province.id) {
      this.provinceService()
        .update(this.province)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('stanchionApp.province.updated', { param: param.id });
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
      this.provinceService()
        .create(this.province)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('stanchionApp.province.created', { param: param.id });
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

  public retrieveProvince(provinceId): void {
    this.provinceService()
      .find(provinceId)
      .then(res => {
        this.province = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.districtService()
      .retrieve()
      .then(res => {
        this.districts = res.data;
      });
  }
}
