package com.sh.mvc.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**	
 * 
 * Filter 작성하기
 * 	Filter 인터페이스 구현
 * 	doFilter 메소드 오버라이드 
 * 
 *  서블릿 호출 전 작업
 *   응답 메세지 발송전 (jsp 작업 이후 ) 작업
 *   
 *   처리되는 필터의 순서를 변경하려면 web.xml에 등록해야함
 *   	필터 처리순서
 *   	1. web.xml 등록된 순서대로
 *   	2. @WebFilter로 등록된 클래스
 *
 */
//@WebFilter("/*")
public class LogFilter implements Filter {

	// ServletRequest는 HtpplServletRequest의 부모타입 HttpServletRequset 다운캐스팅가능
	// ServletResponse HtpplServletResponse의 부모타입 HttpServletResonse 다운캐스팅가능
	// FilterChain 필터묶음 객체. FilterChain#doFilter를 호출하면 다음 필터 또는 서블릿으로 연결
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//반드시 호출해야하는 메소드, 호출해야 서블릿으로 넘어가게 됨.
		// 서블릿 가기전 작업공간임
		HttpServletRequest httpReq = (HttpServletRequest)request;
		String uri = httpReq.getRequestURI();
		String method = httpReq.getMethod();
		
//		System.out.println("================================");
//		System.out.printf("%s %s \n", method, uri);
//		System.out.println("--------------------------------");
		chain.doFilter(request, response);
		//응답메세지 발송전 작업공간
	
		HttpServletResponse httpRes = (HttpServletResponse)response;
		int status = httpRes.getStatus();
//		System.out.println("_-_-_-_-_-____________________");
//		System.out.println(status);
//		System.out.println("_-_-_-_-_-____________________");
//		System.out.println();
	}



}
