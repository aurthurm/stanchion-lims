/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import AnalysisProfileDetailComponent from '@/entities/analysis-profile/analysis-profile-details.vue';
import AnalysisProfileClass from '@/entities/analysis-profile/analysis-profile-details.component';
import AnalysisProfileService from '@/entities/analysis-profile/analysis-profile.service';
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
  describe('AnalysisProfile Management Detail Component', () => {
    let wrapper: Wrapper<AnalysisProfileClass>;
    let comp: AnalysisProfileClass;
    let analysisProfileServiceStub: SinonStubbedInstance<AnalysisProfileService>;

    beforeEach(() => {
      analysisProfileServiceStub = sinon.createStubInstance<AnalysisProfileService>(AnalysisProfileService);

      wrapper = shallowMount<AnalysisProfileClass>(AnalysisProfileDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { analysisProfileService: () => analysisProfileServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundAnalysisProfile = { id: 123 };
        analysisProfileServiceStub.find.resolves(foundAnalysisProfile);

        // WHEN
        comp.retrieveAnalysisProfile(123);
        await comp.$nextTick();

        // THEN
        expect(comp.analysisProfile).toBe(foundAnalysisProfile);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundAnalysisProfile = { id: 123 };
        analysisProfileServiceStub.find.resolves(foundAnalysisProfile);

        // WHEN
        comp.beforeRouteEnter({ params: { analysisProfileId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.analysisProfile).toBe(foundAnalysisProfile);
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
