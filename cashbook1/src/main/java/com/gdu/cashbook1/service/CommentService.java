package com.gdu.cashbook1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook1.mapper.CommentMapper;
import com.gdu.cashbook1.vo.Comment;

@Service
@Transactional
public class CommentService {
	@Autowired private CommentMapper commentMapper;
	// 댓글 삭제
	public int removeComment(Comment comment) {
		return commentMapper.deleteComment(comment);
	}
	
	// 댓글 수정
	public int modifyComment(Comment comment) {
		return commentMapper.updateComment(comment);
	}
	
	// 댓글 리스트
	public List<Comment> selectCommentList(int boardNo) {
		List<Comment> list = commentMapper.selectCommentList(boardNo);
		return list;
	}
	// 댓글 입력
	public int addComment(Comment comment) {
		return commentMapper.insertComment(comment);
	}
}
