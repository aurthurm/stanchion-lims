export interface IDistrict {
  id?: number;
  name?: string | null;
}

export class District implements IDistrict {
  constructor(public id?: number, public name?: string | null) {}
}
