/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import CaptureImageDetailComponent from '@/entities/capture-image/capture-image-details.vue';
import CaptureImageClass from '@/entities/capture-image/capture-image-details.component';
import CaptureImageService from '@/entities/capture-image/capture-image.service';
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
  describe('CaptureImage Management Detail Component', () => {
    let wrapper: Wrapper<CaptureImageClass>;
    let comp: CaptureImageClass;
    let captureImageServiceStub: SinonStubbedInstance<CaptureImageService>;

    beforeEach(() => {
      captureImageServiceStub = sinon.createStubInstance<CaptureImageService>(CaptureImageService);

      wrapper = shallowMount<CaptureImageClass>(CaptureImageDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { captureImageService: () => captureImageServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCaptureImage = { id: 123 };
        captureImageServiceStub.find.resolves(foundCaptureImage);

        // WHEN
        comp.retrieveCaptureImage(123);
        await comp.$nextTick();

        // THEN
        expect(comp.captureImage).toBe(foundCaptureImage);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundCaptureImage = { id: 123 };
        captureImageServiceStub.find.resolves(foundCaptureImage);

        // WHEN
        comp.beforeRouteEnter({ params: { captureImageId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.captureImage).toBe(foundCaptureImage);
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
