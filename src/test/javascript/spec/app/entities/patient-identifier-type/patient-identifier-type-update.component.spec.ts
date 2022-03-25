/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import PatientIdentifierTypeUpdateComponent from '@/entities/patient-identifier-type/patient-identifier-type-update.vue';
import PatientIdentifierTypeClass from '@/entities/patient-identifier-type/patient-identifier-type-update.component';
import PatientIdentifierTypeService from '@/entities/patient-identifier-type/patient-identifier-type.service';

import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.use(ToastPlugin);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('PatientIdentifierType Management Update Component', () => {
    let wrapper: Wrapper<PatientIdentifierTypeClass>;
    let comp: PatientIdentifierTypeClass;
    let patientIdentifierTypeServiceStub: SinonStubbedInstance<PatientIdentifierTypeService>;

    beforeEach(() => {
      patientIdentifierTypeServiceStub = sinon.createStubInstance<PatientIdentifierTypeService>(PatientIdentifierTypeService);

      wrapper = shallowMount<PatientIdentifierTypeClass>(PatientIdentifierTypeUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          patientIdentifierTypeService: () => patientIdentifierTypeServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.patientIdentifierType = entity;
        patientIdentifierTypeServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(patientIdentifierTypeServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.patientIdentifierType = entity;
        patientIdentifierTypeServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(patientIdentifierTypeServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPatientIdentifierType = { id: 123 };
        patientIdentifierTypeServiceStub.find.resolves(foundPatientIdentifierType);
        patientIdentifierTypeServiceStub.retrieve.resolves([foundPatientIdentifierType]);

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
