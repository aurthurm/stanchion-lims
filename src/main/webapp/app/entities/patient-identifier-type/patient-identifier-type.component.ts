import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IPatientIdentifierType } from '@/shared/model/patient-identifier-type.model';

import PatientIdentifierTypeService from './patient-identifier-type.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class PatientIdentifierType extends Vue {
  @Inject('patientIdentifierTypeService') private patientIdentifierTypeService: () => PatientIdentifierTypeService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public patientIdentifierTypes: IPatientIdentifierType[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllPatientIdentifierTypes();
  }

  public clear(): void {
    this.retrieveAllPatientIdentifierTypes();
  }

  public retrieveAllPatientIdentifierTypes(): void {
    this.isFetching = true;
    this.patientIdentifierTypeService()
      .retrieve()
      .then(
        res => {
          this.patientIdentifierTypes = res.data;
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

  public prepareRemove(instance: IPatientIdentifierType): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removePatientIdentifierType(): void {
    this.patientIdentifierTypeService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('stanchionApp.patientIdentifierType.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllPatientIdentifierTypes();
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
