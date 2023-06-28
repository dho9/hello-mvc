package com.sh.mvc.member.controller;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;import org.apache.tomcat.util.http.fileupload.RequestContext;

import com.sh.mvc.member.model.service.MemberService;
import com.sh.mvc.member.model.vo.Member;

/**
 * Servlet implementation class MemberDeleteServlet
 */
@WebServlet("/member/memberDelete")
public class MemberDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final MemberService memberService = new MemberService();
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Member loginMember = (Member) session.getAttribute("loginMember");
		String memberId= loginMember.getMemberId();
		int result = memberService.deleteMember(memberId);
		
		//세션속성삭제
		Enumeration<String> names = session.getAttributeNames(); //저장된 모든 속석명 열람가능 한 타입으로 반환
		while(names.hasMoreElements()) {
			String name = names.nextElement();
			session.removeAttribute(name);
			// 모든속성제거
		}
		//쿠키삭제
		Cookie cookie = new Cookie("saveId",memberId);
		cookie.setPath(request.getContextPath());
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		
		session.setAttribute("msg", "성공적으로 회원탈퇴 했습니다.");
			response.sendRedirect(request.getContextPath() + "/");
		}

}




