/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import ClientContactUpdateComponent from '@/entities/client-contact/client-contact-update.vue';
import ClientContactClass from '@/entities/client-contact/client-contact-update.component';
import ClientContactService from '@/entities/client-contact/client-contact.service';

import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.use(ToastPlugin);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('ClientContact Management Update Component', () => {
    let wrapper: Wrapper<ClientContactClass>;
    let comp: ClientContactClass;
    let clientContactServiceStub: SinonStubbedInstance<ClientContactService>;

    beforeEach(() => {
      clientContactServiceStub = sinon.createStubInstance<ClientContactService>(ClientContactService);

      wrapper = shallowMount<ClientContactClass>(ClientContactUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          clientContactService: () => clientContactServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.clientContact = entity;
        clientContactServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(clientContactServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.clientContact = entity;
        clientContactServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(clientContactServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundClientContact = { id: 123 };
        clientContactServiceStub.find.resolves(foundClientContact);
        clientContactServiceStub.retrieve.resolves([foundClientContact]);

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
