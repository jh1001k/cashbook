package com.gdu.cashbook1.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook1.vo.Category;

@Mapper
public interface CategoryMapper {
	public List<String> selectCategory();
}
