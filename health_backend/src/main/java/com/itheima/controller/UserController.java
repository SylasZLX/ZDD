package com.itheima.controller;


import com.itheima.entity.Result;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/user")
public class UserController
{
    @RequestMapping("/getLoginUsername")
    public Result getLoginUsername()
    {
        try
        {
            //调用Spring Security框架提供的API获取当前登录用户的方法
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            //获取用户名
            String username = user.getUsername();
            String password = user.getPassword();
            Collection<GrantedAuthority> authorities = user.getAuthorities();
            //返回结果
            return new Result(true, "获取当前登录用户名称成功", username);
        }catch (Exception e)
        {
            e.printStackTrace();
            return new Result(false, "获取当前登录用户名称失败");
        }
    }
}
