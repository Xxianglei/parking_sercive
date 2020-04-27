package com.xianglei.park_service.controller;

import com.xianglei.park_service.common.BaseJson;
import com.xianglei.park_service.common.utils.Tools;
import com.xianglei.park_service.domain.BsPark;
import com.xianglei.park_service.service.ParkingService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @Auther: Xianglei
 * @Company: xxx
 * @Date: 2020/4/27 16:20
 * com.xianglei.account_service.controller
 * @Description: 后台接口
 */
@RestController
@RequestMapping("/back")
public class BackController {
    @Autowired
    ParkingService parkingService;


}
