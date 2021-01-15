package com.javaex.dao;

import com.javaex.vo.UserVo;

public class UserDaoTest {
	
	public static void main(String[] args) {
		
		BoardDao br = new BoardDao();
	
		System.out.println(br.getBoardList());
	
	}
}
