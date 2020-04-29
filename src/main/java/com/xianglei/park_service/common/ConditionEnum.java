package com.xianglei.park_service.common;

/**
 * @Auther: Xianglei
 * @Company: xxx
 * @Date: 2020/4/28 21:59
 * com.xianglei.park_service.common
 * @Description:
 */
public enum ConditionEnum {
    MULTIPLE("综合排序"),
    DISTANCE("距离优先"),
    SALES("销量优先"),
    SURPLUS("剩余优先"),
    CHEAP("低价优先");
    private final String name;

    ConditionEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
