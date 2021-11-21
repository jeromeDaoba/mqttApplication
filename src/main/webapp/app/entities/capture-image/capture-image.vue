<template>
  <div>
    <h2 id="page-heading" data-cy="CaptureImageHeading">
      <span v-text="$t('mqttApplicationApp.captureImage.home.title')" id="capture-image-heading">Capture Images</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('mqttApplicationApp.captureImage.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'CaptureImageCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-capture-image"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('mqttApplicationApp.captureImage.home.createLabel')"> Create a new Capture Image </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && captureImages && captureImages.length === 0">
      <span v-text="$t('mqttApplicationApp.captureImage.home.notFound')">No captureImages found</span>
    </div>
    <div class="table-responsive" v-if="captureImages && captureImages.length > 0">
      <table class="table table-striped" aria-describedby="captureImages">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('deviceNo')">
              <span v-text="$t('mqttApplicationApp.captureImage.deviceNo')">Device No</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'deviceNo'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('captureTime')">
              <span v-text="$t('mqttApplicationApp.captureImage.captureTime')">Capture Time</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'captureTime'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('format')">
              <span v-text="$t('mqttApplicationApp.captureImage.format')">Format</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'format'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('hParam')">
              <span v-text="$t('mqttApplicationApp.captureImage.hParam')">H Param</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'hParam'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('image')">
              <span v-text="$t('mqttApplicationApp.captureImage.image')">Image</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'image'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('msgId')">
              <span v-text="$t('mqttApplicationApp.captureImage.msgId')">Msg Id</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'msgId'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="captureImage in captureImages" :key="captureImage.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'CaptureImageView', params: { captureImageId: captureImage.id } }">{{
                captureImage.id
              }}</router-link>
            </td>
            <td>{{ captureImage.deviceNo }}</td>
            <td>{{ captureImage.captureTime }}</td>
            <td>{{ captureImage.format }}</td>
            <td>{{ captureImage.hParam }}</td>
            <td>{{ captureImage.image }}</td>
            <td>{{ captureImage.msgId }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'CaptureImageView', params: { captureImageId: captureImage.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'CaptureImageEdit', params: { captureImageId: captureImage.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(captureImage)"
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
          id="mqttApplicationApp.captureImage.delete.question"
          data-cy="captureImageDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-captureImage-heading" v-text="$t('mqttApplicationApp.captureImage.delete.question', { id: removeId })">
          Are you sure you want to delete this Capture Image?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-captureImage"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeCaptureImage()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="captureImages && captureImages.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./capture-image.component.ts"></script>
