<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="captureImage">
        <h2 class="jh-entity-heading" data-cy="captureImageDetailsHeading">
          <span v-text="$t('mqttApplicationApp.captureImage.detail.title')">CaptureImage</span> {{ captureImage.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('mqttApplicationApp.captureImage.deviceNo')">Device No</span>
          </dt>
          <dd>
            <span>{{ captureImage.deviceNo }}</span>
          </dd>
          <dt>
            <span v-text="$t('mqttApplicationApp.captureImage.captureTime')">Capture Time</span>
          </dt>
          <dd>
            <span>{{ captureImage.captureTime }}</span>
          </dd>
          <dt>
            <span v-text="$t('mqttApplicationApp.captureImage.format')">Format</span>
          </dt>
          <dd>
            <span>{{ captureImage.format }}</span>
          </dd>
          <dt>
            <span v-text="$t('mqttApplicationApp.captureImage.hParam')">H Param</span>
          </dt>
          <dd>
            <span>{{ captureImage.hParam }}</span>
          </dd>
          <dt>
            <span v-text="$t('mqttApplicationApp.captureImage.image')">Image</span>
          </dt>
          <dd>
            <span>{{ captureImage.image }}</span>
          </dd>
          <dt>
            <span v-text="$t('mqttApplicationApp.captureImage.msgId')">Msg Id</span>
          </dt>
          <dd>
            <span>{{ captureImage.msgId }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="captureImage.id"
          :to="{ name: 'CaptureImageEdit', params: { captureImageId: captureImage.id } }"
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

<script lang="ts" src="./capture-image-details.component.ts"></script>
