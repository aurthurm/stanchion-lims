<template>
  <div>
    <h2 id="page-heading" data-cy="PatientIdentifierHeading">
      <span v-text="$t('stanchionApp.patientIdentifier.home.title')" id="patient-identifier-heading">Patient Identifiers</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('stanchionApp.patientIdentifier.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'PatientIdentifierCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-patient-identifier"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('stanchionApp.patientIdentifier.home.createLabel')"> Create a new Patient Identifier </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && patientIdentifiers && patientIdentifiers.length === 0">
      <span v-text="$t('stanchionApp.patientIdentifier.home.notFound')">No patientIdentifiers found</span>
    </div>
    <div class="table-responsive" v-if="patientIdentifiers && patientIdentifiers.length > 0">
      <table class="table table-striped" aria-describedby="patientIdentifiers">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('stanchionApp.patientIdentifier.type')">Type</span></th>
            <th scope="row"><span v-text="$t('stanchionApp.patientIdentifier.value')">Value</span></th>
            <th scope="row"><span v-text="$t('stanchionApp.patientIdentifier.type')">Type</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="patientIdentifier in patientIdentifiers" :key="patientIdentifier.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PatientIdentifierView', params: { patientIdentifierId: patientIdentifier.id } }">{{
                patientIdentifier.id
              }}</router-link>
            </td>
            <td>{{ patientIdentifier.type }}</td>
            <td>{{ patientIdentifier.value }}</td>
            <td>
              <div v-if="patientIdentifier.type">
                <router-link :to="{ name: 'PatientIdentifierTypeView', params: { patientIdentifierTypeId: patientIdentifier.type.id } }">{{
                  patientIdentifier.type.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'PatientIdentifierView', params: { patientIdentifierId: patientIdentifier.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'PatientIdentifierEdit', params: { patientIdentifierId: patientIdentifier.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(patientIdentifier)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span
          id="stanchionApp.patientIdentifier.delete.question"
          data-cy="patientIdentifierDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-patientIdentifier-heading" v-text="$t('stanchionApp.patientIdentifier.delete.question', { id: removeId })">
          Are you sure you want to delete this Patient Identifier?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-patientIdentifier"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removePatientIdentifier()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./patient-identifier.component.ts"></script>
