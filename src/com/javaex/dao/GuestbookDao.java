package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.GuestVo;

public class GuestbookDao {
	// 필드
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String id = "webdb";
	private String pw = "1234";

	// 드라이버
	public void getDriver() {
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

	}

	// 클로스
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

	}

	// 인서트
	public int Insert(GuestVo gv) {
		int count = 0;
		getDriver();
		try {

			String query =  " INSERT INTO guestbook values(SEQ_GUESTBOOK_NO.nextval,?,?,?,sysdate) ";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1,gv.getName());
			pstmt.setString(2,gv.getPassword());
			pstmt.setString(3,gv.getContent());
			count = pstmt.executeUpdate();
			
			//실행메세지
			System.out.println("[DAO]:"+count+"건이 입력되었습니다");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close();
		return count;
	}
	// 셀렉트
	public List<GuestVo> getList() {
		getDriver();
		List<GuestVo> guestList = new ArrayList<GuestVo>();
		try {
		String query = "";
		query += " SELECT no, ";
		query += "        name,  ";
		query += "        password, ";
		query += "        content, ";
		query += "        reg_date ";
		query += " FROM guestbook ";
		pstmt = conn.prepareStatement(query);
		rs = pstmt.executeQuery();
		
		while(rs.next()) {
			int no =rs.getInt("no");
			String name = rs.getString("name");
			String password = rs.getString("password");
			String content = rs.getString("content");
			String date = rs.getString("reg_date");
			
			GuestVo guestVo = new GuestVo(no,name,password,content,date);
			guestList.add(guestVo);
			
		}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		close();
		return guestList;
		
	}
	// 딜리트
	public int delete(GuestVo guestVo) {
		getDriver();
		int count = 0;
		
		try {
		String query = "";
		query += " DELETE guestbook ";
		query += " WHERE password = ? ";
		query += " and no = ? ";
		
		pstmt = conn.prepareStatement(query);
		
		pstmt.setString(1, guestVo.getPassword());	
		pstmt.setInt(2,guestVo.getNo());
		count = pstmt.executeUpdate();
		
		//결과
		System.out.println("[DAO]"+count+"건을 삭제했습니다");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close();
		return count;
		
	}//Delete
	// 비밀번호확인
	public GuestVo checkPassword(int guestNo) {
		getDriver();
		GuestVo guestVo = null;
		try {
		String query = "";
		query += " SELECT no, ";
		query += "        name,  ";
		query += "        password, ";
		query += "        content, ";
		query += "        reg_date ";
		query += " FROM guestbook ";
		query += " where no = ? ";
		pstmt = conn.prepareStatement(query);
		pstmt.setInt(1, guestNo);
		
		//업데이트 후에 쿼리문을 리서트셋에 넣어줘야했다..
		pstmt.executeUpdate();
		rs = pstmt.executeQuery();
		
		while(rs.next()) {
			int no =rs.getInt("no");
			String name = rs.getString("name");
			String password = rs.getString("password");
			String content = rs.getString("content");
			String date = rs.getString("reg_date");
			
			guestVo  = new GuestVo(no,name,password,content,date);
		}
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		close();
		return guestVo;
		
	}//checkPassword
}
