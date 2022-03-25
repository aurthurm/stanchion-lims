/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import JobHistoryComponent from '@/entities/job-history/job-history.vue';
import JobHistoryClass from '@/entities/job-history/job-history.component';
import JobHistoryService from '@/entities/job-history/job-history.service';
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
  describe('JobHistory Management Component', () => {
    let wrapper: Wrapper<JobHistoryClass>;
    let comp: JobHistoryClass;
    let jobHistoryServiceStub: SinonStubbedInstance<JobHistoryService>;

    beforeEach(() => {
      jobHistoryServiceStub = sinon.createStubInstance<JobHistoryService>(JobHistoryService);
      jobHistoryServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<JobHistoryClass>(JobHistoryComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          jobHistoryService: () => jobHistoryServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      jobHistoryServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllJobHistorys();
      await comp.$nextTick();

      // THEN
      expect(jobHistoryServiceStub.retrieve.called).toBeTruthy();
      expect(comp.jobHistories[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      jobHistoryServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(jobHistoryServiceStub.retrieve.callCount).toEqual(1);

      comp.removeJobHistory();
      await comp.$nextTick();

      // THEN
      expect(jobHistoryServiceStub.delete.called).toBeTruthy();
      expect(jobHistoryServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
