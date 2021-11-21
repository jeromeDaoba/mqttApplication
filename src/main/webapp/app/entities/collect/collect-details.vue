<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="collect">
        <h2 class="jh-entity-heading" data-cy="collectDetailsHeading">
          <span v-text="$t('mqttApplicationApp.collect.detail.title')">Collect</span> {{ collect.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('mqttApplicationApp.collect.outSideId')">Out Side Id</span>
          </dt>
          <dd>
            <span>{{ collect.outSideId }}</span>
          </dd>
          <dt>
            <span v-text="$t('mqttApplicationApp.collect.name')">Name</span>
          </dt>
          <dd>
            <span>{{ collect.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('mqttApplicationApp.collect.time')">Time</span>
          </dt>
          <dd>
            <span>{{ collect.time }}</span>
          </dd>
          <dt>
            <span v-text="$t('mqttApplicationApp.collect.data')">Data</span>
          </dt>
          <dd>
            <span>{{ collect.data }}</span>
          </dd>
          <dt>
            <span v-text="$t('mqttApplicationApp.collect.projectId')">Project Id</span>
          </dt>
          <dd>
            <span>{{ collect.projectId }}</span>
          </dd>
          <dt>
            <span v-text="$t('mqttApplicationApp.collect.channelCount')">Channel Count</span>
          </dt>
          <dd>
            <span>{{ collect.channelCount }}</span>
          </dd>
          <dt>
            <span v-text="$t('mqttApplicationApp.collect.plusInterval')">Plus Interval</span>
          </dt>
          <dd>
            <span>{{ collect.plusInterval }}</span>
          </dd>
          <dt>
            <span v-text="$t('mqttApplicationApp.collect.uploadInterval')">Upload Interval</span>
          </dt>
          <dd>
            <span>{{ collect.uploadInterval }}</span>
          </dd>
          <dt>
            <span v-text="$t('mqttApplicationApp.collect.hz')">Hz</span>
          </dt>
          <dd>
            <span>{{ collect.hz }}</span>
          </dd>
          <dt>
            <span v-text="$t('mqttApplicationApp.collect.deviceNo')">Device No</span>
          </dt>
          <dd>
            <span>{{ collect.deviceNo }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="collect.id" :to="{ name: 'CollectEdit', params: { collectId: collect.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./collect-details.component.ts"></script>
