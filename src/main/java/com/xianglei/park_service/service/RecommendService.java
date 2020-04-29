package com.xianglei.park_service.service;

import com.xianglei.park_service.common.ConditionEnum;
import com.xianglei.park_service.domain.BsPark;
import com.xianglei.park_service.domain.BsParkVO;
import org.springframework.web.bind.annotation.RequestParam;

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
     * @return
     */
    List<BsPark> findStrategyThenRecommend(String userId,
                                           String condition,
                                           Double lng,
                                           Double lat);

    /**
     * 处理推荐列表数据
     *
     * @param strategyThenRecommend
     * @return
     */
    List<BsParkVO> formatData(List<BsPark> strategyThenRecommend);
}
