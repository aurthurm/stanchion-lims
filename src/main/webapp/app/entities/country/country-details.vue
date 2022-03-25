<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="country">
        <h2 class="jh-entity-heading" data-cy="countryDetailsHeading">
          <span v-text="$t('stanchionApp.country.detail.title')">Country</span> {{ country.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('stanchionApp.country.name')">Name</span>
          </dt>
          <dd>
            <span>{{ country.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('stanchionApp.country.province')">Province</span>
          </dt>
          <dd>
            <div v-if="country.province">
              <router-link :to="{ name: 'ProvinceView', params: { provinceId: country.province.id } }">{{
                country.province.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('stanchionApp.country.province')">Province</span>
          </dt>
          <dd>
            <div v-if="country.province">
              <router-link :to="{ name: 'ProvinceView', params: { provinceId: country.province.id } }">{{
                country.province.name
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="country.id" :to="{ name: 'CountryEdit', params: { countryId: country.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./country-details.component.ts"></script>
