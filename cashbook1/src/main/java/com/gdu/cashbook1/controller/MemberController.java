package com.gdu.cashbook1.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.gdu.cashbook1.service.MemberService;
import com.gdu.cashbook1.vo.Member;

@Controller
public class MemberController {
	@Autowired
	private MemberService memberService;
	@GetMapping("/addMember")
	public String addMember() {
		return "addMember";
	}
	
	@PostMapping("/addMember")
	public String addMember(Member member) { // Command 객체, 도메인 객체
		// System.out.println(member.toString());
		memberService.addMember(member);
		return "redirect:/index";
	}
}
