package com.sjzy.jczx.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Station.
 */
@Entity
@Table(name = "station")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Station implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "device_no")
    private String deviceNo;

    @Column(name = "capture_time")
    private Long captureTime;

    @Column(name = "bottom_right")
    private String bottomRight;

    @Column(name = "c_value")
    private String cValue;

    @Column(name = "d_value")
    private String dValue;

    @Column(name = "error_code")
    private Integer errorCode;

    @Column(name = "jhi_index")
    private Integer index;

    @Column(name = "obj_para_x")
    private String objParaX;

    @Column(name = "obj_para_y")
    private String objParaY;

    @Column(name = "obj_pos_x")
    private String objPosX;

    @Column(name = "obj_pos_y")
    private String objPosY;

    @Column(name = "top_left")
    private String topLeft;

    @Column(name = "w_param")
    private Integer wParam;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Station id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceNo() {
        return this.deviceNo;
    }

    public Station deviceNo(String deviceNo) {
        this.setDeviceNo(deviceNo);
        return this;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public Long getCaptureTime() {
        return this.captureTime;
    }

    public Station captureTime(Long captureTime) {
        this.setCaptureTime(captureTime);
        return this;
    }

    public void setCaptureTime(Long captureTime) {
        this.captureTime = captureTime;
    }

    public String getBottomRight() {
        return this.bottomRight;
    }

    public Station bottomRight(String bottomRight) {
        this.setBottomRight(bottomRight);
        return this;
    }

    public void setBottomRight(String bottomRight) {
        this.bottomRight = bottomRight;
    }

    public String getcValue() {
        return this.cValue;
    }

    public Station cValue(String cValue) {
        this.setcValue(cValue);
        return this;
    }

    public void setcValue(String cValue) {
        this.cValue = cValue;
    }

    public String getdValue() {
        return this.dValue;
    }

    public Station dValue(String dValue) {
        this.setdValue(dValue);
        return this;
    }

    public void setdValue(String dValue) {
        this.dValue = dValue;
    }

    public Integer getErrorCode() {
        return this.errorCode;
    }

    public Station errorCode(Integer errorCode) {
        this.setErrorCode(errorCode);
        return this;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public Integer getIndex() {
        return this.index;
    }

    public Station index(Integer index) {
        this.setIndex(index);
        return this;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getObjParaX() {
        return this.objParaX;
    }

    public Station objParaX(String objParaX) {
        this.setObjParaX(objParaX);
        return this;
    }

    public void setObjParaX(String objParaX) {
        this.objParaX = objParaX;
    }

    public String getObjParaY() {
        return this.objParaY;
    }

    public Station objParaY(String objParaY) {
        this.setObjParaY(objParaY);
        return this;
    }

    public void setObjParaY(String objParaY) {
        this.objParaY = objParaY;
    }

    public String getObjPosX() {
        return this.objPosX;
    }

    public Station objPosX(String objPosX) {
        this.setObjPosX(objPosX);
        return this;
    }

    public void setObjPosX(String objPosX) {
        this.objPosX = objPosX;
    }

    public String getObjPosY() {
        return this.objPosY;
    }

    public Station objPosY(String objPosY) {
        this.setObjPosY(objPosY);
        return this;
    }

    public void setObjPosY(String objPosY) {
        this.objPosY = objPosY;
    }

    public String getTopLeft() {
        return this.topLeft;
    }

    public Station topLeft(String topLeft) {
        this.setTopLeft(topLeft);
        return this;
    }

    public void setTopLeft(String topLeft) {
        this.topLeft = topLeft;
    }

    public Integer getwParam() {
        return this.wParam;
    }

    public Station wParam(Integer wParam) {
        this.setwParam(wParam);
        return this;
    }

    public void setwParam(Integer wParam) {
        this.wParam = wParam;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Station)) {
            return false;
        }
        return id != null && id.equals(((Station) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Station{" +
            "id=" + getId() +
            ", deviceNo='" + getDeviceNo() + "'" +
            ", captureTime=" + getCaptureTime() +
            ", bottomRight='" + getBottomRight() + "'" +
            ", cValue='" + getcValue() + "'" +
            ", dValue='" + getdValue() + "'" +
            ", errorCode=" + getErrorCode() +
            ", index=" + getIndex() +
            ", objParaX='" + getObjParaX() + "'" +
            ", objParaY='" + getObjParaY() + "'" +
            ", objPosX='" + getObjPosX() + "'" +
            ", objPosY='" + getObjPosY() + "'" +
            ", topLeft='" + getTopLeft() + "'" +
            ", wParam=" + getwParam() +
            "}";
    }
}
