import { IDistrict } from '@/shared/model/district.model';

export interface IProvince {
  id?: number;
  name?: string | null;
  district?: IDistrict | null;
}

export class Province implements IProvince {
  constructor(public id?: number, public name?: string | null, public district?: IDistrict | null) {}
}
