package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;

import java.util.List;

/**
 * 检查组服务接口
 * 该接口定义了与检查组相关的操作方法
 */
public interface CheckGroupService
{
    //    新增检查组
    /**
     * 新增检查组方法
     * @param checkitemIds 检查项ID数组，用于关联检查组与检查项
     * @param checkGroupId 检查组对象，包含需要新增的检查组信息
     */
    public void add(Integer[] checkitemIds, CheckGroup checkGroupId);

    //分页查询检查组
    public PageResult findPage(QueryPageBean queryPageBean);
    //根据检查组id查询检查组基本信息
    public CheckGroup findById(Integer id);

    //根据检查组id查询检查组对检查项的引用
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer checkGroupId);

    //编辑检查组
    public void edit(Integer[] checkitemIds, CheckGroup checkGroup);

    //删除检查组
    public void delete(Integer id);

    //查询所有检查组
    public List<CheckGroup> findAll();
}
