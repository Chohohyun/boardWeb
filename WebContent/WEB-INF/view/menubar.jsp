<%@ page language="java" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="common.jsp" %>
<script>
	$(document).ready(function(){
		printMenubar(
				"black", //menubarBgColo
				"gray", //mouseoverBgColor
				"white", //mouseoverFontColor
				"black", //mouseoutBgColor
				"white", // mouseoutFontColor
				[
					['/z_jsp/boardListForm.do','게시판' ],
					['/z_jsp/loginForm.do','[로그아웃]' ]
				]
		);
	});
</script>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>