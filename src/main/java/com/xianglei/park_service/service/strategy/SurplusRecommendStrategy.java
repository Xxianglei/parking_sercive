package com.xianglei.park_service.service.strategy;

import com.xianglei.park_service.domain.BsPark;
import com.xianglei.park_service.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: Xianglei
 * @Company: xxx
 * @Date: 2020/4/28 22:08
 * com.xianglei.park_service.service.impl
 * @Description:
 */
@Service
public class SurplusRecommendStrategy  implements RecommendStrategy {
    @Autowired
    ParkingService parkingService;
    @Override
    public List<BsPark> recommend(String userId, Double lng, Double lat, String nowDate) {
        List<BsPark> listOrderByVolume = parkingService.getListOrderByVolume();
        return listOrderByVolume;
    }
}
