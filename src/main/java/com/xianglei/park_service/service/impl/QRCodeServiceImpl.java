package com.xianglei.park_service.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.binarywang.utils.qrcode.QrcodeUtils;
import com.google.zxing.NotFoundException;
import com.xianglei.park_service.domain.BsQRCode;
import com.xianglei.park_service.domain.QRCodeContent;
import com.xianglei.park_service.mapper.QRCodeMapper;
import com.xianglei.park_service.service.QRCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.security.krb5.internal.PAData;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @Auther: Xianglei
 * @Company: xxx
 * @Date: 2020/5/15 15:25
 * com.xianglei.park_service.service.impl
 * @Description:
 */
@Service
public class QRCodeServiceImpl implements QRCodeService {
    @Autowired
    QRCodeMapper qrCodeMapper;

    @Override
    public int addCode(String Content, String parkId) {
        // 字节数组  存入数据库即可
        byte[] qrcode = QrcodeUtils.createQrcode(Content, null);
        BsQRCode bsQRCode = new BsQRCode();
        bsQRCode.setContent(qrcode);
        bsQRCode.setFlowId(UUID.randomUUID().toString());
        bsQRCode.setParkId(parkId);
        // 二维码入库
        return qrCodeMapper.insert(bsQRCode);
    }

    @Override
    public int deleteCode(String parkId) {
        // 删除二维码
        return qrCodeMapper.delete(new QueryWrapper<BsQRCode>().eq("PARK_ID", parkId));
    }

    @Override
    public QRCodeContent deCode(File file) {
        QRCodeContent qrCodeContent = null;
        try {
            String content = QrcodeUtils.decodeQrcode(file);
            qrCodeContent = JSON.parseObject(content, QRCodeContent.class);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return qrCodeContent;
    }

    @Override
    public byte[] getImage(String parkId) {
        BsQRCode bsQRCode = qrCodeMapper.selectOne(new QueryWrapper<BsQRCode>().eq("PARK_ID", parkId));
        if (bsQRCode != null) {
            return bsQRCode.getContent();
        } else {
            return null;
        }

    }
}
