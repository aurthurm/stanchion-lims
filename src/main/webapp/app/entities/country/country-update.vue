<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="stanchionApp.country.home.createOrEditLabel"
          data-cy="CountryCreateUpdateHeading"
          v-text="$t('stanchionApp.country.home.createOrEditLabel')"
        >
          Create or edit a Country
        </h2>
        <div>
          <div class="form-group" v-if="country.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="country.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('stanchionApp.country.name')" for="country-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="country-name"
              data-cy="name"
              :class="{ valid: !$v.country.name.$invalid, invalid: $v.country.name.$invalid }"
              v-model="$v.country.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('stanchionApp.country.province')" for="country-province">Province</label>
            <select class="form-control" id="country-province" data-cy="province" name="province" v-model="country.province">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="country.province && provinceOption.id === country.province.id ? country.province : provinceOption"
                v-for="provinceOption in provinces"
                :key="provinceOption.id"
              >
                {{ provinceOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('stanchionApp.country.province')" for="country-province">Province</label>
            <select class="form-control" id="country-province" data-cy="province" name="province" v-model="country.province">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="country.province && provinceOption.id === country.province.id ? country.province : provinceOption"
                v-for="provinceOption in provinces"
                :key="provinceOption.id"
              >
                {{ provinceOption.name }}
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
            :disabled="$v.country.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./country-update.component.ts"></script>
