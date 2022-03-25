/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import AnalysisServiceUpdateComponent from '@/entities/analysis-service/analysis-service-update.vue';
import AnalysisServiceClass from '@/entities/analysis-service/analysis-service-update.component';
import AnalysisServiceService from '@/entities/analysis-service/analysis-service.service';

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
  describe('AnalysisService Management Update Component', () => {
    let wrapper: Wrapper<AnalysisServiceClass>;
    let comp: AnalysisServiceClass;
    let analysisServiceServiceStub: SinonStubbedInstance<AnalysisServiceService>;

    beforeEach(() => {
      analysisServiceServiceStub = sinon.createStubInstance<AnalysisServiceService>(AnalysisServiceService);

      wrapper = shallowMount<AnalysisServiceClass>(AnalysisServiceUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          analysisServiceService: () => analysisServiceServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.analysisService = entity;
        analysisServiceServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(analysisServiceServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.analysisService = entity;
        analysisServiceServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(analysisServiceServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundAnalysisService = { id: 123 };
        analysisServiceServiceStub.find.resolves(foundAnalysisService);
        analysisServiceServiceStub.retrieve.resolves([foundAnalysisService]);

        // WHEN
        comp.beforeRouteEnter({ params: { analysisServiceId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.analysisService).toBe(foundAnalysisService);
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
