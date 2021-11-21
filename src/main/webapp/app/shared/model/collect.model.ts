export interface ICollect {
  id?: number;
  outSideId?: string | null;
  name?: string | null;
  time?: number | null;
  data?: string | null;
  projectId?: string | null;
  channelCount?: number | null;
  plusInterval?: number | null;
  uploadInterval?: number | null;
  hz?: number | null;
  deviceNo?: string | null;
}

export class Collect implements ICollect {
  constructor(
    public id?: number,
    public outSideId?: string | null,
    public name?: string | null,
    public time?: number | null,
    public data?: string | null,
    public projectId?: string | null,
    public channelCount?: number | null,
    public plusInterval?: number | null,
    public uploadInterval?: number | null,
    public hz?: number | null,
    public deviceNo?: string | null
  ) {}
}
