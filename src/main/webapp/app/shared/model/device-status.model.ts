export interface IDeviceStatus {
  id?: number;
  deviceNo?: string | null;
  code?: number | null;
  fourG?: number | null;
  term?: number | null;
  battery?: number | null;
  batMode?: number | null;
  curVersion?: string | null;
  workMode?: number | null;
}

export class DeviceStatus implements IDeviceStatus {
  constructor(
    public id?: number,
    public deviceNo?: string | null,
    public code?: number | null,
    public fourG?: number | null,
    public term?: number | null,
    public battery?: number | null,
    public batMode?: number | null,
    public curVersion?: string | null,
    public workMode?: number | null
  ) {}
}
