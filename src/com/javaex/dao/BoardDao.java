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

			query += " select bo.no as bno, ";
			query += "        bo.title as btitle, ";
			query += "        us.name as uname, ";
			query += "        bo.user_no as userNo,";
			query += "        bo.hit as bhit, ";
			query += "        to_char(bo.reg_date,'yy-mm-dd hh24:mi') as bdate ";
			query += " from  users us ,  board bo ";
			query += " where us.no = bo.user_no ";
			query += " order by bo.no desc ";

			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int no = rs.getInt("bno");
				String title = rs.getString("btitle");
				String uName = rs.getString("uname");
				int userNo = rs.getInt("userNo");
				int hit = rs.getInt("bhit");
				String date = rs.getString("bdate");

				BoardVo boardVo = new BoardVo(no, title, hit, date, userNo, uName);

				boardList.add(boardVo);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		close();
		return boardList;
	}// getBoardList

	public List<BoardVo> getBoardList(String search) {
		getConnection();
		List<BoardVo> boardList = new ArrayList<BoardVo>(); 
		try {
			String query = "";
			query += " select bo.no as bno, ";
			query += "        bo.title as btitle, ";
			query += "        us.name  as uname, ";
			query += "        bo.user_no as userno, ";
			query += "        bo.hit as bhit, ";
			query += "        to_char(bo.reg_date,'yy-mm-dd hh24:mi') as bdate ";
			query += " from board bo, users us ";
			query += " where bo.user_no = us.no ";
			query += " and (bo.title like ?  or us.name like ? ) ";
			query += " order by bo.no desc ";
			
			String like = "%" + search + "%";
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1,like);
			pstmt.setString(2,like);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				int bNo = rs.getInt("bno");
				String bTitle = rs.getString("btitle");
				String uName = rs.getString("uname");
				int userNo = rs.getInt("userno");
				int bHit = rs.getInt("bhit");
				String bDate = rs.getString("bdate");
				
				BoardVo boardVo  = new BoardVo(bNo,bTitle,bHit,bDate,userNo,uName);
				
				boardList.add(boardVo);
			
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close();
		return boardList;
		
	}

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

	public int hitUp(int no) {
		getConnection();
		// 맞게가는지 모르겠다 다음에 질문할 것
		int count = 0;
		try {
			String query = "";
			query += " UPDATE board ";
			query += " set hit = hit + 1 ";
			query += " where no = ? ";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			count = pstmt.executeUpdate();

			System.out.println("[DAO]hitUp:" + no + "번게시물의  조회수가 " + count + " 올랐습니다");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		close();
		return count;
	}

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

	public BoardVo getModifyBoardList(int uNo, int bNo) {
		getConnection();

		BoardVo boardVo = null;
		try {
			String query = "";
			query += " select u.name as name, ";
			query += "        b.hit as hit, ";
			query += "        b.reg_date as reg_date, ";
			query += "        b.title as title, ";
			query += "        b.no as boardNo, ";
			query += "        b.content as  content ";
			query += " from board b , users u ";
			query += " where b.user_no = u.no ";
			query += " and u.no = ? ";
			query += " and b.no = ? ";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, uNo);
			pstmt.setInt(2, bNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String name = rs.getString("name");
				int hit = rs.getInt("hit");
				String date = rs.getString("reg_date");
				String title = rs.getString("title");
				int boardNo = rs.getInt("boardNo");
				String content = rs.getString("content");

				boardVo = new BoardVo(boardNo, title, content, hit, date, name);

			}
			System.out.println(boardVo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		close();
		return boardVo;
	}// getModifyBoardList

	public int update(BoardVo boardVo) {
		getConnection();
		int count = 0;
		try {
			String query = "";
			query += " update board ";
			query += " set title = ?, ";
			query += "     content = ? ";
			query += " where no = ? ";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContent());
			pstmt.setInt(3, boardVo.getNo());

			count = pstmt.executeUpdate();

			System.out.println("[DAO]UPDATE" + count + "건이 수정되었습니다");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		close();
		return count;
	}// update

	public int delete(int no) {
		getConnection();
		int count = 0;
		try {
			String query = "";
			query += " DELETE board ";
			query += " where no = ? ";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			count = pstmt.executeUpdate();

			System.out.println("[DAO]DELETE" + count + "건이 삭제되었습니다");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		close();
		return count;
	}
}// BoardDao
