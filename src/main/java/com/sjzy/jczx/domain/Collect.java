package com.sjzy.jczx.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Collect.
 */
@Entity
@Table(name = "collect")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Collect implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "out_side_id")
    private String outSideId;

    @Column(name = "name")
    private String name;

    @Column(name = "time")
    private Long time;

    @Column(name = "data")
    private String data;

    @Column(name = "project_id")
    private String projectId;

    @Column(name = "channel_count")
    private Integer channelCount;

    @Column(name = "plus_interval")
    private Integer plusInterval;

    @Column(name = "upload_interval")
    private Integer uploadInterval;

    @Column(name = "hz")
    private Integer hz;

    @Column(name = "device_no")
    private String deviceNo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Collect id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOutSideId() {
        return this.outSideId;
    }

    public Collect outSideId(String outSideId) {
        this.setOutSideId(outSideId);
        return this;
    }

    public void setOutSideId(String outSideId) {
        this.outSideId = outSideId;
    }

    public String getName() {
        return this.name;
    }

    public Collect name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTime() {
        return this.time;
    }

    public Collect time(Long time) {
        this.setTime(time);
        return this;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getData() {
        return this.data;
    }

    public Collect data(String data) {
        this.setData(data);
        return this;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getProjectId() {
        return this.projectId;
    }

    public Collect projectId(String projectId) {
        this.setProjectId(projectId);
        return this;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Integer getChannelCount() {
        return this.channelCount;
    }

    public Collect channelCount(Integer channelCount) {
        this.setChannelCount(channelCount);
        return this;
    }

    public void setChannelCount(Integer channelCount) {
        this.channelCount = channelCount;
    }

    public Integer getPlusInterval() {
        return this.plusInterval;
    }

    public Collect plusInterval(Integer plusInterval) {
        this.setPlusInterval(plusInterval);
        return this;
    }

    public void setPlusInterval(Integer plusInterval) {
        this.plusInterval = plusInterval;
    }

    public Integer getUploadInterval() {
        return this.uploadInterval;
    }

    public Collect uploadInterval(Integer uploadInterval) {
        this.setUploadInterval(uploadInterval);
        return this;
    }

    public void setUploadInterval(Integer uploadInterval) {
        this.uploadInterval = uploadInterval;
    }

    public Integer getHz() {
        return this.hz;
    }

    public Collect hz(Integer hz) {
        this.setHz(hz);
        return this;
    }

    public void setHz(Integer hz) {
        this.hz = hz;
    }

    public String getDeviceNo() {
        return this.deviceNo;
    }

    public Collect deviceNo(String deviceNo) {
        this.setDeviceNo(deviceNo);
        return this;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Collect)) {
            return false;
        }
        return id != null && id.equals(((Collect) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Collect{" +
            "id=" + getId() +
            ", outSideId='" + getOutSideId() + "'" +
            ", name='" + getName() + "'" +
            ", time=" + getTime() +
            ", data='" + getData() + "'" +
            ", projectId='" + getProjectId() + "'" +
            ", channelCount=" + getChannelCount() +
            ", plusInterval=" + getPlusInterval() +
            ", uploadInterval=" + getUploadInterval() +
            ", hz=" + getHz() +
            ", deviceNo='" + getDeviceNo() + "'" +
            "}";
    }
}
