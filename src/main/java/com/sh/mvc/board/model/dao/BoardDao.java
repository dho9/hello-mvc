package com.sh.mvc.board.model.dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.sh.mvc.board.model.exception.BoardException;
import com.sh.mvc.board.model.vo.Board;

public class BoardDao {
	private Properties prop = new Properties();

	public BoardDao() {
		String filename = BoardDao.class.getResource("/sql/board/board-query.properties").getPath();
		try {
			prop.load(new FileReader(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Board> findPostAll(Connection conn) {
		List<Board> boards = new ArrayList<>();
		String sql = prop.getProperty("findPostAll");

		try (PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rset = pstmt.executeQuery();) {
			while (rset.next()) {
				Board board = handleBoardResultSet(rset);
				boards.add(board);
			}

		} catch (SQLException e) {
			throw new BoardException(e);
		}
		return boards;
	}

	private Board handleBoardResultSet(ResultSet rset) throws SQLException {
		int no = rset.getInt("no");
		String title = rset.getString("title");
		String writer = rset.getString("writer");
		String content = rset.getString("content");
		int readCount = rset.getInt("read_count");
		Date regDate = rset.getDate("reg_date");
		int attachment =rset.getInt("original_filename");

		return new Board(no, title, writer, content, readCount, regDate, attachment);
	}
}
