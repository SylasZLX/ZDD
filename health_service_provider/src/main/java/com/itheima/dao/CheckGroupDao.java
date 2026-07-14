package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckGroup;

import java.util.List;
import java.util.Map;

/**
 * 检查组数据访问接口
 * 该接口定义了与检查组相关的数据操作方法
 */
public interface CheckGroupDao
{
    /**
     * 新增检查组
     * @param checkGroup 要新增的检查组对象
     */
    public void add(CheckGroup checkGroup);
    /**
     * 设置检查组与检查项的多对多关系
     * @param map 包含检查组ID和检查项ID映射关系的Map集合
     *            key为检查项ID，value为检查组ID
     */
    public void setCheckGroupAndCheckItem(Map<String,Integer> map);

    //分页查询检查组
    public Page<CheckGroup> findByCondition(String queryString);

    //根据检查组id查询检查组基本信息
    public CheckGroup findById(Integer id);

    //根据检查组id查询检查组对检查项的引用
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer checkGroupId);

    //编辑检查组的基本信息
    public void edit(CheckGroup checkGroup);

    //根据检查组的id删除检查组对检查项的引用
    public void deleteAssociation(Integer checkGroupId);

    //根据检查组id查询套餐对检查组的引用
    public long selectCountByCheckGroupId(Integer id);

    //根据检查组id删除检查组
    public void deleteById(Integer id);

    //查询所有检查组
    public List<CheckGroup> findAll();
}
