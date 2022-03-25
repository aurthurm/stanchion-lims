/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import AnalysisProfileUpdateComponent from '@/entities/analysis-profile/analysis-profile-update.vue';
import AnalysisProfileClass from '@/entities/analysis-profile/analysis-profile-update.component';
import AnalysisProfileService from '@/entities/analysis-profile/analysis-profile.service';

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
  describe('AnalysisProfile Management Update Component', () => {
    let wrapper: Wrapper<AnalysisProfileClass>;
    let comp: AnalysisProfileClass;
    let analysisProfileServiceStub: SinonStubbedInstance<AnalysisProfileService>;

    beforeEach(() => {
      analysisProfileServiceStub = sinon.createStubInstance<AnalysisProfileService>(AnalysisProfileService);

      wrapper = shallowMount<AnalysisProfileClass>(AnalysisProfileUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          analysisProfileService: () => analysisProfileServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.analysisProfile = entity;
        analysisProfileServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(analysisProfileServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.analysisProfile = entity;
        analysisProfileServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(analysisProfileServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundAnalysisProfile = { id: 123 };
        analysisProfileServiceStub.find.resolves(foundAnalysisProfile);
        analysisProfileServiceStub.retrieve.resolves([foundAnalysisProfile]);

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
