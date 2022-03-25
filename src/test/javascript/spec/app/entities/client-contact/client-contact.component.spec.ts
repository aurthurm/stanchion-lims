/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import ClientContactComponent from '@/entities/client-contact/client-contact.vue';
import ClientContactClass from '@/entities/client-contact/client-contact.component';
import ClientContactService from '@/entities/client-contact/client-contact.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(ToastPlugin);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('ClientContact Management Component', () => {
    let wrapper: Wrapper<ClientContactClass>;
    let comp: ClientContactClass;
    let clientContactServiceStub: SinonStubbedInstance<ClientContactService>;

    beforeEach(() => {
      clientContactServiceStub = sinon.createStubInstance<ClientContactService>(ClientContactService);
      clientContactServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ClientContactClass>(ClientContactComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          clientContactService: () => clientContactServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      clientContactServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllClientContacts();
      await comp.$nextTick();

      // THEN
      expect(clientContactServiceStub.retrieve.called).toBeTruthy();
      expect(comp.clientContacts[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      clientContactServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(clientContactServiceStub.retrieve.callCount).toEqual(1);

      comp.removeClientContact();
      await comp.$nextTick();

      // THEN
      expect(clientContactServiceStub.delete.called).toBeTruthy();
      expect(clientContactServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
