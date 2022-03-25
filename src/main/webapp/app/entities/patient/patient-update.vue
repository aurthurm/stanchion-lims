<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="stanchionApp.patient.home.createOrEditLabel"
          data-cy="PatientCreateUpdateHeading"
          v-text="$t('stanchionApp.patient.home.createOrEditLabel')"
        >
          Create or edit a Patient
        </h2>
        <div>
          <div class="form-group" v-if="patient.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="patient.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('stanchionApp.patient.firstName')" for="patient-firstName">First Name</label>
            <input
              type="text"
              class="form-control"
              name="firstName"
              id="patient-firstName"
              data-cy="firstName"
              :class="{ valid: !$v.patient.firstName.$invalid, invalid: $v.patient.firstName.$invalid }"
              v-model="$v.patient.firstName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('stanchionApp.patient.lastName')" for="patient-lastName">Last Name</label>
            <input
              type="text"
              class="form-control"
              name="lastName"
              id="patient-lastName"
              data-cy="lastName"
              :class="{ valid: !$v.patient.lastName.$invalid, invalid: $v.patient.lastName.$invalid }"
              v-model="$v.patient.lastName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('stanchionApp.patient.identifier')" for="patient-identifier">Identifier</label>
            <select class="form-control" id="patient-identifier" data-cy="identifier" name="identifier" v-model="patient.identifier">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  patient.identifier && patientIdentifierOption.id === patient.identifier.id ? patient.identifier : patientIdentifierOption
                "
                v-for="patientIdentifierOption in patientIdentifiers"
                :key="patientIdentifierOption.id"
              >
                {{ patientIdentifierOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('stanchionApp.patient.laboratoryRequest')" for="patient-laboratoryRequest"
              >Laboratory Request</label
            >
            <select
              class="form-control"
              id="patient-laboratoryRequest"
              data-cy="laboratoryRequest"
              name="laboratoryRequest"
              v-model="patient.laboratoryRequest"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  patient.laboratoryRequest && laboratoryRequestOption.id === patient.laboratoryRequest.id
                    ? patient.laboratoryRequest
                    : laboratoryRequestOption
                "
                v-for="laboratoryRequestOption in laboratoryRequests"
                :key="laboratoryRequestOption.id"
              >
                {{ laboratoryRequestOption.clientRequestId }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.patient.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./patient-update.component.ts"></script>
