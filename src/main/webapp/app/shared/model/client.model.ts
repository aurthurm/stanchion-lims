import { IClientContact } from '@/shared/model/client-contact.model';
import { IPatient } from '@/shared/model/patient.model';

export interface IClient {
  id?: number;
  name?: string | null;
  contact?: IClientContact | null;
  patient?: IPatient | null;
}

export class Client implements IClient {
  constructor(public id?: number, public name?: string | null, public contact?: IClientContact | null, public patient?: IPatient | null) {}
}
