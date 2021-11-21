/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import DeviceStatusComponent from '@/entities/device-status/device-status.vue';
import DeviceStatusClass from '@/entities/device-status/device-status.component';
import DeviceStatusService from '@/entities/device-status/device-status.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.component('jhi-sort-indicator', {});
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
  describe('DeviceStatus Management Component', () => {
    let wrapper: Wrapper<DeviceStatusClass>;
    let comp: DeviceStatusClass;
    let deviceStatusServiceStub: SinonStubbedInstance<DeviceStatusService>;

    beforeEach(() => {
      deviceStatusServiceStub = sinon.createStubInstance<DeviceStatusService>(DeviceStatusService);
      deviceStatusServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<DeviceStatusClass>(DeviceStatusComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          deviceStatusService: () => deviceStatusServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      deviceStatusServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllDeviceStatuss();
      await comp.$nextTick();

      // THEN
      expect(deviceStatusServiceStub.retrieve.called).toBeTruthy();
      expect(comp.deviceStatuses[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      deviceStatusServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(deviceStatusServiceStub.retrieve.called).toBeTruthy();
      expect(comp.deviceStatuses[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      deviceStatusServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(deviceStatusServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      deviceStatusServiceStub.retrieve.reset();
      deviceStatusServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(deviceStatusServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.deviceStatuses[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,asc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // GIVEN
      comp.propOrder = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,asc', 'id']);
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      deviceStatusServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeDeviceStatus();
      await comp.$nextTick();

      // THEN
      expect(deviceStatusServiceStub.delete.called).toBeTruthy();
      expect(deviceStatusServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
