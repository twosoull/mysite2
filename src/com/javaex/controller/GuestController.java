package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestbookDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestVo;

@WebServlet("/gbc")
public class GuestController extends HttpServlet {
	GuestbookDao guestDao = null;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("questbooklist");
		
		String action = request.getParameter("action");
		if ("add".equals(action)) {
			System.out.println("생성");
			
			String name = request.getParameter("name");
			String pw = request.getParameter("pass");
			String content = request.getParameter("content");
			
			guestDao.Insert(new GuestVo(name,pw,content));
			
			WebUtil.forward(request, response, "/gbc?action=list");
		}else if ("deleteForm".equals(action)) {
			System.out.println("딜리트폼");
			
			WebUtil.forward(request, response, "./WEB-INF/views/guest/deleteForm.jsp");
		}else if ("delete".equals(action)) {
			System.out.println("딜리트");
			
			int no = Integer.parseInt(request.getParameter("no"));
			String password = request.getParameter("pass");
			int count = guestDao.delete(new GuestVo(no,password));
			
			//password 불일치 
			if(count<1) {
				WebUtil.forward(request, response, "/gbc?action=deleteForm");
				System.out.println("번호가 일치하지 않습니다");
			}else if (count >=1){
				//password 일치
				WebUtil.forward(request, response, "/gbc?action=list");
				System.out.println("번호가 일치합니다.");
			}
		}else{
			System.out.println("리스트폼");
			
			guestDao = new GuestbookDao();
			List<GuestVo> guestList = guestDao.getList();
			
			request.setAttribute("guestList", guestList);
			WebUtil.forward(request, response, "./WEB-INF/views/guest/addList.jsp");
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
	}

}
