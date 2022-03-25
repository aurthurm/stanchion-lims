/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import SampleComponent from '@/entities/sample/sample.vue';
import SampleClass from '@/entities/sample/sample.component';
import SampleService from '@/entities/sample/sample.service';
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
  describe('Sample Management Component', () => {
    let wrapper: Wrapper<SampleClass>;
    let comp: SampleClass;
    let sampleServiceStub: SinonStubbedInstance<SampleService>;

    beforeEach(() => {
      sampleServiceStub = sinon.createStubInstance<SampleService>(SampleService);
      sampleServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<SampleClass>(SampleComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          sampleService: () => sampleServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      sampleServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllSamples();
      await comp.$nextTick();

      // THEN
      expect(sampleServiceStub.retrieve.called).toBeTruthy();
      expect(comp.samples[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      sampleServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(sampleServiceStub.retrieve.callCount).toEqual(1);

      comp.removeSample();
      await comp.$nextTick();

      // THEN
      expect(sampleServiceStub.delete.called).toBeTruthy();
      expect(sampleServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
