import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import PatientIdentifierTypeService from '@/entities/patient-identifier-type/patient-identifier-type.service';
import { IPatientIdentifierType } from '@/shared/model/patient-identifier-type.model';

import { IPatientIdentifier, PatientIdentifier } from '@/shared/model/patient-identifier.model';
import PatientIdentifierService from './patient-identifier.service';

const validations: any = {
  patientIdentifier: {
    type: {},
    value: {},
  },
};

@Component({
  validations,
})
export default class PatientIdentifierUpdate extends Vue {
  @Inject('patientIdentifierService') private patientIdentifierService: () => PatientIdentifierService;
  @Inject('alertService') private alertService: () => AlertService;

  public patientIdentifier: IPatientIdentifier = new PatientIdentifier();

  @Inject('patientIdentifierTypeService') private patientIdentifierTypeService: () => PatientIdentifierTypeService;

  public patientIdentifierTypes: IPatientIdentifierType[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.patientIdentifierId) {
        vm.retrievePatientIdentifier(to.params.patientIdentifierId);
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
    if (this.patientIdentifier.id) {
      this.patientIdentifierService()
        .update(this.patientIdentifier)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('stanchionApp.patientIdentifier.updated', { param: param.id });
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
      this.patientIdentifierService()
        .create(this.patientIdentifier)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('stanchionApp.patientIdentifier.created', { param: param.id });
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

  public retrievePatientIdentifier(patientIdentifierId): void {
    this.patientIdentifierService()
      .find(patientIdentifierId)
      .then(res => {
        this.patientIdentifier = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.patientIdentifierTypeService()
      .retrieve()
      .then(res => {
        this.patientIdentifierTypes = res.data;
      });
  }
}
