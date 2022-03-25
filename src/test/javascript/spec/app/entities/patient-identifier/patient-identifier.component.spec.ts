/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import PatientIdentifierComponent from '@/entities/patient-identifier/patient-identifier.vue';
import PatientIdentifierClass from '@/entities/patient-identifier/patient-identifier.component';
import PatientIdentifierService from '@/entities/patient-identifier/patient-identifier.service';
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
  describe('PatientIdentifier Management Component', () => {
    let wrapper: Wrapper<PatientIdentifierClass>;
    let comp: PatientIdentifierClass;
    let patientIdentifierServiceStub: SinonStubbedInstance<PatientIdentifierService>;

    beforeEach(() => {
      patientIdentifierServiceStub = sinon.createStubInstance<PatientIdentifierService>(PatientIdentifierService);
      patientIdentifierServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<PatientIdentifierClass>(PatientIdentifierComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          patientIdentifierService: () => patientIdentifierServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      patientIdentifierServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllPatientIdentifiers();
      await comp.$nextTick();

      // THEN
      expect(patientIdentifierServiceStub.retrieve.called).toBeTruthy();
      expect(comp.patientIdentifiers[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      patientIdentifierServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(patientIdentifierServiceStub.retrieve.callCount).toEqual(1);

      comp.removePatientIdentifier();
      await comp.$nextTick();

      // THEN
      expect(patientIdentifierServiceStub.delete.called).toBeTruthy();
      expect(patientIdentifierServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
