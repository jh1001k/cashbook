package com.gdu.cashbook1.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook1.mapper.BoardMapper;
import com.gdu.cashbook1.vo.Board;

@Service
@Transactional
public class BoardService {
	@Autowired private BoardMapper boardMapper;
	// 회원탈퇴시 게시글 삭제
	public int removeBoardByMember(String memberId) {
		return boardMapper.deleteBoardByMember(memberId);
	}
	
	// 게시글 검색
	public List<Board> selectSearchBoard(String searchBoard) {
		List<Board> list = boardMapper.selectSearchBoard(searchBoard);
		return list;
	}
	
	// 게시글 수정
	public int modifyBoard(Board board) {
		return boardMapper.updateBoard(board);
	}
	
	// 게시글 삭제
	public int removeBoard(int boardNo) {
		return boardMapper.deleteBoard(boardNo);
	}
	
	// 게시판 상세 내용
	public Board selectBoardListOne(int boardNo) {
		Board board = boardMapper.selectBoardListOne(boardNo);
		return board;
	}
	// 게시판 리스트 출력
	public Map<String, Object> selectBoardListALL(int currentPage) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 페이징
		int rowPerPage = 10;
		int beginRow = (currentPage - 1)*rowPerPage;
		int count = boardMapper.boardListCount();
		System.out.println(count+"<-- boardService.count");
		int lastPage = count / rowPerPage;
		if(count % rowPerPage != 0) {
			lastPage += 1;
		}
		map.put("lastPage", lastPage);
		List<Board> list = boardMapper.selectBoardListALL(beginRow, rowPerPage);
		map.put("list", list);
		return map;
	}
	// 게시글 입력
	public int addBoard(Board board) {
		return boardMapper.insertBoard(board);
	}
}
