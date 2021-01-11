package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;

@WebServlet("/user")
public class UserController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("유저폼");
		
		String action = request.getParameter("action");
		System.out.println("action = " + action);
		
		if("joinForm".equals(action)) {
			System.out.println("회원가입폼");
			
			WebUtil.forward(request, response, "./WEB-INF/views/user/joinForm.jsp");
		}else if("join".equals(action)) {
			System.out.println("회원가입(insert)");
			
			String id = request.getParameter("uid");
			String pw = request.getParameter("pw");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			
			UserDao userDao = new UserDao();
			userDao.insert(new UserVo(id,pw,name,gender));
			
			WebUtil.forward(request, response, "./WEB-INF/views/user/joinOk.jsp");
		}else if("loginForm".equals(action)) {
			System.out.println("로그인폼");
			
			WebUtil.forward(request, response, "./WEB-INF/views/user/loginForm.jsp");
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
	}

}
