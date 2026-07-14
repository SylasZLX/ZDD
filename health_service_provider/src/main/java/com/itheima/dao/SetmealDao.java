package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealDao
{
    //新增套餐基本信息
    public void add(Setmeal setmeal);

    //设置套餐和检查组的关联关系，操作中间表
    public void setSetmealAndCheckGroup(Map<String, Integer> map);

    //分页查询套餐信息
    public Page<Setmeal> findByCondition(String queryString);

    //根据id查询套餐信息
    public Setmeal findById(Integer id);

    //根据套餐id查询套餐对检查组的引用
    public List<Integer> findCheckGroupIdsBySetmealId(Integer setmealId);

    //编辑套餐基本信息
    public void edit(Setmeal setmeal);

    //删除套餐和检查组的关联关系
    public void deleteAssociation(Integer id);

    //删除套餐
    public void deleteById(Integer id);
}
