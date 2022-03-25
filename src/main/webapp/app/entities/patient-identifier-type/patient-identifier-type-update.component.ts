import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import { IPatientIdentifierType, PatientIdentifierType } from '@/shared/model/patient-identifier-type.model';
import PatientIdentifierTypeService from './patient-identifier-type.service';

const validations: any = {
  patientIdentifierType: {
    name: {},
  },
};

@Component({
  validations,
})
export default class PatientIdentifierTypeUpdate extends Vue {
  @Inject('patientIdentifierTypeService') private patientIdentifierTypeService: () => PatientIdentifierTypeService;
  @Inject('alertService') private alertService: () => AlertService;

  public patientIdentifierType: IPatientIdentifierType = new PatientIdentifierType();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.patientIdentifierTypeId) {
        vm.retrievePatientIdentifierType(to.params.patientIdentifierTypeId);
      }
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
    if (this.patientIdentifierType.id) {
      this.patientIdentifierTypeService()
        .update(this.patientIdentifierType)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('stanchionApp.patientIdentifierType.updated', { param: param.id });
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
      this.patientIdentifierTypeService()
        .create(this.patientIdentifierType)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('stanchionApp.patientIdentifierType.created', { param: param.id });
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

  public retrievePatientIdentifierType(patientIdentifierTypeId): void {
    this.patientIdentifierTypeService()
      .find(patientIdentifierTypeId)
      .then(res => {
        this.patientIdentifierType = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
