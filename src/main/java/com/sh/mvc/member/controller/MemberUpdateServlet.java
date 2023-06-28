package com.sh.mvc.member.controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sh.mvc.common.util.HelloMvcUtils;
import com.sh.mvc.member.model.service.MemberService;
import com.sh.mvc.member.model.vo.Gender;
import com.sh.mvc.member.model.vo.Member;

/**
 * Servlet implementation class MemberUpdateServlet
 */
@WebServlet("/member/memberUpdate")

public class MemberUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final MemberService memberService = new MemberService();
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		//1.입력값처리
		HttpServletRequest httpreq = (HttpServletRequest) request;
		HttpServletResponse httprep = (HttpServletResponse) response;
		HttpSession session = httpreq.getSession();
		Member loginMember = (Member)session.getAttribute("loginMember");
		
		String id= loginMember.getMemberId();
		String pw = loginMember.getPassword();
		
		String name= request.getParameter("name");
		String _gender= request.getParameter("gender");
		String _birthday= request.getParameter("birthday");
		String email= request.getParameter("email");
		String phone= request.getParameter("phone");
		String[] _hobbies= request.getParameterValues("hobby");
		Gender gender = _gender !=null ? Gender.valueOf(_gender) : null;
		Date birthday = _birthday != null && !"".equals(_birthday) ? Date.valueOf(_birthday) : null;
		String hobby = _hobbies != null ? String.join(",", _hobbies) : null;
		
		Member updateMember = new Member(id, pw, name, null, gender, birthday, email, phone, hobby, 0, null);
		System.out.println(updateMember.toString());
		int result = memberService.updateMember(updateMember);
		//2. 업무로직
		//3. 응답처리
		session.setAttribute("loginMember", updateMember);
		session = request.getSession();
		session.setAttribute("msg", "성공정으로 회원변경 했습니다.");
		// 인덱스페이지 리다이렉트 
		response.sendRedirect(request.getContextPath()+"/member/memberDetail");
		}

}
