package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;

import java.util.List;

public interface CheckItemService{

//    新增检查项方法
    public void add(CheckItem checkItem);

//    分页查询检查项方法
    public PageResult findPage(QueryPageBean queryPageBean);//分页查询检查项

//    根据查询项id查询检查项方法
    public CheckItem findById(Integer id);

//    编辑检查项
    public void edit(CheckItem checkItem);

//    删除检查项
    public void delete(Integer id);

//    查询所有检查项
    public List<CheckItem> findAll();
}

