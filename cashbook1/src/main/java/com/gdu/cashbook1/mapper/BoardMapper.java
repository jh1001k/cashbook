package com.gdu.cashbook1.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gdu.cashbook1.vo.Board;

@Mapper
public interface BoardMapper {
	
	public int deleteBoardByMember(String memberId); // 회원탈퇴시 게시글 삭제
	public int boardListCount(); // 게시글 개수
	public List<Board> selectSearchBoard(String searchBoard); // 게시글 검색
	public int updateBoard(Board board); // 게시글 수정
	public int deleteBoard(int boardNo); // 게시글 삭제
	public List<Board> selectBoardListALL(int beginRow, int rowPerPage); // 게시판 리스트 출력
	public int insertBoard(Board board); // 게시글 입력
	public Board selectBoardListOne(int boardNo); // 게시글 상세 내용
}
