export interface IAnalysis {
  id?: number;
}

export class Analysis implements IAnalysis {
  constructor(public id?: number) {}
}
