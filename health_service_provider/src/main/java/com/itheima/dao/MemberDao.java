package com.itheima.dao;

import com.itheima.pojo.Member;

public interface MemberDao
{
    //根据手机号查询会员信息
    public  Member findByTelephone(String telephone);
    //添加会员
    public void add(Member member);
}
