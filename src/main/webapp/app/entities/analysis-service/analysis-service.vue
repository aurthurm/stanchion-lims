<template>
  <div>
    <h2 id="page-heading" data-cy="AnalysisServiceHeading">
      <span v-text="$t('stanchionApp.analysisService.home.title')" id="analysis-service-heading">Analysis Services</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('stanchionApp.analysisService.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'AnalysisServiceCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-analysis-service"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('stanchionApp.analysisService.home.createLabel')"> Create a new Analysis Service </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && analysisServices && analysisServices.length === 0">
      <span v-text="$t('stanchionApp.analysisService.home.notFound')">No analysisServices found</span>
    </div>
    <div class="table-responsive" v-if="analysisServices && analysisServices.length > 0">
      <table class="table table-striped" aria-describedby="analysisServices">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="analysisService in analysisServices" :key="analysisService.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'AnalysisServiceView', params: { analysisServiceId: analysisService.id } }">{{
                analysisService.id
              }}</router-link>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'AnalysisServiceView', params: { analysisServiceId: analysisService.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'AnalysisServiceEdit', params: { analysisServiceId: analysisService.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(analysisService)"
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
          id="stanchionApp.analysisService.delete.question"
          data-cy="analysisServiceDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-analysisService-heading" v-text="$t('stanchionApp.analysisService.delete.question', { id: removeId })">
          Are you sure you want to delete this Analysis Service?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-analysisService"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeAnalysisService()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./analysis-service.component.ts"></script>
