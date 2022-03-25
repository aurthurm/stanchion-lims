import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ISampleType } from '@/shared/model/sample-type.model';

import SampleTypeService from './sample-type.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class SampleType extends Vue {
  @Inject('sampleTypeService') private sampleTypeService: () => SampleTypeService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public sampleTypes: ISampleType[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllSampleTypes();
  }

  public clear(): void {
    this.retrieveAllSampleTypes();
  }

  public retrieveAllSampleTypes(): void {
    this.isFetching = true;
    this.sampleTypeService()
      .retrieve()
      .then(
        res => {
          this.sampleTypes = res.data;
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

  public prepareRemove(instance: ISampleType): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeSampleType(): void {
    this.sampleTypeService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('stanchionApp.sampleType.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllSampleTypes();
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
