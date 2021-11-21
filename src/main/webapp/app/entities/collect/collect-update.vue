<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="mqttApplicationApp.collect.home.createOrEditLabel"
          data-cy="CollectCreateUpdateHeading"
          v-text="$t('mqttApplicationApp.collect.home.createOrEditLabel')"
        >
          Create or edit a Collect
        </h2>
        <div>
          <div class="form-group" v-if="collect.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="collect.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mqttApplicationApp.collect.outSideId')" for="collect-outSideId"
              >Out Side Id</label
            >
            <input
              type="text"
              class="form-control"
              name="outSideId"
              id="collect-outSideId"
              data-cy="outSideId"
              :class="{ valid: !$v.collect.outSideId.$invalid, invalid: $v.collect.outSideId.$invalid }"
              v-model="$v.collect.outSideId.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mqttApplicationApp.collect.name')" for="collect-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="collect-name"
              data-cy="name"
              :class="{ valid: !$v.collect.name.$invalid, invalid: $v.collect.name.$invalid }"
              v-model="$v.collect.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mqttApplicationApp.collect.time')" for="collect-time">Time</label>
            <input
              type="number"
              class="form-control"
              name="time"
              id="collect-time"
              data-cy="time"
              :class="{ valid: !$v.collect.time.$invalid, invalid: $v.collect.time.$invalid }"
              v-model.number="$v.collect.time.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mqttApplicationApp.collect.data')" for="collect-data">Data</label>
            <input
              type="text"
              class="form-control"
              name="data"
              id="collect-data"
              data-cy="data"
              :class="{ valid: !$v.collect.data.$invalid, invalid: $v.collect.data.$invalid }"
              v-model="$v.collect.data.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mqttApplicationApp.collect.projectId')" for="collect-projectId">Project Id</label>
            <input
              type="text"
              class="form-control"
              name="projectId"
              id="collect-projectId"
              data-cy="projectId"
              :class="{ valid: !$v.collect.projectId.$invalid, invalid: $v.collect.projectId.$invalid }"
              v-model="$v.collect.projectId.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mqttApplicationApp.collect.channelCount')" for="collect-channelCount"
              >Channel Count</label
            >
            <input
              type="number"
              class="form-control"
              name="channelCount"
              id="collect-channelCount"
              data-cy="channelCount"
              :class="{ valid: !$v.collect.channelCount.$invalid, invalid: $v.collect.channelCount.$invalid }"
              v-model.number="$v.collect.channelCount.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mqttApplicationApp.collect.plusInterval')" for="collect-plusInterval"
              >Plus Interval</label
            >
            <input
              type="number"
              class="form-control"
              name="plusInterval"
              id="collect-plusInterval"
              data-cy="plusInterval"
              :class="{ valid: !$v.collect.plusInterval.$invalid, invalid: $v.collect.plusInterval.$invalid }"
              v-model.number="$v.collect.plusInterval.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mqttApplicationApp.collect.uploadInterval')" for="collect-uploadInterval"
              >Upload Interval</label
            >
            <input
              type="number"
              class="form-control"
              name="uploadInterval"
              id="collect-uploadInterval"
              data-cy="uploadInterval"
              :class="{ valid: !$v.collect.uploadInterval.$invalid, invalid: $v.collect.uploadInterval.$invalid }"
              v-model.number="$v.collect.uploadInterval.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mqttApplicationApp.collect.hz')" for="collect-hz">Hz</label>
            <input
              type="number"
              class="form-control"
              name="hz"
              id="collect-hz"
              data-cy="hz"
              :class="{ valid: !$v.collect.hz.$invalid, invalid: $v.collect.hz.$invalid }"
              v-model.number="$v.collect.hz.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mqttApplicationApp.collect.deviceNo')" for="collect-deviceNo">Device No</label>
            <input
              type="text"
              class="form-control"
              name="deviceNo"
              id="collect-deviceNo"
              data-cy="deviceNo"
              :class="{ valid: !$v.collect.deviceNo.$invalid, invalid: $v.collect.deviceNo.$invalid }"
              v-model="$v.collect.deviceNo.$model"
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
            :disabled="$v.collect.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./collect-update.component.ts"></script>
