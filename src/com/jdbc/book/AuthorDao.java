package com.jdbc.book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDao {
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
	public List<AuthorVo> getList() {
		List<AuthorVo> list = new ArrayList<AuthorVo>();
		conn = getConnection();
		String sql = " SELECT AUTHOR_ID, AUTHOR_NAME, AUTHOR_DESC FROM AUTHOR ";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int author_id = rs.getInt(1);
				String author_name = rs.getString(2);
				String author_desc = rs.getString(3);
				AuthorVo vo = new AuthorVo(author_id, author_name, author_desc);
				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	// insert
	public void insert(AuthorVo vo) {
		conn = getConnection();
		String sql = "INSERT INTO author VALUES (?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, vo.getAuthor_id());
	        pstmt.setString(2, vo.getAuthor_name());
			pstmt.setString(3, vo.getAuthor_desc());
			int count = pstmt.executeUpdate();
			
			// 4.결과처리
            System.out.println(count + "건 처리");
            
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// update
	public void update(AuthorVo vo) {
		conn = getConnection();
		String query =  "UPDATE author " + 
						"SET author_name = ?, " + 
							"author_desc = ?  "	+ 
						"WHERE author_id = ? ";
		
		try {
			pstmt = conn.prepareStatement(query);
	        pstmt.setString(1, vo.getAuthor_name());
			pstmt.setString(2, vo.getAuthor_desc());
			pstmt.setLong(3, vo.getAuthor_id());
			
			int count = pstmt.executeUpdate();
			
			// 4.결과처리
			System.out.println(count + "건 Update 완료");
            
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// delete
	public void delete(Long authorId) {
		conn = getConnection();
		String sql = "DELETE FROM author WHERE author_id = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, authorId);
			int count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 delete 완료");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
