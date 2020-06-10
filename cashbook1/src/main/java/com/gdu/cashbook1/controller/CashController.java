package com.gdu.cashbook1.controller;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook1.service.CashService;
import com.gdu.cashbook1.vo.Cash;
import com.gdu.cashbook1.vo.DayAndPrice;
import com.gdu.cashbook1.vo.LoginMember;

@Controller
public class CashController {
	@Autowired private CashService cashService;
	// 일자별 가계부 상세 리스트 수정 getMapping
	@GetMapping("/modifyCashByDate")
	public String modifyCashByDate(HttpSession session, Model model, @RequestParam("cashNo") int cashNo) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		List<String> list = cashService.selectCategoryAll();
		List<Cash> cashNoList = cashService.selectCashListOne(cashNo);
		model.addAttribute("cashNoList", cashNoList);
		model.addAttribute("list", list);
		System.out.println(list+"<--list");
		return "modifyCashByDate";
	}
	// 일자별 가계부 상세 리스트 수정 postMapping
	@PostMapping("/modifyCashByDate")
	public String modifyCashByDate(HttpSession session, Cash cash) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		
		System.out.println(cash);
		cashService.updateCash(cash);
		return "redirect:/getCashListByDate";
	}
	
	@GetMapping("/removeCashByDate") // 게시판 상세 리스트 삭제
	public String removeCashByDate(HttpSession session, @RequestParam("cashNo") int cashNo) {
		System.out.println(cashNo+"<--Controller cashNo");
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		cashService.removeCashByDate(cashNo);
		return "redirect:/getCashListByDate";
	}
		
	@GetMapping("/getCashListByMonth")
	public String getCashListByMonth(HttpSession session, Model model, @RequestParam(value="day", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate day) {
		
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		
		Calendar cDay = Calendar.getInstance(); // 오늘
		
		if(day == null) {	
			day = LocalDate.now();
			
		} else {
			/*
			 * 0. 오늘 LocalDate 타입
			 * 1. 오늘 Calendar 타입
			 * 2. 이번달의 마지막 일
			 * 3. 이번달 1일의 요일
			 */
			cDay.set(day.getYear(), day.getMonthValue()-1, day.getDayOfMonth());
		}
		// 일별 수입, 지출 총액
		String memberId = ((LoginMember)session.getAttribute("loginMember")).getMemberId();
		int year = cDay.get(Calendar.YEAR);
		int month = cDay.get(Calendar.MONTH)+1;		
		List<DayAndPrice> dayAndPriceList = cashService.getCashAndPriceList(memberId, year, month);
		System.out.println(dayAndPriceList);
		
		// 디버깅코드
		for(DayAndPrice dp : dayAndPriceList) {
			System.out.println(dp);
		}
		// int dayMonth = day.getMonthValue()+1;
		// System.out.println(dayMonth+"<--먼쓰");
		model.addAttribute("month", day.getMonthValue());
		model.addAttribute("year", day.getYear());
		model.addAttribute("day", day);
		//	model.addAttribute("month", cDay.get(Calendar.MONTH)+1); // 월
		model.addAttribute("lastDay", cDay.getActualMaximum(Calendar.DATE)); // 마지막 일
		model.addAttribute("dayAndPriceList", dayAndPriceList);
		Calendar firstDay = cDay;
		//firstDay.clear();	
		//firstDay.set(Calendar.YEAR,  cDay.get(Calendar.YEAR));
		firstDay.set(Calendar.DATE, 1); // 일만 1일로 변경
		// firstDay.get(Calendar.DAY_OF_WEEK); // 0 일요일, 1 월요일, ...
		model.addAttribute("firstDayOfWeek", firstDay.get(Calendar.DAY_OF_WEEK));
		return "getCashListByMonth";
	}
	
	// 가계부 추가
	@GetMapping("/addCashByDate")
	public String addCashByDate(HttpSession session, Model model, @RequestParam(value = "day", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate day) {	
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		// category_name list
		List<String> list = cashService.selectCategoryAll();
		
		model.addAttribute("categoryList", list);
		model.addAttribute("day", day);
		
		
		return "addCash";
	}
	
	@PostMapping("/addCashByDate")
	public String addCashByDate(HttpSession session, Cash cash, @RequestParam(value = "day", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate day) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		String memberId = ((LoginMember) (session.getAttribute("loginMember"))).getMemberId();
		cash.setMemberId(memberId);
		cash.setCashDate(day);
		cashService.addCashByDate(cash);
		return "redirect:/getCashListByDate";
	}
	
	@GetMapping("/getCashListByDate") // 로그인 사용자의 오늘날짜 cash 목록
	public String getCashListByDate(HttpSession session, Model model, @RequestParam(value="day", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate day) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
	
		if(day == null) {
			day = LocalDate.now();
		}
		System.out.println(day+"<--day");
		
		
		// 로그인 아이디
		String loginMemberId = ((LoginMember)(session.getAttribute("loginMember"))).getMemberId();
		
		Cash cash = new Cash();  // + id, + date("yyyy-mm-dd")
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
