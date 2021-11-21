/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import CollectDetailComponent from '@/entities/collect/collect-details.vue';
import CollectClass from '@/entities/collect/collect-details.component';
import CollectService from '@/entities/collect/collect.service';
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
  describe('Collect Management Detail Component', () => {
    let wrapper: Wrapper<CollectClass>;
    let comp: CollectClass;
    let collectServiceStub: SinonStubbedInstance<CollectService>;

    beforeEach(() => {
      collectServiceStub = sinon.createStubInstance<CollectService>(CollectService);

      wrapper = shallowMount<CollectClass>(CollectDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { collectService: () => collectServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCollect = { id: 123 };
        collectServiceStub.find.resolves(foundCollect);

        // WHEN
        comp.retrieveCollect(123);
        await comp.$nextTick();

        // THEN
        expect(comp.collect).toBe(foundCollect);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundCollect = { id: 123 };
        collectServiceStub.find.resolves(foundCollect);

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
