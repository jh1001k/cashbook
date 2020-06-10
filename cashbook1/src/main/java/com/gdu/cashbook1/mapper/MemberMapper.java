package com.gdu.cashbook1.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.gdu.cashbook1.vo.LoginMember;
import com.gdu.cashbook1.vo.Member;

@Mapper
public interface MemberMapper {
	
	public List<Member> selectMemberList(); // 회원 리스트
	public String selectMemberPic(String memberId);
	public int updateMemberPw(Member member);
	public String selectMemberIdByMember(Member member); // 아이디 찾기 
	public int modifyMember(Member member); // 회원정보수정
	public int addRemoveMember(String memberId); // 탈퇴 시 아이디 값 memberid 테이블에 저장
	public int removeMember(LoginMember loginMember); // 회원정보 폼 회원탈퇴
	public Member selectMemberOne(LoginMember loginMember);
	public String selectMemberId(String memberIdCheck); // 아이디 중복체크
	public LoginMember selectLoginMember(LoginMember loginMember); // 로그인
	public int addMember(Member member); // 회원가입
}
