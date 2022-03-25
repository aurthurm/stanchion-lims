import { IProvince } from '@/shared/model/province.model';

export interface ICountry {
  id?: number;
  name?: string | null;
  province?: IProvince | null;
  province?: IProvince | null;
}

export class Country implements ICountry {
  constructor(public id?: number, public name?: string | null, public province?: IProvince | null, public province?: IProvince | null) {}
}
