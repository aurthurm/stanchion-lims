<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="patientIdentifier">
        <h2 class="jh-entity-heading" data-cy="patientIdentifierDetailsHeading">
          <span v-text="$t('stanchionApp.patientIdentifier.detail.title')">PatientIdentifier</span> {{ patientIdentifier.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('stanchionApp.patientIdentifier.type')">Type</span>
          </dt>
          <dd>
            <span>{{ patientIdentifier.type }}</span>
          </dd>
          <dt>
            <span v-text="$t('stanchionApp.patientIdentifier.value')">Value</span>
          </dt>
          <dd>
            <span>{{ patientIdentifier.value }}</span>
          </dd>
          <dt>
            <span v-text="$t('stanchionApp.patientIdentifier.type')">Type</span>
          </dt>
          <dd>
            <div v-if="patientIdentifier.type">
              <router-link :to="{ name: 'PatientIdentifierTypeView', params: { patientIdentifierTypeId: patientIdentifier.type.id } }">{{
                patientIdentifier.type.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="patientIdentifier.id"
          :to="{ name: 'PatientIdentifierEdit', params: { patientIdentifierId: patientIdentifier.id } }"
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

<script lang="ts" src="./patient-identifier-details.component.ts"></script>
