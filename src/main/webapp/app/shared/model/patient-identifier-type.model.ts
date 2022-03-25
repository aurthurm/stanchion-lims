export interface IPatientIdentifierType {
  id?: number;
  name?: string | null;
}

export class PatientIdentifierType implements IPatientIdentifierType {
  constructor(public id?: number, public name?: string | null) {}
}
