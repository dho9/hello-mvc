package com.sh.mvc.admin.controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sh.mvc.member.model.service.MemberService;
import com.sh.mvc.member.model.vo.Member;

/**
 * Servlet implementation class AdminMemberRolueUpdateServlet
 */
@WebServlet("/admin/memberRoleUpadte")
public class AdminMemberRolueUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private final MemberService memberService = new MemberService();   
 

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1 입력값처리
		String memberid = request.getParameter("memberId");
		String memberRole = request.getParameter("memberRole");
		System.out.println("service id"+ memberid);
		System.out.println("service role "+ memberRole);
		//2 업무로직
		int result = memberService.memberRoleUpadte(memberid,memberRole);
		///3 응답 redirect
		response.sendRedirect(request.getContextPath()+"/admin/memberList");
		
		
	}

}
