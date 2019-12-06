package com.xianglei.park_service.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 描述：redis中获取token工具类封装
 * 时间：[2019/12/6:9:58]
 * 作者：xianglei
 * params: * @param null
 */
@Component
public class GetTokenFromRedisUtils {

    public static String getTokenFromRedis(HttpServletRequest request, HttpServletResponse response,RedisTemplate redisTemplate) {
        String result = null;
        String header = request.getHeader(WebParamEnum.Token_PARAM.getMessage());
        if (!StringUtils.isEmpty(header)) {
            Object token = redisTemplate.opsForValue().get(header);
            if (!StringUtils.isEmpty(token)) {
                result = (String) token;
            }
        }
        return result;
    }
}
