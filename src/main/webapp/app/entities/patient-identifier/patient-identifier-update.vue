<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="stanchionApp.patientIdentifier.home.createOrEditLabel"
          data-cy="PatientIdentifierCreateUpdateHeading"
          v-text="$t('stanchionApp.patientIdentifier.home.createOrEditLabel')"
        >
          Create or edit a PatientIdentifier
        </h2>
        <div>
          <div class="form-group" v-if="patientIdentifier.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="patientIdentifier.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('stanchionApp.patientIdentifier.type')" for="patient-identifier-type">Type</label>
            <input
              type="text"
              class="form-control"
              name="type"
              id="patient-identifier-type"
              data-cy="type"
              :class="{ valid: !$v.patientIdentifier.type.$invalid, invalid: $v.patientIdentifier.type.$invalid }"
              v-model="$v.patientIdentifier.type.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('stanchionApp.patientIdentifier.value')" for="patient-identifier-value"
              >Value</label
            >
            <input
              type="text"
              class="form-control"
              name="value"
              id="patient-identifier-value"
              data-cy="value"
              :class="{ valid: !$v.patientIdentifier.value.$invalid, invalid: $v.patientIdentifier.value.$invalid }"
              v-model="$v.patientIdentifier.value.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('stanchionApp.patientIdentifier.type')" for="patient-identifier-type">Type</label>
            <select class="form-control" id="patient-identifier-type" data-cy="type" name="type" v-model="patientIdentifier.type">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  patientIdentifier.type && patientIdentifierTypeOption.id === patientIdentifier.type.id
                    ? patientIdentifier.type
                    : patientIdentifierTypeOption
                "
                v-for="patientIdentifierTypeOption in patientIdentifierTypes"
                :key="patientIdentifierTypeOption.id"
              >
                {{ patientIdentifierTypeOption.id }}
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
            :disabled="$v.patientIdentifier.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./patient-identifier-update.component.ts"></script>
