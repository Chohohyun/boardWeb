<!--  현재 이 jsp 페이지 실행 후 생성되는 문서는 html 이고 이문서는 utf-8 방식으로 인코딩한다. -->

<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="common.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
</head>
<body>
	<!-- HttpServletRequest 객체에 admin_id 라는 키값으로 저장된 데이터를 -->
	<!--  꺼내서 body 안에 표현하기 -->
	<!--  EL -->
	${requestScope.admin_idCnt}
</body>
</html>