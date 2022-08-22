package com.jdbc.book;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookInfo {

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
            System.out.println("접속성공");

            // 3. SQL문 준비 / 바인딩 / 실행
            String query = "SELECT b.BOOK_ID , b.TITLE , b.PUBS ,TO_CHAR(b.PUB_DATE ,'yyyy-mm-dd') pub_date,a.AUTHOR_ID ,a.AUTHOR_NAME ,a.AUTHOR_DESC \r\n"
		            		+ "FROM BOOK b , AUTHOR a \r\n"
		            		+ "WHERE b.AUTHOR_ID  = a.AUTHOR_ID";
            
            
            pstmt = conn.prepareStatement(query);

            rs = pstmt.executeQuery();

            // 4.결과처리(join가능!!)
            while (rs.next()) {
                int BOOK_ID = rs.getInt("BOOK_ID");
                String TITLE = rs.getString("TITLE");
                String PUBS = rs.getString("PUBS");
                Date PUB_DATE = rs.getDate("PUB_DATE");
                int AUTHOR_ID = rs.getInt("AUTHOR_ID");
                String AUTHOR_NAME = rs.getString("AUTHOR_NAME");
                String AUTHOR_DESC = rs.getString("AUTHOR_DESC");
                System.out.println(BOOK_ID + "\t" + TITLE + "\t\t\t" +PUBS + "\t\t" + PUB_DATE + "\t"+ AUTHOR_ID + "\t\t" + AUTHOR_NAME + "\t\t" + AUTHOR_DESC + "\t");
            }

        } catch (ClassNotFoundException e) {
            System.out.println("error: 드라이버 로딩 실패 - " + e);
        } catch (SQLException e) {
            System.out.println("error:" + e);
        } finally {
            // 5. 자원정리
            try {
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
