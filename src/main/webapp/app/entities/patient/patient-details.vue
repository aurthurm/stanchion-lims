<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="patient">
        <h2 class="jh-entity-heading" data-cy="patientDetailsHeading">
          <span v-text="$t('stanchionApp.patient.detail.title')">Patient</span> {{ patient.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('stanchionApp.patient.firstName')">First Name</span>
          </dt>
          <dd>
            <span>{{ patient.firstName }}</span>
          </dd>
          <dt>
            <span v-text="$t('stanchionApp.patient.lastName')">Last Name</span>
          </dt>
          <dd>
            <span>{{ patient.lastName }}</span>
          </dd>
          <dt>
            <span v-text="$t('stanchionApp.patient.identifier')">Identifier</span>
          </dt>
          <dd>
            <div v-if="patient.identifier">
              <router-link :to="{ name: 'PatientIdentifierView', params: { patientIdentifierId: patient.identifier.id } }">{{
                patient.identifier.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('stanchionApp.patient.laboratoryRequest')">Laboratory Request</span>
          </dt>
          <dd>
            <div v-if="patient.laboratoryRequest">
              <router-link :to="{ name: 'LaboratoryRequestView', params: { laboratoryRequestId: patient.laboratoryRequest.id } }">{{
                patient.laboratoryRequest.clientRequestId
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="patient.id" :to="{ name: 'PatientEdit', params: { patientId: patient.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./patient-details.component.ts"></script>
