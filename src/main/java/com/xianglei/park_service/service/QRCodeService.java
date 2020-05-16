package com.xianglei.park_service.service;

import com.xianglei.park_service.domain.QRCodeContent;

import java.io.File;

/**
 * @Auther: Xianglei
 * @Company: xxx
 * @Date: 2020/5/15 15:24
 * com.xianglei.park_service.service
 * @Description:二维码生成
 */
public interface QRCodeService {

    int addCode(String Content, String parkId);

    int deleteCode(String parkId);

    QRCodeContent deCode(File file);

    byte[] getImage(String parkId);
}
