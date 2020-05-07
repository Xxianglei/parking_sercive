package com.xianglei.park_service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xianglei.park_service.common.ConditionEnum;
import com.xianglei.park_service.common.utils.DateUtils;
import com.xianglei.park_service.common.utils.Tools;
import com.xianglei.park_service.domain.BsOrder;
import com.xianglei.park_service.domain.BsPark;
import com.xianglei.park_service.domain.BsParkInfo;
import com.xianglei.park_service.domain.BsParkVO;
import com.xianglei.park_service.mapper.OrderMapper;
import com.xianglei.park_service.mapper.ParkInfoMapper;
import com.xianglei.park_service.mapper.ParkMapper;
import com.xianglei.park_service.service.ParkingService;
import com.xianglei.park_service.service.RecommendFactory;
import com.xianglei.park_service.service.RecommendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

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
    @Autowired
    ParkInfoMapper parkInfoMapper;

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
    public Map<String, Object> parkInfoDetails(String parkId, String nowDate, String parkInfoId) {
        HashMap<String, Object> map = new HashMap<>();
        Date date = DateUtils.parse(nowDate, "yyyy-MM-dd");
        // 目标一   获取当天剩余车位数量
        List<BsOrder> bsOrders = orderMapper.selectList(new QueryWrapper<BsOrder>()
                .eq("PARK_ID", parkId)
                .ne("CHARGE", 2));
        parkingService.removeNotToday(bsOrders, date);
        BsPark bsPark1 = parkMapper.selectOne(new QueryWrapper<BsPark>().eq("IN_USED", 1).eq("FLOW_ID", parkId));
        Integer volume1 = bsPark1.getVolume();
        map.put("remain", volume1 - bsOrders.size() > 0 ? volume1 - bsOrders.size() : 0);
        //  目标二 获取当天订单的时间区间数组
        List<BsOrder> orderList = orderMapper.selectList(new QueryWrapper<BsOrder>()
                .eq("PARK_ID", parkId)
                .ne("CHARGE", 2)
                .eq("PARK_INFO_ID", parkInfoId));
        ArrayList<HashMap<String, String>> timeNums = new ArrayList<>();
        HashMap<String, String> timeMap;
        Date formatNowDate = DateUtils.parse(nowDate, "yyyy-MM-dd");
        for (BsOrder bsOrder : orderList) {
            Date startTime = bsOrder.getStartTime();
            Date endTime = bsOrder.getLeaveTime();
            Date formatStartTime = DateUtils.parse(DateUtils.format(startTime, "yyyy-MM-dd"), "yyyy-MM-dd");
            Date formatEndTime = DateUtils.parse(DateUtils.format(endTime, "yyyy-MM-dd"), "yyyy-MM-dd");
            String endTime2 = DateUtils.format(endTime, "HH:mm");
            String startTime2 = DateUtils.format(startTime, "HH:mm");
            timeMap = new HashMap<>();
            // 处理时间   只能下24小时范围内的订单
            if (formatStartTime.compareTo(formatNowDate) == 0 && formatEndTime.compareTo(formatNowDate) == 0) {
                timeMap.put("start", startTime2);
                timeMap.put("end", endTime2);
            }
            timeNums.add(timeMap);
        }
        // 获取当前停车场的最小 最大车位号码
        List<BsParkInfo> parkInfoList = parkInfoMapper.selectList(new QueryWrapper<BsParkInfo>().eq("PARK_ID", parkId).orderByDesc("PARK_NUM"));
        map.put("times", timeNums);
        if (Tools.isNotEmpty(parkInfoList)) {
            String maxNum = parkInfoList.get(0).getParkNum();
            String minNum = parkInfoList.get(parkInfoList.size() - 1).getParkNum();
            maxNum = maxNum + "-01-01";
            minNum = minNum + "-01-01";
            map.put("max", maxNum);
            map.put("min", minNum);
        } else {
            map.put("max", "1000-01-01");
            map.put("min", "1000-01-01");
        }
        return map;
    }
}
