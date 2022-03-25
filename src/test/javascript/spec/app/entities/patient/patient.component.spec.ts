/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import PatientComponent from '@/entities/patient/patient.vue';
import PatientClass from '@/entities/patient/patient.component';
import PatientService from '@/entities/patient/patient.service';
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
  describe('Patient Management Component', () => {
    let wrapper: Wrapper<PatientClass>;
    let comp: PatientClass;
    let patientServiceStub: SinonStubbedInstance<PatientService>;

    beforeEach(() => {
      patientServiceStub = sinon.createStubInstance<PatientService>(PatientService);
      patientServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<PatientClass>(PatientComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          patientService: () => patientServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      patientServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllPatients();
      await comp.$nextTick();

      // THEN
      expect(patientServiceStub.retrieve.called).toBeTruthy();
      expect(comp.patients[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      patientServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(patientServiceStub.retrieve.callCount).toEqual(1);

      comp.removePatient();
      await comp.$nextTick();

      // THEN
      expect(patientServiceStub.delete.called).toBeTruthy();
      expect(patientServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
