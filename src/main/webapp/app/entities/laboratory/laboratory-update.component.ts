import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import { ILaboratory, Laboratory } from '@/shared/model/laboratory.model';
import LaboratoryService from './laboratory.service';

const validations: any = {
  laboratory: {
    name: {},
  },
};

@Component({
  validations,
})
export default class LaboratoryUpdate extends Vue {
  @Inject('laboratoryService') private laboratoryService: () => LaboratoryService;
  @Inject('alertService') private alertService: () => AlertService;

  public laboratory: ILaboratory = new Laboratory();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.laboratoryId) {
        vm.retrieveLaboratory(to.params.laboratoryId);
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
    if (this.laboratory.id) {
      this.laboratoryService()
        .update(this.laboratory)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('stanchionApp.laboratory.updated', { param: param.id });
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
      this.laboratoryService()
        .create(this.laboratory)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('stanchionApp.laboratory.created', { param: param.id });
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

  public retrieveLaboratory(laboratoryId): void {
    this.laboratoryService()
      .find(laboratoryId)
      .then(res => {
        this.laboratory = res;
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
