package com.gdu.cashbook1.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook1.mapper.CashMapper;
import com.gdu.cashbook1.vo.Cash;

@Service
@Transactional
public class CashService {
	@Autowired private CashMapper cashMapper;
	public int removeCashByDate(int cashNo) {
		return cashMapper.deleteCashByDate(cashNo);
	}
	
	public Map<String, Object> getCashListByDate(Cash cash) { // 로그인 사용자의 오늘날짜 cash 목록
		List<Cash> cashList = cashMapper.selectCashListByDate(cash);
		int cashKindSum = cashMapper.selectCashKindSum(cash);
		Map<String, Object> map = new HashMap<>();
		map.put("cashList", cashList);
		map.put("cashKindSum", cashKindSum);
		return map;
	}
	
	public int addCashByDate(Cash cash) {
		return cashMapper.insertCashByDate(cash); // 오늘 날짜 가계부 입력
	}
}
