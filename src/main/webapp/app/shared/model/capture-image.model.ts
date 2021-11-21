export interface ICaptureImage {
  id?: number;
  deviceNo?: string | null;
  captureTime?: number | null;
  format?: string | null;
  hParam?: number | null;
  image?: string | null;
  msgId?: number | null;
}

export class CaptureImage implements ICaptureImage {
  constructor(
    public id?: number,
    public deviceNo?: string | null,
    public captureTime?: number | null,
    public format?: string | null,
    public hParam?: number | null,
    public image?: string | null,
    public msgId?: number | null
  ) {}
}
