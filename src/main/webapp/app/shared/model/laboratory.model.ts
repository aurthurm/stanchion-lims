export interface ILaboratory {
  id?: number;
  name?: string | null;
}

export class Laboratory implements ILaboratory {
  constructor(public id?: number, public name?: string | null) {}
}
