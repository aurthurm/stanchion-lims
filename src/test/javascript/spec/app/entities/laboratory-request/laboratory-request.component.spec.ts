/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import LaboratoryRequestComponent from '@/entities/laboratory-request/laboratory-request.vue';
import LaboratoryRequestClass from '@/entities/laboratory-request/laboratory-request.component';
import LaboratoryRequestService from '@/entities/laboratory-request/laboratory-request.service';
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
  describe('LaboratoryRequest Management Component', () => {
    let wrapper: Wrapper<LaboratoryRequestClass>;
    let comp: LaboratoryRequestClass;
    let laboratoryRequestServiceStub: SinonStubbedInstance<LaboratoryRequestService>;

    beforeEach(() => {
      laboratoryRequestServiceStub = sinon.createStubInstance<LaboratoryRequestService>(LaboratoryRequestService);
      laboratoryRequestServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<LaboratoryRequestClass>(LaboratoryRequestComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          laboratoryRequestService: () => laboratoryRequestServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      laboratoryRequestServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllLaboratoryRequests();
      await comp.$nextTick();

      // THEN
      expect(laboratoryRequestServiceStub.retrieve.called).toBeTruthy();
      expect(comp.laboratoryRequests[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      laboratoryRequestServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(laboratoryRequestServiceStub.retrieve.callCount).toEqual(1);

      comp.removeLaboratoryRequest();
      await comp.$nextTick();

      // THEN
      expect(laboratoryRequestServiceStub.delete.called).toBeTruthy();
      expect(laboratoryRequestServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
