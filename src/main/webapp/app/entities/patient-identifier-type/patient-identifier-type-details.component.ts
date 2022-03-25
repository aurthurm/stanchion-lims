import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPatientIdentifierType } from '@/shared/model/patient-identifier-type.model';
import PatientIdentifierTypeService from './patient-identifier-type.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class PatientIdentifierTypeDetails extends Vue {
  @Inject('patientIdentifierTypeService') private patientIdentifierTypeService: () => PatientIdentifierTypeService;
  @Inject('alertService') private alertService: () => AlertService;

  public patientIdentifierType: IPatientIdentifierType = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.patientIdentifierTypeId) {
        vm.retrievePatientIdentifierType(to.params.patientIdentifierTypeId);
      }
    });
  }

  public retrievePatientIdentifierType(patientIdentifierTypeId) {
    this.patientIdentifierTypeService()
      .find(patientIdentifierTypeId)
      .then(res => {
        this.patientIdentifierType = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
