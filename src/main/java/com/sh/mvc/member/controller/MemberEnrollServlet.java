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
 * Servlet implementation class MemberEnrollServlet
 */
@WebServlet("/member/memberEnroll")
public class MemberEnrollServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final MemberService memberService = new MemberService();

	/**
	 *GET /member/memberEnroll
	 * 회원가입 홈페이지 응답
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/member/memberEnroll.jsp").forward(request, response);
	}

	/**
	 * POST /member/memberEnroll
	 * db에 회원정보 저장.
	 * 기본값 회원권한, 등록일 등 기본값처리 .
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		String memberId= request.getParameter("memberId");
		String password= HelloMvcUtils.getEncryptedPassword(request.getParameter("password"), memberId);
		String name= request.getParameter("name");
		String _gender= request.getParameter("gender");
		String _birthday= request.getParameter("birthday");
		String email= request.getParameter("email");
		String phone= request.getParameter("phone");
		String[] _hobbies= request.getParameterValues("hobby");
		
		Gender gender = _gender != null ? Gender.valueOf(_gender) : null;
		Date birthday = _birthday != null && !"".equals(_birthday) ? Date.valueOf(_birthday) : null;
		String hobby = _hobbies != null ? String.join(",", _hobbies) : null;
		
		// 사용자 입력값처리 
		Member newMember = new Member(memberId, password, name, null, gender, birthday, email, phone, hobby, 0, null);
		System.out.println(newMember);
		// 멤버객체로 ...  jdbc 
		// 업무로직 db저장요청
		int result = memberService.insertMember(newMember);
		System.out.println(result);
		//결과메세지 속성들록 : 성공적으로 등록
		HttpSession session = request.getSession();
		session.setAttribute("msg", "성공정으로 회원등록 했습니다.");
		// 인덱스페이지 리다이렉트 
		response.sendRedirect(request.getContextPath()+"/");
		
	}

}
