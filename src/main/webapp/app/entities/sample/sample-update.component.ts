import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import { ISample, Sample } from '@/shared/model/sample.model';
import SampleService from './sample.service';

const validations: any = {
  sample: {},
};

@Component({
  validations,
})
export default class SampleUpdate extends Vue {
  @Inject('sampleService') private sampleService: () => SampleService;
  @Inject('alertService') private alertService: () => AlertService;

  public sample: ISample = new Sample();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.sampleId) {
        vm.retrieveSample(to.params.sampleId);
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
    if (this.sample.id) {
      this.sampleService()
        .update(this.sample)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('stanchionApp.sample.updated', { param: param.id });
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
      this.sampleService()
        .create(this.sample)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('stanchionApp.sample.created', { param: param.id });
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

  public retrieveSample(sampleId): void {
    this.sampleService()
      .find(sampleId)
      .then(res => {
        this.sample = res;
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
