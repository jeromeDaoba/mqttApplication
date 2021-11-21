<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="deviceStatus">
        <h2 class="jh-entity-heading" data-cy="deviceStatusDetailsHeading">
          <span v-text="$t('mqttApplicationApp.deviceStatus.detail.title')">DeviceStatus</span> {{ deviceStatus.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('mqttApplicationApp.deviceStatus.deviceNo')">Device No</span>
          </dt>
          <dd>
            <span>{{ deviceStatus.deviceNo }}</span>
          </dd>
          <dt>
            <span v-text="$t('mqttApplicationApp.deviceStatus.code')">Code</span>
          </dt>
          <dd>
            <span>{{ deviceStatus.code }}</span>
          </dd>
          <dt>
            <span v-text="$t('mqttApplicationApp.deviceStatus.fourG')">Four G</span>
          </dt>
          <dd>
            <span>{{ deviceStatus.fourG }}</span>
          </dd>
          <dt>
            <span v-text="$t('mqttApplicationApp.deviceStatus.term')">Term</span>
          </dt>
          <dd>
            <span>{{ deviceStatus.term }}</span>
          </dd>
          <dt>
            <span v-text="$t('mqttApplicationApp.deviceStatus.battery')">Battery</span>
          </dt>
          <dd>
            <span>{{ deviceStatus.battery }}</span>
          </dd>
          <dt>
            <span v-text="$t('mqttApplicationApp.deviceStatus.batMode')">Bat Mode</span>
          </dt>
          <dd>
            <span>{{ deviceStatus.batMode }}</span>
          </dd>
          <dt>
            <span v-text="$t('mqttApplicationApp.deviceStatus.curVersion')">Cur Version</span>
          </dt>
          <dd>
            <span>{{ deviceStatus.curVersion }}</span>
          </dd>
          <dt>
            <span v-text="$t('mqttApplicationApp.deviceStatus.workMode')">Work Mode</span>
          </dt>
          <dd>
            <span>{{ deviceStatus.workMode }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="deviceStatus.id"
          :to="{ name: 'DeviceStatusEdit', params: { deviceStatusId: deviceStatus.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./device-status-details.component.ts"></script>
