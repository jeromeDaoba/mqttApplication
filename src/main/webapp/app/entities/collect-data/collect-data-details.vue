<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="collectData">
        <h2 class="jh-entity-heading" data-cy="collectDataDetailsHeading">
          <span v-text="$t('mqttApplicationApp.collectData.detail.title')">CollectData</span> {{ collectData.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('mqttApplicationApp.collectData.deviceNo')">Device No</span>
          </dt>
          <dd>
            <span>{{ collectData.deviceNo }}</span>
          </dd>
          <dt>
            <span v-text="$t('mqttApplicationApp.collectData.time')">Time</span>
          </dt>
          <dd>
            <span>{{ collectData.time }}</span>
          </dd>
          <dt>
            <span v-text="$t('mqttApplicationApp.collectData.xVal')">X Val</span>
          </dt>
          <dd>
            <span>{{ collectData.xVal }}</span>
          </dd>
          <dt>
            <span v-text="$t('mqttApplicationApp.collectData.yVal')">Y Val</span>
          </dt>
          <dd>
            <span>{{ collectData.yVal }}</span>
          </dd>
          <dt>
            <span v-text="$t('mqttApplicationApp.collectData.allVals')">All Vals</span>
          </dt>
          <dd>
            <span>{{ collectData.allVals }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="collectData.id"
          :to="{ name: 'CollectDataEdit', params: { collectDataId: collectData.id } }"
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

<script lang="ts" src="./collect-data-details.component.ts"></script>
