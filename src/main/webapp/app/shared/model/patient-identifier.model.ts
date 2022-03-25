import { IPatientIdentifierType } from '@/shared/model/patient-identifier-type.model';

export interface IPatientIdentifier {
  id?: number;
  type?: string | null;
  value?: string | null;
  type?: IPatientIdentifierType | null;
}

export class PatientIdentifier implements IPatientIdentifier {
  constructor(public id?: number, public type?: string | null, public value?: string | null, public type?: IPatientIdentifierType | null) {}
}
