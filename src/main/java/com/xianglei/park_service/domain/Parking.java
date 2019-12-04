package com.xianglei.park_service.domain;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 停车场类
 * `FLOW_ID` varchar(64) NOT NULL,
 * `PARK_NAME` varchar(64) DEFAULT NULL,
 * `B_PRICE` decimal(8,2) DEFAULT NULL,
 * `Y_PRICE` decimal(8,2) DEFAULT NULL,
 * `V_PRICE` decimal(8,2) DEFAULT NULL,
 * `VOLUME` int(8) DEFAULT NULL,
 * `IN_USED` int(8) DEFAULT NULL,
 * `LOCATION` varchar(64) DEFAULT NULL,
 * `JD` decimal(10,4) DEFAULT NULL,
 * `WD` decimal(10,4) DEFAULT NULL,
 * `CREATE_DATE` date DEFAULT NULL,
 * `STRATAGE` varchar(64) DEFAULT NULL COMMENT '计费策略',
 *
 * @author xianglei
 */
@Component
public class Parking implements Serializable {
    private String flowId;
    private String parkName;
    private BigDecimal bPrice;
    private BigDecimal yPrice;
    private BigDecimal vPrice;
    private int volume;
    private int inUsed;
    private String location;
    private BigDecimal jd;
    private BigDecimal wd;
    private Date createDate;
    private String stratage;
    private static final long serialVersionUID = 7572953960351838173L;

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName;
    }

    public BigDecimal getbPrice() {
        return bPrice;
    }

    public void setbPrice(BigDecimal bPrice) {
        this.bPrice = bPrice;
    }

    public BigDecimal getyPrice() {
        return yPrice;
    }

    public void setyPrice(BigDecimal yPrice) {
        this.yPrice = yPrice;
    }

    public BigDecimal getvPrice() {
        return vPrice;
    }

    public void setvPrice(BigDecimal vPrice) {
        this.vPrice = vPrice;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getInUsed() {
        return inUsed;
    }

    public void setInUsed(int inUsed) {
        this.inUsed = inUsed;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public BigDecimal getJd() {
        return jd;
    }

    public void setJd(BigDecimal jd) {
        this.jd = jd;
    }

    public BigDecimal getWd() {
        return wd;
    }

    public void setWd(BigDecimal wd) {
        this.wd = wd;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getStratage() {
        return stratage;
    }

    public void setStratage(String stratage) {
        this.stratage = stratage;
    }
}
