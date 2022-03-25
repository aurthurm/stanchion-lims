import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IPatientIdentifier } from '@/shared/model/patient-identifier.model';

import PatientIdentifierService from './patient-identifier.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class PatientIdentifier extends Vue {
  @Inject('patientIdentifierService') private patientIdentifierService: () => PatientIdentifierService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public patientIdentifiers: IPatientIdentifier[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllPatientIdentifiers();
  }

  public clear(): void {
    this.retrieveAllPatientIdentifiers();
  }

  public retrieveAllPatientIdentifiers(): void {
    this.isFetching = true;
    this.patientIdentifierService()
      .retrieve()
      .then(
        res => {
          this.patientIdentifiers = res.data;
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

  public prepareRemove(instance: IPatientIdentifier): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removePatientIdentifier(): void {
    this.patientIdentifierService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('stanchionApp.patientIdentifier.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllPatientIdentifiers();
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
