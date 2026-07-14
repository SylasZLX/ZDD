package com.itheima.controller;


import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.utils.SMSUtils;
import com.itheima.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;


@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController
{
    @Autowired
    private JedisPool jedisPool;
    @RequestMapping("/send4Login")
    public Result send4Login(String telephone)
    {
        String code  = ValidateCodeUtils.generateValidateCode(6).toString();
        System.out.println("验证码" + code);
        try {
//            SMSUtils.sendShortMessage(telephone,code);//发送验证码
            //将验证码保存到Redis中，设置有效期5分钟
            jedisPool.getResource().setex(telephone + RedisMessageConstant.SENDTYPE_LOGIN, 60 * 20, code);
            return new Result(true, "手机验证码发送成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "手机验证码发送失败");
        }
    }
}
