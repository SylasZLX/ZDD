package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 检查组控制器
 * 处理与检查组相关的HTTP请求
 */
@RestController
@RequestMapping ("/checkgroup")
public class CheckGroupController {
    // 使用Dubbo远程调用检查组服务
    @Reference
    private CheckGroupService checkGroupService;

    //新增检查组
    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {
        try {
            checkGroupService.add(checkitemIds, checkGroup);
            return new Result(true, "新增检查组成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "新增检查组失败");
        }

    }

    //分页查询检查组
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        //调用Service接口的findPage方法，返回PageResult对象
        return checkGroupService.findPage(queryPageBean);
    }

    //编辑检查组
    //根据检查组id查询检查组基本信息
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        try {
            //调用Service层接口的findById方法
            CheckGroup checkGroup = checkGroupService.findById(id);
            //返回提示信息
            return new Result(true, "查询检查组成功", checkGroup);
        } catch (Exception e) {
            e.printStackTrace();
            //返回提示信息
            return new Result(false, "查询检查组失败");
        }
    }


    //根据检查组id查询检查组对检查项的引用
    @RequestMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(Integer checkgroupId) {
        try {
            //调用Service层接口的findCheckItemIdsByCheckGroupId方法
            List<Integer> list = checkGroupService.findCheckItemIdsByCheckGroupId(checkgroupId);
            //返回提示信息
            return new Result(true, "查询检查组对检查项的引用成功", list);
        } catch (Exception e) {
            e.printStackTrace();
            //返回提示信息
            return new Result(false, "查询检查组对检查项的引用失败");
        }
    }

    //编辑检查组
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {
        try {
            //调用Service层接口的edit方法
            checkGroupService.edit(checkitemIds, checkGroup);
            //返回提示信息
            return new Result(true, "编辑检查组成功");
        } catch (Exception e) {
            e.printStackTrace();
            //返回提示信息
            return new Result(false, "编辑检查组失败");
        }
    }

    //删除检查组
    @RequestMapping("/delete")
    public Result delete(Integer id) {
        try {
            //调用Service层接口的delete方法
            checkGroupService.delete(id);
            //返回提示信息
            return new Result(true, "删除检查组成功");
        } catch (Exception e) {
            e.printStackTrace();
            //返回提示信息
            return new Result(false, "删除检查组失败");
        }
    }

    //查询所有检查组
    @RequestMapping("/findAll")
    public Result findAll() {
        try {
            //调用Service层接口的findAll方法
            List<CheckGroup> list = checkGroupService.findAll();
            //返回提示信息
            return new Result(true, "查询检查组成功", list);
        } catch (Exception e) {
            e.printStackTrace();
            //返回提示信息
            return new Result(false, "查询检查组失败");
        }
    }
}
