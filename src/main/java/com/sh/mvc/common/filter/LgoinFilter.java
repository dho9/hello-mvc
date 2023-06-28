package com.sh.mvc.common.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sh.mvc.member.model.vo.Member;

/**
 * Servlet Filter implementation class LgoinFilter
 */
@WebFilter({ "/member/memberDetail", "/member/memberUpdate", "/member/memberDelete" })
public class LgoinFilter extends HttpFilter implements Filter {
       
    /**
     * @see HttpFilter#HttpFilter()
     */
   
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("로그인 체크중 !");
		HttpServletRequest httpreq = (HttpServletRequest) request;
		HttpServletResponse httprep = (HttpServletResponse) response;
		HttpSession session = httpreq.getSession();
		Member loginMember = (Member)session.getAttribute("loginMember");
		if(loginMember == null) {
			session.setAttribute("msg", "로그인 후 이용가능~");
			httprep.sendRedirect(httpreq.getContextPath()+"/");
			return;
		}
		chain.doFilter(request, response);
	}

	

}
