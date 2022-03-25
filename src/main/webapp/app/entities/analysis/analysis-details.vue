<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="analysis">
        <h2 class="jh-entity-heading" data-cy="analysisDetailsHeading">
          <span v-text="$t('stanchionApp.analysis.detail.title')">Analysis</span> {{ analysis.id }}
        </h2>
        <dl class="row jh-entity-details"></dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="analysis.id" :to="{ name: 'AnalysisEdit', params: { analysisId: analysis.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./analysis-details.component.ts"></script>
