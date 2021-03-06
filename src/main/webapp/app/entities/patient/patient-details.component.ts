import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPatient } from '@/shared/model/patient.model';
import PatientService from './patient.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class PatientDetails extends Vue {
  @Inject('patientService') private patientService: () => PatientService;
  @Inject('alertService') private alertService: () => AlertService;

  public patient: IPatient = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.patientId) {
        vm.retrievePatient(to.params.patientId);
      }
    });
  }

  public retrievePatient(patientId) {
    this.patientService()
      .find(patientId)
      .then(res => {
        this.patient = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
