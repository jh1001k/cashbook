package com.gdu.cashbook1.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook1.mapper.CashMapper;
import com.gdu.cashbook1.mapper.CategoryMapper;
import com.gdu.cashbook1.vo.Cash;
import com.gdu.cashbook1.vo.DayAndPrice;

@Service
@Transactional
public class CashService {
	@Autowired private CashMapper cashMapper;
	@Autowired private CategoryMapper categoryMapper;
	//  회원탈퇴시 캐시삭제
	public int removeCashByMember(String memberId) {
		return cashMapper.deleteCashByMember(memberId);
	}
	
	// 가계부 상세 페이지 수정
	public int updateCash(Cash cash) {
		return cashMapper.updateCash(cash);
	}
	
	// cashNo별 리스트 출력
	public List<Cash> selectCashListOne(int cashNo) {
		List<Cash> list = cashMapper.selectCashListOne(cashNo);
		return list;
	}
	// 카테고리네임 리스트 출력
	public List<String> selectCategoryAll() {
		List<String> list = categoryMapper.selectCategory();
		System.out.println(list);
		return list;
	}
	
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
