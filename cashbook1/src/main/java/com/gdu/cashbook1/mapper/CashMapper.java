package com.gdu.cashbook1.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook1.vo.Cash;

@Mapper
public interface CashMapper {
	public List<Cash> selectCashListByDate(Cash cash); // 로그인 사용자의 오늘날짜 cash 목록
}
