package com.gdu.cashbook1.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook1.vo.Comment;

@Mapper
public interface CommentMapper {
	public int deleteComment(Comment comment); // 댓글 삭제
	public int insertComment(Comment comment); // 댓글입력
	public List<Comment> selectCommentList(int boardNo); // 댓글 리스트
	public int updateComment(Comment comment); // 댓글 수정
}
