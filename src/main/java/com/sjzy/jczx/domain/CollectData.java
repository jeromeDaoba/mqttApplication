package com.sjzy.jczx.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CollectData.
 */
@Entity
@Table(name = "collect_data")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CollectData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "device_no")
    private String deviceNo;

    @Column(name = "time")
    private Long time;

    @Column(name = "x_val")
    private String xVal;

    @Column(name = "y_val")
    private String yVal;

    @Column(name = "all_vals")
    private String allVals;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CollectData id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceNo() {
        return this.deviceNo;
    }

    public CollectData deviceNo(String deviceNo) {
        this.setDeviceNo(deviceNo);
        return this;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public Long getTime() {
        return this.time;
    }

    public CollectData time(Long time) {
        this.setTime(time);
        return this;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getxVal() {
        return this.xVal;
    }

    public CollectData xVal(String xVal) {
        this.setxVal(xVal);
        return this;
    }

    public void setxVal(String xVal) {
        this.xVal = xVal;
    }

    public String getyVal() {
        return this.yVal;
    }

    public CollectData yVal(String yVal) {
        this.setyVal(yVal);
        return this;
    }

    public void setyVal(String yVal) {
        this.yVal = yVal;
    }

    public String getAllVals() {
        return this.allVals;
    }

    public CollectData allVals(String allVals) {
        this.setAllVals(allVals);
        return this;
    }

    public void setAllVals(String allVals) {
        this.allVals = allVals;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CollectData)) {
            return false;
        }
        return id != null && id.equals(((CollectData) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CollectData{" +
            "id=" + getId() +
            ", deviceNo='" + getDeviceNo() + "'" +
            ", time=" + getTime() +
            ", xVal='" + getxVal() + "'" +
            ", yVal='" + getyVal() + "'" +
            ", allVals='" + getAllVals() + "'" +
            "}";
    }
}
