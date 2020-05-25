package com.gdu.cashbook1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook1.mapper.BoardMapper;
import com.gdu.cashbook1.vo.Board;

@Service
@Transactional
public class BoardService {
	@Autowired private BoardMapper boardMapper;
	
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
	public List<Board> selectBoardListALL() {
		List<Board> list = boardMapper.selectBoardListALL();
		return list;
	}
	// 게시글 입력
	public int addBoard(Board board) {
		return boardMapper.insertBoard(board);
	}
}
