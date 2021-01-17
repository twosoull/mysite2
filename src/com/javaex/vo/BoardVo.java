package com.javaex.vo;

public class BoardVo {
	//필드
	private int no;
	private String title;
	private String content;
	private int hit;
	private String regDate;
	private int userNo;
	
	//users 필드
	private String uName;
	
	
	//생성자
	public BoardVo() {
		super();
	}
	
	//modifyForm
	public BoardVo(int no, String title, String content, int hit, String regDate, String uName) {
		super();
		this.no = no;
		this.title = title;
		this.content = content;
		this.hit = hit;
		this.regDate = regDate;
		this.uName = uName;
	}
	
	
	//read
	public BoardVo(String title, String content, int hit, String regDate, int userNo ,String uName) {
		super();
		this.title = title;
		this.content = content;
		this.hit = hit;
		this.regDate = regDate;
		this.userNo = userNo;
		this.uName = uName;
		
	}

	//Boardlist
	public BoardVo(int no, String title, int hit, String regDate, int userNo, String uName) {
		super();
		this.no = no;
		this.title = title;
		this.hit = hit;
		this.regDate = regDate;
		this.userNo = userNo;
		this.uName = uName;
	}
	
	//insert
	public BoardVo(String title, String content, int userNo) {
		super();
		this.title = title;
		this.content = content;
		this.userNo = userNo;
	}

	

	//업데이트(modify)
	public BoardVo(int no, String title, String content) {
		super();
		this.no = no;
		this.title = title;
		this.content = content;
	}

	//메소드 g/s
	public String getuName() {
		return uName;
	}
	
	
	
	public void setuName(String uName) {
		this.uName = uName;
	}

	public int getNo() {
		return no;
	}



	public void setNo(int no) {
		this.no = no;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}



	public int getHit() {
		return hit;
	}



	public void setHit(int hit) {
		this.hit = hit;
	}



	public String getRegDate() {
		return regDate;
	}



	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}



	public int getUserNo() {
		return userNo;
	}



	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}



	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", title=" + title + ", content=" + content + ", hit=" + hit + ", regDate="
				+ regDate + ", userNo=" + userNo + ", uName=" + uName + "]";
	}





	
	
}
