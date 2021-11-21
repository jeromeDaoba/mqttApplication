<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="mqttApplicationApp.deviceStatus.home.createOrEditLabel"
          data-cy="DeviceStatusCreateUpdateHeading"
          v-text="$t('mqttApplicationApp.deviceStatus.home.createOrEditLabel')"
        >
          Create or edit a DeviceStatus
        </h2>
        <div>
          <div class="form-group" v-if="deviceStatus.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="deviceStatus.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mqttApplicationApp.deviceStatus.deviceNo')" for="device-status-deviceNo"
              >Device No</label
            >
            <input
              type="text"
              class="form-control"
              name="deviceNo"
              id="device-status-deviceNo"
              data-cy="deviceNo"
              :class="{ valid: !$v.deviceStatus.deviceNo.$invalid, invalid: $v.deviceStatus.deviceNo.$invalid }"
              v-model="$v.deviceStatus.deviceNo.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mqttApplicationApp.deviceStatus.code')" for="device-status-code">Code</label>
            <input
              type="number"
              class="form-control"
              name="code"
              id="device-status-code"
              data-cy="code"
              :class="{ valid: !$v.deviceStatus.code.$invalid, invalid: $v.deviceStatus.code.$invalid }"
              v-model.number="$v.deviceStatus.code.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mqttApplicationApp.deviceStatus.fourG')" for="device-status-fourG">Four G</label>
            <input
              type="number"
              class="form-control"
              name="fourG"
              id="device-status-fourG"
              data-cy="fourG"
              :class="{ valid: !$v.deviceStatus.fourG.$invalid, invalid: $v.deviceStatus.fourG.$invalid }"
              v-model.number="$v.deviceStatus.fourG.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mqttApplicationApp.deviceStatus.term')" for="device-status-term">Term</label>
            <input
              type="number"
              class="form-control"
              name="term"
              id="device-status-term"
              data-cy="term"
              :class="{ valid: !$v.deviceStatus.term.$invalid, invalid: $v.deviceStatus.term.$invalid }"
              v-model.number="$v.deviceStatus.term.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mqttApplicationApp.deviceStatus.battery')" for="device-status-battery"
              >Battery</label
            >
            <input
              type="number"
              class="form-control"
              name="battery"
              id="device-status-battery"
              data-cy="battery"
              :class="{ valid: !$v.deviceStatus.battery.$invalid, invalid: $v.deviceStatus.battery.$invalid }"
              v-model.number="$v.deviceStatus.battery.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mqttApplicationApp.deviceStatus.batMode')" for="device-status-batMode"
              >Bat Mode</label
            >
            <input
              type="number"
              class="form-control"
              name="batMode"
              id="device-status-batMode"
              data-cy="batMode"
              :class="{ valid: !$v.deviceStatus.batMode.$invalid, invalid: $v.deviceStatus.batMode.$invalid }"
              v-model.number="$v.deviceStatus.batMode.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mqttApplicationApp.deviceStatus.curVersion')" for="device-status-curVersion"
              >Cur Version</label
            >
            <input
              type="text"
              class="form-control"
              name="curVersion"
              id="device-status-curVersion"
              data-cy="curVersion"
              :class="{ valid: !$v.deviceStatus.curVersion.$invalid, invalid: $v.deviceStatus.curVersion.$invalid }"
              v-model="$v.deviceStatus.curVersion.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mqttApplicationApp.deviceStatus.workMode')" for="device-status-workMode"
              >Work Mode</label
            >
            <input
              type="number"
              class="form-control"
              name="workMode"
              id="device-status-workMode"
              data-cy="workMode"
              :class="{ valid: !$v.deviceStatus.workMode.$invalid, invalid: $v.deviceStatus.workMode.$invalid }"
              v-model.number="$v.deviceStatus.workMode.$model"
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
            :disabled="$v.deviceStatus.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./device-status-update.component.ts"></script>
