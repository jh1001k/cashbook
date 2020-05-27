package com.gdu.cashbook1.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook1.vo.Comment;

@Mapper
public interface CommentMapper {
	public int deleteCommentByBoard(int boardNo); // 게시글 삭제시 댓글 삭제
	public int deleteCommentByMember(String memberId); // 회원탈퇴시 댓글 삭제
	public int deleteComment(Comment comment); // 댓글 삭제
	public int insertComment(Comment comment); // 댓글입력
	public List<Comment> selectCommentList(int boardNo); // 댓글 리스트
	public int updateComment(Comment comment); // 댓글 수정
}
