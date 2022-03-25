/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import SampleTypeComponent from '@/entities/sample-type/sample-type.vue';
import SampleTypeClass from '@/entities/sample-type/sample-type.component';
import SampleTypeService from '@/entities/sample-type/sample-type.service';
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
  describe('SampleType Management Component', () => {
    let wrapper: Wrapper<SampleTypeClass>;
    let comp: SampleTypeClass;
    let sampleTypeServiceStub: SinonStubbedInstance<SampleTypeService>;

    beforeEach(() => {
      sampleTypeServiceStub = sinon.createStubInstance<SampleTypeService>(SampleTypeService);
      sampleTypeServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<SampleTypeClass>(SampleTypeComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          sampleTypeService: () => sampleTypeServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      sampleTypeServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllSampleTypes();
      await comp.$nextTick();

      // THEN
      expect(sampleTypeServiceStub.retrieve.called).toBeTruthy();
      expect(comp.sampleTypes[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      sampleTypeServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(sampleTypeServiceStub.retrieve.callCount).toEqual(1);

      comp.removeSampleType();
      await comp.$nextTick();

      // THEN
      expect(sampleTypeServiceStub.delete.called).toBeTruthy();
      expect(sampleTypeServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
