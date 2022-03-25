/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import AnalysisDetailComponent from '@/entities/analysis/analysis-details.vue';
import AnalysisClass from '@/entities/analysis/analysis-details.component';
import AnalysisService from '@/entities/analysis/analysis.service';
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
  describe('Analysis Management Detail Component', () => {
    let wrapper: Wrapper<AnalysisClass>;
    let comp: AnalysisClass;
    let analysisServiceStub: SinonStubbedInstance<AnalysisService>;

    beforeEach(() => {
      analysisServiceStub = sinon.createStubInstance<AnalysisService>(AnalysisService);

      wrapper = shallowMount<AnalysisClass>(AnalysisDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { analysisService: () => analysisServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundAnalysis = { id: 123 };
        analysisServiceStub.find.resolves(foundAnalysis);

        // WHEN
        comp.retrieveAnalysis(123);
        await comp.$nextTick();

        // THEN
        expect(comp.analysis).toBe(foundAnalysis);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundAnalysis = { id: 123 };
        analysisServiceStub.find.resolves(foundAnalysis);

        // WHEN
        comp.beforeRouteEnter({ params: { analysisId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.analysis).toBe(foundAnalysis);
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
