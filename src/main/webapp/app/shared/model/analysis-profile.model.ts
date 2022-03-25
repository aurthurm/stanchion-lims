export interface IAnalysisProfile {
  id?: number;
}

export class AnalysisProfile implements IAnalysisProfile {
  constructor(public id?: number) {}
}
