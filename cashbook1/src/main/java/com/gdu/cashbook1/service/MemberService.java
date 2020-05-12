package com.gdu.cashbook1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook1.mapper.MemberMapper;
import com.gdu.cashbook1.vo.Member;

@Service
@Transactional
public class MemberService {
	@Autowired
	private MemberMapper memberMapper;
	public void addMember(Member member) {
		memberMapper.addMember(member);
	}
}
