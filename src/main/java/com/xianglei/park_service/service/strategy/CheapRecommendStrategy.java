package com.xianglei.park_service.service.strategy;

import com.xianglei.park_service.domain.BsPark;
import com.xianglei.park_service.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther: Xianglei
 * @Company: xxx
 * @Date: 2020/4/28 22:08
 * com.xianglei.park_service.service.impl
 * @Description:
 */
@Service
public class CheapRecommendStrategy implements RecommendStrategy {
    @Autowired
    ParkingService parkingService;

    @Override
    public List<BsPark> recommend(String userId, Double lng, Double lat) {
        List<BsPark> listOrderByPrice = new ArrayList<>();
        // 获取当前时间 判断是否是晚上  低价优先
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("HH");
        String str = df.format(date);
        int time = Integer.parseInt(str);
        if (time >= 6 && time <= 18) {
            // 白天
            listOrderByPrice = parkingService.getListOrderByPrice(true,userId);
        } else {
            // 晚上
            listOrderByPrice = parkingService.getListOrderByPrice(false,userId);
        }
        return listOrderByPrice;
    }
}
