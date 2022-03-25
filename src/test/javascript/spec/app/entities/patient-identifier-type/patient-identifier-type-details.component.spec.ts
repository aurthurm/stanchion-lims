/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import PatientIdentifierTypeDetailComponent from '@/entities/patient-identifier-type/patient-identifier-type-details.vue';
import PatientIdentifierTypeClass from '@/entities/patient-identifier-type/patient-identifier-type-details.component';
import PatientIdentifierTypeService from '@/entities/patient-identifier-type/patient-identifier-type.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('PatientIdentifierType Management Detail Component', () => {
    let wrapper: Wrapper<PatientIdentifierTypeClass>;
    let comp: PatientIdentifierTypeClass;
    let patientIdentifierTypeServiceStub: SinonStubbedInstance<PatientIdentifierTypeService>;

    beforeEach(() => {
      patientIdentifierTypeServiceStub = sinon.createStubInstance<PatientIdentifierTypeService>(PatientIdentifierTypeService);

      wrapper = shallowMount<PatientIdentifierTypeClass>(PatientIdentifierTypeDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { patientIdentifierTypeService: () => patientIdentifierTypeServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPatientIdentifierType = { id: 123 };
        patientIdentifierTypeServiceStub.find.resolves(foundPatientIdentifierType);

        // WHEN
        comp.retrievePatientIdentifierType(123);
        await comp.$nextTick();

        // THEN
        expect(comp.patientIdentifierType).toBe(foundPatientIdentifierType);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPatientIdentifierType = { id: 123 };
        patientIdentifierTypeServiceStub.find.resolves(foundPatientIdentifierType);

        // WHEN
        comp.beforeRouteEnter({ params: { patientIdentifierTypeId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.patientIdentifierType).toBe(foundPatientIdentifierType);
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
