import { IPatientIdentifier } from '@/shared/model/patient-identifier.model';
import { ILaboratoryRequest } from '@/shared/model/laboratory-request.model';

export interface IPatient {
  id?: number;
  firstName?: string | null;
  lastName?: string | null;
  identifier?: IPatientIdentifier | null;
  laboratoryRequest?: ILaboratoryRequest | null;
}

export class Patient implements IPatient {
  constructor(
    public id?: number,
    public firstName?: string | null,
    public lastName?: string | null,
    public identifier?: IPatientIdentifier | null,
    public laboratoryRequest?: ILaboratoryRequest | null
  ) {}
}
