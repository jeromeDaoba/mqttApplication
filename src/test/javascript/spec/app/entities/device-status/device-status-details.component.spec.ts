/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import DeviceStatusDetailComponent from '@/entities/device-status/device-status-details.vue';
import DeviceStatusClass from '@/entities/device-status/device-status-details.component';
import DeviceStatusService from '@/entities/device-status/device-status.service';
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
  describe('DeviceStatus Management Detail Component', () => {
    let wrapper: Wrapper<DeviceStatusClass>;
    let comp: DeviceStatusClass;
    let deviceStatusServiceStub: SinonStubbedInstance<DeviceStatusService>;

    beforeEach(() => {
      deviceStatusServiceStub = sinon.createStubInstance<DeviceStatusService>(DeviceStatusService);

      wrapper = shallowMount<DeviceStatusClass>(DeviceStatusDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { deviceStatusService: () => deviceStatusServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundDeviceStatus = { id: 123 };
        deviceStatusServiceStub.find.resolves(foundDeviceStatus);

        // WHEN
        comp.retrieveDeviceStatus(123);
        await comp.$nextTick();

        // THEN
        expect(comp.deviceStatus).toBe(foundDeviceStatus);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundDeviceStatus = { id: 123 };
        deviceStatusServiceStub.find.resolves(foundDeviceStatus);

        // WHEN
        comp.beforeRouteEnter({ params: { deviceStatusId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.deviceStatus).toBe(foundDeviceStatus);
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
