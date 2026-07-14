package com.itheima.service.impl;

import com.itheima.constant.MessageConstant;
import com.itheima.dao.OrderSettingDao;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;

@org.springframework.stereotype.Service
@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService
{
    //自动注入
    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    public void add(List<OrderSetting> list)
    {
        //判断list
        if (list != null && list.size() > 0)
        {
            //遍历list
            for (OrderSetting orderSetting : list)
            {
                //判断当前日期是否已经进行了数据预约
                long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
                if (count > 0)
                {
                    //查询当前日期的已预约人数
                    Integer number = orderSettingDao.findByReservations(orderSetting.getOrderDate());
                    if (number > orderSetting.getNumber())
                    {
                        throw new RuntimeException(MessageConstant.ORDERSETTING_FAIL);
                    }

                    //更新预约设置
                    orderSettingDao.editNumberByOrderDate(orderSetting);
                }else
                {
                    //新增预约设置
                    orderSettingDao.add(orderSetting);
                }
            }
        }
    }

    @Override
    public List<Map<String, Object>> getOrderSettingByMonth(String month)
    {
        //设置参数，当前月的开始日期和结束时间
        String begin = month + "-01";
        String end = month + "-31";//1 3 5 7 8 10 12
        String[] strings1 = {"4", "6", "9", "11"};//30days
        String[] strings = month.split("-");//分隔符 2025 11
        if (Integer.parseInt(strings[0]) %4 != 0 && Integer.parseInt(strings[1]) == 2)
        {
            end = month + "-28";
        }else if (Integer.parseInt(strings[0]) %4 == 0 && Integer.parseInt(strings[1]) == 2)
        {
            end = month + "-29";
        }else if (Arrays.asList(strings1).contains(strings[1])) {
            end = month + "-30";
        }
        Map<String, String> map = new HashMap<>();
        map.put("begin", begin);
        map.put("end", end);
        //调用持久层接口，查询设置信息
        List<OrderSetting> list = orderSettingDao.getOrderSettingByMonth(map);
        List<Map<String, Object>> data = new ArrayList<>();
        //封装结果类型
        for (OrderSetting orderSetting : list)
        {
            Map<String, Object> orderSettingData = new HashMap<>();
            orderSettingData.put("date", orderSetting.getOrderDate().getDate());
            orderSettingData.put("number", orderSetting.getNumber());
            orderSettingData.put("reservations", orderSetting.getReservations());
            data.add(orderSettingData);
        }
        return data;
    }

    @Override
    public void editNumberByOrderDate(OrderSetting orderSetting) {
        if (orderSetting == null || orderSetting.getOrderDate() == null) {
            throw new IllegalArgumentException("orderSetting 或 orderDate 不能为 null");
        }
        if (orderSettingDao == null) {
            throw new IllegalStateException("OrderSettingDao 未注入，请检查 Spring 配置或扫描路径");
        }

        long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
        if (count > 0) {
            // 已有记录，先检查已预约人数，确保不小于已预约数
            Integer reservations = orderSettingDao.findByReservations(orderSetting.getOrderDate());
            if (reservations == null) {
                reservations = 0;
            }
            if (orderSetting.getNumber() < reservations) {
                throw new RuntimeException("设置的可预约人数不能小于已预约人数");
            }
            orderSettingDao.editNumberByOrderDate(orderSetting);
        } else {
            // 无记录则新增
            orderSettingDao.add(orderSetting);
        }
    }
}
