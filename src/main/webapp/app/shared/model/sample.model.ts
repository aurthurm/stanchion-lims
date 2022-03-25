export interface ISample {
  id?: number;
}

export class Sample implements ISample {
  constructor(public id?: number) {}
}
