<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="mqttApplicationApp.captureImage.home.createOrEditLabel"
          data-cy="CaptureImageCreateUpdateHeading"
          v-text="$t('mqttApplicationApp.captureImage.home.createOrEditLabel')"
        >
          Create or edit a CaptureImage
        </h2>
        <div>
          <div class="form-group" v-if="captureImage.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="captureImage.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mqttApplicationApp.captureImage.deviceNo')" for="capture-image-deviceNo"
              >Device No</label
            >
            <input
              type="text"
              class="form-control"
              name="deviceNo"
              id="capture-image-deviceNo"
              data-cy="deviceNo"
              :class="{ valid: !$v.captureImage.deviceNo.$invalid, invalid: $v.captureImage.deviceNo.$invalid }"
              v-model="$v.captureImage.deviceNo.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mqttApplicationApp.captureImage.captureTime')" for="capture-image-captureTime"
              >Capture Time</label
            >
            <input
              type="number"
              class="form-control"
              name="captureTime"
              id="capture-image-captureTime"
              data-cy="captureTime"
              :class="{ valid: !$v.captureImage.captureTime.$invalid, invalid: $v.captureImage.captureTime.$invalid }"
              v-model.number="$v.captureImage.captureTime.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mqttApplicationApp.captureImage.format')" for="capture-image-format"
              >Format</label
            >
            <input
              type="text"
              class="form-control"
              name="format"
              id="capture-image-format"
              data-cy="format"
              :class="{ valid: !$v.captureImage.format.$invalid, invalid: $v.captureImage.format.$invalid }"
              v-model="$v.captureImage.format.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mqttApplicationApp.captureImage.hParam')" for="capture-image-hParam"
              >H Param</label
            >
            <input
              type="number"
              class="form-control"
              name="hParam"
              id="capture-image-hParam"
              data-cy="hParam"
              :class="{ valid: !$v.captureImage.hParam.$invalid, invalid: $v.captureImage.hParam.$invalid }"
              v-model.number="$v.captureImage.hParam.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mqttApplicationApp.captureImage.image')" for="capture-image-image">Image</label>
            <input
              type="text"
              class="form-control"
              name="image"
              id="capture-image-image"
              data-cy="image"
              :class="{ valid: !$v.captureImage.image.$invalid, invalid: $v.captureImage.image.$invalid }"
              v-model="$v.captureImage.image.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mqttApplicationApp.captureImage.msgId')" for="capture-image-msgId">Msg Id</label>
            <input
              type="number"
              class="form-control"
              name="msgId"
              id="capture-image-msgId"
              data-cy="msgId"
              :class="{ valid: !$v.captureImage.msgId.$invalid, invalid: $v.captureImage.msgId.$invalid }"
              v-model.number="$v.captureImage.msgId.$model"
            />
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.captureImage.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./capture-image-update.component.ts"></script>
