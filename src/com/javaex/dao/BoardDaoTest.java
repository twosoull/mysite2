package com.javaex.dao;

import java.util.List;

import com.javaex.vo.BoardVo;

public class BoardDaoTest {
	
	public static void main(String[] args) {
		//br.getBoardList() 테스트
		BoardDao boardDao = new BoardDao();
		/*
	
		System.out.println(boardDao.getBoardList());
		//insert 테스트
		BoardVo bv = new BoardVo(1,"타이틀","콘텐츠");
		boardDao.insert(bv);
		
		//hitup 데스트
		boardDao.hitUp(1);
		
		//read 테스트
		boardDao.read(1);
		
		//getModifyBoardList 테스트
		boardDao.getModifyBoardList(1, 1);
		
		//update테스트
		bv = new BoardVo(1,"타이틀","콘텐츠");
		boardDao.update(bv);
		
		//delete
		boardDao.delete(1);
		
		*/
		//boardList search 테스트
		List<BoardVo> bl = boardDao.getBoardList("삼");
		for(int i = 0; i <bl.size(); i++) {
		System.out.println(bl.get(i));
		}
	}
}
