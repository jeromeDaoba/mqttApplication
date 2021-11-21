import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import { ICollect, Collect } from '@/shared/model/collect.model';
import CollectService from './collect.service';

const validations: any = {
  collect: {
    outSideId: {},
    name: {},
    time: {},
    data: {},
    projectId: {},
    channelCount: {},
    plusInterval: {},
    uploadInterval: {},
    hz: {},
    deviceNo: {},
  },
};

@Component({
  validations,
})
export default class CollectUpdate extends Vue {
  @Inject('collectService') private collectService: () => CollectService;
  @Inject('alertService') private alertService: () => AlertService;

  public collect: ICollect = new Collect();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.collectId) {
        vm.retrieveCollect(to.params.collectId);
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
    if (this.collect.id) {
      this.collectService()
        .update(this.collect)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('mqttApplicationApp.collect.updated', { param: param.id });
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
      this.collectService()
        .create(this.collect)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('mqttApplicationApp.collect.created', { param: param.id });
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

  public retrieveCollect(collectId): void {
    this.collectService()
      .find(collectId)
      .then(res => {
        this.collect = res;
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
