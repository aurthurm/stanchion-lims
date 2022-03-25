/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import PatientIdentifierUpdateComponent from '@/entities/patient-identifier/patient-identifier-update.vue';
import PatientIdentifierClass from '@/entities/patient-identifier/patient-identifier-update.component';
import PatientIdentifierService from '@/entities/patient-identifier/patient-identifier.service';

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
  describe('PatientIdentifier Management Update Component', () => {
    let wrapper: Wrapper<PatientIdentifierClass>;
    let comp: PatientIdentifierClass;
    let patientIdentifierServiceStub: SinonStubbedInstance<PatientIdentifierService>;

    beforeEach(() => {
      patientIdentifierServiceStub = sinon.createStubInstance<PatientIdentifierService>(PatientIdentifierService);

      wrapper = shallowMount<PatientIdentifierClass>(PatientIdentifierUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          patientIdentifierService: () => patientIdentifierServiceStub,
          alertService: () => new AlertService(),

          patientIdentifierTypeService: () =>
            sinon.createStubInstance<PatientIdentifierTypeService>(PatientIdentifierTypeService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.patientIdentifier = entity;
        patientIdentifierServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(patientIdentifierServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.patientIdentifier = entity;
        patientIdentifierServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(patientIdentifierServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPatientIdentifier = { id: 123 };
        patientIdentifierServiceStub.find.resolves(foundPatientIdentifier);
        patientIdentifierServiceStub.retrieve.resolves([foundPatientIdentifier]);

        // WHEN
        comp.beforeRouteEnter({ params: { patientIdentifierId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.patientIdentifier).toBe(foundPatientIdentifier);
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
