/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import SampleTypeUpdateComponent from '@/entities/sample-type/sample-type-update.vue';
import SampleTypeClass from '@/entities/sample-type/sample-type-update.component';
import SampleTypeService from '@/entities/sample-type/sample-type.service';

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
  describe('SampleType Management Update Component', () => {
    let wrapper: Wrapper<SampleTypeClass>;
    let comp: SampleTypeClass;
    let sampleTypeServiceStub: SinonStubbedInstance<SampleTypeService>;

    beforeEach(() => {
      sampleTypeServiceStub = sinon.createStubInstance<SampleTypeService>(SampleTypeService);

      wrapper = shallowMount<SampleTypeClass>(SampleTypeUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          sampleTypeService: () => sampleTypeServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.sampleType = entity;
        sampleTypeServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(sampleTypeServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.sampleType = entity;
        sampleTypeServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(sampleTypeServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundSampleType = { id: 123 };
        sampleTypeServiceStub.find.resolves(foundSampleType);
        sampleTypeServiceStub.retrieve.resolves([foundSampleType]);

        // WHEN
        comp.beforeRouteEnter({ params: { sampleTypeId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.sampleType).toBe(foundSampleType);
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
