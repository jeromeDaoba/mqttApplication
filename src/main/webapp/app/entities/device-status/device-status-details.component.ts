import { Component, Vue, Inject } from 'vue-property-decorator';

import { IDeviceStatus } from '@/shared/model/device-status.model';
import DeviceStatusService from './device-status.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class DeviceStatusDetails extends Vue {
  @Inject('deviceStatusService') private deviceStatusService: () => DeviceStatusService;
  @Inject('alertService') private alertService: () => AlertService;

  public deviceStatus: IDeviceStatus = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.deviceStatusId) {
        vm.retrieveDeviceStatus(to.params.deviceStatusId);
      }
    });
  }

  public retrieveDeviceStatus(deviceStatusId) {
    this.deviceStatusService()
      .find(deviceStatusId)
      .then(res => {
        this.deviceStatus = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
