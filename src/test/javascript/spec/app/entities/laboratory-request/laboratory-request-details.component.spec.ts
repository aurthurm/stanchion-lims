/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import LaboratoryRequestDetailComponent from '@/entities/laboratory-request/laboratory-request-details.vue';
import LaboratoryRequestClass from '@/entities/laboratory-request/laboratory-request-details.component';
import LaboratoryRequestService from '@/entities/laboratory-request/laboratory-request.service';
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
  describe('LaboratoryRequest Management Detail Component', () => {
    let wrapper: Wrapper<LaboratoryRequestClass>;
    let comp: LaboratoryRequestClass;
    let laboratoryRequestServiceStub: SinonStubbedInstance<LaboratoryRequestService>;

    beforeEach(() => {
      laboratoryRequestServiceStub = sinon.createStubInstance<LaboratoryRequestService>(LaboratoryRequestService);

      wrapper = shallowMount<LaboratoryRequestClass>(LaboratoryRequestDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { laboratoryRequestService: () => laboratoryRequestServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundLaboratoryRequest = { id: 123 };
        laboratoryRequestServiceStub.find.resolves(foundLaboratoryRequest);

        // WHEN
        comp.retrieveLaboratoryRequest(123);
        await comp.$nextTick();

        // THEN
        expect(comp.laboratoryRequest).toBe(foundLaboratoryRequest);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundLaboratoryRequest = { id: 123 };
        laboratoryRequestServiceStub.find.resolves(foundLaboratoryRequest);

        // WHEN
        comp.beforeRouteEnter({ params: { laboratoryRequestId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.laboratoryRequest).toBe(foundLaboratoryRequest);
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
