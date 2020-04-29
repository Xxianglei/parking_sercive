package com.xianglei.park_service.service.strategy;

import com.xianglei.park_service.domain.BsPark;
import com.xianglei.park_service.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: Xianglei
 * @Company: xxx
 * @Date: 2020/4/28 22:07
 * com.xianglei.park_service.service
 * @Description:
 */
@Service
public class DistanceRecommendStrategy implements RecommendStrategy {
    @Autowired
    ParkingService parkingService;

    @Override
    public List<BsPark> recommend(String userId, Double lng, Double lat) {
        List<BsPark> listOrderByDistance = parkingService.getListOrderByDistance(lng, lat);
        return listOrderByDistance;
    }
}
