import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

// prettier-ignore
const Country = () => import('@/entities/country/country.vue');
// prettier-ignore
const CountryUpdate = () => import('@/entities/country/country-update.vue');
// prettier-ignore
const CountryDetails = () => import('@/entities/country/country-details.vue');
// prettier-ignore
const District = () => import('@/entities/district/district.vue');
// prettier-ignore
const DistrictUpdate = () => import('@/entities/district/district-update.vue');
// prettier-ignore
const DistrictDetails = () => import('@/entities/district/district-details.vue');
// prettier-ignore
const Province = () => import('@/entities/province/province.vue');
// prettier-ignore
const ProvinceUpdate = () => import('@/entities/province/province-update.vue');
// prettier-ignore
const ProvinceDetails = () => import('@/entities/province/province-details.vue');
// prettier-ignore
const Laboratory = () => import('@/entities/laboratory/laboratory.vue');
// prettier-ignore
const LaboratoryUpdate = () => import('@/entities/laboratory/laboratory-update.vue');
// prettier-ignore
const LaboratoryDetails = () => import('@/entities/laboratory/laboratory-details.vue');
// prettier-ignore
const Client = () => import('@/entities/client/client.vue');
// prettier-ignore
const ClientUpdate = () => import('@/entities/client/client-update.vue');
// prettier-ignore
const ClientDetails = () => import('@/entities/client/client-details.vue');
// prettier-ignore
const ClientContact = () => import('@/entities/client-contact/client-contact.vue');
// prettier-ignore
const ClientContactUpdate = () => import('@/entities/client-contact/client-contact-update.vue');
// prettier-ignore
const ClientContactDetails = () => import('@/entities/client-contact/client-contact-details.vue');
// prettier-ignore
const Patient = () => import('@/entities/patient/patient.vue');
// prettier-ignore
const PatientUpdate = () => import('@/entities/patient/patient-update.vue');
// prettier-ignore
const PatientDetails = () => import('@/entities/patient/patient-details.vue');
// prettier-ignore
const PatientIdentifierType = () => import('@/entities/patient-identifier-type/patient-identifier-type.vue');
// prettier-ignore
const PatientIdentifierTypeUpdate = () => import('@/entities/patient-identifier-type/patient-identifier-type-update.vue');
// prettier-ignore
const PatientIdentifierTypeDetails = () => import('@/entities/patient-identifier-type/patient-identifier-type-details.vue');
// prettier-ignore
const PatientIdentifier = () => import('@/entities/patient-identifier/patient-identifier.vue');
// prettier-ignore
const PatientIdentifierUpdate = () => import('@/entities/patient-identifier/patient-identifier-update.vue');
// prettier-ignore
const PatientIdentifierDetails = () => import('@/entities/patient-identifier/patient-identifier-details.vue');
// prettier-ignore
const LaboratoryRequest = () => import('@/entities/laboratory-request/laboratory-request.vue');
// prettier-ignore
const LaboratoryRequestUpdate = () => import('@/entities/laboratory-request/laboratory-request-update.vue');
// prettier-ignore
const LaboratoryRequestDetails = () => import('@/entities/laboratory-request/laboratory-request-details.vue');
// prettier-ignore
const SampleType = () => import('@/entities/sample-type/sample-type.vue');
// prettier-ignore
const SampleTypeUpdate = () => import('@/entities/sample-type/sample-type-update.vue');
// prettier-ignore
const SampleTypeDetails = () => import('@/entities/sample-type/sample-type-details.vue');
// prettier-ignore
const AnalysisProfile = () => import('@/entities/analysis-profile/analysis-profile.vue');
// prettier-ignore
const AnalysisProfileUpdate = () => import('@/entities/analysis-profile/analysis-profile-update.vue');
// prettier-ignore
const AnalysisProfileDetails = () => import('@/entities/analysis-profile/analysis-profile-details.vue');
// prettier-ignore
const AnalysisService = () => import('@/entities/analysis-service/analysis-service.vue');
// prettier-ignore
const AnalysisServiceUpdate = () => import('@/entities/analysis-service/analysis-service-update.vue');
// prettier-ignore
const AnalysisServiceDetails = () => import('@/entities/analysis-service/analysis-service-details.vue');
// prettier-ignore
const Sample = () => import('@/entities/sample/sample.vue');
// prettier-ignore
const SampleUpdate = () => import('@/entities/sample/sample-update.vue');
// prettier-ignore
const SampleDetails = () => import('@/entities/sample/sample-details.vue');
// prettier-ignore
const Analysis = () => import('@/entities/analysis/analysis.vue');
// prettier-ignore
const AnalysisUpdate = () => import('@/entities/analysis/analysis-update.vue');
// prettier-ignore
const AnalysisDetails = () => import('@/entities/analysis/analysis-details.vue');
// prettier-ignore
const Location = () => import('@/entities/location/location.vue');
// prettier-ignore
const LocationUpdate = () => import('@/entities/location/location-update.vue');
// prettier-ignore
const LocationDetails = () => import('@/entities/location/location-details.vue');
// prettier-ignore
const Department = () => import('@/entities/department/department.vue');
// prettier-ignore
const DepartmentUpdate = () => import('@/entities/department/department-update.vue');
// prettier-ignore
const DepartmentDetails = () => import('@/entities/department/department-details.vue');
// prettier-ignore
const Task = () => import('@/entities/task/task.vue');
// prettier-ignore
const TaskUpdate = () => import('@/entities/task/task-update.vue');
// prettier-ignore
const TaskDetails = () => import('@/entities/task/task-details.vue');
// prettier-ignore
const Employee = () => import('@/entities/employee/employee.vue');
// prettier-ignore
const EmployeeUpdate = () => import('@/entities/employee/employee-update.vue');
// prettier-ignore
const EmployeeDetails = () => import('@/entities/employee/employee-details.vue');
// prettier-ignore
const Job = () => import('@/entities/job/job.vue');
// prettier-ignore
const JobUpdate = () => import('@/entities/job/job-update.vue');
// prettier-ignore
const JobDetails = () => import('@/entities/job/job-details.vue');
// prettier-ignore
const JobHistory = () => import('@/entities/job-history/job-history.vue');
// prettier-ignore
const JobHistoryUpdate = () => import('@/entities/job-history/job-history-update.vue');
// prettier-ignore
const JobHistoryDetails = () => import('@/entities/job-history/job-history-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'country',
      name: 'Country',
      component: Country,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'country/new',
      name: 'CountryCreate',
      component: CountryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'country/:countryId/edit',
      name: 'CountryEdit',
      component: CountryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'country/:countryId/view',
      name: 'CountryView',
      component: CountryDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'district',
      name: 'District',
      component: District,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'district/new',
      name: 'DistrictCreate',
      component: DistrictUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'district/:districtId/edit',
      name: 'DistrictEdit',
      component: DistrictUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'district/:districtId/view',
      name: 'DistrictView',
      component: DistrictDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'province',
      name: 'Province',
      component: Province,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'province/new',
      name: 'ProvinceCreate',
      component: ProvinceUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'province/:provinceId/edit',
      name: 'ProvinceEdit',
      component: ProvinceUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'province/:provinceId/view',
      name: 'ProvinceView',
      component: ProvinceDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'laboratory',
      name: 'Laboratory',
      component: Laboratory,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'laboratory/new',
      name: 'LaboratoryCreate',
      component: LaboratoryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'laboratory/:laboratoryId/edit',
      name: 'LaboratoryEdit',
      component: LaboratoryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'laboratory/:laboratoryId/view',
      name: 'LaboratoryView',
      component: LaboratoryDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'client',
      name: 'Client',
      component: Client,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'client/new',
      name: 'ClientCreate',
      component: ClientUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'client/:clientId/edit',
      name: 'ClientEdit',
      component: ClientUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'client/:clientId/view',
      name: 'ClientView',
      component: ClientDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'client-contact',
      name: 'ClientContact',
      component: ClientContact,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'client-contact/new',
      name: 'ClientContactCreate',
      component: ClientContactUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'client-contact/:clientContactId/edit',
      name: 'ClientContactEdit',
      component: ClientContactUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'client-contact/:clientContactId/view',
      name: 'ClientContactView',
      component: ClientContactDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'patient',
      name: 'Patient',
      component: Patient,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'patient/new',
      name: 'PatientCreate',
      component: PatientUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'patient/:patientId/edit',
      name: 'PatientEdit',
      component: PatientUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'patient/:patientId/view',
      name: 'PatientView',
      component: PatientDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'patient-identifier-type',
      name: 'PatientIdentifierType',
      component: PatientIdentifierType,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'patient-identifier-type/new',
      name: 'PatientIdentifierTypeCreate',
      component: PatientIdentifierTypeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'patient-identifier-type/:patientIdentifierTypeId/edit',
      name: 'PatientIdentifierTypeEdit',
      component: PatientIdentifierTypeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'patient-identifier-type/:patientIdentifierTypeId/view',
      name: 'PatientIdentifierTypeView',
      component: PatientIdentifierTypeDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'patient-identifier',
      name: 'PatientIdentifier',
      component: PatientIdentifier,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'patient-identifier/new',
      name: 'PatientIdentifierCreate',
      component: PatientIdentifierUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'patient-identifier/:patientIdentifierId/edit',
      name: 'PatientIdentifierEdit',
      component: PatientIdentifierUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'patient-identifier/:patientIdentifierId/view',
      name: 'PatientIdentifierView',
      component: PatientIdentifierDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'laboratory-request',
      name: 'LaboratoryRequest',
      component: LaboratoryRequest,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'laboratory-request/new',
      name: 'LaboratoryRequestCreate',
      component: LaboratoryRequestUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'laboratory-request/:laboratoryRequestId/edit',
      name: 'LaboratoryRequestEdit',
      component: LaboratoryRequestUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'laboratory-request/:laboratoryRequestId/view',
      name: 'LaboratoryRequestView',
      component: LaboratoryRequestDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'sample-type',
      name: 'SampleType',
      component: SampleType,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'sample-type/new',
      name: 'SampleTypeCreate',
      component: SampleTypeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'sample-type/:sampleTypeId/edit',
      name: 'SampleTypeEdit',
      component: SampleTypeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'sample-type/:sampleTypeId/view',
      name: 'SampleTypeView',
      component: SampleTypeDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'analysis-profile',
      name: 'AnalysisProfile',
      component: AnalysisProfile,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'analysis-profile/new',
      name: 'AnalysisProfileCreate',
      component: AnalysisProfileUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'analysis-profile/:analysisProfileId/edit',
      name: 'AnalysisProfileEdit',
      component: AnalysisProfileUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'analysis-profile/:analysisProfileId/view',
      name: 'AnalysisProfileView',
      component: AnalysisProfileDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'analysis-service',
      name: 'AnalysisService',
      component: AnalysisService,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'analysis-service/new',
      name: 'AnalysisServiceCreate',
      component: AnalysisServiceUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'analysis-service/:analysisServiceId/edit',
      name: 'AnalysisServiceEdit',
      component: AnalysisServiceUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'analysis-service/:analysisServiceId/view',
      name: 'AnalysisServiceView',
      component: AnalysisServiceDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'sample',
      name: 'Sample',
      component: Sample,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'sample/new',
      name: 'SampleCreate',
      component: SampleUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'sample/:sampleId/edit',
      name: 'SampleEdit',
      component: SampleUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'sample/:sampleId/view',
      name: 'SampleView',
      component: SampleDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'analysis',
      name: 'Analysis',
      component: Analysis,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'analysis/new',
      name: 'AnalysisCreate',
      component: AnalysisUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'analysis/:analysisId/edit',
      name: 'AnalysisEdit',
      component: AnalysisUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'analysis/:analysisId/view',
      name: 'AnalysisView',
      component: AnalysisDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'location',
      name: 'Location',
      component: Location,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'location/new',
      name: 'LocationCreate',
      component: LocationUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'location/:locationId/edit',
      name: 'LocationEdit',
      component: LocationUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'location/:locationId/view',
      name: 'LocationView',
      component: LocationDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'department',
      name: 'Department',
      component: Department,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'department/new',
      name: 'DepartmentCreate',
      component: DepartmentUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'department/:departmentId/edit',
      name: 'DepartmentEdit',
      component: DepartmentUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'department/:departmentId/view',
      name: 'DepartmentView',
      component: DepartmentDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'task',
      name: 'Task',
      component: Task,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'task/new',
      name: 'TaskCreate',
      component: TaskUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'task/:taskId/edit',
      name: 'TaskEdit',
      component: TaskUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'task/:taskId/view',
      name: 'TaskView',
      component: TaskDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'employee',
      name: 'Employee',
      component: Employee,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'employee/new',
      name: 'EmployeeCreate',
      component: EmployeeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'employee/:employeeId/edit',
      name: 'EmployeeEdit',
      component: EmployeeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'employee/:employeeId/view',
      name: 'EmployeeView',
      component: EmployeeDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'job',
      name: 'Job',
      component: Job,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'job/new',
      name: 'JobCreate',
      component: JobUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'job/:jobId/edit',
      name: 'JobEdit',
      component: JobUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'job/:jobId/view',
      name: 'JobView',
      component: JobDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'job-history',
      name: 'JobHistory',
      component: JobHistory,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'job-history/new',
      name: 'JobHistoryCreate',
      component: JobHistoryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'job-history/:jobHistoryId/edit',
      name: 'JobHistoryEdit',
      component: JobHistoryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'job-history/:jobHistoryId/view',
      name: 'JobHistoryView',
      component: JobHistoryDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
