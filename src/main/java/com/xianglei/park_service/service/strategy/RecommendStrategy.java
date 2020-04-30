package com.xianglei.park_service.service.strategy;

import com.xianglei.park_service.domain.BsPark;

import java.util.List;

/**
 * @Auther: Xianglei
 * @Company: xxx
 * @Date: 2020/4/28 22:04
 * com.xianglei.park_service.service
 * @Description:推荐策略 顶层抽象
 */
public interface RecommendStrategy {
    /**
     * 根据用户id推荐
     *
     * @param userId
     * @param lng
     * @param lat
     * @param nowDate
     * @return
     */
    List<BsPark> recommend(String userId, Double lng, Double lat, String nowDate);
}
