package com.gdu.cashbook1.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook1.service.BoardService;
import com.gdu.cashbook1.service.CommentService;
import com.gdu.cashbook1.vo.Board;
import com.gdu.cashbook1.vo.Comment;
import com.gdu.cashbook1.vo.LoginMember;


@Controller
public class BoardController {
	@Autowired private BoardService boardService;
	@Autowired private CommentService commentService;
	
	// 게시글 수정
	@GetMapping("/modifyBoard")
	public String modifyBoard(HttpSession session, Model model, @RequestParam("boardNo") int boardNo) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		System.out.println(boardNo+"<-- modifyBoard.boardNo");
		Board board = boardService.selectBoardListOne(boardNo);
		model.addAttribute("board", board);
		return "modifyBoard";
	}
	@PostMapping("/modifyBoard")
	public String modifyBoard(HttpSession session, Board board, @RequestParam("boardNo") int boardNo) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		board.setBoardNo(boardNo);
		boardService.modifyBoard(board);
		System.out.println(board +"<--modifyBoard.board");
		return "redirect:/boardDetail?boardNo="+boardNo;
	}
	
	// 게시글 삭제
	@GetMapping("/removeBoard")
	public String removeBoard(HttpSession session, @RequestParam("boardNo") int boardNo) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		commentService.removeCommentByBoard(boardNo);
		boardService.removeBoard(boardNo);
		return "redirect:/boardList";
	}
	
	// 게시판 리스트
	@GetMapping("/boardList")
	public String selectBoardListALL(HttpSession session, Model model, String searchBoard, @RequestParam(value = "currentPage", defaultValue = "1") int currentPage) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		System.out.println(searchBoard + "<--boardList.searchBoard");
		// 검색기능 추가
		if(searchBoard == null || searchBoard == "") {
			Map<String, Object> map = boardService.selectBoardListALL(currentPage);
			model.addAttribute("list", map.get("list"));
			model.addAttribute("lastPage", map.get("lastPage"));
			model.addAttribute("currentPage", currentPage);
		} else {
			List<Board> list = boardService.selectSearchBoard(searchBoard);
			model.addAttribute("list", list);
		}
		
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
		List<Comment> list = commentService.selectCommentList(boardNo);
		model.addAttribute("board", board);
		model.addAttribute("list", list);
		return "boardDetail";
	}
}

