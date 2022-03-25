/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import AnalysisServiceDetailComponent from '@/entities/analysis-service/analysis-service-details.vue';
import AnalysisServiceClass from '@/entities/analysis-service/analysis-service-details.component';
import AnalysisServiceService from '@/entities/analysis-service/analysis-service.service';
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
  describe('AnalysisService Management Detail Component', () => {
    let wrapper: Wrapper<AnalysisServiceClass>;
    let comp: AnalysisServiceClass;
    let analysisServiceServiceStub: SinonStubbedInstance<AnalysisServiceService>;

    beforeEach(() => {
      analysisServiceServiceStub = sinon.createStubInstance<AnalysisServiceService>(AnalysisServiceService);

      wrapper = shallowMount<AnalysisServiceClass>(AnalysisServiceDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { analysisServiceService: () => analysisServiceServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundAnalysisService = { id: 123 };
        analysisServiceServiceStub.find.resolves(foundAnalysisService);

        // WHEN
        comp.retrieveAnalysisService(123);
        await comp.$nextTick();

        // THEN
        expect(comp.analysisService).toBe(foundAnalysisService);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundAnalysisService = { id: 123 };
        analysisServiceServiceStub.find.resolves(foundAnalysisService);

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
