package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaex.vo.UserVo;

public class UserDao {
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "1234";
	private String driver = "oracle.jdbc.driver.OracleDriver";
	public void getConnection() {

		try {
			Class.forName(driver);

			conn = DriverManager.getConnection(url, id, pw);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}// getConnection

	public void close() {

		try {
			if (conn != null)
				conn.close();

			if (pstmt != null)
				pstmt.close();

			if (rs != null)
				rs.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}// close

	public int insert(UserVo userVo) {
		int count = 0;
		getConnection();

		try {
			String query = "";
			query += " INSERT INTO users ";
			query += " values(seq_users_no.nextval, ";
			query += "        ?, ";		//id
			query += "        ?, ";		//password
			query += "        ?, ";		//name
			query += "        ? ";		//gender
			query += "         ) ";

			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, userVo.getId() );
			pstmt.setString(2, userVo.getPassword());
			pstmt.setString(3, userVo.getName() );
			pstmt.setString(4, userVo.getGender() );
			
			count = pstmt.executeUpdate();
			
			System.out.println("[DAO]insert :"+count+"건이 추가되었습니다");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close();
		return count;
	}

}
