<template>
  <div>
    <h2 id="page-heading" data-cy="LaboratoryHeading">
      <span v-text="$t('stanchionApp.laboratory.home.title')" id="laboratory-heading">Laboratories</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('stanchionApp.laboratory.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'LaboratoryCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-laboratory"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('stanchionApp.laboratory.home.createLabel')"> Create a new Laboratory </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && laboratories && laboratories.length === 0">
      <span v-text="$t('stanchionApp.laboratory.home.notFound')">No laboratories found</span>
    </div>
    <div class="table-responsive" v-if="laboratories && laboratories.length > 0">
      <table class="table table-striped" aria-describedby="laboratories">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('stanchionApp.laboratory.name')">Name</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="laboratory in laboratories" :key="laboratory.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'LaboratoryView', params: { laboratoryId: laboratory.id } }">{{ laboratory.id }}</router-link>
            </td>
            <td>{{ laboratory.name }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'LaboratoryView', params: { laboratoryId: laboratory.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'LaboratoryEdit', params: { laboratoryId: laboratory.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(laboratory)"
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
        ><span id="stanchionApp.laboratory.delete.question" data-cy="laboratoryDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-laboratory-heading" v-text="$t('stanchionApp.laboratory.delete.question', { id: removeId })">
          Are you sure you want to delete this Laboratory?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-laboratory"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeLaboratory()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./laboratory.component.ts"></script>
