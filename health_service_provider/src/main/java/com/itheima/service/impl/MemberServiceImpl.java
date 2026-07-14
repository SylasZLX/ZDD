package com.itheima.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import com.itheima.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@org.springframework.stereotype.Service
@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements  MemberService
{
    @Autowired
    private MemberDao memberDao;
    //根据手机号查询会员信息
    public Member findByTelephone(String telephone){return memberDao.findByTelephone(telephone); }
    //添加会员
    public void add(Member member)
    {
        //如果用户设置了密码，需要对密码进行MD5加密后再存储
        String password = member.getPassword();
        if(password != null)
        {
            password = MD5Utils.md5(password);
            member.setPassword(password);
        }
        memberDao.add(member);
    }
}
