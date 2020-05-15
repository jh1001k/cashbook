package com.gdu.cashbook1.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook1.mapper.MemberMapper;
import com.gdu.cashbook1.vo.LoginMember;
import com.gdu.cashbook1.vo.Member;

@Service
@Transactional
public class MemberService {
	@Autowired private MemberMapper memberMapper;
	@Autowired private JavaMailSender javaMailSender; 
	
	public int getMemberPw(Member member) {
		//password 추가
		UUID uuid = UUID.randomUUID(); //UUID타입으로 생성됨
		String memberPw = uuid.toString().substring(0, 8);
		member.setMemberPw(memberPw);
		System.out.println(memberPw+"<--update memberPw");
		System.out.println(member.getMemberId()+"<--memberPw.. memberId");
		System.out.println(member.getMemberEmail()+"<--2");
		System.out.println(member.getMemberPw()+"<===memberPw");
		int row = memberMapper.updateMemberPw(member);
		System.out.println(row+"<== update row");
		if(row == 1) {
			System.out.println(memberPw+"<--update memberPw");
			// 메일로  update가 성공한 랜덤 pw를 전송
			// 메일객체가 필요 new JavaMailSender();
			// javaMailSender.send(new SimpleMailMessage());
			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
			simpleMailMessage.setTo(member.getMemberEmail());
			simpleMailMessage.setFrom("ktyyaa1@gmail.com");
			simpleMailMessage.setSubject("cashbook 비밀번호 찾기 메일");
			simpleMailMessage.setText("변경된 비밀번호 :"+memberPw+"입니다.");
			javaMailSender.send(simpleMailMessage);
		}
		return row;
	}
	
	public String selectMemberIdByMember(Member member) {
		return memberMapper.selectMemberIdByMember(member);
	}
	
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
