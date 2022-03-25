import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import { ILaboratoryRequest, LaboratoryRequest } from '@/shared/model/laboratory-request.model';
import LaboratoryRequestService from './laboratory-request.service';

const validations: any = {
  laboratoryRequest: {
    clientRequestId: {},
  },
};

@Component({
  validations,
})
export default class LaboratoryRequestUpdate extends Vue {
  @Inject('laboratoryRequestService') private laboratoryRequestService: () => LaboratoryRequestService;
  @Inject('alertService') private alertService: () => AlertService;

  public laboratoryRequest: ILaboratoryRequest = new LaboratoryRequest();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.laboratoryRequestId) {
        vm.retrieveLaboratoryRequest(to.params.laboratoryRequestId);
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
    if (this.laboratoryRequest.id) {
      this.laboratoryRequestService()
        .update(this.laboratoryRequest)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('stanchionApp.laboratoryRequest.updated', { param: param.id });
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
      this.laboratoryRequestService()
        .create(this.laboratoryRequest)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('stanchionApp.laboratoryRequest.created', { param: param.id });
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

  public retrieveLaboratoryRequest(laboratoryRequestId): void {
    this.laboratoryRequestService()
      .find(laboratoryRequestId)
      .then(res => {
        this.laboratoryRequest = res;
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
