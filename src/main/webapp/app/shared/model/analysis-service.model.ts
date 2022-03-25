export interface IAnalysisService {
  id?: number;
}

export class AnalysisService implements IAnalysisService {
  constructor(public id?: number) {}
}
