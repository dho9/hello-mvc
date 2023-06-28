<%@page import="com.sh.mvc.member.model.vo.MemberRole"%>
<%@page import="com.sh.mvc.member.model.vo.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
    String msg = (String)session.getAttribute("msg"); //object반환임으로 형변환 ! 
	if(msg != null) session.removeAttribute("msg"); //현재 로그인을 해도 새로고침시 계속 alter 뜨께됨 
	// removeAttribute 사용시 일회용으로 변환하게 해줌 
    //System.out.println("msg ="+ msg);
    //request 생명주기가 짧아서 session 사용 ! 
    // session 사용자마다 하나씩 가짐. 로그인 하는사용자마다 개별로 하나씩 가지고 있는거다 .! 
    Member loginMember = (Member)session.getAttribute("loginMember");
	
    //System.out.println("loginMember ="+ loginMember);
    Cookie[] cookies = request.getCookies();
    String saveId= null;
    if(cookies !=null){
    	for(Cookie cookie : cookies){
    		String name = cookie.getName();
    		String value = cookie.getValue();
    		//System.out.println("[Cookie] " + name + "=" + value);
    		if("saveId".equals(name)){
    			saveId = value;
    		}
    	}
    }
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Hello MVC</title>
<link rel="stylesheet"
	href="<%=request.getContextPath() %>/css/style.css" />
<script>
	window.onload = ()=>{
		
		<% if(msg != null) {%>
		alert("<%= msg%>");
		<%}%>
		//req.msg 한번 사용하고 끝 -> 독립적이다. msg에 저장해놨지만 새롭게 로딩시 null 이라 뜨는이유임 req 매번응답시 새로만들어지고 폐기되는것..
		<%if(loginMember == null){%>
		document.loginFrm.onsubmit = (e)=>{
			//id 검사
			// 로그인시 넘어온건 없어짐 . 계속 자스에서 오류나서 자바 if로없애주기 ! 
			const memberId = e.target.memberId;
			if(!/^\w{4,}$/.test(memberId.value)){
				alert("아이디는 4글자 이상입력하세요");
				e.preventDefault;
				return;
			}
			// 비밀번호검사
			const password = e.target.password;
			if(!/^\w{4,}$/.test(password.value)){
				alert("비밀번호 4글자 이상입력하세요");
				e.preventDefault;
				return;
			}
		}
		<%}%>
	};

</script>
</head>
<body>
	<div id="container">
		<header>
			<h1>Hello MVC</h1>
			<div class="login-container">
				<!-- 로그인폼 시작 -->
				<%if (loginMember ==null){ %>
				<form id="loginFrm" name="loginFrm"
					action="<%= request.getContextPath() %>/member/login" method="post">
					<table>
						<tr>
						<!--  tabindex="1" 현재 탭을이용해 넘어가묜 tr td 순으로 가서 아이디 다음 로그인임. 이걸 인위적으로 변경하고싶으면 tabindex 속성 사용 -->
							<td><input type="text" name="memberId" id="memberId" tabindex="1"
								placeholder="아이디" value="<%=saveId != null ? saveId : ""%>"></td>
							<td><input type="submit" value="로그인" tabindex="3"></td>
						</tr>
						<tr>
							<td><input type="password" name="password" id="password" tabindex="2"
								placeholder="비밀번호"></td>
							<td></td>
						</tr>
						<tr>
							<td colspan="2"><input type="checkbox" name="saveId" 
								id="saveId" <%= saveId != null ? "checked" : "" %> /> <label for="saveId">아이디저장</label>&nbsp;&nbsp; <input
								type="button" value="회원가입"
								onclick="location.href='<%=request.getContextPath() %>/member/memberEnroll';"></td>
						</tr>
					</table>
				</form>
				<%}else{ %>
				<!-- 로그인폼 끝-->

				<!-- 로그인사용자 정보 시작 -->
				<table id="login">
					<tr>
						<td><%=loginMember.getName() %>님, 안녕하세요.</td>
					</tr>
					<tr>
						<td><input type="button" value="내정보보기"
						onclick = "location.href = '<%=request.getContextPath()%>/member/memberDetail';"> 
						<input
							type="button" value="로그아웃"
							onclick="location.href='<%=request.getContextPath() %>/member.logout'">
							</td>
					</tr>
				</table>
				<%} %>
				<!-- 로그인사용자 정보 끝 -->
			</div>


			<!-- 메인메뉴 시작 -->
			<nav>
				<ul class="main-nav">
					<li class="home"><a href="<%= request.getContextPath() %>">Home</a></li>
					<li class="notice"><a href="#">공지사항</a></li>
					<li class="board"><a href="<%= request.getContextPath()%>/board/boardList">게시판</a></li>
					<%if( loginMember != null && loginMember.getMemberRole() == MemberRole.A){ %>
					<!--  로그인 멤버 조건안달면 페이지로딩 x -->
					<li class="admin"><a href="<%=request.getContextPath()%>/admin/memberList">회원목록</a></li>
					<%} %>
				</ul>
			</nav>
			<!-- 메인메뉴 끝-->
		</header>

		<section id="content">