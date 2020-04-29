package com.xianglei.park_service.service;


import com.xianglei.park_service.domain.BsPark;

import java.util.List;

/**
 * 停车场管理
 */
public interface ParkingService {

    /**
     * 根据价位降序排序
     *
     * @return
     */
    List<BsPark> getListOrderByPrice(Boolean isDay, String userId);

    /**
     * 根据剩余降序排序
     *
     * @return
     */
    List<BsPark> getListOrderByVolume();

    /**
     * 根据价位销量排序
     *
     * @return
     */
    List<BsPark> getListOrderBySale();

    /**
     * 根据距离降序排序
     *
     * @return
     */
    List<BsPark> getListOrderByDistance(Double lng, Double lat);

    /**
     * 根据价位降序排序
     *
     * @return
     */
    List<BsPark> getListOrderByMultiple(String userId, Double lng, Double lat);
}