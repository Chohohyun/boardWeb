<!--  현재 이 jsp 페이지 실행 후 생성되는 문서는 html 이고 이문서는 utf-8 방식으로 인코딩한다. -->

<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="common.jsp" %>
<%@include file="checkLogin.jsp" %>
<%@include file="menubar.jsp" %>
<c:if test="${empty requestScope.board}">
	<script>
		alert("게시판 글이 삭제되었음!");
		location.replace("/z_jsp/boardListForm.do");
	</script>
</c:if>
<script src="/z_jsp/resources/jquery-1.11.0.min.js"></script>
<script>
</script>
<script>
		// 게시판 댓글 화면으로 이동하는 함수 선언
		function goBoardRegForm( ) {
			document.boardRegForm.submit();
			
		}
		function goBoardUpDelForm( ) {
			document.boardUpDelForm.submit();
		}
		
</script>
<html>
<head>
<title>게시판</title>
</head>
<body>
<br>
<center>
<form class="boardContentForm" name="boardContentForm" method="post" action="/z_jsp/boardRegForm.do">
	<b>[글 상세보기]</b>
	<table class="tbcss1" width = "500" border="1" bordercolor="#DDDDDD" cellpadding="5" align="center">
		<tr align="center">
			<th bgcolor="${headerColor}" width=60>글번호
			<td width=150>${board.b_no}
			<th bgcolor="${headerColor}" width=60>조회수
			<td width=150>${board.readcount}
		<tr align="center">
			<th bgcolor="${headerColor}">작성자
			<td>${board.writer}
			<th bgcolor="${headerColor}">작성일
			<td>${board.reg_date}
		<tr>
			<th bgcolor="${headerColor}">글제목
			<td colspan="3">${board.subject}
		<tr>
			<th bgcolor="${headerColor}">글내용
			<td colspan="3"><pre>${board.content}</pre>
	</table>
	<table>
		<tr height=3>
		<td>
	</table>
	<input type="hidden" name="b_no" value="${board.b_no}">
	<input type="button" value="글목록 보기" onclick="document.boardListForm.submit();">
	<input type="button" value="댓글달기" onClick="goBoardRegForm()">&nbsp;
	<input type="button" value="수정/삭제" onClick="goBoardUpDelForm()">&nbsp;
	</form>
	<form name="boardListForm" method="post" action="/z_jsp/boardListForm.do">
	<!-- 	
	<input type="hidden" name="keyword1" value="${param.keyword1}">
	<input type="hidden" name="keyword2" value="${param.keyword2}">
	<input type="hidden" name="or_and" value="${param.or_and}">
	 -->
	</form>
	<form name="boardRegForm" method="post" action="/z_jsp/boardRegForm.do">
		<!-- 게시판 상세보기 화면을 구성하는 글의 고유번호를 hidden 태그에 저장한다 -->
		<!-- 댓글을 달려면 주인글의 고유번호를 알아야하기 때문이다. -->
		<input type="hidden" name="b_no" value="${board.b_no }">	
	</form>
	<form name="boardUpDelForm" method="post" action="/z_jsp/boardUpDelForm.do">
		<!-- 게시판 상세보기 화면을 구성하는 글의 고유번호를 hidden 태그에 저장한다 -->
		<!-- 수정/삭제를 하려면 현재 글의 고유번호를 알아야하기 때문이다. -->
		<input type="hidden" name="b_no" value="${board.b_no}">	
	</form>
	</center>
	
</body>
</html>