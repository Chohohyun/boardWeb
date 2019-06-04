<!--  현재 이 jsp 페이지 실행 후 생성되는 문서는 html 이고 이문서는 utf-8 방식으로 인코딩한다. -->

<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!-- jsp 기술의 한 종류인 include Directive를 이용하여 common.jsp 파일 내의 소스를 삽입하기 -->
<%@include file="common.jsp" %>
<script>
	$(document).ready(function() {
		
		$(".admin_id").val("abc");
		$(".pwd").val("abc123");
		$("[name=loginForm] .login").click(function() {
			checkLoginForm();
		});
		//$('[name="loginForm"]').find('. login').click(function(){
	});
	function checkLoginForm() {
		// 입력된 아이디를 가져와 변수에 저장
		var admin_id = $(".admin_id").val();
		// 아이디를 입력 안했으면 경고하고 함수 중단
		if (admin_id.split(" ").join("") == "") {
			$(".admin_id").val("");
			alert("아이디를 입력하지 않았습니다.");
			return;
		}

		// 입력된 비밀번호를 가져와 변수에 저장
		var pwd = $(".pwd").val();
		// 비밀번호를 입력 안했으면 경고하고 함수 중단
		if (pwd.split(" ").join("") == "") {
			$(".pwd").val("");
			alert("비밀번호를 입력하지 않았습니다.");
			return;
		}

		$.ajax({
			url:"/z_jsp/loginProc.do",
			type:"post",
			// 일일이 써야함
			//data:{'admin_id':admin_id, 'pwd':pwd}
			// 여러개도 가능	
			data: $("[name=loginForm]").serialize(),
			datatype:"html",
			success:function(html){
				var idCnt = $(html).text();
				alert(idCnt);
				idCnt = idCnt.split(" ").join("");
				if(idCnt==1){
					alert("로그인 성공!");
					location.replace("/z_jsp/boardListForm.do");
				}
				else{
					alert("로그인 실패!");
				}
			},
			error : function(html){
				alert("서버 접속 실패!");
			}
			
		});

	}
</script>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<center>
		<form name="loginForm" method="post" action="/z_jsp/loginProc.do">
			<table class="tbcss1" border=1 cellpadding=20 cellspacing=20>
				<tr>
					<th><b>[로그인]</b>
						<div style="height: 6"></div>
						<table class="tbcss1" border=1 cellpadding=5 cellspacing=0 bordercolor="gray">
							<tr>
								<th bgcolor="${headerColor}" align=center>아이디
								<td><input type="text" name="admin_id" class="admin_id"
									size="20">
							<tr>
								<th bgcolor="${headerColor}" align=center>암호
								<td><input type="password" class="pwd" name="pwd" size="20">
						</table>
						<div style="height: 6"></div> <input type="button" value="로그인" class="login">
			</table>
		</form>
	</center>
</body>
</html>