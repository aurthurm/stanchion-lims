<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="laboratoryRequest">
        <h2 class="jh-entity-heading" data-cy="laboratoryRequestDetailsHeading">
          <span v-text="$t('stanchionApp.laboratoryRequest.detail.title')">LaboratoryRequest</span> {{ laboratoryRequest.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('stanchionApp.laboratoryRequest.clientRequestId')">Client Request Id</span>
          </dt>
          <dd>
            <span>{{ laboratoryRequest.clientRequestId }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="laboratoryRequest.id"
          :to="{ name: 'LaboratoryRequestEdit', params: { laboratoryRequestId: laboratoryRequest.id } }"
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

<script lang="ts" src="./laboratory-request-details.component.ts"></script>
