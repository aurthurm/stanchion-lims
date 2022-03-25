<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="laboratory">
        <h2 class="jh-entity-heading" data-cy="laboratoryDetailsHeading">
          <span v-text="$t('stanchionApp.laboratory.detail.title')">Laboratory</span> {{ laboratory.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('stanchionApp.laboratory.name')">Name</span>
          </dt>
          <dd>
            <span>{{ laboratory.name }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="laboratory.id"
          :to="{ name: 'LaboratoryEdit', params: { laboratoryId: laboratory.id } }"
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

<script lang="ts" src="./laboratory-details.component.ts"></script>
