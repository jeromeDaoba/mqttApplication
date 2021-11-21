import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICollect } from '@/shared/model/collect.model';
import CollectService from './collect.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class CollectDetails extends Vue {
  @Inject('collectService') private collectService: () => CollectService;
  @Inject('alertService') private alertService: () => AlertService;

  public collect: ICollect = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.collectId) {
        vm.retrieveCollect(to.params.collectId);
      }
    });
  }

  public retrieveCollect(collectId) {
    this.collectService()
      .find(collectId)
      .then(res => {
        this.collect = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
