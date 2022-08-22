package com.kosta.jdbcdao.khk;

public class AccountVO {
	private int seq_id;		//일련번호
	private int deposit;	//입금
	private int withdraw;	//출금
	private String tr_date;	//거래일
	private int balance;	//잔액
	private int menuNum;	//메뉴번호
	
	public AccountVO() {
		super();
	}
	
	public AccountVO(int seq_id, int deposit, int withdraw, String tr_date, int balance) {
		super();
		this.seq_id = seq_id;
		this.deposit = deposit;
		this.withdraw = withdraw;
		this.tr_date = tr_date;
		this.balance = balance;
	}

	public int getSeq_id() {
		return seq_id;
	}
	public void setSeq_id(int seq_id) {
		this.seq_id = seq_id;
	}
	public int getDeposit() {
		return deposit;
	}
	public void setDeposit(int deposit) {
		this.deposit = deposit;
	}
	public int getWithdraw() {
		return withdraw;
	}
	public void setWithdraw(int withdraw) {
		this.withdraw = withdraw;
	}
	public String getTr_date() {
		return tr_date;
	}
	public void setTr_date(String tr_date) {
		this.tr_date = tr_date;
	}
	public int getBalance() {
		return this.balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public int getMenuNum() {
		return menuNum;
	}

	public void setMenuNum(int menuNum) {
		this.menuNum = menuNum;
	}

	@Override
	public String toString() {
		return this.balance + "원";
	}

	
}