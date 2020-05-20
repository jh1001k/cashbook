package com.gdu.cashbook1.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook1.mapper.CashMapper;
import com.gdu.cashbook1.vo.Cash;
import com.gdu.cashbook1.vo.DayAndPrice;

@Service
@Transactional
public class CashService {
	@Autowired private CashMapper cashMapper;
	public List<DayAndPrice> getCashAndPriceList(String memberId, int year, int month) {
		Map<String, Object> map = new HashMap<>();
		map.put("memberId", memberId);
		map.put("month", month);
		map.put("year", year);
		return cashMapper.selectDayAndPriceList(map);
	}
	
	// 가계부 일자별 상세 리스트 삭제
	public int removeCashByDate(int cashNo) {
		System.out.println(cashNo+"<-- cashService cashNo");
		return cashMapper.deleteCashByDate(cashNo);
	}
	
	// 로그인 사용자의 오늘날짜 cash 목록
	public Map<String, Object> getCashListByDate(Cash cash) { 
		List<Cash> cashList = cashMapper.selectCashListByDate(cash);
		int cashKindSum = cashMapper.selectCashKindSum(cash);
		Map<String, Object> map = new HashMap<>();
		map.put("cashList", cashList);
		map.put("cashKindSum", cashKindSum);
		return map;
	}
	
	// 오늘 날짜 가계부 입력
	public int addCashByDate(Cash cash) {
		return cashMapper.insertCashByDate(cash); 
	}
}
