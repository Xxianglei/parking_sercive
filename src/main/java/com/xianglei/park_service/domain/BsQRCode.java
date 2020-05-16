package com.xianglei.park_service.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: Xianglei
 * @Company: xxx
 * @Date: 2020/4/17 20:59
 * com.xianglei.account_service.domain
 * @Description:
 */
@TableName(value = "BS_QRCODE")
public class BsQRCode implements Serializable {
    @TableId("FLOW_ID")
    String flowId;
    @TableField("CREATE_DATE")
    Date createDate;
    @TableField("PARK_ID")
    String parkId;
    @TableField("CONTENT")
    byte[] content;

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getParkId() {
        return parkId;
    }

    public void setParkId(String parkId) {
        this.parkId = parkId;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
