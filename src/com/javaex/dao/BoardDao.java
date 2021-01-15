package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;

public class BoardDao {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "1234";

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

	}// getConnection()

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

	}// close()

	public List<BoardVo> getBoardList() {
		getConnection();
		List<BoardVo> boardList = new ArrayList<BoardVo>();
		try {
			String query = " ";
			
			query += " select bo.no bno, ";
			query += "        bo.title btitle, ";
			query += "        us.name uname, ";
			query += "        bo.hit bhit, ";
			query += "        bo.reg_date bdate ";
			query += " from  users us ,  board bo ";
			query += " where us.no = bo.user_no ";
			query += " order by bo.no desc ";
			

			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int no = rs.getInt("bno");
				String title = rs.getString("btitle");
				String uName = rs.getString("uname");
				int hit = rs.getInt("bhit");
				String date = rs.getString("bdate");
				
				BoardVo boardVo = new BoardVo(no,title,hit,date,uName);
				
				
				boardList.add(boardVo);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		close();
		return boardList;
	}
}
