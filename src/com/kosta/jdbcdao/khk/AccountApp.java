package com.kosta.jdbcdao.khk;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class AccountApp {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		AccountDao account = new AccountDaoImpl();
		
		// 현재 날짜
//		SimpleDateFormat now = new SimpleDateFormat("yyyy-MM-dd");
//		Date time = new Date();
//		String TradingDate = now.format(time);
		
		boolean run = true;
		long money;
		
		while (run) {
			System.out.println();
			System.out.println("--------------------------------------------------------------");
			System.out.println(" 1.예금 | 2.출금 | 3.잔액조회 | 4.거래일 조회 | 5.기간별 조회 | 6.종료");
			System.out.println("--------------------------------------------------------------");
			System.out.print("선택> ");
			int menuNo = sc.nextInt();
			switch (menuNo) {
			case 1:
				System.out.print("예금액> ");
				money = sc.nextInt();
				account.insertTradeInfo("예금", money);
				break;
			case 2:
				System.out.print("출금액> ");
				money = sc.nextInt();
				account.insertTradeInfo("출금", money);
				break;
			case 3:
				System.out.print("잔고액> ");
				System.out.println(account.getBalance());
				break;
			case 4:
				List<AccountVO> list = new ArrayList<AccountVO>();
				System.out.print("조회 날짜를 입력해주세요(yyyy-MM-dd)\n시작>> ");
				String TradingDate = sc.next();
				list = account.getList(TradingDate);
				if(list.size() == 0) {
					System.out.println("해당 날짜의 거래내역이 없습니다.");
				}else {
					System.out.println("조회일 : " + TradingDate);
					for (AccountVO vo : list) {
						System.out.println(	"입금액 : " + vo.getDeposit() + "\t"
										  + "출금액 : " + vo.getWithdraw() + "\t"
										  + "잔액 : " + vo.getBalance()	+ "\t"
										  + "거래일 : "+vo.getTr_date());
					}
				}
				break;
			case 5:
				System.out.print("조회 시작 날짜와 끝 날짜를 입력해주세요(yyyy-MM-dd)\n시작>> ");
				String dayStart = sc.next();
				System.out.print("종료>> ");
				String dayEnd = sc.next();
				List<AccountVO> btwlist = new ArrayList<AccountVO>();
				btwlist = account.getList(dayStart, dayEnd);
				System.out.println("조회시작기간 : "+ dayStart + "  조회종료기간 : " + dayEnd);
				for (AccountVO vo : btwlist) {
					System.out.println(	"입금액 : " + vo.getDeposit() + "\t"
										+"출금액 : " + vo.getWithdraw() + "\t"
										+"잔액 : " + vo.getBalance()	+ "\t"
										+"거래일 : "+vo.getTr_date());
				}
				break;
			case 6:
				System.out.println("종료");
				sc.close();
				run = false;
				break;
			default:
				System.out.println("다시 입력해주세요.");
				break;
			}
		}

	}

	

}
