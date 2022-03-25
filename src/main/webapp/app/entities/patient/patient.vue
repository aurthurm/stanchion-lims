<template>
  <div>
    <h2 id="page-heading" data-cy="PatientHeading">
      <span v-text="$t('stanchionApp.patient.home.title')" id="patient-heading">Patients</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('stanchionApp.patient.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'PatientCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-patient"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('stanchionApp.patient.home.createLabel')"> Create a new Patient </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && patients && patients.length === 0">
      <span v-text="$t('stanchionApp.patient.home.notFound')">No patients found</span>
    </div>
    <div class="table-responsive" v-if="patients && patients.length > 0">
      <table class="table table-striped" aria-describedby="patients">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('stanchionApp.patient.firstName')">First Name</span></th>
            <th scope="row"><span v-text="$t('stanchionApp.patient.lastName')">Last Name</span></th>
            <th scope="row"><span v-text="$t('stanchionApp.patient.identifier')">Identifier</span></th>
            <th scope="row"><span v-text="$t('stanchionApp.patient.laboratoryRequest')">Laboratory Request</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="patient in patients" :key="patient.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PatientView', params: { patientId: patient.id } }">{{ patient.id }}</router-link>
            </td>
            <td>{{ patient.firstName }}</td>
            <td>{{ patient.lastName }}</td>
            <td>
              <div v-if="patient.identifier">
                <router-link :to="{ name: 'PatientIdentifierView', params: { patientIdentifierId: patient.identifier.id } }">{{
                  patient.identifier.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="patient.laboratoryRequest">
                <router-link :to="{ name: 'LaboratoryRequestView', params: { laboratoryRequestId: patient.laboratoryRequest.id } }">{{
                  patient.laboratoryRequest.clientRequestId
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'PatientView', params: { patientId: patient.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'PatientEdit', params: { patientId: patient.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(patient)"
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
        ><span id="stanchionApp.patient.delete.question" data-cy="patientDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-patient-heading" v-text="$t('stanchionApp.patient.delete.question', { id: removeId })">
          Are you sure you want to delete this Patient?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-patient"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removePatient()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./patient.component.ts"></script>
