package com.gdu.cashbook1.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.gdu.cashbook1.service.MemberService;
import com.gdu.cashbook1.vo.LoginMember;
import com.gdu.cashbook1.vo.Member;

@Controller
public class MemberController {
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/login") // login Form으로 이동
	public String login() {
		return "login";
	}
	@PostMapping("/login") // login Action
	public String login(LoginMember loginMember, HttpSession session) { // HttpSession session = request.getSession()
		LoginMember returnLoginMember = memberService.login(loginMember);
		if(returnLoginMember == null) { //로그인 실패시
			return "redirect:/login";
		} else { //로그인 성공시
			session.setAttribute("loginMember", returnLoginMember);
			return "redirect:/";
		}
	
	}
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	
	@GetMapping("/addMember") // 회원가입 Form
	public String addMember() {
		return "addMember";
	}
	
	@PostMapping("/addMember") // 회원가입 Action
	public String addMember(Member member) { // Command 객체, 도메인 객체
		// System.out.println(member.toString());
		memberService.addMember(member);
		return "redirect:/index";
	}
}
