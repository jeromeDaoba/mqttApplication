import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import { ICaptureImage, CaptureImage } from '@/shared/model/capture-image.model';
import CaptureImageService from './capture-image.service';

const validations: any = {
  captureImage: {
    deviceNo: {},
    captureTime: {},
    format: {},
    hParam: {},
    image: {},
    msgId: {},
  },
};

@Component({
  validations,
})
export default class CaptureImageUpdate extends Vue {
  @Inject('captureImageService') private captureImageService: () => CaptureImageService;
  @Inject('alertService') private alertService: () => AlertService;

  public captureImage: ICaptureImage = new CaptureImage();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.captureImageId) {
        vm.retrieveCaptureImage(to.params.captureImageId);
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
    if (this.captureImage.id) {
      this.captureImageService()
        .update(this.captureImage)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('mqttApplicationApp.captureImage.updated', { param: param.id });
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
      this.captureImageService()
        .create(this.captureImage)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('mqttApplicationApp.captureImage.created', { param: param.id });
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

  public retrieveCaptureImage(captureImageId): void {
    this.captureImageService()
      .find(captureImageId)
      .then(res => {
        this.captureImage = res;
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
