package com.xianglei.park_service.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xianglei.park_service.domain.BsQRCode;
import com.xianglei.park_service.domain.BsUserCar;
import org.springframework.stereotype.Repository;

/**
 * @Auther: Xianglei
 * @Company: xxx
 * @Date: 2020/4/17 21:58
 * com.xianglei.account_service.mapper
 * @Description:二维码mapper
 */
@Repository
public interface QRCodeMapper extends BaseMapper<BsQRCode> {
}
