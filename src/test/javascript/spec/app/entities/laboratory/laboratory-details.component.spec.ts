/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import LaboratoryDetailComponent from '@/entities/laboratory/laboratory-details.vue';
import LaboratoryClass from '@/entities/laboratory/laboratory-details.component';
import LaboratoryService from '@/entities/laboratory/laboratory.service';
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
  describe('Laboratory Management Detail Component', () => {
    let wrapper: Wrapper<LaboratoryClass>;
    let comp: LaboratoryClass;
    let laboratoryServiceStub: SinonStubbedInstance<LaboratoryService>;

    beforeEach(() => {
      laboratoryServiceStub = sinon.createStubInstance<LaboratoryService>(LaboratoryService);

      wrapper = shallowMount<LaboratoryClass>(LaboratoryDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { laboratoryService: () => laboratoryServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundLaboratory = { id: 123 };
        laboratoryServiceStub.find.resolves(foundLaboratory);

        // WHEN
        comp.retrieveLaboratory(123);
        await comp.$nextTick();

        // THEN
        expect(comp.laboratory).toBe(foundLaboratory);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundLaboratory = { id: 123 };
        laboratoryServiceStub.find.resolves(foundLaboratory);

        // WHEN
        comp.beforeRouteEnter({ params: { laboratoryId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.laboratory).toBe(foundLaboratory);
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
