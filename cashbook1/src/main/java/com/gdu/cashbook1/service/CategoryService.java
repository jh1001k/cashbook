package com.gdu.cashbook1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook1.mapper.CategoryMapper;
import com.gdu.cashbook1.vo.Category;

@Service
@Transactional
public class CategoryService {
	@Autowired private CategoryMapper categoryMapper;
	public int addCategory(Category category) {
		return categoryMapper.insertCategory(category);
	}
}
