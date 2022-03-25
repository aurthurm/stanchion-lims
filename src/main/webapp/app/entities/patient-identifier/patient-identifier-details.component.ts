import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPatientIdentifier } from '@/shared/model/patient-identifier.model';
import PatientIdentifierService from './patient-identifier.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class PatientIdentifierDetails extends Vue {
  @Inject('patientIdentifierService') private patientIdentifierService: () => PatientIdentifierService;
  @Inject('alertService') private alertService: () => AlertService;

  public patientIdentifier: IPatientIdentifier = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.patientIdentifierId) {
        vm.retrievePatientIdentifier(to.params.patientIdentifierId);
      }
    });
  }

  public retrievePatientIdentifier(patientIdentifierId) {
    this.patientIdentifierService()
      .find(patientIdentifierId)
      .then(res => {
        this.patientIdentifier = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
