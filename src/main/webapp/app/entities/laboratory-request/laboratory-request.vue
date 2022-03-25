<template>
  <div>
    <h2 id="page-heading" data-cy="LaboratoryRequestHeading">
      <span v-text="$t('stanchionApp.laboratoryRequest.home.title')" id="laboratory-request-heading">Laboratory Requests</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('stanchionApp.laboratoryRequest.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'LaboratoryRequestCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-laboratory-request"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('stanchionApp.laboratoryRequest.home.createLabel')"> Create a new Laboratory Request </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && laboratoryRequests && laboratoryRequests.length === 0">
      <span v-text="$t('stanchionApp.laboratoryRequest.home.notFound')">No laboratoryRequests found</span>
    </div>
    <div class="table-responsive" v-if="laboratoryRequests && laboratoryRequests.length > 0">
      <table class="table table-striped" aria-describedby="laboratoryRequests">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('stanchionApp.laboratoryRequest.clientRequestId')">Client Request Id</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="laboratoryRequest in laboratoryRequests" :key="laboratoryRequest.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'LaboratoryRequestView', params: { laboratoryRequestId: laboratoryRequest.id } }">{{
                laboratoryRequest.id
              }}</router-link>
            </td>
            <td>{{ laboratoryRequest.clientRequestId }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'LaboratoryRequestView', params: { laboratoryRequestId: laboratoryRequest.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'LaboratoryRequestEdit', params: { laboratoryRequestId: laboratoryRequest.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(laboratoryRequest)"
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
          id="stanchionApp.laboratoryRequest.delete.question"
          data-cy="laboratoryRequestDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-laboratoryRequest-heading" v-text="$t('stanchionApp.laboratoryRequest.delete.question', { id: removeId })">
          Are you sure you want to delete this Laboratory Request?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-laboratoryRequest"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeLaboratoryRequest()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./laboratory-request.component.ts"></script>
