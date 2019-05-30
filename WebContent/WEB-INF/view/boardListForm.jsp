<!--  현재 이 jsp 페이지 실행 후 생성되는 문서는 html 이고 이문서는 utf-8 방식으로 인코딩한다. -->

<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script src="/z_jsp/resources/jquery-1.11.0.min.js"></script>
<!--  JSTL 커스텀 태그와 EL을 사용하여 HttpSession 객체에 로그인 아이디가 없으면 경고하고 로그인 화면으로 이동 시키기-->
<!--  만약 세션객체에 admin_id라는 키값으로 저장된 놈이 없다면.. -->
<c:if test="${empty sessionScope.admin_id }">

	<script>
		//alert("로그인 요망");
		//location.replace("/z_jsp/loginForm.do");
	</script>
</c:if>
<script>
	function goBoardRegForm(){
		// name = boardRegForm을 가진 form 태그안의 action에 설정된 URL 로 이동하기
		// 이동 시 form 태그안의 모든 입력 양식이 파라미터값으로 전송된다.
		document.boardRegForm.submit();
	}
	function goBoardContentForm(b_no){
		$("[name=boardContentForm] [name=b_no]").val(b_no);

		document.boardContentForm.submit();
	}
</script>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<title>게시판</title>
</head>
<body>
	<center>
		<br> <a href="javascript:goBoardRegForm();">[새 글쓰기]</a>
		<table border=1>
			<tr>
				<th>번호
				<th>제목
				<th>글쓴이
				<th>등록일
				<th>조회수 <c:forEach items="${boardList}" var="board"
						varStatus="loopTagStatus">
						<tr style="cursor:pointer" onClick="goBoardContentForm(${board.b_no});">
							<td>${requestScope.boardListCnt-loopTagStatus.index}
							<td><c:if test="${board.print_level>0}">
									<c:forEach begin="0" end="${board.print_level}">
							&nbsp;&nbsp;&nbsp;
						</c:forEach>
						ㄴ
					</c:if> ${board.subject}
							<td>${board.writer}
							<td>${board.reg_date}
							<td>${board.readcount}
					</c:forEach>
		</table>
		<form name = "boardRegForm" method="post" action="/z_jsp/boardRegForm.do">
		
		</form>
		<form name = "boardContentForm" method="post" action="/z_jsp/boardContentForm.do">
			<input type="hidden" name="b_no">
		
		</form>
		
	</center>
</body>
</html>