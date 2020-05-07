package com.xianglei.park_service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.xianglei.park_service.common.utils.DateUtils;
import com.xianglei.park_service.common.utils.Tools;
import com.xianglei.park_service.domain.BsPark;
import com.xianglei.park_service.domain.BsParkInfo;
import com.xianglei.park_service.mapper.ParkInfoMapper;
import com.xianglei.park_service.mapper.ParkMapper;
import com.xianglei.park_service.service.ParkingManageService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.plugin.util.UIUtil;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Auther: Xianglei
 * @Company: xxx
 * @Date: 2020/5/7 13:41
 * com.xianglei.park_service.service.impl
 * @Description:
 */
@Service
public class ParkingManageServiceImpl implements ParkingManageService {
    @Autowired
    ParkMapper parkMapper;
    @Autowired
    ParkInfoMapper parkInfoMapper;

    @Override
    public int addPark(BsPark map) {
        String parkId = UUID.randomUUID().toString();
        map.setFlowId(parkId);
        int insert = parkMapper.insert(map);
        Integer volume = map.getVolume();
        int startIndexNum = 1000;
        if (Tools.isNotNull(volume) && volume > 0) {
            // 批量田间默认车位信息
            for (int i = 0; i < volume; i++) {
                UUID uuid = UUID.randomUUID();
                BsParkInfo bsParkInfo = new BsParkInfo();
                bsParkInfo.setFlowId(uuid.toString());
                bsParkInfo.setParkId(parkId);
                bsParkInfo.setParkNum(String.valueOf(startIndexNum));
                parkInfoMapper.insert(bsParkInfo);
                startIndexNum++;
            }
        }
        return insert;
    }

    @Override
    public List<BsPark> queryParkList() {
        List<BsPark> bsParkList = parkMapper.selectList(new QueryWrapper<BsPark>().orderByDesc("CREATE_DATE"));
        return bsParkList;
    }

    @Override
    public int updateParkById(BsPark bsPark) {
        String flowId = bsPark.getFlowId();
        if (StringUtils.isNotEmpty(flowId)) {
            // 默认容量不可修改 只能删除后重新添加
            BsPark selectById = parkMapper.selectById(bsPark.getFlowId());
            Integer oldVolume = selectById.getVolume();
            bsPark.setVolume(oldVolume);
            int i = parkMapper.updateById(bsPark);
            return i;
        } else {
            return 0;
        }
    }

    @Override
    public int deleteParkById(String flowId) {
        // 删除车位信息
        int delete = parkInfoMapper.delete(new QueryWrapper<BsParkInfo>().eq("PARK_ID", flowId));
        if (delete != 0) {
            // 删除停车场信息
            int i = parkMapper.deleteById(flowId);
            return i;
        } else {
            return 0;
        }
    }
}
