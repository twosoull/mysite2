package com.javaex.dao;

import com.javaex.vo.UserVo;

public class UserDaoTest {
	
	public static void main(String[] args) {
		UserDao userDao = new UserDao();
		UserVo vo = userDao.getUser("초사이안","1212");
		
		System.out.println(vo);
	}
}
