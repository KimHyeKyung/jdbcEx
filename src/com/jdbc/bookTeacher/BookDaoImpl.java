package com.jdbc.bookTeacher;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDaoImpl implements BookDao {
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
	}

	@Override
	public List<BookVo> getList() {
		List<BookVo> list = new ArrayList<BookVo>();
		conn = getConnection();
		String sql = " SELECT b.BOOK_ID ,\n" + "       b.TITLE ,\n" + "       b.PUBS ,\n"
				+ "       TO_CHAR(b.PUB_DATE ,'YYYY-MM-DD') PUB_DATE,\n" + "       a.AUTHOR_NAME \n"
				+ " FROM BOOK b , AUTHOR a \n" + " WHERE a.AUTHOR_ID = b.AUTHOR_ID";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				long book_id = rs.getInt(1);
				String title = rs.getString(2);
				String pubs = rs.getString(3);
				String pub_date = rs.getString(4);
				String author_name = rs.getString(5);
				BookVo vo = new BookVo(book_id, title, pubs, pub_date, author_name);
				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<BookVo> getSearchList(String keyWord) {
		List<BookVo> list = new ArrayList<BookVo>();
		conn = getConnection();
		String sql = " SELECT b.BOOK_ID ,\n" + "       b.TITLE ,\n" + "       b.PUBS ,\n"
				+ "       b.PUB_DATE,\n" + "       a.AUTHOR_NAME \n"
				+ " FROM BOOK b , AUTHOR a \n" + " WHERE a.AUTHOR_ID = b.AUTHOR_ID \n"
				+ " AND b.TITLE || b.PUBS || a.AUTHOR_NAME LIKE ? ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, '%' + keyWord + '%');
			rs = pstmt.executeQuery();

			while (rs.next()) {
				long book_id = rs.getInt(1);
				String title = rs.getString(2);
				String pubs = rs.getString(3);
				String pub_date = rs.getString(4);
				String author_name = rs.getString(5);
				BookVo vo = new BookVo(book_id, title, pubs, pub_date, author_name);
				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

}
