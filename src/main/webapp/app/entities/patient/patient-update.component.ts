import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import PatientIdentifierService from '@/entities/patient-identifier/patient-identifier.service';
import { IPatientIdentifier } from '@/shared/model/patient-identifier.model';

import LaboratoryRequestService from '@/entities/laboratory-request/laboratory-request.service';
import { ILaboratoryRequest } from '@/shared/model/laboratory-request.model';

import { IPatient, Patient } from '@/shared/model/patient.model';
import PatientService from './patient.service';

const validations: any = {
  patient: {
    firstName: {},
    lastName: {},
  },
};

@Component({
  validations,
})
export default class PatientUpdate extends Vue {
  @Inject('patientService') private patientService: () => PatientService;
  @Inject('alertService') private alertService: () => AlertService;

  public patient: IPatient = new Patient();

  @Inject('patientIdentifierService') private patientIdentifierService: () => PatientIdentifierService;

  public patientIdentifiers: IPatientIdentifier[] = [];

  @Inject('laboratoryRequestService') private laboratoryRequestService: () => LaboratoryRequestService;

  public laboratoryRequests: ILaboratoryRequest[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.patientId) {
        vm.retrievePatient(to.params.patientId);
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
    if (this.patient.id) {
      this.patientService()
        .update(this.patient)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('stanchionApp.patient.updated', { param: param.id });
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
      this.patientService()
        .create(this.patient)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('stanchionApp.patient.created', { param: param.id });
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

  public retrievePatient(patientId): void {
    this.patientService()
      .find(patientId)
      .then(res => {
        this.patient = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.patientIdentifierService()
      .retrieve()
      .then(res => {
        this.patientIdentifiers = res.data;
      });
    this.laboratoryRequestService()
      .retrieve()
      .then(res => {
        this.laboratoryRequests = res.data;
      });
  }
}
