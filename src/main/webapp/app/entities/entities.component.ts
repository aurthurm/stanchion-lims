import { Component, Provide, Vue } from 'vue-property-decorator';

import UserService from '@/entities/user/user.service';
import CountryService from './country/country.service';
import DistrictService from './district/district.service';
import ProvinceService from './province/province.service';
import LaboratoryService from './laboratory/laboratory.service';
import ClientService from './client/client.service';
import ClientContactService from './client-contact/client-contact.service';
import PatientService from './patient/patient.service';
import PatientIdentifierTypeService from './patient-identifier-type/patient-identifier-type.service';
import PatientIdentifierService from './patient-identifier/patient-identifier.service';
import LaboratoryRequestService from './laboratory-request/laboratory-request.service';
import SampleTypeService from './sample-type/sample-type.service';
import AnalysisProfileService from './analysis-profile/analysis-profile.service';
import AnalysisServiceService from './analysis-service/analysis-service.service';
import SampleService from './sample/sample.service';
import AnalysisService from './analysis/analysis.service';
import LocationService from './location/location.service';
import DepartmentService from './department/department.service';
import TaskService from './task/task.service';
import EmployeeService from './employee/employee.service';
import JobService from './job/job.service';
import JobHistoryService from './job-history/job-history.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

@Component
export default class Entities extends Vue {
  @Provide('userService') private userService = () => new UserService();
  @Provide('countryService') private countryService = () => new CountryService();
  @Provide('districtService') private districtService = () => new DistrictService();
  @Provide('provinceService') private provinceService = () => new ProvinceService();
  @Provide('laboratoryService') private laboratoryService = () => new LaboratoryService();
  @Provide('clientService') private clientService = () => new ClientService();
  @Provide('clientContactService') private clientContactService = () => new ClientContactService();
  @Provide('patientService') private patientService = () => new PatientService();
  @Provide('patientIdentifierTypeService') private patientIdentifierTypeService = () => new PatientIdentifierTypeService();
  @Provide('patientIdentifierService') private patientIdentifierService = () => new PatientIdentifierService();
  @Provide('laboratoryRequestService') private laboratoryRequestService = () => new LaboratoryRequestService();
  @Provide('sampleTypeService') private sampleTypeService = () => new SampleTypeService();
  @Provide('analysisProfileService') private analysisProfileService = () => new AnalysisProfileService();
  @Provide('analysisServiceService') private analysisServiceService = () => new AnalysisServiceService();
  @Provide('sampleService') private sampleService = () => new SampleService();
  @Provide('analysisService') private analysisService = () => new AnalysisService();
  @Provide('locationService') private locationService = () => new LocationService();
  @Provide('departmentService') private departmentService = () => new DepartmentService();
  @Provide('taskService') private taskService = () => new TaskService();
  @Provide('employeeService') private employeeService = () => new EmployeeService();
  @Provide('jobService') private jobService = () => new JobService();
  @Provide('jobHistoryService') private jobHistoryService = () => new JobHistoryService();
  // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
}
