export interface ISampleType {
  id?: number;
}

export class SampleType implements ISampleType {
  constructor(public id?: number) {}
}
