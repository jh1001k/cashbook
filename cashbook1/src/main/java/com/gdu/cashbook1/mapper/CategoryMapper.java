package com.gdu.cashbook1.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook1.vo.Category;

@Mapper
public interface CategoryMapper {
	public List<String> selectCategory(); // 카테고리 리스트
	public int insertCategory(Category category); // 카테고리 추가
}
