package com.xianglei.park_service.controller;

import com.xianglei.park_service.common.BaseJson;
import com.xianglei.park_service.common.ListParamUtils;
import com.xianglei.park_service.common.Tools;
import com.xianglei.park_service.domain.Parking;
import com.xianglei.park_service.service.ParkLotService;
import com.xianglei.park_service.service.commonservice.CommonUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 描述：停车场管理
 * 增删改查
 * 时间：[2019/12/3:16:05]
 * 作者：xianglei
 * params: * @param null
 */
@RestController
@RequestMapping("/parkManger")
public class ParkingController {

    private Logger logger = LoggerFactory.getLogger(ParkingController.class);
    @Autowired
    ParkLotService parkLotService;

    /**
     * 添加停车场 设置信息
     * @param park
     * @return
     */
    @PostMapping("/addPark")
    private BaseJson addPark(@RequestBody Parking park) {
        BaseJson baseJson = new BaseJson(false);
        try {
            int nums = parkLotService.addPark(park);
            baseJson.setMessage("停车场新增成功");
            baseJson.setStatus(true);
            baseJson.setData(nums);
            baseJson.setCode(HttpStatus.OK.value());
            logger.info(park.getFlowId()+":停车场新增成功");
        } catch (Exception e) {
            logger.error("停车场新增接口错误:{}\n堆栈信息:{}", e.getMessage(), e);
            baseJson.setMessage("服务端内部错误:" + e.getMessage());
            baseJson.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return baseJson;
    }

   /* @PostMapping("/deleteUser")
    private BaseJson deleteUser(@RequestBody Map<String, String> map) {
        BaseJson baseJson = new BaseJson(false);
        try {
            if (!Tools.isNull(map)) {
                String flowId = map.get("flowId") == null ? "" : map.get("flowId").toString();
                int nums = userMangerService.deleteUser(flowId);
                if (nums > 0) {
                    baseJson.setMessage("删除成功");
                } else {
                    baseJson.setMessage("没有数据删除");
                    logger.warn("没有数据删除:{}",flowId);
                }
                baseJson.setStatus(true);
                baseJson.setData(nums);
                baseJson.setCode(HttpStatus.OK.value());
            } else {
                baseJson.setStatus(true);
                baseJson.setMessage("参数不能为空");
                logger.warn("参数不能为空");
                baseJson.setCode(HttpStatus.OK.value());
            }
        } catch (Exception e) {
            logger.error("人员删除接口错误:{}\n堆栈信息:{}", e.getMessage(), e);
            baseJson.setMessage("服务端内部错误:" + e.getMessage());
            baseJson.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return baseJson;
    }

    @PostMapping("/updateUser")
    private BaseJson updateUser(@RequestBody User user) {
        BaseJson baseJson = new BaseJson(false);
        try {
            userMangerService.update(user);
            baseJson.setMessage("更新成功");
            baseJson.setStatus(true);
            baseJson.setCode(HttpStatus.OK.value());
            logger.info("数据更新成功");
        } catch (Exception e) {
            logger.error("人员更新接口错误:{}\n堆栈信息:{}", e.getMessage(), e);
            baseJson.setMessage("服务端内部错误:" + e.getMessage());
            baseJson.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return baseJson;
    }

    @PostMapping("/findUser")
    private BaseJson findUser(@RequestBody Map<String, String> map) {
        BaseJson baseJson = new BaseJson(false);
        try {
            if (!Tools.isNull(map)) {
                String flowId = map.get("flowId") == null ? "" : map.get("flowId").toString();
                User user = userMangerService.findUser(flowId);
                baseJson.setMessage("查询成功");
                baseJson.setData(user);
                baseJson.setStatus(true);
                baseJson.setCode(HttpStatus.OK.value());
            } else {
                baseJson.setMessage("参数不可为空");
                baseJson.setStatus(true);
                baseJson.setCode(HttpStatus.OK.value());
                logger.warn("参数不可为空");
            }
        } catch (Exception e) {
            logger.error("人员查询接口错误:{}\n堆栈信息:{}", e.getMessage(), e);
            baseJson.setMessage("服务端内部错误:" + e.getMessage());
            baseJson.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return baseJson;
    }

    @PostMapping("/findAllUser")
    private BaseJson findAllUser(@RequestBody Map<String, Integer> map) {
        BaseJson baseJson = new BaseJson(false);
        try {
            if (!Tools.isNull(map)) {
                int isSuperUser = map.get("isSuperUser") == null ? 0 : map.get("isSuperUser");
                List<User> userList = userMangerService.findAllUser(isSuperUser);
                baseJson.setMessage("查询成功");
                baseJson.setData(userList);
                baseJson.setStatus(true);
                baseJson.setCode(HttpStatus.OK.value());
            } else {
                baseJson.setMessage("你的参数为空");
                baseJson.setStatus(true);
                baseJson.setCode(HttpStatus.OK.value());
                logger.warn("参数不可为空");
            }
        } catch (Exception e) {
            logger.error("人员查询接口错误:{}\n堆栈信息:{}", e.getMessage(), e);
            baseJson.setMessage("服务端内部错误:" + e.getMessage());
            baseJson.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return baseJson;
    }

    @PostMapping("/batchDeleteUser")
    private BaseJson batchDeleteUser(@RequestBody ListParamUtils<String> param) {
        BaseJson baseJson = new BaseJson(false);
        try {
            if (!Tools.isNull(param)) {
                List<String> list = param.getList();
                int success = userMangerService.batchDeleteUser(list);
                if (success > 0) {
                    baseJson.setMessage("批量删除成功");
                    baseJson.setData(success);
                } else {
                    baseJson.setMessage("没有可删除数据");
                    logger.warn("没得数据可以删除");
                }

            } else {
                baseJson.setMessage("参数不能为空");
                logger.warn("参数不能为空");
            }
            baseJson.setStatus(true);
            baseJson.setCode(HttpStatus.OK.value());

        } catch (Exception e) {
            logger.error("人员批量删除接口错误:{}\n堆栈信息:{}", e.getMessage(), e);
            baseJson.setMessage("服务端内部错误:" + e.getMessage());
            baseJson.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return baseJson;
    }

    @PostMapping("/findUserByCondition")
    private BaseJson findUserByCondition(@RequestBody Map<String, Integer> map) {
        BaseJson baseJson = new BaseJson(false);
        try {
            if (!Tools.isNull(map)) {
                int status = map.get("status") == null ? 0 : map.get("status");
                int vip = map.get("vip") == null ? 0 : map.get("vip");
                int sexy = map.get("sexy") == null ? 0 : map.get("sexy");
                List<User> userByCondition = userMangerService.findUserByCondition(status, vip, sexy);
                baseJson.setMessage("查询成功");
                baseJson.setData(userByCondition);
                baseJson.setStatus(true);
                baseJson.setCode(HttpStatus.OK.value());
            } else {
                logger.warn("参数为空");
                baseJson.setMessage("参数为空");
                baseJson.setStatus(true);
                baseJson.setCode(HttpStatus.OK.value());
            }
        } catch (Exception e) {
            logger.error("人员条件接口错误:{}\n堆栈信息:{}", e.getMessage(), e);
            baseJson.setMessage("服务端内部错误:" + e.getMessage());
            baseJson.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return baseJson;
    }*/


}
