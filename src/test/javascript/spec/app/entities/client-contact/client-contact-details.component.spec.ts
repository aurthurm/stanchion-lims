/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ClientContactDetailComponent from '@/entities/client-contact/client-contact-details.vue';
import ClientContactClass from '@/entities/client-contact/client-contact-details.component';
import ClientContactService from '@/entities/client-contact/client-contact.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ClientContact Management Detail Component', () => {
    let wrapper: Wrapper<ClientContactClass>;
    let comp: ClientContactClass;
    let clientContactServiceStub: SinonStubbedInstance<ClientContactService>;

    beforeEach(() => {
      clientContactServiceStub = sinon.createStubInstance<ClientContactService>(ClientContactService);

      wrapper = shallowMount<ClientContactClass>(ClientContactDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { clientContactService: () => clientContactServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundClientContact = { id: 123 };
        clientContactServiceStub.find.resolves(foundClientContact);

        // WHEN
        comp.retrieveClientContact(123);
        await comp.$nextTick();

        // THEN
        expect(comp.clientContact).toBe(foundClientContact);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundClientContact = { id: 123 };
        clientContactServiceStub.find.resolves(foundClientContact);

        // WHEN
        comp.beforeRouteEnter({ params: { clientContactId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.clientContact).toBe(foundClientContact);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
