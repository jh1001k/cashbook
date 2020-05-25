package com.gdu.cashbook1.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.gdu.cashbook1.service.CommentService;
import com.gdu.cashbook1.vo.Comment;
import com.gdu.cashbook1.vo.LoginMember;

@Controller
public class CommentController {
	@Autowired private CommentService commentService;
	@GetMapping("removeComment")
	public String removeComment(HttpSession session, Comment comment) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		commentService.removeComment(comment);
		return "redirect:/boardDetail?boardNo="+comment.getBoardNo();
	}
	
	// 댓글 수정
	@PostMapping("/modifyComment")
	public String modifyComment(HttpSession session, Comment comment) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		commentService.modifyComment(comment);
		return "redirect:/boardDetail?boardNo="+comment.getBoardNo();
	}
	
	// 댓글 입력
	@PostMapping("/addComment")
	public String addComment(HttpSession session, Comment comment) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		String memberId = ((LoginMember) (session.getAttribute("loginMember"))).getMemberId();
		comment.setMemberId(memberId);
		commentService.addComment(comment);
		return "redirect:/boardDetail?boardNo="+comment.getBoardNo();
	}
}
