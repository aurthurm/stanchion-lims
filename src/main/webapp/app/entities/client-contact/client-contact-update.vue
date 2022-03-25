<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="stanchionApp.clientContact.home.createOrEditLabel"
          data-cy="ClientContactCreateUpdateHeading"
          v-text="$t('stanchionApp.clientContact.home.createOrEditLabel')"
        >
          Create or edit a ClientContact
        </h2>
        <div>
          <div class="form-group" v-if="clientContact.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="clientContact.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('stanchionApp.clientContact.name')" for="client-contact-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="client-contact-name"
              data-cy="name"
              :class="{ valid: !$v.clientContact.name.$invalid, invalid: $v.clientContact.name.$invalid }"
              v-model="$v.clientContact.name.$model"
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
            :disabled="$v.clientContact.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./client-contact-update.component.ts"></script>
