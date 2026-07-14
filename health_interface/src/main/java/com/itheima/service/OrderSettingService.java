package com.itheima.service;

import com.itheima.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrderSettingService
{
    //文件上传
    public void add(List<OrderSetting> data);
    //根据年月查询对应的设置信息
    public List<Map<String, Object>> getOrderSettingByMonth(String month);
    //根据日期修改可预约人数
    public void editNumberByOrderDate(OrderSetting orderSetting);
}
