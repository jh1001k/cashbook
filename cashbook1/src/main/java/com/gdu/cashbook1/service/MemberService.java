package com.gdu.cashbook1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook1.mapper.MemberMapper;
import com.gdu.cashbook1.vo.LoginMember;
import com.gdu.cashbook1.vo.Member;

@Service
@Transactional
public class MemberService {
	@Autowired
	private MemberMapper memberMapper;
	
	public int modifyMember(Member member) {
		return memberMapper.modifyMember(member);
	}
	
	public void removeMember(LoginMember loginMember, String memberId) {
		System.out.println(loginMember.getMemberId() + "로그인아이디");
		memberMapper.addRemoveMember(memberId);
		memberMapper.removeMember(loginMember);
	} // 회원탈퇴
	
	public Member getMemberOne(LoginMember loginMember) {
		return memberMapper.selectMemberOne(loginMember);
	} // 회원정보
	
	public String checkMemberId(String memberIdCheck) {
		return memberMapper.selectMemberId(memberIdCheck);
	} // 회원가입 아이디 중복체크
	
	public LoginMember login(LoginMember loginMember) {
		return memberMapper.selectLoginMember(loginMember);
	} // 로그인
	
	public int addMember(Member member) {
		return memberMapper.addMember(member);
	} //회원가입
}
