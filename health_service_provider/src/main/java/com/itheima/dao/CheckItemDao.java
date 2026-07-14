package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckItem;

import java.util.List;

public interface CheckItemDao {
    public void add(CheckItem checkItem);//新增检查项

    public Page<CheckItem> findByCondition(String queryString);//查询检查项

    public CheckItem findById(Integer id);

    public void edit(CheckItem checkItem);

    public long selectCountByCheckItemId(Integer checkItemId);

    public void deleteById(Integer id);

    //查询所有检查项
    public List<CheckItem> findAllCheckItem();
}
