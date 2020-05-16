package com.xianglei.park_service.domain;

/**
 * @Auther: Xianglei
 * @Company: xxx
 * @Date: 2020/5/15 15:44
 * com.xianglei.park_service.domain
 * @Description: 二维码实体类
 */
public class QRCodeContent {
    String parkName;
    String parkId;

    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName;
    }

    public String getParkId() {
        return parkId;
    }

    public void setParkId(String parkId) {
        this.parkId = parkId;
    }
}
