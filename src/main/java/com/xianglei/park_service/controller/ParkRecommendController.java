package com.xianglei.park_service.controller;

import com.xianglei.park_service.common.BaseJson;
import com.xianglei.park_service.common.utils.Tools;
import com.xianglei.park_service.domain.BsPark;
import com.xianglei.park_service.domain.BsParkVO;
import com.xianglei.park_service.domain.PreBsOrder;
import com.xianglei.park_service.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Xianglei
 * @Company: xxx
 * @Date: 2020/4/28 21:49
 * com.xianglei.park_service.controller
 * @Description: 车位推荐算法
 */
@RestController
@RequestMapping("/api")
public class ParkRecommendController {
    @Autowired
    RecommendService recommendService;

    /**
     * 推荐算法
     * 综合推荐
     * 距离优先
     * 销量优先
     * 剩余优先
     * 低价优先
     *
     * @param userId
     * @param condition
     * @return
     */
    @RequestMapping("/getPark")
    public BaseJson recommendPark(@RequestParam(value = "userId") String userId,
                                  @RequestParam(value = "condition") String condition,
                                  @RequestParam(required = false) Double lng,
                                  @RequestParam(required = false) Double lat,
                                  @RequestParam(required = false) String nowDate) {
        BaseJson baseJson = new BaseJson(false);
        try {
            List<BsPark> strategyThenRecommend = recommendService.findStrategyThenRecommend(userId, condition, lng, lat, nowDate);
            if (Tools.isNotEmpty(strategyThenRecommend)) {
                List<BsParkVO> result = recommendService.formatData(strategyThenRecommend);
                baseJson.setStatus(true);
                baseJson.setMessage("获取成功");
                baseJson.setCode(200);
                baseJson.setData(result);
            } else {
                baseJson.setMessage("获取失败");
                baseJson.setCode(500);
            }
        } catch (Exception e) {
            baseJson.setMessage("获取失败" + e);
            baseJson.setCode(500);
        }

        return baseJson;
    }

    /**
     * 获取当天剩余车位数量
     * 获取当天订单的时间区间数组
     * @param parkId
     * @param nowDate
     * @return
     */
    @RequestMapping("/getDetails")
    public BaseJson parkInfoDetails(@RequestParam(value = "parkId") String parkId,
                                    @RequestParam(required = false) String nowDate,
                                    @RequestParam(value = "parkInfoId") String parkInfoId) {
        BaseJson baseJson = new BaseJson(false);
        try {
            Map<String,Object> strategyThenRecommend = recommendService.parkInfoDetails(parkId, nowDate,parkInfoId);
            if (Tools.isNotNull(strategyThenRecommend)) {
                baseJson.setStatus(true);
                baseJson.setMessage("获取成功");
                baseJson.setCode(200);
                baseJson.setData(strategyThenRecommend);
            } else {
                baseJson.setMessage("获取失败");
                baseJson.setCode(500);
            }
        } catch (Exception e) {
            baseJson.setMessage("获取失败" + e);
            baseJson.setCode(500);
        }

        return baseJson;
    }

}
