<template>
  <div>
    <h2 id="page-heading" data-cy="DeviceStatusHeading">
      <span v-text="$t('mqttApplicationApp.deviceStatus.home.title')" id="device-status-heading">Device Statuses</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('mqttApplicationApp.deviceStatus.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'DeviceStatusCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-device-status"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('mqttApplicationApp.deviceStatus.home.createLabel')"> Create a new Device Status </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && deviceStatuses && deviceStatuses.length === 0">
      <span v-text="$t('mqttApplicationApp.deviceStatus.home.notFound')">No deviceStatuses found</span>
    </div>
    <div class="table-responsive" v-if="deviceStatuses && deviceStatuses.length > 0">
      <table class="table table-striped" aria-describedby="deviceStatuses">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('deviceNo')">
              <span v-text="$t('mqttApplicationApp.deviceStatus.deviceNo')">Device No</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'deviceNo'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('code')">
              <span v-text="$t('mqttApplicationApp.deviceStatus.code')">Code</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'code'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fourG')">
              <span v-text="$t('mqttApplicationApp.deviceStatus.fourG')">Four G</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fourG'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('term')">
              <span v-text="$t('mqttApplicationApp.deviceStatus.term')">Term</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'term'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('battery')">
              <span v-text="$t('mqttApplicationApp.deviceStatus.battery')">Battery</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'battery'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('batMode')">
              <span v-text="$t('mqttApplicationApp.deviceStatus.batMode')">Bat Mode</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'batMode'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('curVersion')">
              <span v-text="$t('mqttApplicationApp.deviceStatus.curVersion')">Cur Version</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'curVersion'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('workMode')">
              <span v-text="$t('mqttApplicationApp.deviceStatus.workMode')">Work Mode</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'workMode'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="deviceStatus in deviceStatuses" :key="deviceStatus.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'DeviceStatusView', params: { deviceStatusId: deviceStatus.id } }">{{
                deviceStatus.id
              }}</router-link>
            </td>
            <td>{{ deviceStatus.deviceNo }}</td>
            <td>{{ deviceStatus.code }}</td>
            <td>{{ deviceStatus.fourG }}</td>
            <td>{{ deviceStatus.term }}</td>
            <td>{{ deviceStatus.battery }}</td>
            <td>{{ deviceStatus.batMode }}</td>
            <td>{{ deviceStatus.curVersion }}</td>
            <td>{{ deviceStatus.workMode }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'DeviceStatusView', params: { deviceStatusId: deviceStatus.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'DeviceStatusEdit', params: { deviceStatusId: deviceStatus.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(deviceStatus)"
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
          id="mqttApplicationApp.deviceStatus.delete.question"
          data-cy="deviceStatusDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-deviceStatus-heading" v-text="$t('mqttApplicationApp.deviceStatus.delete.question', { id: removeId })">
          Are you sure you want to delete this Device Status?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-deviceStatus"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeDeviceStatus()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="deviceStatuses && deviceStatuses.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./device-status.component.ts"></script>
