export interface IClientContact {
  id?: number;
  name?: string | null;
}

export class ClientContact implements IClientContact {
  constructor(public id?: number, public name?: string | null) {}
}
