<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="station">
        <h2 class="jh-entity-heading" data-cy="stationDetailsHeading">
          <span v-text="$t('mqttApplicationApp.station.detail.title')">Station</span> {{ station.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('mqttApplicationApp.station.deviceNo')">Device No</span>
          </dt>
          <dd>
            <span>{{ station.deviceNo }}</span>
          </dd>
          <dt>
            <span v-text="$t('mqttApplicationApp.station.captureTime')">Capture Time</span>
          </dt>
          <dd>
            <span>{{ station.captureTime }}</span>
          </dd>
          <dt>
            <span v-text="$t('mqttApplicationApp.station.bottomRight')">Bottom Right</span>
          </dt>
          <dd>
            <span>{{ station.bottomRight }}</span>
          </dd>
          <dt>
            <span v-text="$t('mqttApplicationApp.station.cValue')">C Value</span>
          </dt>
          <dd>
            <span>{{ station.cValue }}</span>
          </dd>
          <dt>
            <span v-text="$t('mqttApplicationApp.station.dValue')">D Value</span>
          </dt>
          <dd>
            <span>{{ station.dValue }}</span>
          </dd>
          <dt>
            <span v-text="$t('mqttApplicationApp.station.errorCode')">Error Code</span>
          </dt>
          <dd>
            <span>{{ station.errorCode }}</span>
          </dd>
          <dt>
            <span v-text="$t('mqttApplicationApp.station.index')">Index</span>
          </dt>
          <dd>
            <span>{{ station.index }}</span>
          </dd>
          <dt>
            <span v-text="$t('mqttApplicationApp.station.objParaX')">Obj Para X</span>
          </dt>
          <dd>
            <span>{{ station.objParaX }}</span>
          </dd>
          <dt>
            <span v-text="$t('mqttApplicationApp.station.objParaY')">Obj Para Y</span>
          </dt>
          <dd>
            <span>{{ station.objParaY }}</span>
          </dd>
          <dt>
            <span v-text="$t('mqttApplicationApp.station.objPosX')">Obj Pos X</span>
          </dt>
          <dd>
            <span>{{ station.objPosX }}</span>
          </dd>
          <dt>
            <span v-text="$t('mqttApplicationApp.station.objPosY')">Obj Pos Y</span>
          </dt>
          <dd>
            <span>{{ station.objPosY }}</span>
          </dd>
          <dt>
            <span v-text="$t('mqttApplicationApp.station.topLeft')">Top Left</span>
          </dt>
          <dd>
            <span>{{ station.topLeft }}</span>
          </dd>
          <dt>
            <span v-text="$t('mqttApplicationApp.station.wParam')">W Param</span>
          </dt>
          <dd>
            <span>{{ station.wParam }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="station.id" :to="{ name: 'StationEdit', params: { stationId: station.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./station-details.component.ts"></script>
