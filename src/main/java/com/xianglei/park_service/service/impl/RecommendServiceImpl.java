package com.xianglei.park_service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xianglei.park_service.common.ConditionEnum;
import com.xianglei.park_service.common.utils.DateUtils;
import com.xianglei.park_service.domain.BsOrder;
import com.xianglei.park_service.domain.BsPark;
import com.xianglei.park_service.domain.BsParkVO;
import com.xianglei.park_service.domain.PreBsOrder;
import com.xianglei.park_service.mapper.OrderMapper;
import com.xianglei.park_service.mapper.ParkMapper;
import com.xianglei.park_service.service.ParkingService;
import com.xianglei.park_service.service.RecommendFactory;
import com.xianglei.park_service.service.RecommendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
    @Autowired
    ParkingService parkingService;
    @Autowired
    ParkMapper parkMapper;

    @Override
    public List<BsPark> findStrategyThenRecommend(String userId, String condition, Double lng, Double lat, String nowDate) {
        List<BsPark> recommend = new ArrayList<>();
        recommendFactory.setParams(userId, lng, lat, nowDate);
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
            /**
             * 获取当天车位容量使用情况
             */
            List<BsOrder> bsOrders = orderMapper.selectList(new QueryWrapper<BsOrder>()
                    .eq("PARK_ID", bsPark.getFlowId())
                    .ne("CHARGE", 2));
            parkingService.removeNotToday(bsOrders, new Date());
            Integer re = volume - bsOrders.size();
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

    @Override
    public BsParkVO parkInfoDetails(String parkId, String nowDate) {
        Date date = DateUtils.parse(nowDate, "yyyy-MM-dd");
        List<BsOrder> bsOrders = orderMapper.selectList(new QueryWrapper<BsOrder>()
                .eq("PARK_ID", parkId)
                .ne("CHARGE", 2));
        parkingService.removeNotToday(bsOrders, date);
        BsPark bsPark1 = parkMapper.selectOne(new QueryWrapper<BsPark>().eq("IN_USED", 1).eq("FLOW_ID", parkId));
        Integer volume1 = bsPark1.getVolume();
        BsParkVO bsParkVO = new BsParkVO();
        bsParkVO.setRemain(volume1 - bsOrders.size() > 0 ? volume1 - bsOrders.size() : 0);
        return bsParkVO;
    }
}
