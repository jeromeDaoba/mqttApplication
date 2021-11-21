export interface IStation {
  id?: number;
  deviceNo?: string | null;
  captureTime?: number | null;
  bottomRight?: string | null;
  cValue?: string | null;
  dValue?: string | null;
  errorCode?: number | null;
  index?: number | null;
  objParaX?: string | null;
  objParaY?: string | null;
  objPosX?: string | null;
  objPosY?: string | null;
  topLeft?: string | null;
  wParam?: number | null;
}

export class Station implements IStation {
  constructor(
    public id?: number,
    public deviceNo?: string | null,
    public captureTime?: number | null,
    public bottomRight?: string | null,
    public cValue?: string | null,
    public dValue?: string | null,
    public errorCode?: number | null,
    public index?: number | null,
    public objParaX?: string | null,
    public objParaY?: string | null,
    public objPosX?: string | null,
    public objPosY?: string | null,
    public topLeft?: string | null,
    public wParam?: number | null
  ) {}
}
