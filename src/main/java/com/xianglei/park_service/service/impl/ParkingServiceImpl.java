package com.xianglei.park_service.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.xianglei.park_service.common.utils.DateUtils;
import com.xianglei.park_service.domain.BsOrder;
import com.xianglei.park_service.domain.BsPark;
import com.xianglei.park_service.domain.BsUser;
import com.xianglei.park_service.mapper.OrderMapper;
import com.xianglei.park_service.mapper.ParkMapper;
import com.xianglei.park_service.mapper.UserMapper;
import com.xianglei.park_service.service.ParkingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 计费管理service
 */
@Service
public class ParkingServiceImpl implements ParkingService {
    private Logger logger = LoggerFactory.getLogger(ParkingServiceImpl.class);
    @Autowired
    ParkMapper parkMapper;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    UserMapper userMapper;

    @Override
    public List<BsPark> getListOrderByPrice(Boolean isDay, String userId) {
        List<BsPark> bsParks;
        BsUser bsUser = userMapper.selectOne(new QueryWrapper<BsUser>().eq("FLOW_ID", userId));
        // 0 会员  1 或空非会员
        String vip = bsUser.getVip();
        if (isDay) {
            //1 或空非会员
            if (!"0".equals(vip)) {
                bsParks = parkMapper.selectList(new QueryWrapper<BsPark>().eq("IN_USED", 1).orderByAsc("B_PRICE"));
            } else {
                // 会员价格
                bsParks = parkMapper.selectList(new QueryWrapper<BsPark>().eq("IN_USED", 1).orderByAsc("B_PRICE"));
                bsParks.sort(new Comparator<BsPark>() {
                    @Override
                    public int compare(BsPark o1, BsPark o2) {
                        Double v2 = o2.getbPrice() * o2.getvPrice();
                        Double v1 = o2.getbPrice() * o2.getvPrice();
                        return v2.compareTo(v1);
                    }
                });
            }
            return bsParks;
        } else {
            //1 或空非会员
            if (!"0".equals(vip)) {
                bsParks = parkMapper.selectList(new QueryWrapper<BsPark>().eq("IN_USED", 1).orderByDesc("Y_PRICE"));
            } else {
                // 会员价格
                bsParks = parkMapper.selectList(new QueryWrapper<BsPark>().eq("IN_USED", 1).orderByDesc("Y_PRICE"));
                bsParks.sort(new Comparator<BsPark>() {
                    @Override
                    public int compare(BsPark o1, BsPark o2) {
                        Double v2 = o2.getyPrice() * o2.getvPrice();
                        Double v1 = o2.getyPrice() * o2.getvPrice();
                        return v2.compareTo(v1);
                    }
                });
            }
            return bsParks;
        }
    }

    @Override
    public List<BsPark> getListOrderByVolume() {
        /**
         * 按照剩余车位排序  肯定是当天剩余车位
         */
        List<BsPark> bsParks = parkMapper.selectList(new QueryWrapper<BsPark>().eq("IN_USED", 1));
        // 获取当前时间
        Date date = new Date();
        // 获取到所有未过期的订单  意思就是当前停车场已经使用的车位数量
        bsParks.sort(new Comparator<BsPark>() {
            @Override
            public int compare(BsPark o1, BsPark o2) {
                // 查到所有未过期订单
                List<BsOrder> bsOrders = orderMapper.selectList(new QueryWrapper<BsOrder>()
                        .eq("PARK_ID", o1.getFlowId())
                        .ne("CHARGE", 2));
                removeNotToday(bsOrders, date);
                BsPark bsPark1 = parkMapper.selectOne(new QueryWrapper<BsPark>().eq("IN_USED", 1).eq("FLOW_ID", o1.getFlowId()));
                Integer volume1 = bsPark1.getVolume();
                List<BsOrder> bsOrders2 = orderMapper.selectList(new QueryWrapper<BsOrder>().eq("PARK_ID", o2.getFlowId()).ne("CHARGE", 2));
                removeNotToday(bsOrders2, date);
                BsPark bsPark2 = parkMapper.selectOne(new QueryWrapper<BsPark>().eq("IN_USED", 1).eq("FLOW_ID", o1.getFlowId()));
                Integer volume2 = bsPark2.getVolume();
                Integer result1 = volume1 - bsOrders.size();
                Integer result2 = volume2 - bsOrders2.size();
                return result2 - result1;
            }
        });
        return bsParks;
    }

    /**
     * 去除不是当天的订单
     *
     * @param bsOrders
     * @param now
     */
    @Override
    public void removeNotToday(List<BsOrder> bsOrders, Date now) {
        Iterator<BsOrder> iterator = bsOrders.iterator();
        // 计算正在使用中的车位数量
        while (iterator.hasNext()) {
            BsOrder order = iterator.next();
            Date startTime = order.getStartTime();
            Date leaveTime = order.getLeaveTime();
            if ((startTime.before(now)&& leaveTime.after(leaveTime))) {
                iterator.remove();
            }
        }
    }

    @Override
    public List<BsPark> getListOrderBySale() {
        List<BsPark> bsParks = parkMapper.selectList(new QueryWrapper<BsPark>().eq("IN_USED", 1));
        // 获取到每个停车场的非为支付的订单数量
        bsParks.sort(new Comparator<BsPark>() {
            @Override
            public int compare(BsPark o1, BsPark o2) {
                Integer nums = orderMapper.selectCount(new QueryWrapper<BsOrder>().eq("PARK_ID", o1.getFlowId()).ne("CHARGE", 0));
                Integer nums2 = orderMapper.selectCount(new QueryWrapper<BsOrder>().eq("PARK_ID", o2.getFlowId()).ne("CHARGE", 0));
                return nums2 - nums;
            }
        });
        return bsParks;
    }

    /**
     * @param lng 经度
     * @param lat 维度
     * @return
     */
    @Override
    public List<BsPark> getListOrderByDistance(Double lng, Double lat) {
        List<BsPark> bsParks = parkMapper.selectList(new QueryWrapper<BsPark>().eq("IN_USED", 1));
        bsParks.sort(new Comparator<BsPark>() {
            @Override
            public int compare(BsPark o1, BsPark o2) {
                Double jd1 = o1.getJd();
                Double wd1 = o1.getWd();
                Double jd2 = o2.getJd();
                Double wd2 = o2.getWd();
                Double dis1 = Math.sqrt(Math.pow(Math.abs(jd1 - lng), 2) + Math.pow(Math.abs(wd1 - lat), 2));
                Double dis2 = Math.sqrt(Math.pow(Math.abs(jd2 - lng), 2) + Math.pow(Math.abs(wd2 - lat), 2));
                return dis1.compareTo(dis2);
            }
        });
        return bsParks;
    }

    @Override
    public List<BsPark> getListOrderByMultiple(String userId, Double lng, Double lat) {
        // 按距离拿数据
        // 近的  便宜的
        List<BsPark> bsParks;
        BsUser bsUser = userMapper.selectOne(new QueryWrapper<BsUser>().eq("FLOW_ID", userId));
        // 0 会员  1 或空非会员
        String vip = bsUser.getVip();
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("HH");
        String str = df.format(date);
        int time = Integer.parseInt(str);
        bsParks = parkMapper.selectList(new QueryWrapper<BsPark>().eq("IN_USED", 1));
        bsParks.sort(new Comparator<BsPark>() {
            Double res1;
            Double res2;
            double atan1;
            double atan2;
            double atan11;
            double atan22;

            @Override
            public int compare(BsPark o1, BsPark o2) {
                Double v2;
                Double v1;
                if (time >= 6 && time <= 18) {
                    // 白天
                    // 如果是回员
                    if ("0".equals(vip)) {
                        v2 = o2.getbPrice() * o2.getvPrice();
                        v1 = o2.getbPrice() * o2.getvPrice();
                    } else {
                        v2 = o1.getbPrice();
                        v1 = o2.getbPrice();
                    }
                    Double jd1 = o1.getJd();
                    Double wd1 = o1.getWd();
                    Double jd2 = o2.getJd();
                    Double wd2 = o2.getWd();
                    Double dis1 = Math.sqrt(Math.pow(Math.abs(jd1 - lng), 2) + Math.pow(Math.abs(wd1 - lat), 2));
                    Double dis2 = Math.sqrt(Math.pow(Math.abs(jd2 - lng), 2) + Math.pow(Math.abs(wd2 - lat), 2));
                    atan1 = Math.atan(dis1) * 2 / Math.PI;
                    atan2 = Math.atan(dis2) * 2 / Math.PI;
                    atan11 = Math.atan(v2) * 2 / Math.PI;
                    atan22 = Math.atan(v1) * 2 / Math.PI;
                } else {
                    // 晚上
                    // 如果是回员
                    if ("0".equals(vip)) {
                        v2 = o2.getyPrice() * o2.getvPrice();
                        v1 = o2.getyPrice() * o2.getvPrice();
                    } else {
                        v2 = o1.getyPrice();
                        v1 = o2.getyPrice();
                    }
                    Double jd1 = o1.getJd();
                    Double wd1 = o1.getWd();
                    Double jd2 = o2.getJd();
                    Double wd2 = o2.getWd();
                    Double dis1 = Math.sqrt(Math.pow(Math.abs(jd1 - lng), 2) + Math.pow(Math.abs(wd1 - lat), 2));
                    Double dis2 = Math.sqrt(Math.pow(Math.abs(jd2 - lng), 2) + Math.pow(Math.abs(wd2 - lat), 2));
                    // 数据标准化
                    /**
                     * 用反正切函数也可以实现数据的归一化，使用这个方法需要注意的是如果想映射的区间为[0,1]，则数据都应该大于等于0。
                     * 反正切函数（inverse tangent）是数学术语，反三角函数之一知，指函数y=tanx的反函数。计算方法：设两锐角分别为A，B，则有下列道表示：
                     * arctan 就是反正切的意思，例如：tan45度=1，则arctan1=45度，就是求“逆”的运算，就好比乘法的“逆”运算是除法一样。
                     * 若tanA=1.9/5，则 A=arctan1.9/5；若tanB=5/1.9，则B=arctan5/1.9。
                     * y=atan(x)*2/PI
                     */
                    atan1 = Math.atan(dis1) * 2 / Math.PI;
                    atan2 = Math.atan(dis2) * 2 / Math.PI;
                    atan11 = Math.atan(v2) * 2 / Math.PI;
                    atan22 = Math.atan(v1) * 2 / Math.PI;
                }
                res1 = (atan1 + atan11) / 2;
                res2 = (atan2 + atan22) / 2;
                return res1.compareTo(res2);
            }
        });

        return bsParks;
    }
}
