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

			while (rs.next()) {
				int no = rs.getInt("bno");
				String title = rs.getString("btitle");
				String uName = rs.getString("uname");
				int hit = rs.getInt("bhit");
				String date = rs.getString("bdate");

				BoardVo boardVo = new BoardVo(no, title, hit, date, uName);

				boardList.add(boardVo);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		close();
		return boardList;
	}// getBoardList

	public int insert(BoardVo boardVo) {
		getConnection();
		int count = 0;
		try {
			String query = "";
			query += " INSERT INTO board ";
			query += " values(seq_board_no.nextval, "; // 보드넘버
			query += "        ?, "; // 타이틀
			query += "        ?, "; // 콘텐트
			query += "        0, "; // 조회수
			query += "        sysdate, "; // 날짜
			query += "        ? "; // 유저아이디세션값
			query += "        ) ";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContent());
			pstmt.setInt(3, boardVo.getUserNo());

			count = pstmt.executeUpdate();

			System.out.println("[DAO]INSERT" + count + "건이 생성되었습니다");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		close();
		return count;
	}// insert()

	public BoardVo read(int no) {

		getConnection();
		BoardVo boardVo = null;
		try {
			String query = "";
			query += " select b.title as title, ";
			query += "        b.content as content, ";
			query += "        b.hit as hit, ";
			query += "        b.user_no as userNo, ";
			query += "        b.reg_date as reg_date, ";
			query += "        u.name as name ";
			query += " from board b,users u ";
			query += " where u.no = b.user_no ";
			query += " and b.no= ? ";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				String title = rs.getString("title");
				String content = rs.getString("content");
				int hit = rs.getInt("hit");
				int userNo = rs.getInt("userNo");
				String date = rs.getString("reg_date");
				String name = rs.getString("name");

				boardVo = new BoardVo(title, content, hit, date, userNo, name);
			}

			System.out.println(boardVo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		close();
		return boardVo;
	}// read

	public BoardVo getModifyBoardList(int no) {
		getConnection();
		
		BoardVo boardVo = null;
		try {
			String query = "";
			query += " select u.name as name, ";
			query += "        b.hit as hit, ";
			query += "        b.reg_date as reg_date, ";
			query += "        b.title as title, ";
			query += "        b.user_no as userNo, ";
			query += "        b.content as  content ";
			query += " from board b , users u ";
			query += " where b.user_no = u.no ";
			query += " and u.no = ? ";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String name = rs.getString("name");
				int hit = rs.getInt("hit");
				String date = rs.getString("reg_date");
				String title = rs.getString("title");
				int userNo = rs.getInt("userNo");
				String content = rs.getString("content");
				
				boardVo = new BoardVo(title,content,hit,date,userNo,name);
				
			}
			System.out.println(boardVo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		close();
		return boardVo;
	}//getModifyBoardList
	public int update(BoardVo boardVo) {
		getConnection();
		
		String query = "";
		query += " update board ";
		query += " set title = ?, ";
		query += "     content = ? ";
		query += " where no = ? " ;
		
		
		
		
		close();
		
	}
}//BoardDao
