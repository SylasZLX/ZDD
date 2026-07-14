package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.RedisConstant;
import com.itheima.dao.SetmealDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import com.itheima.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@org.springframework.stereotype.Service
@Service(interfaceClass = SetmealService.class)
@Transactional //表明当前类中的方法需要进行事务管理
public class SetmealServiceImpl implements SetmealService
{

    @Autowired
    private SetmealDao setmealDao;

    @Autowired
    private JedisPool jedisPool;

    //完成新增套餐功能
    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds)
    {
        //新增套餐的基本信息
        setmealDao.add(setmeal);

        //设置套餐对检查组的引用（中间表）
        Integer setmealId = setmeal.getId();
        this.setSetmealAndCheckGroup(setmealId,checkgroupIds);
        //完成所有数据库操作之后，将图片保存到Redis中
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());
    }


    private void setSetmealAndCheckGroup(Integer setmealId,Integer[] checkgroupIds)
    {
        if (checkgroupIds != null && checkgroupIds.length > 0)
        {
            for (Integer checkgroupId : checkgroupIds)
            {
                Map<String, Integer> map = new HashMap<>();//创建一个Map集合用于存储套餐ID和检查组ID
                map.put("setmealId",setmealId);//往Map集合中存储套餐ID
                map.put("checkgroupId",checkgroupId);//当前遍历到的检查组ID
                setmealDao.setSetmealAndCheckGroup(map);//
            }
        }
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean)
    {
        Integer currentPage = queryPageBean.getCurrentPage();//当前页
        Integer pageSize = queryPageBean.getPageSize();//每页记录数
        String queryString = queryPageBean.getQueryString();//查询条件

        //使用分页插件进行分页查询
        PageHelper.startPage(currentPage, pageSize);
        //调用DAO层方法进行分页查询
        Page<Setmeal> page = setmealDao.findByCondition(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    //根据id查询套餐信息
    @Override
    public Setmeal findById(Integer id)
    {
        return setmealDao.findById(id);
    }

    //根据套餐id查询套餐对检查组的引用
    @Override
    public List<Integer> findCheckGroupIdsBySetmealId(Integer setmealId)
    {
        return setmealDao.findCheckGroupIdsBySetmealId(setmealId);
    }

    //编辑套餐
    @Override
    public void edit(Setmeal setmeal, Integer[] checkgroupIds)
    {
        //编辑套餐基本信息
        setmealDao.edit(setmeal);
        //删除套餐对检查组的引用
        setmealDao.deleteAssociation(setmeal.getId());
        //重新建立套餐对检查组的引用
        this.setSetmealAndCheckGroup(setmeal.getId(),checkgroupIds);
    }

    //删除套餐
    @Override
    public void delete(Integer id)
    {
        Setmeal setmeal = setmealDao.findById(id);//查询套餐信息
        //判断套餐基本信息中的图片是否为空
        if (setmeal.getImg() != null && !"".equals(setmeal.getImg()))
        {
            //图片不为空，删除图片
            //调用七牛云工具类删除图片
            QiniuUtils.deleteFileFromQiniu(setmeal.getImg());
        }
        setmealDao.deleteAssociation(id);//删除套餐对检查组的关联关系
        setmealDao.deleteById(id);//删除套餐基本信息
    }
}
