import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import { ICollectData, CollectData } from '@/shared/model/collect-data.model';
import CollectDataService from './collect-data.service';

const validations: any = {
  collectData: {
    deviceNo: {},
    time: {},
    xVal: {},
    yVal: {},
    allVals: {},
  },
};

@Component({
  validations,
})
export default class CollectDataUpdate extends Vue {
  @Inject('collectDataService') private collectDataService: () => CollectDataService;
  @Inject('alertService') private alertService: () => AlertService;

  public collectData: ICollectData = new CollectData();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.collectDataId) {
        vm.retrieveCollectData(to.params.collectDataId);
      }
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.collectData.id) {
      this.collectDataService()
        .update(this.collectData)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('mqttApplicationApp.collectData.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.collectDataService()
        .create(this.collectData)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('mqttApplicationApp.collectData.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public retrieveCollectData(collectDataId): void {
    this.collectDataService()
      .find(collectDataId)
      .then(res => {
        this.collectData = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
