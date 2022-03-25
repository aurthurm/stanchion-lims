import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IProvince } from '@/shared/model/province.model';

import ProvinceService from './province.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Province extends Vue {
  @Inject('provinceService') private provinceService: () => ProvinceService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public provinces: IProvince[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllProvinces();
  }

  public clear(): void {
    this.retrieveAllProvinces();
  }

  public retrieveAllProvinces(): void {
    this.isFetching = true;
    this.provinceService()
      .retrieve()
      .then(
        res => {
          this.provinces = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
          this.alertService().showHttpError(this, err.response);
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IProvince): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeProvince(): void {
    this.provinceService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('stanchionApp.province.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllProvinces();
        this.closeDialog();
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
