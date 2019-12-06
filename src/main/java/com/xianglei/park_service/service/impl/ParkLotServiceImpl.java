package com.xianglei.park_service.service.impl;

import com.xianglei.park_service.domain.Parking;
import com.xianglei.park_service.mapper.ParkLotMapper;
import com.xianglei.park_service.service.ParkLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ParkLotServiceImpl implements ParkLotService {

    @Autowired
    ParkLotMapper parkLotMapper;

    @Override
    public int addPark(Parking park) {
        UUID uuid = UUID.randomUUID();
        park.setFlowId(uuid.toString());
        int result = parkLotMapper.insertPark(park);
        return result;
    }
}
