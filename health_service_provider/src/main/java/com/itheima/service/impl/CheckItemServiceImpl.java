package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;
import com.itheima.dao.CheckItemDao;
import com.itheima.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@org.springframework.stereotype.Service
@Service(interfaceClass = CheckItemService.class)
@Transactional

public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;

    //新增检查项
    @Override

    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    public PageResult findPage(QueryPageBean queryPageBean){
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckItem> page = checkItemDao.findByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    //编辑检查项
    @Override
    public CheckItem findById(Integer id){
        return checkItemDao.findById(id);
    }

    @Override
    public void edit(CheckItem checkItem){
        checkItemDao.edit(checkItem);
    }

    @Override
    public void delete(Integer id){
        long count = checkItemDao.selectCountByCheckItemId(id);
        if(count >0){
            throw new RuntimeException(MessageConstant.DELETE_CHECKITEM_FAIL);
        }else{
            checkItemDao.deleteById(id);
        }
    }

    //查询所有检查项
    @Override
    public List<CheckItem> findAll(){
        return checkItemDao.findAllCheckItem();
    }
}
