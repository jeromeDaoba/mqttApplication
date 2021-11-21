/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import CollectDataDetailComponent from '@/entities/collect-data/collect-data-details.vue';
import CollectDataClass from '@/entities/collect-data/collect-data-details.component';
import CollectDataService from '@/entities/collect-data/collect-data.service';
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
  describe('CollectData Management Detail Component', () => {
    let wrapper: Wrapper<CollectDataClass>;
    let comp: CollectDataClass;
    let collectDataServiceStub: SinonStubbedInstance<CollectDataService>;

    beforeEach(() => {
      collectDataServiceStub = sinon.createStubInstance<CollectDataService>(CollectDataService);

      wrapper = shallowMount<CollectDataClass>(CollectDataDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { collectDataService: () => collectDataServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCollectData = { id: 123 };
        collectDataServiceStub.find.resolves(foundCollectData);

        // WHEN
        comp.retrieveCollectData(123);
        await comp.$nextTick();

        // THEN
        expect(comp.collectData).toBe(foundCollectData);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundCollectData = { id: 123 };
        collectDataServiceStub.find.resolves(foundCollectData);

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
