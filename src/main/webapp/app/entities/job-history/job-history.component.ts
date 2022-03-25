import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IJobHistory } from '@/shared/model/job-history.model';

import JobHistoryService from './job-history.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class JobHistory extends Vue {
  @Inject('jobHistoryService') private jobHistoryService: () => JobHistoryService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public jobHistories: IJobHistory[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllJobHistorys();
  }

  public clear(): void {
    this.retrieveAllJobHistorys();
  }

  public retrieveAllJobHistorys(): void {
    this.isFetching = true;
    this.jobHistoryService()
      .retrieve()
      .then(
        res => {
          this.jobHistories = res.data;
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

  public prepareRemove(instance: IJobHistory): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeJobHistory(): void {
    this.jobHistoryService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('stanchionApp.jobHistory.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllJobHistorys();
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
