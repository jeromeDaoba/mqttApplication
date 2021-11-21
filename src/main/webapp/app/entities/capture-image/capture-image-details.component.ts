import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICaptureImage } from '@/shared/model/capture-image.model';
import CaptureImageService from './capture-image.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class CaptureImageDetails extends Vue {
  @Inject('captureImageService') private captureImageService: () => CaptureImageService;
  @Inject('alertService') private alertService: () => AlertService;

  public captureImage: ICaptureImage = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.captureImageId) {
        vm.retrieveCaptureImage(to.params.captureImageId);
      }
    });
  }

  public retrieveCaptureImage(captureImageId) {
    this.captureImageService()
      .find(captureImageId)
      .then(res => {
        this.captureImage = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
