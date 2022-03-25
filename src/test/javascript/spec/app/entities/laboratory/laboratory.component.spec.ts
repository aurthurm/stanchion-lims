/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import LaboratoryComponent from '@/entities/laboratory/laboratory.vue';
import LaboratoryClass from '@/entities/laboratory/laboratory.component';
import LaboratoryService from '@/entities/laboratory/laboratory.service';
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
  describe('Laboratory Management Component', () => {
    let wrapper: Wrapper<LaboratoryClass>;
    let comp: LaboratoryClass;
    let laboratoryServiceStub: SinonStubbedInstance<LaboratoryService>;

    beforeEach(() => {
      laboratoryServiceStub = sinon.createStubInstance<LaboratoryService>(LaboratoryService);
      laboratoryServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<LaboratoryClass>(LaboratoryComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          laboratoryService: () => laboratoryServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      laboratoryServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllLaboratorys();
      await comp.$nextTick();

      // THEN
      expect(laboratoryServiceStub.retrieve.called).toBeTruthy();
      expect(comp.laboratories[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      laboratoryServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(laboratoryServiceStub.retrieve.callCount).toEqual(1);

      comp.removeLaboratory();
      await comp.$nextTick();

      // THEN
      expect(laboratoryServiceStub.delete.called).toBeTruthy();
      expect(laboratoryServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
