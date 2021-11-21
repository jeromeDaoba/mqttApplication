/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import CollectUpdateComponent from '@/entities/collect/collect-update.vue';
import CollectClass from '@/entities/collect/collect-update.component';
import CollectService from '@/entities/collect/collect.service';

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
  describe('Collect Management Update Component', () => {
    let wrapper: Wrapper<CollectClass>;
    let comp: CollectClass;
    let collectServiceStub: SinonStubbedInstance<CollectService>;

    beforeEach(() => {
      collectServiceStub = sinon.createStubInstance<CollectService>(CollectService);

      wrapper = shallowMount<CollectClass>(CollectUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          collectService: () => collectServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.collect = entity;
        collectServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(collectServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.collect = entity;
        collectServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(collectServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundCollect = { id: 123 };
        collectServiceStub.find.resolves(foundCollect);
        collectServiceStub.retrieve.resolves([foundCollect]);

        // WHEN
        comp.beforeRouteEnter({ params: { collectId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.collect).toBe(foundCollect);
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
