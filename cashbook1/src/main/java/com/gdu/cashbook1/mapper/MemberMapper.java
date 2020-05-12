package com.gdu.cashbook1.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook1.vo.Member;
@Mapper
public interface MemberMapper {
	public void addMember(Member member);
}
