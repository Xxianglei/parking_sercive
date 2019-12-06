package com.xianglei.park_service.common;

import org.springframework.stereotype.Component;

/**
 * 描述：web参数枚举类
 * 时间：[2019/12/6:10:02]
 * 作者：xianglei
 * params: * @param null
 */
public enum WebParamEnum {

    Token_PARAM(-1, "tokens");

    private Integer code;
    private String message;

    WebParamEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
