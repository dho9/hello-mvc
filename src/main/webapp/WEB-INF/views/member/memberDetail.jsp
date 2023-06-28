


<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page import="com.sh.mvc.member.model.vo.Gender"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<%
String name= loginMember.getName();
String id= loginMember.getMemberId();
Date _birthday = loginMember.getBirthday();
String birthday = _birthday != null ? _birthday.toString() : "";
String email = loginMember.getEmail();
email = email !=null ? email : ""; //null 값노출방지
int point = loginMember.getPoint();
String phone = loginMember.getPhone();
Gender gender = loginMember.getGender();
String hobby = loginMember.getHobby();
String pw =loginMember.getPassword();

//취미 List로 처리하기
List<String> hobbies = null;
if(hobby != null){
	hobbies = Arrays.asList(hobby.split(","));
	//  운동 취미 등산 으로 쪼개져서 배열로 나옴 -> 문자열배열로나오게되는것을 aslist 쓰면 문자열로나오게 해주는것 [운동, 등산, 독서] 로 나오게 됨. 배열처럼나오지만 배열은아님
	// List로 바꾼이유는 list 하위 contains 있기때문 -> 운동 개운동시키기 일반컨테인쓰면 운동 에 두개 다 체크됨 
	// list 로 사용하면 운동 < 정확이 일치하는 운동만 체크되게 함.
	
}

String _gender = gender +"";

%>
<section id=enroll-container>
	<h2>회원 정보</h2>
	<form name="memberUpdateFrm" action="<%= request.getContextPath() %>/member/memberUpdate" method="POST">
		<table>
			<tr>
				<th>아이디<sup>*</sup></th>
				<td>
					<input type="text" name="memberId" id="memberId" value="<%=id %>" readonly>
				</td>
			</tr>
			<tr>
				<th>이름<sup>*</sup></th>
				<td>	
				<input type="text"  name="name" id="name" value="<%=name %>"  required><br>
				</td>
			</tr>
			<tr>
				<th>생년월일</th>
				<td>	
				<input type="date" name="birthday" id="birthday" value="<%=birthday %>"><br>
				</td>
			</tr> 
			<tr>
				<th>이메일</th>
				<td>	
					<input type="email" placeholder="abc@xyz.com" name="email" id="email" value="<%=email %>"><br>
				</td>
			</tr>
			<tr>
				<th>휴대폰<sup>*</sup></th>
				<td>	
					<input type="tel" placeholder="(-없이)01012345678" name="phone" id="phone" maxlength="11" value="<%=phone %>" required><br>
				</td>
			</tr>
			<tr>
				<th>포인트</th>
				<td>	
					<input type="text" placeholder="" name="point" id="point" value="<%=point %>" readonly><br>
				</td>
			</tr>
			<tr>
				<th>성별 </th>
				<td>
				
			       		 <input type="radio" name="gender" id="gender0" value="M" <%=_gender.equals("M") ? "checked" : "" %> >
						 <label for="gender0">남</label>
						 <input type="radio" name="gender" id="gender1" value="F" <%=_gender.equals("F") ? "checked" : "" %> >
						 <label for="gender1">여</label>
				</td>
			</tr>
			<tr>
				<th>취미 </th>
				<td>
					<input type="checkbox" name="hobby" id="hobby0" value="운동" <%=hobbies != null && hobbies.contains("운동") ? "checked" : ""  %> ><label for="hobby0">운동</label>
					<input type="checkbox" name="hobby" id="hobby1" value="등산" <%=hobbies != null && hobbies.contains("등산") ? "checked" : ""  %>><label for="hobby1">등산</label>
					<input type="checkbox" name="hobby" id="hobby2" value="독서" <%=hobbies != null && hobbies.contains("독서") ? "checked" : ""  %>><label for="hobby2">독서</label><br />
					<input type="checkbox" name="hobby" id="hobby3" value="게임" <%=hobbies != null && hobbies.contains("게임") ? "checked" : ""  %>><label for="hobby3">게임</label>
					<input type="checkbox" name="hobby" id="hobby4" value="여행" <%=hobbies != null && hobbies.contains("여행") ? "checked" : ""  %>><label for="hobby4">여행</label><br />


				</td>
			</tr>
		</table>
        <input type="submit" value="정보수정"
        />
        <input type="button" onclick="deleteMember();" value="탈퇴"/>
	</form>
</section>
<form name="memberDelFrm" action="<%= request.getContextPath() %>/member/memberDelete" method="post"></form>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
<script>
    const deleteMember = () => {
    	const frm = document.memberDelFrm;
    	if(confirm("정말 삭제하시겠습니까?")){
    		frm.submit();
    	}
    };
  
</script>

