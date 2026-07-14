package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.RedisConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import com.itheima.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/setmeal")
public class SetmealController
{
    //查找服务
    @Reference
    public SetmealService setmealService;

    @Autowired
    private JedisPool jedisPool;

    //图片上传
    @RequestMapping("/upload")
    public Result upload(@RequestParam(name = "imgFile") MultipartFile imgFile)
    {
        // 获取图片原始文件名
        String originalFilename = imgFile.getOriginalFilename();
        //对图片名称进行分割
        int index = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(index);
        //随机生成新的图片名称
        String filename = UUID.randomUUID().toString() + suffix;
        //将图片上传到七牛云
        try
        {
            //调用工具类方法上传图片
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),filename);
            //图片上传成功之后，将图片名称保存到Redis中
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,filename);
            //返回结果
            return new Result(true,"图片上传成功",filename);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            // 失败返回结果
            return new Result(false,"图片上传失败");
        }
    }

    //新增套餐
    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds)
    {
        try
        {
            //调用Service层调用接口方法add
            setmealService.add(setmeal,checkgroupIds);
            //返回结果
            return new Result(true,"新增套餐成功");
        }catch (Exception e)
        {
            e.printStackTrace();
            // 失败返回结果
            return new Result(false,"新增套餐失败");
        }
    }

    //分页查询套餐信息
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean)
    {
        return setmealService.findPage(queryPageBean);
    }

    //根据id查询套餐信息
    @RequestMapping("/findById")
    public Result findById(Integer id)
    {
        try
        {
            Setmeal setmeal = setmealService.findById(id);
            //返回结果
            return new Result(true,"查询套餐信息成功",setmeal);
        }catch (Exception e)
        {
            e.printStackTrace();
            // 失败返回结果
            return new Result(false,"查询套餐信息失败");
        }
    }

    //根据套餐id查询套餐对检查组的引用
    @RequestMapping("/findCheckGroupIdsBySetmealId")
    public Result findCheckGroupIdsBySetmealId(Integer setmealId)
    {
        try
        {
            List<Integer> list = setmealService.findCheckGroupIdsBySetmealId(setmealId);
            //返回结果
            return new Result(true,"查询检查组id成功",list);
        }catch (Exception e)
        {
            e.printStackTrace();
            // 失败返回结果
            return new Result(false,"查询检查组id失败");
        }
    }

    //编辑套餐
    @RequestMapping("/edit")
    public Result edit(@RequestBody Setmeal setmeal, Integer[] checkgroupIds)
    {
        try
        {
            //调用Service层调用接口方法add
            setmealService.edit(setmeal,checkgroupIds);
            //返回结果
            return new Result(true,"编辑套餐成功");
        }catch (Exception e)
        {
            e.printStackTrace();
            // 失败返回结果
            return new Result(false,"编辑套餐失败");
        }
    }

    //删除套餐
    @RequestMapping("/delete")
    public Result delete(Integer id)
    {
        try
        {
            setmealService.delete(id);
            //返回结果
            return new Result(true,"删除套餐成功");
        } catch (Exception e)
        {
            e.printStackTrace();
            // 失败返回结果
            return new Result(false,"删除套餐失败");
        }
    }
}
