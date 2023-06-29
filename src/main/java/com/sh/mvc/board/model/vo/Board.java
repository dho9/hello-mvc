package com.sh.mvc.board.model.vo;

import java.sql.Date;

public class Board extends BoardEntity {
	private int attachment;
	public Board() {
		// TODO Auto-generated constructor stub
	}
	public Board(int no, String title, String writer, String content, int readCount, Date regDate, int attachment) {
		super(no, title, writer, content, readCount, regDate);
		this.attachment = attachment;
	}
	public int getAttachment() {
		return attachment;
	}
	public void setAttachment(int attachment) {
		this.attachment = attachment;
	}
	@Override
	public String toString() {
		return "Board [attachment=" + attachment + ", toString()=" + super.toString() + "]";
	}
	
	
}
