package com.javaex.dao;

import com.javaex.vo.BoardVo;

public class BoardDaoTest {
	
	public static void main(String[] args) {
		//br.getBoardList() 테스트
		BoardDao BoardDao = new BoardDao();
	
		System.out.println(BoardDao.getBoardList());
		//insert 테스트
		BoardVo bv = new BoardVo(1,"타이틀","콘텐츠");
		BoardDao.insert(bv);
		
		//hitup 데스트
		BoardDao.hitUp(1);
		
		//read 테스트
		BoardDao.read(1);
		
		//getModifyBoardList 테스트
		BoardDao.getModifyBoardList(1, 1);
		
		//update테스트
		bv = new BoardVo(1,"타이틀","콘텐츠");
		BoardDao.update(bv);
		
		//delete
		BoardDao.delete(1);
	
	}
}
