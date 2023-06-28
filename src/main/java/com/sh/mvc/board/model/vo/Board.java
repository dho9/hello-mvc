package com.sh.mvc.board.model.vo;

import java.sql.Date;

public class Board {
	private int no;
	private String title;
	private String writer;
	private String content;
	private int readCount;
	private Date regDate;
	private int attachment;
	public Board(int no, String title, String writer, String content, int readCount, Date regDate, int attachment) {
		super();
		this.no = no;
		this.title = title;
		this.writer = writer;
		this.content = content;
		this.readCount = readCount;
		this.regDate = regDate;
		this.attachment= attachment;
		
	}
	public Board() {
		// TODO Auto-generated constructor stub
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
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getReadCount() {
		return readCount;
	}
	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public int getAttachment() {
		return attachment;
	}
	public void setAttachment(int attachment) {
		this.attachment = attachment;
	}
	
	
}
