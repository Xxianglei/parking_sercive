package com.xianglei.park_service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xianglei.park_service.common.utils.Tools;
import com.xianglei.park_service.domain.BsOrder;
import com.xianglei.park_service.domain.BsPark;
import com.xianglei.park_service.domain.BsParkInfo;
import com.xianglei.park_service.mapper.OrderMapper;
import com.xianglei.park_service.mapper.ParkInfoMapper;
import com.xianglei.park_service.mapper.ParkMapper;
import com.xianglei.park_service.service.ParkingManageService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.BitSet;
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
    @Autowired
    OrderMapper orderMapper;

    @Transactional
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

    @Transactional
    @Override
    public int deleteParkById(String flowId) {
        // 删除车位信息
        int delete = parkInfoMapper.delete(new QueryWrapper<BsParkInfo>().eq("PARK_ID", flowId));
        // 删除停车场信息
        int i = parkMapper.deleteById(flowId);
        return i;
    }

    @Override
    public List<BsParkInfo> getParkInfoListByParkId(String flowId) {
        List<BsParkInfo> parkInfoList = parkInfoMapper.selectList(new QueryWrapper<BsParkInfo>().eq("PARK_ID", flowId).orderByAsc("PARK_NUM"));
        return parkInfoList;
    }

    @Transactional
    @Override
    public int deleteParkInfoByParkId(String parkInfoFlowId) {
        int res = 0;
        // 获取车位详细信息
        BsParkInfo bsParkInfo = parkInfoMapper.selectById(parkInfoFlowId);
        String parkNum = bsParkInfo.getParkNum();
        String parkId = bsParkInfo.getParkId();
        if (Tools.isNotNull(bsParkInfo)) {
            // 删除改车位下的订单
            int delete = orderMapper.delete(new QueryWrapper<BsOrder>().eq("PARK_INFO_ID", parkNum).eq("PARK_ID", parkId));
            // 删除车位
            int i = parkInfoMapper.deleteById(parkInfoFlowId);
            if (i != 0) {
                // 修改容量
                BsPark bsPark = parkMapper.selectById(bsParkInfo.getParkId());
                bsPark.setVolume(bsPark.getVolume() - 1 >= 0 ? bsPark.getVolume() - 1 : 0);
                res = parkMapper.updateById(bsPark);
            }
            return res;
        } else {
            return 0;
        }
    }

    @Transactional
    @Override
    public int addParkInfoListByParkId(BsParkInfo bsParkInfo) {
        // 获取最大编号
        int res = 0;
        Integer max = 0;
        List<BsParkInfo> parkInfoList = parkInfoMapper.selectList(new QueryWrapper<BsParkInfo>().eq("PARK_ID", bsParkInfo.getParkId()).orderByDesc("PARK_NUM"));
        if (Tools.isNotEmpty(parkInfoList)) {
            BsParkInfo bsParkInfo1 = parkInfoList.get(0);
            String parkNum = bsParkInfo1.getParkNum();
            max = Integer.valueOf(parkNum);
            max++;
            // 新增车位
            BsParkInfo bsParkInfo2 = new BsParkInfo();
            bsParkInfo2.setParkNum(String.valueOf(max));
            bsParkInfo2.setParkId(bsParkInfo.getParkId());
            bsParkInfo2.setFlowId(UUID.randomUUID().toString());
            int insert = parkInfoMapper.insert(bsParkInfo2);
            //修改容量
            if (insert != 0) {
                BsPark bsPark = parkMapper.selectById(bsParkInfo.getParkId());
                bsPark.setVolume(bsPark.getVolume() + 1);
                res = parkMapper.updateById(bsPark);
            }
        }
        return res;
    }

    @Override
    public int updateParkInfoByParkId(BsParkInfo bsParkInfo) {
        int res = 0;
        if (Tools.isNotNull(bsParkInfo)) {
            String flowId = bsParkInfo.getFlowId();
            BsParkInfo bsParkInfo1 = parkInfoMapper.selectById(flowId);
            if (Tools.isNotNull(bsParkInfo1)) {
                bsParkInfo1.setLength(bsParkInfo.getLength());
                bsParkInfo1.setWidth(bsParkInfo.getWidth());
                int update = parkInfoMapper.updateById(bsParkInfo1);
                res = update;
            }
        } else {
            return 0;
        }
        return res;
    }

    @Override
    public List<BsPark> getParks(String parkName) {
        List<BsPark> bsParks = parkMapper.selectList(new QueryWrapper<BsPark>().like("PARK_NAME", parkName));
        return bsParks;
    }
}
