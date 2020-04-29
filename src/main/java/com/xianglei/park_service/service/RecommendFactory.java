package com.xianglei.park_service.service;

import com.xianglei.park_service.common.ConditionEnum;
import com.xianglei.park_service.domain.BsPark;
import com.xianglei.park_service.service.strategy.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Xianglei
 * @Company: xxx
 * @Date: 2020/4/28 22:03
 * com.xianglei.park_service.service
 * @Description:推荐策略匹配工厂
 */
@Component
public class RecommendFactory {

    String userId;
    Double lng;
    Double lat;
    @Autowired
    CheapRecommendStrategy cheapRecommendStrategy;
    @Autowired
    SalesRecommendStrategy salesRecommendStrategy;
    @Autowired
    SurplusRecommendStrategy surplusRecommendStrategy;
    @Autowired
    DistanceRecommendStrategy distanceRecommendStrategy;
    @Autowired
    MultipleRecommendStrategy multipleRecommendStrategy;

    public void setParams(String userId, Double lng, Double lat) {
        this.userId = userId;
        this.lng = lng;
        this.lat = lat;
    }


    public List<BsPark> recommend(ConditionEnum conditionEnum) {
        List<BsPark> bsParks;
        switch (conditionEnum) {
            case CHEAP:
                bsParks = cheapRecommendStrategy.recommend(userId, lng, lat);
                break;
            case SALES:
                bsParks = salesRecommendStrategy.recommend(userId, lng, lat);
                break;
            case SURPLUS:
                bsParks = surplusRecommendStrategy.recommend(userId, lng, lat);
                break;
            case DISTANCE:
                bsParks = distanceRecommendStrategy.recommend(userId, lng, lat);
                break;
            case MULTIPLE:
                bsParks = multipleRecommendStrategy.recommend(userId, lng, lat);
                break;
            default:
                bsParks = new ArrayList<>();
                break;
        }

        return bsParks;
    }

}
