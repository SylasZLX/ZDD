package com.itheima.service;

import com.itheima.pojo.Member;

public interface MemberService
{
    public  Member findByTelephone(String telephone);//根据手机号查询会员信息
    public void add(Member member);//添加会员
}
