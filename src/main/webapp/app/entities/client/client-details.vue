<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="client">
        <h2 class="jh-entity-heading" data-cy="clientDetailsHeading">
          <span v-text="$t('stanchionApp.client.detail.title')">Client</span> {{ client.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('stanchionApp.client.name')">Name</span>
          </dt>
          <dd>
            <span>{{ client.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('stanchionApp.client.contact')">Contact</span>
          </dt>
          <dd>
            <div v-if="client.contact">
              <router-link :to="{ name: 'ClientContactView', params: { clientContactId: client.contact.id } }">{{
                client.contact.name
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('stanchionApp.client.patient')">Patient</span>
          </dt>
          <dd>
            <div v-if="client.patient">
              <router-link :to="{ name: 'PatientView', params: { patientId: client.patient.id } }">{{ client.patient.name }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="client.id" :to="{ name: 'ClientEdit', params: { clientId: client.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./client-details.component.ts"></script>
