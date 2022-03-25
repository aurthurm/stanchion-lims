<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="stanchionApp.patientIdentifierType.home.createOrEditLabel"
          data-cy="PatientIdentifierTypeCreateUpdateHeading"
          v-text="$t('stanchionApp.patientIdentifierType.home.createOrEditLabel')"
        >
          Create or edit a PatientIdentifierType
        </h2>
        <div>
          <div class="form-group" v-if="patientIdentifierType.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="patientIdentifierType.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('stanchionApp.patientIdentifierType.name')" for="patient-identifier-type-name"
              >Name</label
            >
            <input
              type="text"
              class="form-control"
              name="name"
              id="patient-identifier-type-name"
              data-cy="name"
              :class="{ valid: !$v.patientIdentifierType.name.$invalid, invalid: $v.patientIdentifierType.name.$invalid }"
              v-model="$v.patientIdentifierType.name.$model"
            />
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
            :disabled="$v.patientIdentifierType.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./patient-identifier-type-update.component.ts"></script>
