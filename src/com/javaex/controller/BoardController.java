package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@WebServlet("/board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("BoardController");
		
		//action 받기
		String action = request.getParameter("action");
		
		if("list".equals(action)) {
			System.out.println("list");
			
			//리스트용 리스트 불러오기
			BoardDao boardDao = new BoardDao();
			List<BoardVo> boardList = boardDao.getBoardList();
			
			//어트리뷰트
			request.setAttribute("boardList", boardList);
			WebUtil.forward(request, response, "./WEB-INF/views/board/list.jsp");
			
		}else if("writeForm".equals(action)) {
			System.out.println("글쓰기폼");
			
			WebUtil.forward(request, response, "./WEB-INF/views/board/writeForm.jsp");
		}else if("write".equals(action)) {
			System.out.println("write");
			//세션에서 값authUser받기
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			
			//Vo에 넣기
			int num = authUser.getNo();
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			
			//Dao를 통해 인서트하기
			BoardVo boardVo = new BoardVo(title,content,num);
			
			BoardDao boardDao = new BoardDao();
			boardDao.insert(boardVo);
			
			WebUtil.redirect(request, response, "/mysite02/board?action=list");
			
		}else if("read".equals(action)) {
			System.out.println("읽기");
			int no = Integer.parseInt(request.getParameter("no"));
			
			BoardDao boardDao = new BoardDao();
			
			BoardVo boardVo = boardDao.read(no);
			
			request.setAttribute("boardVo", boardVo);
			WebUtil.forward(request, response, "./WEB-INF/views/board/read.jsp");
		}else if("modifyForm".equals(action)) {
			System.out.println("수정폼");
			
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			System.out.println("dd");
			
			int no = authUser.getNo();
			
			BoardDao boardDao = new BoardDao();
			BoardVo boardVo = boardDao.getModifyBoardList(no);
			
			
			request.setAttribute("boardVo", boardVo);
			WebUtil.forward(request, response, "./WEB-INF/views/board/modifyForm.jsp");
			
		}else if("modify".equals(action)) {
			System.out.println("수정");
			
			
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}



