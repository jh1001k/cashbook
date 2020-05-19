package com.gdu.cashbook1.controller;

import java.time.LocalDate;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook1.service.CashService;
import com.gdu.cashbook1.vo.Cash;
import com.gdu.cashbook1.vo.LoginMember;

@Controller
public class CashController {
	@Autowired private CashService cashService;
	
	@GetMapping("/removeCashByDate") // 게시판 상세 리스트 삭제
	public String removeCashByDate(HttpSession session, int cashNo) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		cashService.removeCashByDate(cashNo);
		return "getCashListByDate";
	}
	
	@GetMapping("/addCashByDate")
	public String addCashByDate(HttpSession session) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		
		return "addCash";
	}
	
	@GetMapping("/getCashListByDate") // 로그인 사용자의 오늘날짜 cash 목록
	public String getCashListByDate(HttpSession session, Model model, @RequestParam(value="day", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate day) {
		if(day == null) {
			day = LocalDate.now();
		}
		System.out.println(day+"<--day");
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		
		// 로그인 아이디
		String loginMemberId = ((LoginMember)(session.getAttribute("loginMember"))).getMemberId();
		
		Cash cash = new Cash(); // + id, + date("yyyy-mm-dd")
		cash.setMemberId(loginMemberId);
		cash.setCashDate(day);
		
		Map<String, Object> map = cashService.getCashListByDate(cash);
		model.addAttribute("cashKindSum", map.get("cashKindSum"));
		model.addAttribute("cashList", map.get("cashList"));
		model.addAttribute("day", day);
		
		
		//debugging
		/*
		for(Cash c : map.get("cashList")) {
			System.out.println(c);
		}
		*/
		return "getCashListByDate";
	}
}
