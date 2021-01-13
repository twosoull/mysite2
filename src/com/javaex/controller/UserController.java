package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;

@WebServlet("/user")
public class UserController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("UserController");

		String action = request.getParameter("action");
		System.out.println("action = " + action);

		if ("joinForm".equals(action)) {
			System.out.println("회원가입폼");

			WebUtil.forward(request, response, "./WEB-INF/views/user/joinForm.jsp");
		} else if ("join".equals(action)) {
			System.out.println("회원가입(insert)");

			String id = request.getParameter("uid");
			String pw = request.getParameter("pw");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");

			UserDao userDao = new UserDao();
			userDao.insert(new UserVo(id, pw, name, gender));

			WebUtil.forward(request, response, "./WEB-INF/views/user/joinOk.jsp");
		} else if ("loginForm".equals(action)) {
			System.out.println("로그인폼");

			WebUtil.forward(request, response, "./WEB-INF/views/user/loginForm.jsp");
		} else if ("login".equals(action)) {
			System.out.println("로그인");
			// 파라미터
			String id = request.getParameter("id");
			String pw = request.getParameter("pw");

			// dao --> getUser();
			UserDao userDao = new UserDao();
			UserVo authVo = userDao.getUser(id, pw);

			System.out.println(authVo); // id,pw --> no,name

			if (authVo == null) {// 로그인실패
				System.out.println("로그인 실패");
				// 리다이렉트 -->로그인폼
				WebUtil.redirect(request, response, "/mysite02/user?action=loginForm");

			} else {
				System.out.println("로그인 성공");
				
				// 세션영역에 값을 넣어준다
				HttpSession session = request.getSession();
				session.setAttribute("authUser", authVo);
				
				WebUtil.redirect(request, response, "/mysite02/main");
			}

		} else if("logout".equals(action)) {
			System.out.println("로그아웃");
			
			//세션영역에 있는 vo를 삭제해야함
			HttpSession session = request.getSession();
			session.removeAttribute("authUser");
			session.invalidate();
			
			WebUtil.redirect(request, response, "/mysite02/main");
		}else if ("modifyForm".equals(action)) {
			System.out.println("회원정보수정폼");
			//********고민 2.테스트 해보니 접속중이 아니여도 url을 통해 접근 할 수 있다
			//트라이문으로 해결해보았다
			try {
			//두가지 방법이있다
			//1- 회원정보수정을 눌렀을 때 url에서 no의 값을 파라미터로 넘겨줘서 받는방법
			int no = Integer.parseInt(request.getParameter("no"));
			
			
			//2- 세션자체에서 받아내는 방법 (url 파라미터no 가 필요없으므로 변경필요)
			//뭐가 더 괜찮은 방법일까 고민해보기
			/*
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			System.out.println(authUser);
			int no = authUser.getNo();
			*/
			
			//userDao.getUser(no) 이용해서 vo 받아오기
			UserDao userDao = new UserDao();
			UserVo userVo = userDao.getUser(no);
			
			//request.setAttribute()로 어트리뷰트 값 넘겨주기
			
			request.setAttribute("UserVo", userVo);
			WebUtil.forward(request, response, "./WEB-INF/views/user/modifyForm.jsp");
			}catch (Exception e ) {
				System.out.println("잘못된 접근입니다");
				
			WebUtil.redirect(request, response, "/mysite02/main");
			}
			
		}else if("modify".equals(action)) {
			System.out.println("회원정보수정");
			
			//파라미터값 받기
			int no = Integer.parseInt(request.getParameter("no"));
			String password = request.getParameter("password");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			
			UserVo userVo = new UserVo(no,password,name,gender);
			//UserDao.update(userVo)로 데이터 값 수정
			
			UserDao userDao = new UserDao();
			userDao.update(userVo);
			
			
			
			//수정완료 후 세션값도 함께 변경
			UserVo authUser = userDao.getUser(no);
			
			HttpSession session = request.getSession();
			session.removeAttribute("authUser");
			session.setAttribute("authUser", authUser);
			
			WebUtil.redirect(request, response, "/mysite02/main");
			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
