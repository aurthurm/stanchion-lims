/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ProvinceDetailComponent from '@/entities/province/province-details.vue';
import ProvinceClass from '@/entities/province/province-details.component';
import ProvinceService from '@/entities/province/province.service';
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
  describe('Province Management Detail Component', () => {
    let wrapper: Wrapper<ProvinceClass>;
    let comp: ProvinceClass;
    let provinceServiceStub: SinonStubbedInstance<ProvinceService>;

    beforeEach(() => {
      provinceServiceStub = sinon.createStubInstance<ProvinceService>(ProvinceService);

      wrapper = shallowMount<ProvinceClass>(ProvinceDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { provinceService: () => provinceServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundProvince = { id: 123 };
        provinceServiceStub.find.resolves(foundProvince);

        // WHEN
        comp.retrieveProvince(123);
        await comp.$nextTick();

        // THEN
        expect(comp.province).toBe(foundProvince);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundProvince = { id: 123 };
        provinceServiceStub.find.resolves(foundProvince);

        // WHEN
        comp.beforeRouteEnter({ params: { provinceId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.province).toBe(foundProvince);
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
