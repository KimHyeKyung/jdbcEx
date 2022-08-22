package com.kosta.jdbcdao.khk;

import java.util.List;

public interface AccountDao {

	// 잔액조회
	public AccountVO getBalance();

	// 모든 거래내역
	public List<AccountVO> getListAll();

	// 기간별 조회 getList(검색시작일, 검색종료일)
	public List<AccountVO> getList(String searchStartDate, String searchEndDate);
	
	// 거래일 조회 getList(거래일)
	public List<AccountVO> getList(String TradingDate);

	// insertTradeInfo(예금,예금액) or insertTradeInfo(출금,출금액)
	public int insertTradeInfo(String tr, Long money);
	
	 
}
