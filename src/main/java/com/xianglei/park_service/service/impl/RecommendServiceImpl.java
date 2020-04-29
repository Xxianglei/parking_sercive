package com.xianglei.park_service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xianglei.park_service.common.ConditionEnum;
import com.xianglei.park_service.domain.BsOrder;
import com.xianglei.park_service.domain.BsPark;
import com.xianglei.park_service.domain.BsParkVO;
import com.xianglei.park_service.mapper.OrderMapper;
import com.xianglei.park_service.service.RecommendFactory;
import com.xianglei.park_service.service.RecommendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Xianglei
 * @Company: xxx
 * @Date: 2020/4/28 21:55
 * com.xianglei.park_service.service.impl
 * @Description:
 */
@Service
public class RecommendServiceImpl implements RecommendService {
    private Logger logger = LoggerFactory.getLogger(RecommendServiceImpl.class);
    @Autowired
    RecommendFactory recommendFactory;
    @Autowired
    OrderMapper orderMapper;

    @Override
    public List<BsPark> findStrategyThenRecommend(String userId, String condition, Double lng, Double lat) {
        List<BsPark> recommend = new ArrayList<>();
        recommendFactory.setParams(userId, lng, lat);
        if (ConditionEnum.CHEAP.getName().equals(condition)) {
            recommend = recommendFactory.recommend(ConditionEnum.CHEAP);
        }
        if (ConditionEnum.DISTANCE.getName().equals(condition)) {
            recommend = recommendFactory.recommend(ConditionEnum.DISTANCE);
        }
        if (ConditionEnum.SALES.getName().equals(condition)) {
            recommend = recommendFactory.recommend(ConditionEnum.SALES);
        }
        if (ConditionEnum.SURPLUS.getName().equals(condition)) {
            recommend = recommendFactory.recommend(ConditionEnum.SURPLUS);
        }
        if (ConditionEnum.MULTIPLE.getName().equals(condition)) {
            recommend = recommendFactory.recommend(ConditionEnum.MULTIPLE);
        }
        return recommend;
    }

    @Override
    public List<BsParkVO> formatData(List<BsPark> strategyThenRecommend) {
        ArrayList<BsParkVO> bsParkVOS = new ArrayList<>();
        for (BsPark bsPark : strategyThenRecommend) {
            BsParkVO bsParkVO = new BsParkVO();
            Integer volume = bsPark.getVolume();
            Integer nums = orderMapper.selectCount(new QueryWrapper<BsOrder>().eq("PARK_ID", bsPark.getFlowId()).ne("CHARGE", 2));
            Integer re = volume - nums;
            bsParkVO.setFlowId(bsPark.getFlowId());
            bsParkVO.setRemain(re > 0 ? re : 0);
            bsParkVO.setLocation(bsPark.getLocation());
            bsParkVO.setbPrice(bsPark.getbPrice());
            bsParkVO.setyPrice(bsPark.getyPrice());
            bsParkVO.setParkName(bsPark.getParkName());
            bsParkVO.setVolume(bsPark.getVolume());
            bsParkVOS.add(bsParkVO);
        }
        return bsParkVOS;
    }
}