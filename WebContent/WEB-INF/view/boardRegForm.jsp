<!--  현재 이 jsp 페이지 실행 후 생성되는 문서는 html 이고 이문서는 utf-8 방식으로 인코딩한다. -->

<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@include file="checkLogin.jsp" %>
<%@include file="common.jsp" %>

<script>
	$(document).ready(function(){
		//$("[name=boardListForm]").hide();
		inputData("keyword1","${param.keyword1}");
		inputData("keyword2","${param.keyword2}");
		inputData("or_and","${param.or_and}");
		<c:forEach items="${paramValues.date}" var="date">
			inputData("date","${date}");
		</c:forEach>
	});
	function checkBoardRegForm() {
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
		
		if (confirm("정말 저장하시겠습니까?") == false) {
			return;
		}
		alert($("[name=boardRegForm]").serialize());
		// 현재화면에서 페이지 이동 없이 서버쪽 "/z_jsp/boardRegProc.do"을 호출하여
		// 게시판 입력 행 적용 개수가 있는html 소스를 문자열로 받기
		$.ajax({
			url : "/z_jsp/boardRegProc.do",
			type : "post",
			// 일일이 써야함
			//data:{'admin_id':admin_id, 'pwd':pwd}
			// 여러개도 가능	
			data : $("[name=boardRegForm]").serialize(),
			datatype : "html",
			success : function(html) {
				var boardRegCnt = $(html).text();
				boardRegCnt = boardRegCnt.split(" ").join("");
				alert(boardRegCnt);
				if (boardRegCnt == 1) {
					alert("게시판 새글 등록 성공!");
					location.replace("/z_jsp/boardListForm.do");
				} else {
					alert("게시판 새글 등록 실패! 관리자에게 문의 바람!");
				}
			},
			error : function(html) {
				alert("서버와 비동기 방식 통신 실패!");
			}

		});
	}
	function checkBoardRegForm2() {
		var writer = $("[name=writer]").val();
		if (writer.split(" ").join("") == "") {
			alert("이름을 입력해 주십시요");
			$("[name=writer]").focus();
			return;
		}

		var subject = $("[name=subject]").val();
		if (subject.split(" ").join("") == "") {
			alert("제목을 입력해 주십시요");
			$("[name=subject]").focus();
			return;
		}

		var email = $("[name=email]").val();
		if (email.split(" ").join("") == "") {
			alert("이메일을 입력해 주십시요");
			$("[name=email]").focus();
			return;
		}

		var content = $("[name=content]").val();
		if (content.split(" ").join("") == "") {
			alert("내용을 입력해 주십시요");
			$("[name=content]").focus();
			return;
		}

		var pwd = $("[name=pwd]").val();
		if (pwd.split(" ").join("") == "") {
			alert("비밀번호를 입력해 주십시요");
			$("[name=pwd]").focus();
			return;
		}
		if (confirm("정말 저장하시겠습니까?") == false) {
			return;
		}
		alert($("[name=boardRegForm]").serialize());
		// 현재화면에서 페이지 이동 없이 서버쪽 "/z_jsp/boardRegProc.do"을 호출하여
		// 게시판 입력 행 적용 개수가 있는html 소스를 문자열로 받기
		$.ajax({
			url : "/z_jsp/boardRegProc.do",
			type : "post",
			// 일일이 써야함
			//data:{'admin_id':admin_id, 'pwd':pwd}
			// 여러개도 가능	
			data : $("[name=boardRegForm]").serialize(),
			datatype : "html",
			success : function(html) {
				var boardRegCnt = $(html).text();
				boardRegCnt = boardRegCnt.split(" ").join("");
				alert(boardRegCnt);
				if (boardRegCnt == 1) {
					alert("게시판 새글 등록 성공!");
					location.replace("/z_jsp/boardListForm.do");
				} else {
					alert("게시판 새글 등록 실패! 관리자에게 문의 바람!");
				}
			},
			error : function(html) {
				alert("서버와 비동기 방식 통신 실패!");
			}

		});
	}
</script>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<title>게시판 새글쓰기</title>
</head>
<body>
	<center>

		<center>
			<form method="post" name="boardRegForm"
				action="/z_jsp/boardRegProc.do">
				<c:if test="${empty param.b_no}">
				<b>[새 글쓰기]</b>
				</c:if>
				<c:if test="${!empty param.b_no}">
				<b>[댓글쓰기]</b>
				</c:if>
				<table  class="tbcss1" border="1" bordercolor=gray cellspacing="0" cellpadding="5"
					align="center">
					<tr>
						<th bgcolor="${headerColor}">이 름
						<td><input type="text" size="10" maxlength="10" name="writer"></td>
					</tr>
					<tr>
						<th bgcolor="${headerColor}">제 목
						<td><input type="text" size="40" maxlength="50"
							name="subject"></td>
					</tr>
					<tr>
						<th bgcolor="${headerColor}">이 메 일
						<td><input type="text" size="40" maxlength="30" name="email"></td>
					</tr>
					<tr>
						<th bgcolor="${headerColor}">내 용
						<td><textarea name="content" size="13" cols="40"> </textarea>
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


				<input type="hidden" name="b_no" value="${param.b_no}"> 
				<input type="button" value="저장" onClick="checkBoardRegForm();"> 
				<input type="reset" value="다시작성"> 
				<input type="button" value="목록보기" onClick="document.boardListForm.submit();">
			</form>

			<form name="boardListForm" method="post" action="/z_jsp/boardListForm.do">
				<input type="hidden" name="keyword1" class="keyword1" >
				<input type="hidden" name="keyword2" class="keyword2" >
				<input type="hidden" name="or_and" class="or_and" >
				<input type="checkbox" name="date" value="오늘" >오늘
				<input type="checkbox" name="date" value="어제" >어제
			</form>
		</center>
	</center>
</body>
</html>