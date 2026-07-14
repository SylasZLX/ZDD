package com.itheima.jobs;

import com.itheima.constant.RedisConstant;
import com.itheima.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

public class ClearImgJob
{
    @Autowired
    private JedisPool jedisPool;
    //定时清理图片任务
    public void clearImg()
    {
        System.out.println("周慧怡定时清理垃圾图片任务");
        Set<String> set = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        if (set != null && !set.isEmpty())
        {
            for (String fileName : set)
            {
                System.out.println("周慧怡定时清理垃圾图片："+fileName);
                //根据图片名称从七牛云服务器中删除图片
                QiniuUtils.deleteFileFromQiniu(fileName);
                //从Redis集合中删除图片名称
                jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
            }
        }
    }
}
