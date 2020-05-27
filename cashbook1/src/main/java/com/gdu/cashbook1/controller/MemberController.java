package com.gdu.cashbook1.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.gdu.cashbook1.service.BoardService;
import com.gdu.cashbook1.service.CashService;
import com.gdu.cashbook1.service.CommentService;
import com.gdu.cashbook1.service.MemberService;
import com.gdu.cashbook1.vo.LoginMember;
import com.gdu.cashbook1.vo.Member;
import com.gdu.cashbook1.vo.MemberForm;

@Controller
public class MemberController {
	@Autowired private MemberService memberService;
	@Autowired private CashService cashService;
	@Autowired private BoardService boardService;
	@Autowired private CommentService commentService;
	
	@GetMapping("/findMemberPw")
	public String findMemberPw(HttpSession session) {
			if(session.getAttribute("loginMember") != null) {
				return "redirect:/";
			}
			return "findMemberPw";
	}
	@PostMapping("/findMemberPw")
	public String findMemberPw(HttpSession session, Model model, Member member) {
		System.out.println(member);
		int row = this.memberService.getMemberPw(member);
		String msg = "아이디와 비밀번호를 입력하세요";
		if(row == 1) {
			msg = "비밀번호를 입력한 메일로 전송하였습니다.";
		}
		model.addAttribute("msg", msg);
		return "findMemberPw";
	}
	
	@GetMapping("/findMemberId")
	public String findMemberId(HttpSession session) {
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		return "findMemberId";
	}
	@PostMapping("/findMemberId")
	public String findMemberId(HttpSession session, Model model, Member member) {
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		String memberIdPart = memberService.selectMemberIdByMember(member);
		String msg = "당신의 아이디는 "+memberIdPart+" 입니다.";
		model.addAttribute("msg", msg);
		return "findMemberId";
	}
	// 회원정보 수정
	@GetMapping("/modifyMember")
	public String modifyMember(HttpSession session, Model model) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		Member member = memberService.getMemberOne((LoginMember)(session.getAttribute("loginMember")));
		model.addAttribute("member", member);
		return "modifyMember";
	}
	
	@PostMapping("/modifyMember")
	public String modifyMember(HttpSession session, Model model, MemberForm memberForm) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");
		System.out.println(memberForm.getMemberPic().getContentType()+"<--타입");
		if (memberForm.getMemberPic().getContentType().equals("image/png")
	            || memberForm.getMemberPic().getContentType().equals("image/jpeg")
	            || memberForm.getMemberPic().getContentType().equals("image/gif")) { 
	         memberService.modifyMember(memberForm, loginMember);
	         model.addAttribute("loginMember", loginMember);
	         return "redirect:/memberInfo";
		}
		memberService.modifyMember(memberForm, loginMember);
	     model.addAttribute("loginMember", loginMember);
	     return "redirect:/memberInfo";
	}
	// 회원탈퇴
	@GetMapping("/removeMember") 
	public String removeMember(HttpSession session, LoginMember loginMember, String memberId) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		cashService.removeCashByMember(memberId);
		commentService.removeCommentByMember(memberId);
		boardService.removeBoardByMember(memberId);
		memberService.removeMember(loginMember, memberId);
		
		session.invalidate(); // 세션 초기화
		return "redirect:/";
	}
	//회원정보
	@GetMapping("/memberInfo") 
	public String memberInfo(HttpSession session, Model model) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		Member member = memberService.getMemberOne((LoginMember)(session.getAttribute("loginMember")));
		System.out.println(member);
		model.addAttribute("member", member);
		return "memberInfo";
	}
	
	@PostMapping("/checkMemberId") // 아이디 중복검사
	public String checkMemberId(Model model, @RequestParam("memberIdCheck") String memberIdCheck, HttpSession session) { // 값이 하나만넘어와서 RequestParam 사용
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		
		String confirmMemberId = memberService.checkMemberId(memberIdCheck);
		if(confirmMemberId != null) {
			// 아이디를 사용할 수 없습니다.
			System.out.println(confirmMemberId);
			model.addAttribute("msg", "사용중인 아이디입니다.");
		} else {
			// 아이디를 사용할 수 있습니다.
			System.out.println(confirmMemberId);
			model.addAttribute("memberIdCheck", memberIdCheck);
		}
		return "addMember";
	}
	
	@GetMapping("/login") // login Form으로 이동
	public String login(HttpSession session) {
		// 로그인 상태
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		// 로그인 상태가 아닐때
		return "login";
	}
	@PostMapping("/login") // login Action
	public String login(Model model, LoginMember loginMember, HttpSession session) { // HttpSession session = request.getSession()
		// 로그인 상태
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		LoginMember returnLoginMember = memberService.login(loginMember);
		if(returnLoginMember == null) { //로그인 실패시
			model.addAttribute("msg", "아이디와 비밀번호를 확인하세요");
			return "login";
		} else { //로그인 성공시
			session.setAttribute("loginMember", returnLoginMember);
			return "redirect:/home";
		}
	
	}
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		// 로그인 상태가 아닐 때
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		
		session.invalidate();
		return "redirect:/";
	}
	
	
	@GetMapping("/addMember") // 회원가입 Form
	public String addMember(HttpSession session) {
		// 로그인 상태
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		return "addMember";
	}
	
	@PostMapping("/addMember") // 회원가입 Action
	public String addMember(MemberForm memberForm, HttpSession session) { // Command 객체, 도메인 객체
		// 로그인 상태
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		MultipartFile mf = memberForm.getMemberPic();
		System.out.println(memberForm+"<--memberForm");
		//System.out.println(member.toString());
		System.out.println(memberForm.getMemberPic()+"<--getMemberPic");
		if(memberForm.getMemberPic() != null && !mf.getOriginalFilename().equals("")) {
			if(!memberForm.getMemberPic().getContentType().equals("image/png") && !memberForm.getMemberPic().getContentType().equals("image/jpeg") && !memberForm.getMemberPic().getContentType().equals("image/gif")) {
				return "redirect:/addMember";
			} 
		}
		memberService.addMember(memberForm);
		return "redirect:/index";
	}
}
