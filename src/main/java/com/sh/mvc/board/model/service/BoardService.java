package com.sh.mvc.board.model.service;

import static com.sh.mvc.common.JdbcTemplate.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import com.sh.mvc.board.model.dao.BoardDao;
import com.sh.mvc.board.model.vo.Board;
import com.sh.mvc.member.model.dao.MemberDao;
import com.sh.mvc.member.model.vo.Member;

public class BoardService {
	public final BoardDao boardDao = new BoardDao();


	
	/**
	 *
	 * DQL
	 * 1. connection 생성
	 * 2. PrepareStatement 생성 및 ? 값대입
	 * 3. 실행 및 resultSet처리
	 * 4. 자원반납

	 * DML
	 * 1. Connection 생성 (setAutoComit :false) 
	 * 2. PreparStatement 생성 및 ? 값대입
	 * 3. 실행 동시에 int 반환 
	 * 4. 트랜잭션(commit/rollback)
	 * 5. 자원반납 
	 */
	
	public List<Board> findPostAll() {
		Connection conn = getConnection();
		List<Board> boards = boardDao.findPostAll(conn);
		close(conn);
		return boards;
	}
}
