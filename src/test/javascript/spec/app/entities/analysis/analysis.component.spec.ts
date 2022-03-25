/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import AnalysisComponent from '@/entities/analysis/analysis.vue';
import AnalysisClass from '@/entities/analysis/analysis.component';
import AnalysisService from '@/entities/analysis/analysis.service';
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
  describe('Analysis Management Component', () => {
    let wrapper: Wrapper<AnalysisClass>;
    let comp: AnalysisClass;
    let analysisServiceStub: SinonStubbedInstance<AnalysisService>;

    beforeEach(() => {
      analysisServiceStub = sinon.createStubInstance<AnalysisService>(AnalysisService);
      analysisServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<AnalysisClass>(AnalysisComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          analysisService: () => analysisServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      analysisServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllAnalysiss();
      await comp.$nextTick();

      // THEN
      expect(analysisServiceStub.retrieve.called).toBeTruthy();
      expect(comp.analyses[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      analysisServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(analysisServiceStub.retrieve.callCount).toEqual(1);

      comp.removeAnalysis();
      await comp.$nextTick();

      // THEN
      expect(analysisServiceStub.delete.called).toBeTruthy();
      expect(analysisServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
