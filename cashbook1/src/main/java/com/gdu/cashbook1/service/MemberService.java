package com.gdu.cashbook1.service;

import java.io.File;
import java.security.cert.Extension;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.gdu.cashbook1.mapper.MemberMapper;
import com.gdu.cashbook1.vo.LoginMember;
import com.gdu.cashbook1.vo.Member;
import com.gdu.cashbook1.vo.MemberForm;

@Service
@Transactional
public class MemberService {
	@Autowired private MemberMapper memberMapper;
	@Autowired private JavaMailSender javaMailSender; 
	@Value("D:/김종훈/git-cashbook/cashbook1/src/main/resources/static/upload/")
	private String path;
	
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
		// 1. 멤버 이미지 파일 삭제
		// 1.1 파일이름 select member_pic from member
		String memberPic = memberMapper.selectMemberPic(memberId);
		File file = new File(path+memberPic);
		if(file.exists()) { // file.exists() : 경로에 file/directory(folder)가 존재하는지 확인한다.
			file.delete(); // 존재하는 파일을 삭제함.
		}
		
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
	
	public int addMember(MemberForm memberForm) {
		MultipartFile mf = memberForm.getMemberPic();
		// 확장자 필요
		String originName = mf.getOriginalFilename();
		/*
		if(mf.getContentType().equals("image/png") || mf.getContentType().equals("image/jpg")) {
			//  업로드
		} else {
			// 업로드 실패
		}
		*/
		System.out.println(originName+"<--originName");
		int lastDot = originName.lastIndexOf(".");  // 좌석표.png
		String extension = originName.substring(lastDot);
		// 새로운 이름을 생성 : UUID
		String memberPic = memberForm.getMemberId()+extension;
		// 1. db저장
		Member member = new Member();
		member.setMemberAddr(memberForm.getMemberAddr());
		member.setMemberDate(memberForm.getMemberDate());
		member.setMemberEmail(memberForm.getMemberEmail());
		member.setMemberId(memberForm.getMemberId());
		member.setMemberName(memberForm.getMemberName());
		member.setMemberPhone(memberForm.getMemberPhone());
		member.setMemberPw(memberForm.getMemberPw());
		member.setMemberPic(memberPic);
		System.out.println(member+"<--MemberService.addmember:member");
		int row = memberMapper.addMember(member);
		// 2. 파일 저장
		File file = new File(path+memberPic);
		System.out.println(memberPic+"<--지선");
		
		try{
			mf.transferTo(file);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
			// Exception
			// 1. 예외처리를 해야만 문법적으로 이상이 없는 예외
			// 2. 예외처리를 코드에서 구현하지 않아도 아무 문제 없는 예외  RuntimeException()
		} 
		
		return row;
	} //회원가입
}
