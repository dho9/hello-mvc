package com.sh.mvc.member.controller;

import java.io.IOException;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sh.mvc.common.util.HelloMvcUtils;
import com.sh.mvc.member.model.service.MemberService;
import com.sh.mvc.member.model.vo.Member;

/**
 * Servlet implementation class MemberLoginServlet
 */
@WebServlet("/member/login")

/**
 *
 * 컨테스트객체
 * pageContext : PageContext
 * request : HttpServletRequest
 * session : TrrpSession
 * application : ServletContext
 * 
 * 위 객체는 모두 setAttribute(String , Object) , getAttribute(String): Object를 지원한다.
 * 
 * Session | Cookie
 * 	Http 통신의 상태관리 메커니즘
 *  기본적으로 Http 통신은 stateless 하다. 요청이 가고 응답하면 끝나버림.
 *  네이버 요청 -> 네이버 보여주고 끝나버림 
 *  다시 다른메뉴 누르면 다시 요청이 되는것임 . (연결자체가 유지가 되는것이 아님 , 연결유지하면 유지비용 높음)
 *  이전에 요청했던 사용자가 다음요청시 동일한 사용자 인것을 구분 할 수 없음. (로그인했다가 페이지 이동시 로그인 풀림현상 )
 *  -> 로그인을 끊키지 않게 - > 사용자 정보를 서버쪽에 보관하는 기술 -> Session 
 *  사용자정보를 클라이언트(브라우져)에 보관하는 기술 -> Cookie 
 *  Session , Cookie 는 한 쌍 
 *  
 *  발급 및 사용과정
 *  1. 클라이언트 접속 요청시, 서버는 세션객체를 하나 생성함  (최초인지 ? jsessionid 없는걸 보고 암)
 *  2. 발급된 세션아이디를 응답에 전송 ( Set-Cookie : 세션ID)
 *  3. 클라이언트는 JSESSIONID에 세션ID로 쿠키를 저장함. 
 *  4. 클라이언트는 다음 요청부터 매번 요청헤더에 Cookied 항목으로 JSESSIONID를 함께 전송함.
 *  5. 서버에서는 JSESSIONID 검증 후 해당 session객체 사용하도록 해줌 
 *
 *	Session 객체는 클라이언트 별로 생성해 관리함. 클라이언트 많을수록 서버부담 증가
 *-> 세션객체의 유효시간은 30분이다(마지막 요청시간으로부터). 
 *
 *	request.getSession(create :boolean)
 *	- 세션이 유효하지 않은 경우 생성여부를 가리킴
 *	true(기본값) : 세션 id가 유효하지 않거나 없는경우 세션객체를 새로 생성해서 반환
 *	false : 세션id가 유효하지 않거나, 없는경우 null을 반환
 */
public class MemberLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final MemberService memberService = new MemberService();
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//0. 인코딩처리
		request.setCharacterEncoding("utf-8");
		//1.사용자 입력값 가져오기 ( memberId, password)
		String memberid = request.getParameter("memberId");
		String password = HelloMvcUtils.getEncryptedPassword(request.getParameter("password"),memberid);
		System.out.println(password);
		// 암호화 된 비밀번호랑 비교해야함. 동일메소드로 같이 던져줘서 변화시키고 비교하게 해야함.
		String saveId = request.getParameter("saveId");
		System.out.println("id="+memberid);
		System.out.println("pw="+password);
		System.out.println("saveId"+saveId);
		//2. 업무로직 로그인확인
		//아이디로 db에서 조회 select * from member where member_id = ?
		//member객체가 null 이아니면서 비밀번호가 일치한지 ? 일치하면 login 성공 , 
		Member member = memberService.findById(memberid);
//		System.out.println("member@servlet = " + member);
		//객체가 null이거나 비밀번호 일치하지 않으면 로그인 실패 
		HttpSession session = request.getSession(); //request.getSession(true)와 동일, 없으면 새로만들어서라도 반환해주기
//		System.out.println(session.getId());
	
		if( member != null && password.equals(member.getPassword())) {
			// 로그인성공
			session.setAttribute("loginMember", member);
//			session.setAttribute("msg", "로그인성공~");
			
			//아이디 저장 쿠키
			// setPath  : path : 쿠리를 사용할 url . 서버전송 시 부모 경로만 지정하면 자식까지 다 됨. /설정시 모든요청에 사용. 
			// /mvc 설정시 /mvc로 시작하는 모든 요청에 사용
			// setMaxAge 설정하지않은 경우  -> Session Cookie -> 접속한 동안만 클라이언트에 보관 
			// setMaxAge 설정한 경우  -> Persistent Cookie : 지정한 시각가지만 클라이언트에 보관 
			
			
			if(saveId != null) {
				Cookie cookie = new Cookie("saveId",memberid);
				cookie.setPath(request.getContextPath()); // 쿠키 사용할 url
				cookie.setMaxAge(60*60*24*7);// 7일동안 쿠키유지.
				response.addCookie(cookie); // 응답 헤더 Set-cookie: sasveId=honggd
			}else {
				// 기존쿠키 삭제 삭제하는코드는 따로 없음 . MaxAge 값으로 바꿔야함.(만료기간을 0으료변경 , 쿠키가 넘어오자마자 삭제되는것!)
				Cookie cookie = new Cookie("saveId",memberid);
				cookie.setPath(request.getContextPath()); // 쿠키 사용할 url
				cookie.setMaxAge(0);// 7일동안 쿠키유지.
				response.addCookie(cookie); // 응답 헤더 Set-cookie: sasveId=honggd
			}
			
		}else {
			//로그인실패
			session.setAttribute("msg", "아이디 또는 비밀번호가 틀려ㅠㅠ");
		}
		//3. 응답처리
//		RequestDispatcher reqDispatcher = request.getRequestDispatcher("/index.jsp");
//		reqDispatcher.forward(request, response);
		//forward 를 이용하묜 url 이 계속 유지됨 http://localhost:8080/mvc/member/login 
		//sendRedirect 사용하면 url이 변경됨 ! 계속 login에 안남게해줌 
		response.sendRedirect(request.getContextPath()+"/");
		//http://localhost:8080/mvc/ 로그인성공시 url이 login 302번(found) 에서 다시 넘어오게됨
		//브라우저가 302번요청들어오면 바로 다음요청을 보냄 get을 통해 -> 다시 index.jsp 요청 후 index html 로넘어오게됨 ! 
	}

}
