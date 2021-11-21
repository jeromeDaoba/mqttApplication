export interface ICollectData {
  id?: number;
  deviceNo?: string | null;
  time?: number | null;
  xVal?: string | null;
  yVal?: string | null;
  allVals?: string | null;
}

export class CollectData implements ICollectData {
  constructor(
    public id?: number,
    public deviceNo?: string | null,
    public time?: number | null,
    public xVal?: string | null,
    public yVal?: string | null,
    public allVals?: string | null
  ) {}
}
