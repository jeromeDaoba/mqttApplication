<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="mqttApplicationApp.station.home.createOrEditLabel"
          data-cy="StationCreateUpdateHeading"
          v-text="$t('mqttApplicationApp.station.home.createOrEditLabel')"
        >
          Create or edit a Station
        </h2>
        <div>
          <div class="form-group" v-if="station.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="station.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mqttApplicationApp.station.deviceNo')" for="station-deviceNo">Device No</label>
            <input
              type="text"
              class="form-control"
              name="deviceNo"
              id="station-deviceNo"
              data-cy="deviceNo"
              :class="{ valid: !$v.station.deviceNo.$invalid, invalid: $v.station.deviceNo.$invalid }"
              v-model="$v.station.deviceNo.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mqttApplicationApp.station.captureTime')" for="station-captureTime"
              >Capture Time</label
            >
            <input
              type="number"
              class="form-control"
              name="captureTime"
              id="station-captureTime"
              data-cy="captureTime"
              :class="{ valid: !$v.station.captureTime.$invalid, invalid: $v.station.captureTime.$invalid }"
              v-model.number="$v.station.captureTime.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mqttApplicationApp.station.bottomRight')" for="station-bottomRight"
              >Bottom Right</label
            >
            <input
              type="text"
              class="form-control"
              name="bottomRight"
              id="station-bottomRight"
              data-cy="bottomRight"
              :class="{ valid: !$v.station.bottomRight.$invalid, invalid: $v.station.bottomRight.$invalid }"
              v-model="$v.station.bottomRight.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mqttApplicationApp.station.cValue')" for="station-cValue">C Value</label>
            <input
              type="text"
              class="form-control"
              name="cValue"
              id="station-cValue"
              data-cy="cValue"
              :class="{ valid: !$v.station.cValue.$invalid, invalid: $v.station.cValue.$invalid }"
              v-model="$v.station.cValue.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mqttApplicationApp.station.dValue')" for="station-dValue">D Value</label>
            <input
              type="text"
              class="form-control"
              name="dValue"
              id="station-dValue"
              data-cy="dValue"
              :class="{ valid: !$v.station.dValue.$invalid, invalid: $v.station.dValue.$invalid }"
              v-model="$v.station.dValue.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mqttApplicationApp.station.errorCode')" for="station-errorCode">Error Code</label>
            <input
              type="number"
              class="form-control"
              name="errorCode"
              id="station-errorCode"
              data-cy="errorCode"
              :class="{ valid: !$v.station.errorCode.$invalid, invalid: $v.station.errorCode.$invalid }"
              v-model.number="$v.station.errorCode.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mqttApplicationApp.station.index')" for="station-index">Index</label>
            <input
              type="number"
              class="form-control"
              name="index"
              id="station-index"
              data-cy="index"
              :class="{ valid: !$v.station.index.$invalid, invalid: $v.station.index.$invalid }"
              v-model.number="$v.station.index.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mqttApplicationApp.station.objParaX')" for="station-objParaX">Obj Para X</label>
            <input
              type="text"
              class="form-control"
              name="objParaX"
              id="station-objParaX"
              data-cy="objParaX"
              :class="{ valid: !$v.station.objParaX.$invalid, invalid: $v.station.objParaX.$invalid }"
              v-model="$v.station.objParaX.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mqttApplicationApp.station.objParaY')" for="station-objParaY">Obj Para Y</label>
            <input
              type="text"
              class="form-control"
              name="objParaY"
              id="station-objParaY"
              data-cy="objParaY"
              :class="{ valid: !$v.station.objParaY.$invalid, invalid: $v.station.objParaY.$invalid }"
              v-model="$v.station.objParaY.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mqttApplicationApp.station.objPosX')" for="station-objPosX">Obj Pos X</label>
            <input
              type="text"
              class="form-control"
              name="objPosX"
              id="station-objPosX"
              data-cy="objPosX"
              :class="{ valid: !$v.station.objPosX.$invalid, invalid: $v.station.objPosX.$invalid }"
              v-model="$v.station.objPosX.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mqttApplicationApp.station.objPosY')" for="station-objPosY">Obj Pos Y</label>
            <input
              type="text"
              class="form-control"
              name="objPosY"
              id="station-objPosY"
              data-cy="objPosY"
              :class="{ valid: !$v.station.objPosY.$invalid, invalid: $v.station.objPosY.$invalid }"
              v-model="$v.station.objPosY.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mqttApplicationApp.station.topLeft')" for="station-topLeft">Top Left</label>
            <input
              type="text"
              class="form-control"
              name="topLeft"
              id="station-topLeft"
              data-cy="topLeft"
              :class="{ valid: !$v.station.topLeft.$invalid, invalid: $v.station.topLeft.$invalid }"
              v-model="$v.station.topLeft.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mqttApplicationApp.station.wParam')" for="station-wParam">W Param</label>
            <input
              type="number"
              class="form-control"
              name="wParam"
              id="station-wParam"
              data-cy="wParam"
              :class="{ valid: !$v.station.wParam.$invalid, invalid: $v.station.wParam.$invalid }"
              v-model.number="$v.station.wParam.$model"
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
            :disabled="$v.station.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./station-update.component.ts"></script>
