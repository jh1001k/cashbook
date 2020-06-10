package com.gdu.cashbook1.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.gdu.cashbook1.service.CategoryService;
import com.gdu.cashbook1.vo.Category;

@Controller
public class CategoryController {
	@Autowired private CategoryService categoryService;
	// 관리자로 로그인시 카테고리 추가 기능 폼
		@GetMapping("/addCategory")
		public String addCategory(HttpSession session) {
			if(session.getAttribute("loginMember") == null) {
				return "redirect:/login";
			}
			return "addCategory";
		}
		
		//카테고리 추가 액션
		@PostMapping("/addCategory")
		public String addCategory(HttpSession session, Category category) {
			if(session.getAttribute("loginMember") == null) {
				return "redirect:/login";
			}
			categoryService.addCategory(category);
			return "adminHome";
		}
}
