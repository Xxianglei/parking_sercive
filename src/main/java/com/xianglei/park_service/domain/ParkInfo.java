package com.xianglei.park_service.domain;

import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.io.Serializable;
/**
 * 描述：车位具体信息
 * 时间：[2019/12/3:16:28]
 * 作者：xianglei
 * params: * @param null
 *   `FLOW_ID` varchar(64) NOT NULL,
 *   `PARK_NUM` int(8) DEFAULT NULL,
 *   `STATUS` int(1) DEFAULT NULL COMMENT '0/1/2 不可用/有车/预约',
 *   `LENGTH` double DEFAULT NULL,
 *   `WIDTH` double DEFAULT NULL,
 *   `PARK_ID` varchar(64) DEFAULT NULL,
 */
@Component
public class ParkInfo implements Serializable {
    private static final long serialVersionUID = -4743527388263409694L;
    private String flowId;
    private String parkId;
    private int parkNum;
    private int status;
    private double length;
    private double width;

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getParkId() {
        return parkId;
    }

    public void setParkId(String parkId) {
        this.parkId = parkId;
    }

    public int getParkNum() {
        return parkNum;
    }

    public void setParkNum(int parkNum) {
        this.parkNum = parkNum;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }
}
