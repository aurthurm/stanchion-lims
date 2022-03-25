<template>
  <div>
    <b-dropdown-item to="/country">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.country')">Country</span>
    </b-dropdown-item>
    <b-dropdown-item to="/district">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.district')">District</span>
    </b-dropdown-item>
    <b-dropdown-item to="/province">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.province')">Province</span>
    </b-dropdown-item>
    <b-dropdown-item to="/laboratory">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.laboratory')">Laboratory</span>
    </b-dropdown-item>
    <b-dropdown-item to="/client">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.client')">Client</span>
    </b-dropdown-item>
    <b-dropdown-item to="/client-contact">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.clientContact')">Client Contact</span>
    </b-dropdown-item>
    <b-dropdown-item to="/patient">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.patient')">Patient</span>
    </b-dropdown-item>
    <b-dropdown-item to="/patient-identifier-type">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.patientIdentifierType')">Patient Identifier Type</span>
    </b-dropdown-item>
    <b-dropdown-item to="/patient-identifier">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.patientIdentifier')">Patient Identifier</span>
    </b-dropdown-item>
    <b-dropdown-item to="/laboratory-request">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.laboratoryRequest')">Laboratory Request</span>
    </b-dropdown-item>
    <b-dropdown-item to="/sample-type">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.sampleType')">Sample Type</span>
    </b-dropdown-item>
    <b-dropdown-item to="/analysis-profile">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.analysisProfile')">Analysis Profile</span>
    </b-dropdown-item>
    <b-dropdown-item to="/analysis-service">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.analysisService')">Analysis Service</span>
    </b-dropdown-item>
    <b-dropdown-item to="/sample">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.sample')">Sample</span>
    </b-dropdown-item>
    <b-dropdown-item to="/analysis">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.analysis')">Analysis</span>
    </b-dropdown-item>
    <b-dropdown-item to="/location">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.location')">Location</span>
    </b-dropdown-item>
    <b-dropdown-item to="/department">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.department')">Department</span>
    </b-dropdown-item>
    <b-dropdown-item to="/task">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.task')">Task</span>
    </b-dropdown-item>
    <b-dropdown-item to="/employee">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.employee')">Employee</span>
    </b-dropdown-item>
    <b-dropdown-item to="/job">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.job')">Job</span>
    </b-dropdown-item>
    <b-dropdown-item to="/job-history">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.jobHistory')">Job History</span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
