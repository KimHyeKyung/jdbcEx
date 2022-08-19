package com.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTest {

	public static void main(String[] args) {
		
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2. Connection 얻어오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "webdb", "1234");
			
			// 3. SQL문 준비 / 바인딩 / 실행
			String sql = "SELECT a.AUTHOR_ID ,a.AUTHOR_NAME ,a.AUTHOR_DESC FROM AUTHOR a "; //sql문
			pstmt = conn.prepareStatement(sql);	//바인딩
			rs = pstmt.executeQuery(sql); //sql실행
			
			// 4. 결과처리
			while(rs.next()) {
				System.out.println(rs.getInt(1) + ", \t" 
								   + rs.getString(2) + ", \t" 
								   + rs.getString(3) + "\t" 
									);
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 5. 자원정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		}


	}

}
