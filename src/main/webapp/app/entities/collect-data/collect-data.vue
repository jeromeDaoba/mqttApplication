<template>
  <div>
    <h2 id="page-heading" data-cy="CollectDataHeading">
      <span v-text="$t('mqttApplicationApp.collectData.home.title')" id="collect-data-heading">Collect Data</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('mqttApplicationApp.collectData.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'CollectDataCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-collect-data"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('mqttApplicationApp.collectData.home.createLabel')"> Create a new Collect Data </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && collectData && collectData.length === 0">
      <span v-text="$t('mqttApplicationApp.collectData.home.notFound')">No collectData found</span>
    </div>
    <div class="table-responsive" v-if="collectData && collectData.length > 0">
      <table class="table table-striped" aria-describedby="collectData">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('deviceNo')">
              <span v-text="$t('mqttApplicationApp.collectData.deviceNo')">Device No</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'deviceNo'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('time')">
              <span v-text="$t('mqttApplicationApp.collectData.time')">Time</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'time'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('xVal')">
              <span v-text="$t('mqttApplicationApp.collectData.xVal')">X Val</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'xVal'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('yVal')">
              <span v-text="$t('mqttApplicationApp.collectData.yVal')">Y Val</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'yVal'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('allVals')">
              <span v-text="$t('mqttApplicationApp.collectData.allVals')">All Vals</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'allVals'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="collectData in collectData" :key="collectData.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'CollectDataView', params: { collectDataId: collectData.id } }">{{ collectData.id }}</router-link>
            </td>
            <td>{{ collectData.deviceNo }}</td>
            <td>{{ collectData.time }}</td>
            <td>{{ collectData.xVal }}</td>
            <td>{{ collectData.yVal }}</td>
            <td>{{ collectData.allVals }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'CollectDataView', params: { collectDataId: collectData.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'CollectDataEdit', params: { collectDataId: collectData.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(collectData)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span
          id="mqttApplicationApp.collectData.delete.question"
          data-cy="collectDataDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-collectData-heading" v-text="$t('mqttApplicationApp.collectData.delete.question', { id: removeId })">
          Are you sure you want to delete this Collect Data?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-collectData"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeCollectData()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="collectData && collectData.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./collect-data.component.ts"></script>
