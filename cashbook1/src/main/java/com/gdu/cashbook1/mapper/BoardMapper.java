package com.gdu.cashbook1.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook1.vo.Board;

@Mapper
public interface BoardMapper {
	
	public int updateBoard(Board board); // 게시글 수정
	public int deleteBoard(int boardNo); // 게시글 삭제
	public List<Board> selectBoardListALL(); // 게시판 리스트 출력
	public int insertBoard(Board board); // 게시글 입력
	public Board selectBoardListOne(int boardNo); // 게시글 상세 내용
}
