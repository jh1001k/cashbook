package com.gdu.cashbook1.service;

import java.io.File;
import java.security.cert.Extension;
import java.util.List;
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
	@Value("/jh1001k/tomcat/webapps/cashbook1/WEB-INF/classes/static/upload/")
	private String path;

	/*
	 * public LoginMember adminLogin(String memberId) {
		return memberMapper.selectAdmin(memberId);
	}
	 */
	public int getMemberPw(Member member) {
		// password 추가
		UUID uuid = UUID.randomUUID(); // UUID타입으로 생성됨
		String memberPw = uuid.toString().substring(0, 8);
		member.setMemberPw(memberPw);
		System.out.println(memberPw + "<--update memberPw");
		System.out.println(member.getMemberId() + "<--memberPw.. memberId");
		System.out.println(member.getMemberEmail() + "<--2");
		System.out.println(member.getMemberPw() + "<===memberPw");
		int row = memberMapper.updateMemberPw(member);
		System.out.println(row + "<== update row");
		if (row == 1) {
			System.out.println(memberPw + "<--update memberPw");
			// 메일로 update가 성공한 랜덤 pw를 전송
			// 메일객체가 필요 new JavaMailSender();
			// javaMailSender.send(new SimpleMailMessage());
			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
			simpleMailMessage.setTo(member.getMemberEmail());
			simpleMailMessage.setFrom("ktyyaa1@gmail.com");
			simpleMailMessage.setSubject("cashbook 비밀번호 찾기 메일");
			simpleMailMessage.setText("변경된 비밀번호 :" + memberPw + "입니다.");
			javaMailSender.send(simpleMailMessage);
		}
		return row;
	}

	public String selectMemberIdByMember(Member member) {
		return memberMapper.selectMemberIdByMember(member);
	}

	// 회원정보 수정
	public int modifyMember(MemberForm memberForm, LoginMember loginMember) {
		String originMemberPic = memberMapper.selectMemberPic(memberForm.getMemberId()); // id에저장된 사진이름
		System.out.println(originMemberPic+"<--modifyMember.Pic");
		MultipartFile mf = memberForm.getMemberPic();
		System.out.println(mf+"<--???");
		String originName = mf.getOriginalFilename(); // 바꿀 사진 원래 이름
		System.out.println(originName + "<--origib");
		String memberPic = null;
		
		if (originName.length() == 0 && !originMemberPic.equals("default.jpg")) { // 바뀔 사진 이름이 0이면 
			memberPic = originMemberPic; // 저장되어 있는 사진이름으로 저장
		} else {
			// 파일 저장 삭제
			File file = new File(path+originMemberPic);
			if(file.exists()) {
				file.delete();
			}
			int lastDot = originName.lastIndexOf(".");
			String extension = originName.substring(lastDot);
			memberPic = memberForm.getMemberId() + extension;
		}
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
		
		File file = new File(path+memberPic);
        try {
           mf.transferTo(file);
        } catch (Exception e) {
              e.printStackTrace();
              throw new RuntimeException();
        }     
		return memberMapper.modifyMember(member);
	}

	// 회원가입
	public int addMember(MemberForm memberForm) {
		MultipartFile mf = memberForm.getMemberPic();
		// 확장자 필요
		String originName = mf.getOriginalFilename();
		/*
		 * if(mf.getContentType().equals("image/png") ||
		 * mf.getContentType().equals("image/jpg")) { // 업로드 } else { // 업로드 실패 }
		 */
		System.out.println(originName + "<--originName");
		
		
		String memberPic = null;
		System.out.println(memberForm.getMemberPic()+"<--getMemberPic");
		if(!originName.equals("")) {
		// 새로운 이름을 생성 : UUID
			int lastDot = originName.lastIndexOf("."); // 좌석표.png
			String extension = originName.substring(lastDot);
			memberPic = memberForm.getMemberId() + extension;
		} else {
			memberPic = "default.jpg";
		}
		System.out.println(memberPic+"<--mememem");
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
		System.out.println(member + "<--MemberService.addmember:member");
		int row = memberMapper.addMember(member);
		// 2. 파일 저장
		File file = new File(path + memberPic);
		System.out.println(memberPic+"<-- memberPic");

		try {
			mf.transferTo(file);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
			// Exception
			// 1. 예외처리를 해야만 문법적으로 이상이 없는 예외
			// 2. 예외처리를 코드에서 구현하지 않아도 아무 문제 없는 예외 RuntimeException()
		}
		return row;
	}

	// 회원탈퇴
	public int removeMember(LoginMember loginMember, Member member) {
		// 1. 멤버 이미지 파일 삭제
		// 1.1 파일이름 select member_pic from member
		String memberPic = memberMapper.selectMemberPic(member.getMemberId());
		File file = new File(path + memberPic);
		if (file.exists()) { // file.exists() : 경로에 file/directory(folder)가 존재하는지 확인한다.
			file.delete(); // 존재하는 파일을 삭제함.
		}

		System.out.println(loginMember.getMemberId() + "로그인아이디");
		System.out.println(loginMember+"<--memberService.removeMember");
		int row = memberMapper.removeMember(loginMember);
		if(row == 1) {
			row = memberMapper.addRemoveMember(member.getMemberId());
		}
		
		System.out.println(row+"<--memberService.removeMember");
		return row;
	}

	// 회원리스트
	public List<Member> selectMemberList() {
		return memberMapper.selectMemberList();
	}
	
	// 회원정보
	public Member getMemberOne(LoginMember loginMember) {
		return memberMapper.selectMemberOne(loginMember);
	}

	// 회원가입 아이디 중복체크
	public String checkMemberId(String memberIdCheck) {
		return memberMapper.selectMemberId(memberIdCheck);
	}

	// 로그인
	public LoginMember login(LoginMember loginMember) {
		return memberMapper.selectLoginMember(loginMember);
	}

}
