import { Component, Vue, Inject } from 'vue-property-decorator';

import { ILaboratoryRequest } from '@/shared/model/laboratory-request.model';
import LaboratoryRequestService from './laboratory-request.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class LaboratoryRequestDetails extends Vue {
  @Inject('laboratoryRequestService') private laboratoryRequestService: () => LaboratoryRequestService;
  @Inject('alertService') private alertService: () => AlertService;

  public laboratoryRequest: ILaboratoryRequest = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.laboratoryRequestId) {
        vm.retrieveLaboratoryRequest(to.params.laboratoryRequestId);
      }
    });
  }

  public retrieveLaboratoryRequest(laboratoryRequestId) {
    this.laboratoryRequestService()
      .find(laboratoryRequestId)
      .then(res => {
        this.laboratoryRequest = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
