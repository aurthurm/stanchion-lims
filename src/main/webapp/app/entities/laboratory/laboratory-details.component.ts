import { Component, Vue, Inject } from 'vue-property-decorator';

import { ILaboratory } from '@/shared/model/laboratory.model';
import LaboratoryService from './laboratory.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class LaboratoryDetails extends Vue {
  @Inject('laboratoryService') private laboratoryService: () => LaboratoryService;
  @Inject('alertService') private alertService: () => AlertService;

  public laboratory: ILaboratory = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.laboratoryId) {
        vm.retrieveLaboratory(to.params.laboratoryId);
      }
    });
  }

  public retrieveLaboratory(laboratoryId) {
    this.laboratoryService()
      .find(laboratoryId)
      .then(res => {
        this.laboratory = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
