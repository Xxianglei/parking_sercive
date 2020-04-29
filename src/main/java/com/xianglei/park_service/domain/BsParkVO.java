package com.xianglei.park_service.domain;


import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

/**
 * 前端推荐车位展示
 */

public class BsParkVO implements Serializable, Cloneable {

    private String flowId;

    private String parkName;

    private Integer volume;
    // 剩余
    private Integer remain;

    private String location;

    private Double bPrice;

    private Double yPrice;


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

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public Integer getRemain() {
        return remain;
    }

    public void setRemain(Integer remain) {
        this.remain = remain;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getbPrice() {
        return bPrice;
    }

    public void setbPrice(Double bPrice) {
        this.bPrice = bPrice;
    }

    public Double getyPrice() {
        return yPrice;
    }

    public void setyPrice(Double yPrice) {
        this.yPrice = yPrice;
    }
}