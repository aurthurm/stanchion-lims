/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import ProvinceComponent from '@/entities/province/province.vue';
import ProvinceClass from '@/entities/province/province.component';
import ProvinceService from '@/entities/province/province.service';
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
  describe('Province Management Component', () => {
    let wrapper: Wrapper<ProvinceClass>;
    let comp: ProvinceClass;
    let provinceServiceStub: SinonStubbedInstance<ProvinceService>;

    beforeEach(() => {
      provinceServiceStub = sinon.createStubInstance<ProvinceService>(ProvinceService);
      provinceServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ProvinceClass>(ProvinceComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          provinceService: () => provinceServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      provinceServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllProvinces();
      await comp.$nextTick();

      // THEN
      expect(provinceServiceStub.retrieve.called).toBeTruthy();
      expect(comp.provinces[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      provinceServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(provinceServiceStub.retrieve.callCount).toEqual(1);

      comp.removeProvince();
      await comp.$nextTick();

      // THEN
      expect(provinceServiceStub.delete.called).toBeTruthy();
      expect(provinceServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
