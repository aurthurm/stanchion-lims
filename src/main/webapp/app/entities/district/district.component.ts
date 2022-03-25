import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IDistrict } from '@/shared/model/district.model';

import DistrictService from './district.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class District extends Vue {
  @Inject('districtService') private districtService: () => DistrictService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public districts: IDistrict[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllDistricts();
  }

  public clear(): void {
    this.retrieveAllDistricts();
  }

  public retrieveAllDistricts(): void {
    this.isFetching = true;
    this.districtService()
      .retrieve()
      .then(
        res => {
          this.districts = res.data;
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

  public prepareRemove(instance: IDistrict): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeDistrict(): void {
    this.districtService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('stanchionApp.district.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllDistricts();
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
