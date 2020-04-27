package com.xianglei.park_service.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xianglei.park_service.domain.BsMessage;
import org.springframework.stereotype.Repository;

/**
 * @Auther: Xianglei
 * @Company: xxx
 * @Date: 2020/4/17 21:01
 * com.xianglei.account_service.mapper
 * @Description:消息幂等查询接口
 */
@Repository
public interface MessageMapper extends BaseMapper<BsMessage> {
}
