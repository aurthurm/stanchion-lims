/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import PatientIdentifierTypeComponent from '@/entities/patient-identifier-type/patient-identifier-type.vue';
import PatientIdentifierTypeClass from '@/entities/patient-identifier-type/patient-identifier-type.component';
import PatientIdentifierTypeService from '@/entities/patient-identifier-type/patient-identifier-type.service';
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
  describe('PatientIdentifierType Management Component', () => {
    let wrapper: Wrapper<PatientIdentifierTypeClass>;
    let comp: PatientIdentifierTypeClass;
    let patientIdentifierTypeServiceStub: SinonStubbedInstance<PatientIdentifierTypeService>;

    beforeEach(() => {
      patientIdentifierTypeServiceStub = sinon.createStubInstance<PatientIdentifierTypeService>(PatientIdentifierTypeService);
      patientIdentifierTypeServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<PatientIdentifierTypeClass>(PatientIdentifierTypeComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          patientIdentifierTypeService: () => patientIdentifierTypeServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      patientIdentifierTypeServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllPatientIdentifierTypes();
      await comp.$nextTick();

      // THEN
      expect(patientIdentifierTypeServiceStub.retrieve.called).toBeTruthy();
      expect(comp.patientIdentifierTypes[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      patientIdentifierTypeServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(patientIdentifierTypeServiceStub.retrieve.callCount).toEqual(1);

      comp.removePatientIdentifierType();
      await comp.$nextTick();

      // THEN
      expect(patientIdentifierTypeServiceStub.delete.called).toBeTruthy();
      expect(patientIdentifierTypeServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
