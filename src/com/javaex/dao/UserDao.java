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
			query += "        ?, "; // id
			query += "        ?, "; // password
			query += "        ?, "; // name
			query += "        ? "; // gender
			query += "         ) ";

			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, userVo.getId());
			pstmt.setString(2, userVo.getPassword());
			pstmt.setString(3, userVo.getName());
			pstmt.setString(4, userVo.getGender());

			count = pstmt.executeUpdate();

			System.out.println("[DAO]insert :" + count + "건이 추가되었습니다");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close();
		return count;
	}

	public UserVo getUser(String id, String pw) {
		getConnection();
		UserVo userVo = null;

		try {
			String query = "";
			query += " SELECT no, ";
			query += "        name ";
			query += " FROM users ";
			query += " where id = ? ";
			query += " and password = ?  ";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);

			rs = pstmt.executeQuery();

			// 결과처림
			while (rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");

				userVo = new UserVo(no, name);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		close();
		return userVo;
	}// getUser(String id, String pw)

	public UserVo getUser(int UserNo) {
		UserVo userVo = null;
		getConnection();
		try {
			String query = "";
			query += " SELECT no, ";
			query += "        id, ";
			query += "        password, ";
			query += "        name, ";
			query += "        gender ";
			query += " FROM users ";
			query += " where no = ? ";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, UserNo);
			rs = pstmt.executeQuery();

			// 결과확인
			while (rs.next()) {
				int no = rs.getInt("no");
				String id = rs.getNString("id");
				String password = rs.getNString("password");
				String name = rs.getNString("name");
				String gender = rs.getNString("gender");

				userVo = new UserVo(no, id, password, name, gender);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		close();
		return userVo;
	}// getUser(int no)

	public void update(UserVo userVo) {
		getConnection();
		int count = 0;

		try {
			String query = "";
			query += " UPDATE users ";
			query += " set password = ? , ";
			query += "     name= ? , ";
			query += "     gender= ?  ";
			query += " where no = ? ";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1,userVo.getPassword());
			pstmt.setString(2,userVo.getName());
			pstmt.setString(3,userVo.getGender());
			pstmt.setInt(4, userVo.getNo());
			
			count = pstmt.executeUpdate();
			
			System.out.println("[DAO]Update : "+count+"건이 수정되었습니다.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		close();
	}
}
