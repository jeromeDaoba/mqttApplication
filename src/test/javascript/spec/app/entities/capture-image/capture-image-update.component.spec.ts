/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import CaptureImageUpdateComponent from '@/entities/capture-image/capture-image-update.vue';
import CaptureImageClass from '@/entities/capture-image/capture-image-update.component';
import CaptureImageService from '@/entities/capture-image/capture-image.service';

import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('CaptureImage Management Update Component', () => {
    let wrapper: Wrapper<CaptureImageClass>;
    let comp: CaptureImageClass;
    let captureImageServiceStub: SinonStubbedInstance<CaptureImageService>;

    beforeEach(() => {
      captureImageServiceStub = sinon.createStubInstance<CaptureImageService>(CaptureImageService);

      wrapper = shallowMount<CaptureImageClass>(CaptureImageUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          captureImageService: () => captureImageServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.captureImage = entity;
        captureImageServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(captureImageServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.captureImage = entity;
        captureImageServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(captureImageServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundCaptureImage = { id: 123 };
        captureImageServiceStub.find.resolves(foundCaptureImage);
        captureImageServiceStub.retrieve.resolves([foundCaptureImage]);

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