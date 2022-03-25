/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import LaboratoryUpdateComponent from '@/entities/laboratory/laboratory-update.vue';
import LaboratoryClass from '@/entities/laboratory/laboratory-update.component';
import LaboratoryService from '@/entities/laboratory/laboratory.service';

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
  describe('Laboratory Management Update Component', () => {
    let wrapper: Wrapper<LaboratoryClass>;
    let comp: LaboratoryClass;
    let laboratoryServiceStub: SinonStubbedInstance<LaboratoryService>;

    beforeEach(() => {
      laboratoryServiceStub = sinon.createStubInstance<LaboratoryService>(LaboratoryService);

      wrapper = shallowMount<LaboratoryClass>(LaboratoryUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          laboratoryService: () => laboratoryServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.laboratory = entity;
        laboratoryServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(laboratoryServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.laboratory = entity;
        laboratoryServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(laboratoryServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundLaboratory = { id: 123 };
        laboratoryServiceStub.find.resolves(foundLaboratory);
        laboratoryServiceStub.retrieve.resolves([foundLaboratory]);

        // WHEN
        comp.beforeRouteEnter({ params: { laboratoryId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.laboratory).toBe(foundLaboratory);
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
