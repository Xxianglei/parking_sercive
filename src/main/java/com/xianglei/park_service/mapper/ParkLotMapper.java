package com.xianglei.park_service.mapper;

import com.xianglei.park_service.domain.Parking;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ParkLotMapper {
    int insertPark(@Param("park") Parking park);
}
