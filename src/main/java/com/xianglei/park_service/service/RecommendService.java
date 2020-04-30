package com.xianglei.park_service.service;

import com.xianglei.park_service.domain.BsPark;
import com.xianglei.park_service.domain.BsParkVO;

import java.util.List;

/**
 * @Auther: Xianglei
 * @Company: xxx
 * @Date: 2020/4/28 21:55
 * com.xianglei.park_service.service
 * @Description:推荐
 */
public interface RecommendService {
    /**
     * 获取对应推荐策略 并返回推荐列表
     *
     * @param userId
     * @param condition
     * @param lng
     * @param lat
     * @param nowDate
     * @return
     */
    List<BsPark> findStrategyThenRecommend(String userId,
                                           String condition,
                                           Double lng,
                                           Double lat, String nowDate);

    /**
     * 处理推荐列表数据
     *
     * @param strategyThenRecommend
     * @return
     */
    List<BsParkVO> formatData(List<BsPark> strategyThenRecommend);
}
