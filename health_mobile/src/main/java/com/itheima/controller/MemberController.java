package com.itheima.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/member")
public class MemberController
{
    @Autowired
    private JedisPool jedisPool;
    @Reference
    private MemberService memberService;
    //手机验证码登录
    @RequestMapping("/login")
    public Result login(HttpServletResponse response, @RequestBody Map map)
    {
        String telephone = (String) map.get("telephone");
        String validateCode = (String) map.get("validateCode");
        //从Redis中获取保存的验证码
        String codeInRedis = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_LOGIN);
        //进行验证码的比对（页面提交的验证码和Redis中保存的验证码进行比对）
        if (codeInRedis == null || !codeInRedis.equals(validateCode))
        {
            //验证码错误
            return new Result(false, "验证码输入错误");
        }
        //验证码正确
        Member member = memberService.findByTelephone(telephone);
        if (member == null)
        {
            //当前用户不是会员，自动完成注册
            member = new Member();
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());
            memberService.add(member);
        }
        //登录成功，向客户端写入Cookie，内容为手机号
        Cookie cookie = new Cookie("member_login_telephone", telephone);
        cookie.setPath("/");//路径
        cookie.setMaxAge(60 * 60 * 24 * 30);//有效期
        //响应到客户端
        response.addCookie(cookie);
        //将会员信息保存到Redis中，使用手机号作为key，保存时间为30分钟
        jedisPool.getResource().setex(telephone, 60 * 30, member.toString());
        return new Result(true, "登录成功");
    }
}
