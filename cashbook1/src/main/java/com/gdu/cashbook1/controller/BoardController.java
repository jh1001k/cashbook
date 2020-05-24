package com.gdu.cashbook1.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook1.service.BoardService;
import com.gdu.cashbook1.vo.Board;
import com.gdu.cashbook1.vo.LoginMember;


@Controller
public class BoardController {
	@Autowired private BoardService boardService;
	
	// 게시판 리스트
	@GetMapping("/boardList")
	public String selectBoardListALL(HttpSession session, Model model) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		List<Board> list = boardService.selectBoardListALL();
		model.addAttribute("list", list);
		
		return "boardList";
	}
	// 게시글 입력
	@GetMapping("/addBoard")
	public String addBoard(HttpSession session) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		return "addBoard";
	}
	
	@PostMapping("/addBoard")
	public String addBoard(HttpSession session, Board board) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		String memberId = ((LoginMember)session.getAttribute("loginMember")).getMemberId();
		board.setMemberId(memberId);
		boardService.addBoard(board);
		return "redirect:/boardList";
	}
	// 게시글 상세 내용
	@GetMapping("/boardDetail")
	public String boardDetail(HttpSession session, Model model, @RequestParam("boardNo") int boardNo) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		System.out.println(boardNo+"<-- boardDetail boardNo");
		Board board = boardService.selectBoardListOne(boardNo);
		
		model.addAttribute("board", board);
		return "boardDetail";
	}
}

