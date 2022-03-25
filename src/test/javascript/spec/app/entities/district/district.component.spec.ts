/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import DistrictComponent from '@/entities/district/district.vue';
import DistrictClass from '@/entities/district/district.component';
import DistrictService from '@/entities/district/district.service';
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
  describe('District Management Component', () => {
    let wrapper: Wrapper<DistrictClass>;
    let comp: DistrictClass;
    let districtServiceStub: SinonStubbedInstance<DistrictService>;

    beforeEach(() => {
      districtServiceStub = sinon.createStubInstance<DistrictService>(DistrictService);
      districtServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<DistrictClass>(DistrictComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          districtService: () => districtServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      districtServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllDistricts();
      await comp.$nextTick();

      // THEN
      expect(districtServiceStub.retrieve.called).toBeTruthy();
      expect(comp.districts[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      districtServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(districtServiceStub.retrieve.callCount).toEqual(1);

      comp.removeDistrict();
      await comp.$nextTick();

      // THEN
      expect(districtServiceStub.delete.called).toBeTruthy();
      expect(districtServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
