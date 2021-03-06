import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import { IStation, Station } from '@/shared/model/station.model';
import StationService from './station.service';

const validations: any = {
  station: {
    deviceNo: {},
    captureTime: {},
    bottomRight: {},
    cValue: {},
    dValue: {},
    errorCode: {},
    index: {},
    objParaX: {},
    objParaY: {},
    objPosX: {},
    objPosY: {},
    topLeft: {},
    wParam: {},
  },
};

@Component({
  validations,
})
export default class StationUpdate extends Vue {
  @Inject('stationService') private stationService: () => StationService;
  @Inject('alertService') private alertService: () => AlertService;

  public station: IStation = new Station();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.stationId) {
        vm.retrieveStation(to.params.stationId);
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
    if (this.station.id) {
      this.stationService()
        .update(this.station)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('mqttApplicationApp.station.updated', { param: param.id });
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
      this.stationService()
        .create(this.station)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('mqttApplicationApp.station.created', { param: param.id });
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

  public retrieveStation(stationId): void {
    this.stationService()
      .find(stationId)
      .then(res => {
        this.station = res;
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
