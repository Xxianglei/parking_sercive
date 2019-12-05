package com.xianglei.park_service.service.impl;

import com.xianglei.park_service.domain.Parking;
import com.xianglei.park_service.mapper.ParkLotMapper;
import com.xianglei.park_service.service.ParkLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParkLotServiceImpl implements ParkLotService {

    @Autowired
    ParkLotMapper parkLotMapper;

    @Override
    public int addPark(Parking park) {
        return 0;
    }
}
