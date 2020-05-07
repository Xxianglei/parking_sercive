package com.xianglei.park_service.controller;

import com.xianglei.park_service.common.BaseJson;
import com.xianglei.park_service.common.utils.Tools;
import com.xianglei.park_service.domain.BsPark;
import com.xianglei.park_service.service.ParkingManageService;
import com.xianglei.park_service.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: Xianglei
 * @Company: xxx
 * @Date: 2020/4/27 16:20
 * com.xianglei.account_service.controller
 * @Description: 停车场后台管理接口  包括停车场的增删改查  车位信息的增删改查
 */
@RestController
@RequestMapping("/back")
public class BackController {
    @Autowired
    ParkingService parkingService;
    @Autowired
    ParkingManageService ParkingManageService;

    /**
     * 停车位信息添加
     *
     * @param map
     * @return
     */
    @PostMapping("/addPark")
    public BaseJson addPark(@RequestBody BsPark map) {
        BaseJson baseJson = new BaseJson(false);
        try {
            if (Tools.isNotNull(map)) {
                int nums = ParkingManageService.addPark(map);
                if (nums != 0) {
                    baseJson.setStatus(true);
                    baseJson.setMessage("新增成功");
                    baseJson.setCode(200);
                } else {
                    baseJson.setMessage("新增失败");
                    baseJson.setCode(500);
                }
            } else {
                baseJson.setMessage("参数不可为空");
                baseJson.setCode(500);
            }
        } catch (Exception e) {
            baseJson.setMessage("新增停车场内部异常导致失败" + e);
            baseJson.setCode(500);
        }
        return baseJson;
    }

    /**
     * 停车场列表查看
     *
     * @param
     * @return
     */
    @RequestMapping("/parkList")
    public BaseJson parkList() {
        BaseJson baseJson = new BaseJson(false);
        try {
            List<BsPark> bsParkList = ParkingManageService.queryParkList();
            if (Tools.isNotEmpty(bsParkList)) {
                baseJson.setStatus(true);
                baseJson.setMessage("查询成功");
                baseJson.setCode(200);
                baseJson.setData(bsParkList);
            } else {
                baseJson.setMessage("停车场列表为空");
                baseJson.setCode(500);
            }

        } catch (Exception e) {
            baseJson.setMessage("停车场列表查看内部异常导致失败" + e);
            baseJson.setCode(500);
        }
        return baseJson;
    }

    /**
     * 停车场信息更新
     *
     * @param
     * @return
     */
    @PostMapping("/updatePark")
    public BaseJson updatePark(@RequestBody BsPark bsPark) {
        BaseJson baseJson = new BaseJson(false);
        try {
            int result = ParkingManageService.updateParkById(bsPark);
            if (result != 0) {
                baseJson.setStatus(true);
                baseJson.setMessage("更新成功");
                baseJson.setCode(200);
            } else {
                baseJson.setMessage("更新失败");
                baseJson.setCode(500);
            }

        } catch (Exception e) {
            baseJson.setMessage("停车场列表更新内部异常导致失败" + e);
            baseJson.setCode(500);
        }
        return baseJson;
    }

    /**
     * 停车场删除
     *
     * @param
     * @return
     */
    @RequestMapping("/deletePark")
    public BaseJson deletePark(@RequestParam String flowId) {
        BaseJson baseJson = new BaseJson(false);
        try {
            int result = ParkingManageService.deleteParkById(flowId);
            if (result != 0) {
                baseJson.setStatus(true);
                baseJson.setMessage("删除成功");
                baseJson.setCode(200);
            } else {
                baseJson.setMessage("删除失败");
                baseJson.setCode(500);
            }

        } catch (Exception e) {
            baseJson.setMessage("停车场列表删除内部异常导致失败" + e);
            baseJson.setCode(500);
        }
        return baseJson;
    }
}
