package com.xianglei.park_service.intercepter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xerces.internal.util.HTTPInputSource;
import com.xianglei.park_service.common.BaseJson;
import com.xianglei.park_service.common.Tools;
import com.xianglei.park_service.service.commonservice.CommonUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * 超级用户拦截器
 */
@Configuration
public class MyWebUserIntercepter extends HandlerInterceptorAdapter {
    @Autowired
    CommonUserService commonUserService;
    private Logger logger = LoggerFactory.getLogger(MyWebUserIntercepter.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        BaseJson baseJson = new BaseJson(false);
        HttpSession session = request.getSession();
        Object token = session.getAttribute("flow_Id");
        if (!Tools.isNull(token)) {
            logger.info("通过拦截器");
            return commonUserService.isSuperMan(token.toString());
        } else {
            logger.info("当前请求用户不具备超级管理权限：{}", request.getRemoteHost());
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter writer = response.getWriter();
            ObjectMapper mapper = new ObjectMapper();
            baseJson.setCode(HttpStatus.OK.value());
            baseJson.setMessage("当前请求用户不具备超级管理权限");
            String result = mapper.writeValueAsString(baseJson);
            writer.write(result);
            return false;
        }
    }
}
