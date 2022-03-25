import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ISample } from '@/shared/model/sample.model';

import SampleService from './sample.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Sample extends Vue {
  @Inject('sampleService') private sampleService: () => SampleService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public samples: ISample[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllSamples();
  }

  public clear(): void {
    this.retrieveAllSamples();
  }

  public retrieveAllSamples(): void {
    this.isFetching = true;
    this.sampleService()
      .retrieve()
      .then(
        res => {
          this.samples = res.data;
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

  public prepareRemove(instance: ISample): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeSample(): void {
    this.sampleService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('stanchionApp.sample.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllSamples();
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
