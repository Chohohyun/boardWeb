<!--  현재 이 jsp 페이지 실행 후 생성되는 문서는 html 이고 이문서는 utf-8 방식으로 인코딩한다. -->

<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="common.jsp" %>
<%@include file="checkLogin.jsp" %>
<%@include file="menubar.jsp"%>
<script src="/z_jsp/resources/jquery-1.11.0.min.js"></script>

<script>
	function checkBoardUpDelForm(upDel) {
		if(upDel=="del"){
			if (is_empty("pwd")) {
				alert("암호를 입력해 주십시요");
				$("[name=pwd]").focus();
				return;
			}
			document.boardUpDelForm.upDel.value="del";
			if(confirm("정말 삭제 하시겠습니까?")==false){
				return;
			}
		}		
		else if (upDel=="up"){
			alert("1");
			if (is_empty("writer")) {
				alert("이름을 입력해 주십시요");
				$("[name=writer]").focus();
				return;
			}
			if (is_empty("subject")) {
				alert("제목을 입력해 주십시요");
				$("[name=subject]").focus();
				return;
			}
			if (is_empty("email")) {
				alert("이메일을 입력해 주십시요");
				$("[name=email]").focus();
				return;
			}
			if (is_empty("content")) {
				alert("내용을 입력해 주십시요");
				$("[name=content]").focus();
				return;
			}if (is_empty("pwd")) {
				alert("암호를 입력해 주십시요");
				$("[name=pwd]").focus();
				return;
			}
			if(!is_pattern("writer", /^[a-zA-Z]{3,10}$/) && !is_pattern("writer", /^[가-힣]{3,10}$/)){
				alert("영어 또는 한글만 가능");
				$("[name=writer]").val("");
				return;
			}
			var content=$("[name=content]").val();
			if(content.length>1000){
				alert("게시판 내용글은 1000자가 넘어서면 안됩니다.");
				return;
			}
			if(confirm("정말 수정 하시겠습니까?")==false){
				return;
			}
		}
		$.ajax({
			url : "/z_jsp/boardUpDelProc.do",
			type:"post",
			data:$("[name=boardUpDelForm]").serialize(),
			success:function(html){
				var upDelCnt = $(html).text();
				upDelCnt=upDelCnt.split(" ").join("");
				if(upDel=="up"){
					if(upDelCnt==1){
						alert("수정 성공");
						location.replace("/z_jsp/boardListForm.do");
					}
					else if(upDelCnt==0){
						alert("비밀번호가 잘못 입력 되었습니다.");
					}
					else if(upDelCnt==-1){
						alert("이미 삭제된 게시물입니다.");
						location.replace("/z_jsp/boardListForm.do");
					}
					else{
						alert("서버쪽 DB 연동 실패!");
					}
				}
				else if(upDel=="del"){
					alert(upDelCnt);
					if(upDelCnt==1){
						alert("삭제 성공!");
						location.replace("/z_jsp/boardListForm.do");
					}
					else if(upDelCnt==-1){
						alert("이미 삭제된 글입니다.");
						location.replace("/z_jsp/boardListForm.do");
					}
					else if(upDelCnt==-2){
						alert("비밀번호가 틀립니다..");
					}
					else if(upDelCnt==-3){
						alert("자식글이 있어 삭제 불가능합니다..");
					}
					else{
						alert("서버쪽 DB 연동 실패");
					}
				}
			},
			error:function(html){
				alert("서버와 통신 실패!");
			}
		})
	}
</script>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<title>게시판</title>
</head>
<body>
	<center>

		<center>
			<form method="post" name="boardUpDelForm"
				action="/z_jsp/boardUpDelProc.do">
				<b>[글 수정/삭제]</b>
				<table class="tbcss1" border="1" bordercolor=gray cellspacing="0" cellpadding="5"
					align="center">
					<tr>
						<th bgcolor="${headerColor}">작성자
						<td><input type="text" size="10" maxlength="10" name="writer"
							value="${boardDTO.writer}"></td>
					</tr>
					<tr>
						<th bgcolor="${headerColor}">제 목
						<td><input type="text" size="40" maxlength="50"
							name="subject" value="${boardDTO.subject}"></td>
					</tr>
					<tr>
						<th bgcolor="${headerColor}">이 메 일
						<td><input type="text" size="40" maxlength="30" name="email"
							value="${boardDTO.email}"></td>
					</tr>
					<tr>
						<th bgcolor="${headerColor}">내 용
						<td><textarea name="content" size="13" cols="40">${boardDTO.content} </textarea>
					</tr>
					<tr>
						<th bgcolor="${headerColor}">비 밀 번 호
						<td><input type="password" size="8" maxlength="12" name="pwd"></td>
					</tr>
				</table>
				<table>
					<tr height=4>
						<td>
				</table>


				<input type="hidden" name="upDel" value="up"> 
				<input type="hidden" name="b_no" value="${requestScope.boardDTO.b_no}">
				<input type="button" value="수정" onClick="checkBoardUpDelForm('up')">
				<input type="button" value="삭제" onClick="checkBoardUpDelForm('del')">
				<input type="button" value="목록보기"
					onClick="document.boardListForm.submit();">
			</form>

			<form name="boardListForm" method="post"
				action="/z_jsp/boardListForm.do"></form>
				<br>
			<input type="button" value="html정보보기"
					onClick="print_html_info();">
		</center>
	</center>
</body>
</html>