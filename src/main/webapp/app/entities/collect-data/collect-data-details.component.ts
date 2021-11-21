import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICollectData } from '@/shared/model/collect-data.model';
import CollectDataService from './collect-data.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class CollectDataDetails extends Vue {
  @Inject('collectDataService') private collectDataService: () => CollectDataService;
  @Inject('alertService') private alertService: () => AlertService;

  public collectData: ICollectData = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.collectDataId) {
        vm.retrieveCollectData(to.params.collectDataId);
      }
    });
  }

  public retrieveCollectData(collectDataId) {
    this.collectDataService()
      .find(collectDataId)
      .then(res => {
        this.collectData = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
