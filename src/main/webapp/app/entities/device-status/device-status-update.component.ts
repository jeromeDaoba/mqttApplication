import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import { IDeviceStatus, DeviceStatus } from '@/shared/model/device-status.model';
import DeviceStatusService from './device-status.service';

const validations: any = {
  deviceStatus: {
    deviceNo: {},
    code: {},
    fourG: {},
    term: {},
    battery: {},
    batMode: {},
    curVersion: {},
    workMode: {},
  },
};

@Component({
  validations,
})
export default class DeviceStatusUpdate extends Vue {
  @Inject('deviceStatusService') private deviceStatusService: () => DeviceStatusService;
  @Inject('alertService') private alertService: () => AlertService;

  public deviceStatus: IDeviceStatus = new DeviceStatus();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.deviceStatusId) {
        vm.retrieveDeviceStatus(to.params.deviceStatusId);
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
    if (this.deviceStatus.id) {
      this.deviceStatusService()
        .update(this.deviceStatus)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('mqttApplicationApp.deviceStatus.updated', { param: param.id });
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
      this.deviceStatusService()
        .create(this.deviceStatus)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('mqttApplicationApp.deviceStatus.created', { param: param.id });
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

  public retrieveDeviceStatus(deviceStatusId): void {
    this.deviceStatusService()
      .find(deviceStatusId)
      .then(res => {
        this.deviceStatus = res;
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
