<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="stanchionApp.province.home.createOrEditLabel"
          data-cy="ProvinceCreateUpdateHeading"
          v-text="$t('stanchionApp.province.home.createOrEditLabel')"
        >
          Create or edit a Province
        </h2>
        <div>
          <div class="form-group" v-if="province.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="province.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('stanchionApp.province.name')" for="province-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="province-name"
              data-cy="name"
              :class="{ valid: !$v.province.name.$invalid, invalid: $v.province.name.$invalid }"
              v-model="$v.province.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('stanchionApp.province.district')" for="province-district">District</label>
            <select class="form-control" id="province-district" data-cy="district" name="district" v-model="province.district">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="province.district && districtOption.id === province.district.id ? province.district : districtOption"
                v-for="districtOption in districts"
                :key="districtOption.id"
              >
                {{ districtOption.name }}
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
            :disabled="$v.province.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./province-update.component.ts"></script>
