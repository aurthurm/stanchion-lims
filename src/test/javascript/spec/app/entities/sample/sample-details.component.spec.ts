/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import SampleDetailComponent from '@/entities/sample/sample-details.vue';
import SampleClass from '@/entities/sample/sample-details.component';
import SampleService from '@/entities/sample/sample.service';
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
  describe('Sample Management Detail Component', () => {
    let wrapper: Wrapper<SampleClass>;
    let comp: SampleClass;
    let sampleServiceStub: SinonStubbedInstance<SampleService>;

    beforeEach(() => {
      sampleServiceStub = sinon.createStubInstance<SampleService>(SampleService);

      wrapper = shallowMount<SampleClass>(SampleDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { sampleService: () => sampleServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundSample = { id: 123 };
        sampleServiceStub.find.resolves(foundSample);

        // WHEN
        comp.retrieveSample(123);
        await comp.$nextTick();

        // THEN
        expect(comp.sample).toBe(foundSample);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundSample = { id: 123 };
        sampleServiceStub.find.resolves(foundSample);

        // WHEN
        comp.beforeRouteEnter({ params: { sampleId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.sample).toBe(foundSample);
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
