package com.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDao {
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

	// select
	public List<BookVo> getList() {
		List<BookVo> list = new ArrayList<BookVo>();
		conn = getConnection();
		String sql = " SELECT book_id, title, pubs, pub_date, author_id FROM book ";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int book_id = rs.getInt(1);
				String title = rs.getString(2);
				String pubs = rs.getString(3);
				String pub_date = rs.getString(4);
				int author_id = rs.getInt(5);
				BookVo vo = new BookVo(book_id, title, pubs, pub_date ,author_id);
				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	// insert
	public void insert(BookVo vo) {
		conn = getConnection();
		String sql = "INSERT INTO book VALUES (?, ?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, vo.getBook_id());
	        pstmt.setString(2, vo.getTitle());
			pstmt.setString(3, vo.getPubs());
			pstmt.setString(4, vo.getPub_date());
			pstmt.setLong(5, vo.getAuthor_id());
			int count = pstmt.executeUpdate();
			
			// 4.결과처리
            System.out.println(count + "건 처리");
            
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// update
	public void update(BookVo vo) {
		conn = getConnection();
		String query =  "UPDATE book " + 
						"SET title = ? " + 
						"WHERE book_id = ? ";
		
		try {
			pstmt = conn.prepareStatement(query);
	        pstmt.setString(1, vo.getTitle());
			pstmt.setLong(2, vo.getBook_id());
			
			int count = pstmt.executeUpdate();
			
			// 4.결과처리
			System.out.println(count + "건 Update 완료");
            
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// delete
	public void delete(Long book_id) {
		conn = getConnection();
		String sql = "DELETE FROM book WHERE book_id = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, book_id);
			int count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 delete 완료");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//find
	public List<BookVo> findList(String title) {
		List<BookVo> list2 = new ArrayList<BookVo>();
		conn = getConnection();
		String sql =  "SELECT b.BOOK_ID , b.TITLE , b.PUBS ,PUB_DATE, b.author_id \r\n"
	         		+ "FROM BOOK b \r\n"
	         		+ "WHERE b.TITLE like ?";
		try {
			pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + title + "%");
            rs = pstmt.executeQuery();
            while (rs.next()) {
            	int book_id = rs.getInt(1);
            	String bookTitle = rs.getString(2);
            	String pubs = rs.getString(3);
            	String pub_date = rs.getString(4);
            	int author_id = rs.getInt(5);
            	BookVo vo = new BookVo(book_id, bookTitle, pubs, pub_date ,author_id);
            	list2.add(vo);
            }

            getList();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list2;
	}
}
