package com.xianglei.park_service.service.commonservice;

import com.xianglei.park_service.domain.Parking;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "common-service")
public interface CommonUserService {
    @RequestMapping(value = "/user/checkSuper")
    Boolean isSuperMan(@RequestParam(value = "flowId") String flowId);
}
