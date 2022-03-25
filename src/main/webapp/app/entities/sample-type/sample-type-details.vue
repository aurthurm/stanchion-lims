<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="sampleType">
        <h2 class="jh-entity-heading" data-cy="sampleTypeDetailsHeading">
          <span v-text="$t('stanchionApp.sampleType.detail.title')">SampleType</span> {{ sampleType.id }}
        </h2>
        <dl class="row jh-entity-details"></dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="sampleType.id"
          :to="{ name: 'SampleTypeEdit', params: { sampleTypeId: sampleType.id } }"
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

<script lang="ts" src="./sample-type-details.component.ts"></script>
