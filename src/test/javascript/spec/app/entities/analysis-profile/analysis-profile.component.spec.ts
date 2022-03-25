/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import AnalysisProfileComponent from '@/entities/analysis-profile/analysis-profile.vue';
import AnalysisProfileClass from '@/entities/analysis-profile/analysis-profile.component';
import AnalysisProfileService from '@/entities/analysis-profile/analysis-profile.service';
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
  describe('AnalysisProfile Management Component', () => {
    let wrapper: Wrapper<AnalysisProfileClass>;
    let comp: AnalysisProfileClass;
    let analysisProfileServiceStub: SinonStubbedInstance<AnalysisProfileService>;

    beforeEach(() => {
      analysisProfileServiceStub = sinon.createStubInstance<AnalysisProfileService>(AnalysisProfileService);
      analysisProfileServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<AnalysisProfileClass>(AnalysisProfileComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          analysisProfileService: () => analysisProfileServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      analysisProfileServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllAnalysisProfiles();
      await comp.$nextTick();

      // THEN
      expect(analysisProfileServiceStub.retrieve.called).toBeTruthy();
      expect(comp.analysisProfiles[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      analysisProfileServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(analysisProfileServiceStub.retrieve.callCount).toEqual(1);

      comp.removeAnalysisProfile();
      await comp.$nextTick();

      // THEN
      expect(analysisProfileServiceStub.delete.called).toBeTruthy();
      expect(analysisProfileServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
