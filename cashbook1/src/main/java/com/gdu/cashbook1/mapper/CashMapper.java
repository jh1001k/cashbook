package com.gdu.cashbook1.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook1.vo.Cash;
import com.gdu.cashbook1.vo.DayAndPrice;

@Mapper
public interface CashMapper {
	
	public int updateCash(Cash cash); // 가계부 일자별 상세보기 수정
	public List<DayAndPrice> selectDayAndPriceList(Map<String, Object> map);
	public List<Cash> selectCashListOne(int cashNo);
	public int deleteCashByDate(int cashNo);
	public List<Cash> selectCashListByDate(Cash cash); // 로그인 사용자의 오늘날짜 cash 목록
	public int selectCashKindSum(Cash cash); // 가계부 상세보기 합계
	public int insertCashByDate(Cash cash); // 오늘 날짜 가계부 입력
}
