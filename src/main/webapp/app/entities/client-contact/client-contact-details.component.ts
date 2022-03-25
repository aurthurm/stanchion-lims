import { Component, Vue, Inject } from 'vue-property-decorator';

import { IClientContact } from '@/shared/model/client-contact.model';
import ClientContactService from './client-contact.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class ClientContactDetails extends Vue {
  @Inject('clientContactService') private clientContactService: () => ClientContactService;
  @Inject('alertService') private alertService: () => AlertService;

  public clientContact: IClientContact = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.clientContactId) {
        vm.retrieveClientContact(to.params.clientContactId);
      }
    });
  }

  public retrieveClientContact(clientContactId) {
    this.clientContactService()
      .find(clientContactId)
      .then(res => {
        this.clientContact = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
