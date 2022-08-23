package com.kosta.jdbcdao.khk;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDaoImpl implements AccountDao{
	//DB연결을 위한 셋팅
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	public Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String dburl = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(dburl, "webdb", "1234");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}//셋팅끝
	
	AccountVO vo = new AccountVO();
	int count;
	int seq_id = 0;			//일련번호
	int deposit= 0;			//입금
	int withdraw= 0;		//출금
	String tr_date = "";	//거래일
	int balance= 0;			//잔액

	// 잔액조회
	public AccountVO getBalance() {
		conn = getConnection();
		String sql = 	"SELECT rownum, seq_id, deposit, withdraw, to_char(tr_date,'yyyy-mm-dd') tr_date, balance\r\n"
				+ "		 FROM (	SELECT seq_id, deposit, withdraw, tr_date, balance\r\n"
				+ "				FROM ACCOUNT\r\n"
				+ "				ORDER BY seq_id DESC\r\n"
				+ "			  )a\r\n"
				+ "		 WHERE rownum = ? \n";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 1); //seq_id desc한거 1번째 내역을 가져오기위해 셋팅
			rs = pstmt.executeQuery();

			/* 처음에 한 방법 -> return을 new AccountVO(seq_id, deposit, withdraw, tr_date, balance) ;로 했다.
				 while (rs.next()) {
					seq_id = rs.getInt("seq_id");			//일련번호
					deposit = rs.getInt("deposit");			//입금
					withdraw = rs.getInt("withdraw");		//출금
					tr_date = rs.getString("tr_date");		//거래일
					balance = rs.getInt("balance");			//잔액
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return new AccountVO(seq_id, deposit, withdraw, tr_date, balance) ;
			 */
			
			//두번째 방법 위에 vo가 선언되어있으니 vo에 set으로 값을 넣고 vo자체를 리턴하자.
			while (rs.next()) {
				seq_id = rs.getInt("seq_id");			//일련번호
				deposit = rs.getInt("deposit");			//입금
				withdraw = rs.getInt("withdraw");		//출금
				tr_date = rs.getString("tr_date");		//거래일
				balance = rs.getInt("balance");			//잔액
			}
			vo.setSeq_id(seq_id);
			vo.setDeposit(deposit);
			vo.setWithdraw(withdraw);
			vo.setTr_date(tr_date);
			vo.setBalance(balance);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return vo; //toString() 메소드는 자동으로 호출된다.
	}
	
	//getList(거래일)
	@Override
	public List<AccountVO> getList(String TradingDate) {
		List<AccountVO> todayList = new ArrayList<AccountVO>();
		conn = getConnection();
		
		String sql =  "SELECT seq_id, deposit, withdraw, TO_CHAR(tr_date, 'yyyy-mm-dd') tr_date, balance \n"
					+ "FROM ACCOUNT \n"
					+ "WHERE TO_CHAR(tr_date, 'yyyy-mm-dd') = ? ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, TradingDate);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				seq_id = rs.getInt("seq_id");			//일련번호
				deposit = rs.getInt("deposit");			//입금
				withdraw = rs.getInt("withdraw");		//출금
				tr_date = rs.getString("tr_date");		//거래일
				balance = rs.getInt("balance");			//잔액
				AccountVO vo = new AccountVO(seq_id, deposit, withdraw, this.tr_date, balance);
				todayList.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return todayList;
	}
		
	// getList(검색시작일, 검색종료일)
	@Override
	public List<AccountVO> getList(String searchStartDate, String searchEndDate) {
		List<AccountVO> btwList = new ArrayList<AccountVO>();
		conn = getConnection();
		
		String sql =  "SELECT seq_id, deposit, withdraw, TO_CHAR(tr_date, 'yyyy-mm-dd') tr_date, balance \n"
					+ "FROM ACCOUNT \n"
					+ "WHERE TO_CHAR(tr_date, 'yyyy-mm-dd') between ? and ? ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, searchStartDate);
			pstmt.setString(2, searchEndDate);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				seq_id = rs.getInt("seq_id");			//일련번호
				deposit = rs.getInt("deposit");			//입금
				withdraw = rs.getInt("withdraw");		//출금
				tr_date = rs.getString("tr_date");		//거래일
				balance = rs.getInt("balance");			//잔액
				AccountVO vo = new AccountVO(seq_id, deposit, withdraw, this.tr_date, balance);
				btwList.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return btwList;
	}
	
	// insertTradeInfo(예금,예금액) or insertTradeInfo(출금,출금액)
	@Override
	public int insertTradeInfo(String tr, Long money) {
		getBalance();
		conn = getConnection();
		if(tr.equals("예금")) {
			String sql =  "INSERT INTO account(seq_id, deposit, withdraw, tr_date, balance) \n"
						+ "select ACCOUNT_SEQ_ID.nextval, ?, ?, sysdate, balance + ? \n"
						+ "from account \n"
						+ "where seq_id = (select max(seq_id) from account)";
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setLong(1, money);	//예금
				pstmt.setInt(2, 0); 		//출금
				pstmt.setLong(3, money);	//잔액
				
				count = pstmt.executeUpdate();
		
		        // 4.결과처리
		        System.out.println(count + "건 처리");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else {
			if (money > vo.getBalance()) {
				System.out.println("출금액이 부족합니다.");
			} else {
				String sql = "INSERT INTO account(seq_id, deposit, withdraw, tr_date, balance) \n"
						+ "select ACCOUNT_SEQ_ID.nextval, ?, ?, sysdate, balance - ? \n" + "from account \n"
						+ "where seq_id = (select max(seq_id) from account)";
				try {
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, 0); // 예금
					pstmt.setLong(2, money); // 출금
					pstmt.setLong(3, money); // 잔액
		
					count = pstmt.executeUpdate();
		
					// 4.결과처리
					System.out.println(count + "건 처리");
				} catch (SQLException e) {
					e.printStackTrace();
					
				}
			}
		
		}
		
		return count;
	}

	
	// 모든 거래내역
	@Override
	public List<AccountVO> getListAll() {
		List<AccountVO> list = new ArrayList<AccountVO>();
		conn = getConnection();
		String sql = 	"SELECT seq_id, deposit, withdraw, tr_date, balance"
						+ "FROM ACCOUNT"
						+ "WHERE seq_id = ?"
						+ "ORDER BY seq_id DESC";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 1); //seq_id desc한거 1번째 내역을 가져오기위해 셋팅
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int seqId = rs.getInt(1);			//일련번호
				int deposit = rs.getInt(2);			//입금
				int withdraw = rs.getInt(3);		//출금
				String trDate = rs.getString(4);	//거래일
				int balance = rs.getInt(5);			//잔액
				AccountVO vo = new AccountVO(seqId, deposit, withdraw, trDate, balance);
				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

}


