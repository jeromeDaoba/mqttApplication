<template>
  <div>
    <h2 id="page-heading" data-cy="StationHeading">
      <span v-text="$t('mqttApplicationApp.station.home.title')" id="station-heading">Stations</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('mqttApplicationApp.station.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'StationCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-station"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('mqttApplicationApp.station.home.createLabel')"> Create a new Station </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && stations && stations.length === 0">
      <span v-text="$t('mqttApplicationApp.station.home.notFound')">No stations found</span>
    </div>
    <div class="table-responsive" v-if="stations && stations.length > 0">
      <table class="table table-striped" aria-describedby="stations">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('deviceNo')">
              <span v-text="$t('mqttApplicationApp.station.deviceNo')">Device No</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'deviceNo'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('captureTime')">
              <span v-text="$t('mqttApplicationApp.station.captureTime')">Capture Time</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'captureTime'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('bottomRight')">
              <span v-text="$t('mqttApplicationApp.station.bottomRight')">Bottom Right</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'bottomRight'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('cValue')">
              <span v-text="$t('mqttApplicationApp.station.cValue')">C Value</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'cValue'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('dValue')">
              <span v-text="$t('mqttApplicationApp.station.dValue')">D Value</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'dValue'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('errorCode')">
              <span v-text="$t('mqttApplicationApp.station.errorCode')">Error Code</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'errorCode'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('index')">
              <span v-text="$t('mqttApplicationApp.station.index')">Index</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'index'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('objParaX')">
              <span v-text="$t('mqttApplicationApp.station.objParaX')">Obj Para X</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'objParaX'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('objParaY')">
              <span v-text="$t('mqttApplicationApp.station.objParaY')">Obj Para Y</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'objParaY'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('objPosX')">
              <span v-text="$t('mqttApplicationApp.station.objPosX')">Obj Pos X</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'objPosX'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('objPosY')">
              <span v-text="$t('mqttApplicationApp.station.objPosY')">Obj Pos Y</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'objPosY'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('topLeft')">
              <span v-text="$t('mqttApplicationApp.station.topLeft')">Top Left</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'topLeft'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('wParam')">
              <span v-text="$t('mqttApplicationApp.station.wParam')">W Param</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'wParam'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="station in stations" :key="station.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'StationView', params: { stationId: station.id } }">{{ station.id }}</router-link>
            </td>
            <td>{{ station.deviceNo }}</td>
            <td>{{ station.captureTime }}</td>
            <td>{{ station.bottomRight }}</td>
            <td>{{ station.cValue }}</td>
            <td>{{ station.dValue }}</td>
            <td>{{ station.errorCode }}</td>
            <td>{{ station.index }}</td>
            <td>{{ station.objParaX }}</td>
            <td>{{ station.objParaY }}</td>
            <td>{{ station.objPosX }}</td>
            <td>{{ station.objPosY }}</td>
            <td>{{ station.topLeft }}</td>
            <td>{{ station.wParam }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'StationView', params: { stationId: station.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'StationEdit', params: { stationId: station.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(station)"
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
        ><span id="mqttApplicationApp.station.delete.question" data-cy="stationDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-station-heading" v-text="$t('mqttApplicationApp.station.delete.question', { id: removeId })">
          Are you sure you want to delete this Station?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-station"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeStation()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="stations && stations.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./station.component.ts"></script>
