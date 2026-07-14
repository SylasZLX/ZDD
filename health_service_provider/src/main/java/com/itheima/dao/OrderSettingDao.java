package com.itheima.dao;

import com.itheima.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;


public interface OrderSettingDao
{
    //查询指定日期的预约设置
    public long findCountByOrderDate(Date orderDate);
    //查询指定日期的已预约人数
    public Integer findByReservations(Date orderDate);
    //更新预约设置
    public void editNumberByOrderDate(OrderSetting orderSetting);
    //新增预约设置
    public void add(OrderSetting orderSetting);
    //根据今年月查询对应的设置信息
    public List<OrderSetting> getOrderSettingByMonth(Map<String, String> map);
}
