<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="stanchionApp.client.home.createOrEditLabel"
          data-cy="ClientCreateUpdateHeading"
          v-text="$t('stanchionApp.client.home.createOrEditLabel')"
        >
          Create or edit a Client
        </h2>
        <div>
          <div class="form-group" v-if="client.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="client.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('stanchionApp.client.name')" for="client-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="client-name"
              data-cy="name"
              :class="{ valid: !$v.client.name.$invalid, invalid: $v.client.name.$invalid }"
              v-model="$v.client.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('stanchionApp.client.contact')" for="client-contact">Contact</label>
            <select class="form-control" id="client-contact" data-cy="contact" name="contact" v-model="client.contact">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="client.contact && clientContactOption.id === client.contact.id ? client.contact : clientContactOption"
                v-for="clientContactOption in clientContacts"
                :key="clientContactOption.id"
              >
                {{ clientContactOption.name }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('stanchionApp.client.patient')" for="client-patient">Patient</label>
            <select class="form-control" id="client-patient" data-cy="patient" name="patient" v-model="client.patient">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="client.patient && patientOption.id === client.patient.id ? client.patient : patientOption"
                v-for="patientOption in patients"
                :key="patientOption.id"
              >
                {{ patientOption.name }}
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
            :disabled="$v.client.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./client-update.component.ts"></script>
