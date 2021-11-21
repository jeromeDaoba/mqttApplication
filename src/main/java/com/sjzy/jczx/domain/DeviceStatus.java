package com.sjzy.jczx.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DeviceStatus.
 */
@Entity
@Table(name = "device_status")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DeviceStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "device_no")
    private String deviceNo;

    @Column(name = "code")
    private Integer code;

    @Column(name = "four_g")
    private Integer fourG;

    @Column(name = "term")
    private Long term;

    @Column(name = "battery")
    private Integer battery;

    @Column(name = "bat_mode")
    private Integer batMode;

    @Column(name = "cur_version")
    private String curVersion;

    @Column(name = "work_mode")
    private Integer workMode;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DeviceStatus id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceNo() {
        return this.deviceNo;
    }

    public DeviceStatus deviceNo(String deviceNo) {
        this.setDeviceNo(deviceNo);
        return this;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public Integer getCode() {
        return this.code;
    }

    public DeviceStatus code(Integer code) {
        this.setCode(code);
        return this;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getFourG() {
        return this.fourG;
    }

    public DeviceStatus fourG(Integer fourG) {
        this.setFourG(fourG);
        return this;
    }

    public void setFourG(Integer fourG) {
        this.fourG = fourG;
    }

    public Long getTerm() {
        return this.term;
    }

    public DeviceStatus term(Long term) {
        this.setTerm(term);
        return this;
    }

    public void setTerm(Long term) {
        this.term = term;
    }

    public Integer getBattery() {
        return this.battery;
    }

    public DeviceStatus battery(Integer battery) {
        this.setBattery(battery);
        return this;
    }

    public void setBattery(Integer battery) {
        this.battery = battery;
    }

    public Integer getBatMode() {
        return this.batMode;
    }

    public DeviceStatus batMode(Integer batMode) {
        this.setBatMode(batMode);
        return this;
    }

    public void setBatMode(Integer batMode) {
        this.batMode = batMode;
    }

    public String getCurVersion() {
        return this.curVersion;
    }

    public DeviceStatus curVersion(String curVersion) {
        this.setCurVersion(curVersion);
        return this;
    }

    public void setCurVersion(String curVersion) {
        this.curVersion = curVersion;
    }

    public Integer getWorkMode() {
        return this.workMode;
    }

    public DeviceStatus workMode(Integer workMode) {
        this.setWorkMode(workMode);
        return this;
    }

    public void setWorkMode(Integer workMode) {
        this.workMode = workMode;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeviceStatus)) {
            return false;
        }
        return id != null && id.equals(((DeviceStatus) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DeviceStatus{" +
            "id=" + getId() +
            ", deviceNo='" + getDeviceNo() + "'" +
            ", code=" + getCode() +
            ", fourG=" + getFourG() +
            ", term=" + getTerm() +
            ", battery=" + getBattery() +
            ", batMode=" + getBatMode() +
            ", curVersion='" + getCurVersion() + "'" +
            ", workMode=" + getWorkMode() +
            "}";
    }
}
