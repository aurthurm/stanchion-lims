import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IEmployee } from '@/shared/model/employee.model';

import EmployeeService from './employee.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Employee extends Vue {
  @Inject('employeeService') private employeeService: () => EmployeeService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public employees: IEmployee[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllEmployees();
  }

  public clear(): void {
    this.retrieveAllEmployees();
  }

  public retrieveAllEmployees(): void {
    this.isFetching = true;
    this.employeeService()
      .retrieve()
      .then(
        res => {
          this.employees = res.data;
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

  public prepareRemove(instance: IEmployee): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeEmployee(): void {
    this.employeeService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('stanchionApp.employee.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllEmployees();
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
