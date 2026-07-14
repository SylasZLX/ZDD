package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.container.page.PageHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckGroupDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 检查组服务实现类
 * 实现了CheckGroupService接口，处理检查组相关的业务逻辑
 */
@org.springframework.stereotype.Service
@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService
{

    /**
     * 自动注入的检查组数据访问对象
     * 用于操作数据库中的检查组数据
     */
    @Autowired
    private CheckGroupDao checkGroupDao;

    //新增检查组，同时需要设置检查组和检查项的关联关系
    @Override
    public void add(Integer[] checkitemIds, CheckGroup checkGroup)
    {
        checkGroupDao.add(checkGroup);
        Integer checkGroupId = checkGroup.getId();//检查组id
        //设置检查组对检查项的引用
        this.setCheckGroupAndCheckItem(checkitemIds, checkGroupId);
    }


    private void setCheckGroupAndCheckItem(Integer[] checkitemIds, Integer checkGroupId)
    {
        //设置多对多关系
        if (checkitemIds != null && checkitemIds.length > 0)
        {
            for (Integer checkitemId : checkitemIds)
            {
                Map<String, Integer> map = new HashMap<>();
                map.put("checkgroupId", checkGroupId);//检查组id
                map.put("checkitemId", checkitemId);//检查项id
                checkGroupDao.setCheckGroupAndCheckItem(map);
            }
        }
    }

    //分页查询检查组
    @Override
    public PageResult findPage(QueryPageBean queryPageBean)
    {
        //获取当前页码
        Integer currentPage = queryPageBean.getCurrentPage();
        //获取每页显示的记录数
        Integer pageSize = queryPageBean.getPageSize();
        //获取查询条件
        String queryString = queryPageBean.getQueryString();

        //分页插件，会在执行SQL语句之前将分页关键字追加到SQL后面
        PageHelper.startPage(currentPage, pageSize);
        //调用DAO层接口中的方法
        Page<CheckGroup> page = checkGroupDao.findByCondition(queryString);
        //返回分页对象
        return new PageResult(page.getTotal(), page.getResult());
    }

    //根据检查组id查询检查组基本信息
    @Override
    public CheckGroup findById(Integer id)
    {
        return checkGroupDao.findById(id);
    }

    //根据检查组id查询检查组对检查项的引用
    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer checkGroupId) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(checkGroupId);
    }

    //编辑检查组
    @Override
    public void edit(Integer[] checkitemIds, CheckGroup checkGroup) {
        //编辑检查组的基本信息
        checkGroupDao.edit(checkGroup);
        //根据检查组的id删除检查组对检查项的引用
        checkGroupDao.deleteAssociation(checkGroup.getId());
        //重新设置检查组对检查项的引用
        this.setCheckGroupAndCheckItem(checkitemIds, checkGroup.getId());
    }

    @Override
    public void delete(Integer id) {
        //根据检查组id查询套餐对检查组的引用
        long count = checkGroupDao.selectCountByCheckGroupId(id);
        if (count > 0)
        {
            //不能删除，已被套餐引用
            throw new RuntimeException("不能删除，已被套餐引用");
        }else
        {
            //根据检查组id删除检查组对检查项的引用
            checkGroupDao.deleteAssociation(id);
            //根据检查组id删除检查组的基本信息
            checkGroupDao.deleteById(id);
        }
    }

    //查询所有检查组
    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }
}