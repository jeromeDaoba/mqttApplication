<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="mqttApplicationApp.collectData.home.createOrEditLabel"
          data-cy="CollectDataCreateUpdateHeading"
          v-text="$t('mqttApplicationApp.collectData.home.createOrEditLabel')"
        >
          Create or edit a CollectData
        </h2>
        <div>
          <div class="form-group" v-if="collectData.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="collectData.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mqttApplicationApp.collectData.deviceNo')" for="collect-data-deviceNo"
              >Device No</label
            >
            <input
              type="text"
              class="form-control"
              name="deviceNo"
              id="collect-data-deviceNo"
              data-cy="deviceNo"
              :class="{ valid: !$v.collectData.deviceNo.$invalid, invalid: $v.collectData.deviceNo.$invalid }"
              v-model="$v.collectData.deviceNo.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mqttApplicationApp.collectData.time')" for="collect-data-time">Time</label>
            <input
              type="number"
              class="form-control"
              name="time"
              id="collect-data-time"
              data-cy="time"
              :class="{ valid: !$v.collectData.time.$invalid, invalid: $v.collectData.time.$invalid }"
              v-model.number="$v.collectData.time.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mqttApplicationApp.collectData.xVal')" for="collect-data-xVal">X Val</label>
            <input
              type="text"
              class="form-control"
              name="xVal"
              id="collect-data-xVal"
              data-cy="xVal"
              :class="{ valid: !$v.collectData.xVal.$invalid, invalid: $v.collectData.xVal.$invalid }"
              v-model="$v.collectData.xVal.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mqttApplicationApp.collectData.yVal')" for="collect-data-yVal">Y Val</label>
            <input
              type="text"
              class="form-control"
              name="yVal"
              id="collect-data-yVal"
              data-cy="yVal"
              :class="{ valid: !$v.collectData.yVal.$invalid, invalid: $v.collectData.yVal.$invalid }"
              v-model="$v.collectData.yVal.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mqttApplicationApp.collectData.allVals')" for="collect-data-allVals"
              >All Vals</label
            >
            <input
              type="text"
              class="form-control"
              name="allVals"
              id="collect-data-allVals"
              data-cy="allVals"
              :class="{ valid: !$v.collectData.allVals.$invalid, invalid: $v.collectData.allVals.$invalid }"
              v-model="$v.collectData.allVals.$model"
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
            :disabled="$v.collectData.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./collect-data-update.component.ts"></script>
