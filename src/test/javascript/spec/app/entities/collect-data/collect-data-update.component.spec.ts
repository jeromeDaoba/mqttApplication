/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import CollectDataUpdateComponent from '@/entities/collect-data/collect-data-update.vue';
import CollectDataClass from '@/entities/collect-data/collect-data-update.component';
import CollectDataService from '@/entities/collect-data/collect-data.service';

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
  describe('CollectData Management Update Component', () => {
    let wrapper: Wrapper<CollectDataClass>;
    let comp: CollectDataClass;
    let collectDataServiceStub: SinonStubbedInstance<CollectDataService>;

    beforeEach(() => {
      collectDataServiceStub = sinon.createStubInstance<CollectDataService>(CollectDataService);

      wrapper = shallowMount<CollectDataClass>(CollectDataUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          collectDataService: () => collectDataServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.collectData = entity;
        collectDataServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(collectDataServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.collectData = entity;
        collectDataServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(collectDataServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundCollectData = { id: 123 };
        collectDataServiceStub.find.resolves(foundCollectData);
        collectDataServiceStub.retrieve.resolves([foundCollectData]);

        // WHEN
        comp.beforeRouteEnter({ params: { collectDataId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.collectData).toBe(foundCollectData);
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