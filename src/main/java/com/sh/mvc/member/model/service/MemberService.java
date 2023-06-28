package com.sh.mvc.member.model.service;

import static com.sh.mvc.common.JdbcTemplate.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import com.sh.mvc.member.model.dao.MemberDao;
import com.sh.mvc.member.model.vo.Member;

public class MemberService {
	public final MemberDao memberDao = new MemberDao();

	
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
	public Member findById(String memberid) {
		Connection conn = getConnection();
		Member member = memberDao.findById(conn,memberid);
		close(conn);
		return member;
	}


	public int insertMember(Member newMember) {
		int result=0;
		Connection conn = getConnection();
		try {
			result = memberDao.insertMember(conn, newMember);
			commit(conn);
		}catch (Exception e) {
			rollback(conn);
			throw e;
		} finally {
			close(conn);
		}
		return result;
	}


	public int updateMember(Member newMember) {
		int result =0;
		Connection conn = getConnection();
		
		try{
			result= memberDao.updateMember(conn,newMember);
			commit(conn);
		}catch (Exception e) {
			rollback(conn);
			throw e;
		}finally {
			close(conn);
		}
		return result;
		
	}


	public int deleteMember(String memberId) {
		int result=0;
		Connection conn = getConnection();
		try {
			result = memberDao.deleteMember(conn, memberId);
			commit(conn);
		}catch (Exception e) {
			rollback(conn);
			throw e;
		} finally {
			close(conn);
		}
		return result;
	}


	public List<Member> findAll() {
		Connection conn = getConnection();
		List<Member> members = memberDao.findAll(conn);
		close(conn);
		return members;
	}


	public int memberRoleUpadte(String memberid, String memberRole) {
		int result =0;
		Connection conn= getConnection();
		try {
			result = memberDao.memberRoleUpadte(conn, memberid,memberRole);
			commit(conn);
		}catch (Exception e) {
			rollback(conn);
			throw e;
		}finally {
			close(conn);
		}
		
		return result;
	}

	
	public List<Member> searchMember(String serachType, String searchKeyword) {
		Connection conn = getConnection();
		List<Member> members = memberDao.searchMember(conn,serachType,searchKeyword);
		close(conn);
		return members;
	}
}
