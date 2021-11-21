package com.sjzy.jczx.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CaptureImage.
 */
@Entity
@Table(name = "capture_image")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CaptureImage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "device_no")
    private String deviceNo;

    @Column(name = "capture_time")
    private Long captureTime;

    @Column(name = "format")
    private String format;

    @Column(name = "h_param")
    private Integer hParam;

    @Column(name = "image")
    private String image;

    @Column(name = "msg_id")
    private Integer msgId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CaptureImage id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceNo() {
        return this.deviceNo;
    }

    public CaptureImage deviceNo(String deviceNo) {
        this.setDeviceNo(deviceNo);
        return this;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public Long getCaptureTime() {
        return this.captureTime;
    }

    public CaptureImage captureTime(Long captureTime) {
        this.setCaptureTime(captureTime);
        return this;
    }

    public void setCaptureTime(Long captureTime) {
        this.captureTime = captureTime;
    }

    public String getFormat() {
        return this.format;
    }

    public CaptureImage format(String format) {
        this.setFormat(format);
        return this;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Integer gethParam() {
        return this.hParam;
    }

    public CaptureImage hParam(Integer hParam) {
        this.sethParam(hParam);
        return this;
    }

    public void sethParam(Integer hParam) {
        this.hParam = hParam;
    }

    public String getImage() {
        return this.image;
    }

    public CaptureImage image(String image) {
        this.setImage(image);
        return this;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getMsgId() {
        return this.msgId;
    }

    public CaptureImage msgId(Integer msgId) {
        this.setMsgId(msgId);
        return this;
    }

    public void setMsgId(Integer msgId) {
        this.msgId = msgId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CaptureImage)) {
            return false;
        }
        return id != null && id.equals(((CaptureImage) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CaptureImage{" +
            "id=" + getId() +
            ", deviceNo='" + getDeviceNo() + "'" +
            ", captureTime=" + getCaptureTime() +
            ", format='" + getFormat() + "'" +
            ", hParam=" + gethParam() +
            ", image='" + getImage() + "'" +
            ", msgId=" + getMsgId() +
            "}";
    }
}
