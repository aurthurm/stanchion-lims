export interface ILaboratoryRequest {
  id?: number;
  clientRequestId?: string | null;
}

export class LaboratoryRequest implements ILaboratoryRequest {
  constructor(public id?: number, public clientRequestId?: string | null) {}
}
