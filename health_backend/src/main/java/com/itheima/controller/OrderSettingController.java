package com.itheima.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import com.itheima.utils.POIUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController
{
    @Reference
    private OrderSettingService orderSettingService;
    @RequestMapping("/upload")
    public Result upload(@RequestParam("excelFile")MultipartFile excelFile) {
        try {
            //通过工具POIUtils返回excel文件中的数据
            List<String[]> list = POIUtils.readExcel(excelFile);
            //创建预约设置集合
            List<OrderSetting> date = new ArrayList<>();
            //判断List
            if (list != null && list.size() > 0)
            {
                for (String[] strings : list)
                {
                    OrderSetting orderSetting = new OrderSetting(new SimpleDateFormat(
                            "yyyy/MM/dd").parse(strings[0]), Integer.parseInt(strings[1]
                    ));
                    date.add(orderSetting);
                }
            }
            //调用业务层
            orderSettingService.add(date);
            return new Result(true,"导入数据成功");
        }catch (Exception e)
        {
            e.printStackTrace();
            return new Result(false,"导入数据失败");
        }
    }

    @RequestMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String month)
    {
        try
        {
            List<Map<String, Object>> list = orderSettingService.getOrderSettingByMonth(month);
            return new Result(true,"查询预约设置数据成功",list);
        }catch (Exception e)
        {
            e.printStackTrace();
            return new Result(false,"查询预约设置数据失败");
        }
    }

    @RequestMapping("/editNumberByOrderDate")
    public Result editNumberByOrderDate(@RequestBody OrderSetting orderSetting)
    {
        try
        {
            //调用业务层
            orderSettingService.editNumberByOrderDate(orderSetting);
            return new Result(true,"查询预约设置数据成功");
        } catch (Exception e)
        {
            e.printStackTrace();
            return new Result(false, "查询预约设置数据失败");
        }
    }
}
