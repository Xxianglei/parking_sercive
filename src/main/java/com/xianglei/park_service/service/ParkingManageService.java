package com.xianglei.park_service.service;

import com.xianglei.park_service.domain.BsPark;

import java.util.List;

/**
 * @Auther: Xianglei
 * @Company: xxx
 * @Date: 2020/5/7 13:41
 * com.xianglei.park_service.service
 * @Description:停车场管理service
 */
public interface ParkingManageService {
    /**
     * 停车场新增
     *
     * @param map
     * @return
     */
    int addPark(BsPark map);

    /**
     * 按照创建时间排序获取停车场列表
     *
     * @return
     */
    List<BsPark> queryParkList();

    /**
     * 根据ID 更新停车场信息
     *
     * @param bsPark
     * @return
     */
    int updateParkById(BsPark bsPark);

    /**
     * 根据ID删除停车场
     * @param flowId
     * @return
     */
    int deleteParkById(String flowId);

}
