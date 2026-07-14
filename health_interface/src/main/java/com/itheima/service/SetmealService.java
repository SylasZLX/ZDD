package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Setmeal;

import java.util.List;

public interface SetmealService 
{

    //新增套餐
    public void add(Setmeal setmeal, Integer[] checkgroupIds);

    //分页查询套餐
    public PageResult findPage(QueryPageBean queryPageBean);

    //根据id查询套餐信息
    public Setmeal findById(Integer id);

    //根据套餐id查询套餐对检查组的引用
    public List<Integer> findCheckGroupIdsBySetmealId(Integer setmealId);

    //编辑套餐
    public void edit(Setmeal setmeal, Integer[] checkgroupIds);

    //删除套餐
    public void delete(Integer id);
}
