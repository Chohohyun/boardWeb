<!--  현재 이 jsp 페이지 실행 후 생성되는 문서는 html 이고 이문서는 utf-8 방식으로 인코딩한다. -->

<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script src="/z_jsp/resources/jquery-1.11.0.min.js"></script>
<!--  JSTL 커스텀 태그와 EL을 사용하여 HttpSession 객체에 로그인 아이디가 없으면 경고하고 로그인 화면으로 이동 시키기-->
<!--  만약 세션객체에 admin_id라는 키값으로 저장된 놈이 없다면.. -->

<%@include file="checkLogin.jsp" %>
<script>
$(document).ready(function(){
	//id = "staff" 가 있는 태그 후손의 thead 안의 후손의 tr요소들에 지정한 css적용
	
	//$('#staff tr:eq(0)').css('background','#888888'); 같은 말
	//$('#staff tr').eq(0).css('background','#888888'); 같은 말

	//for(var i = 0; i<$('#staff tbody tr').length; i+=2){
		
//		$('#staff tbody tr:eq('+i+')').css('background','#999999');
//	}
	
	// 짝수 홀수 배경색 바꾸기
	$('#board tr:odd').css('background','#999999');
	$('#board tr:even').css('background','#C6C6C6');
	$('#board tr').eq(0).css('background','#8A8AFF');
	// 마우스 갖다대면 배경색 바꾸기 
	$('#board tr').hover(
	function(){
		// 마우스를 갖다댄 tr 태그 후손의 td태그에 class="style1" 삽입
		$(this).find('td').addClass('style1');
	},
	function(){
		$(this).find('td').removeClass('style1');
	});

	$('#board tr th').hover(
	function(){
		//순서번호를 저장해준다.
		var no = $(this).index()+1;

		// td 중에 no번째 자식들만.
		$('#board tr td:nth-child('+no+')').addClass('style1');

	},
	
	function(){
	
		$('#board tr td').removeClass('style1');
	});

});

	function goBoardRegForm(){
		// name = boardRegForm을 가진 form 태그안의 action에 설정된 URL 로 이동하기
		// 이동 시 form 태그안의 모든 입력 양식이 파라미터값으로 전송된다.
		document.boardRegForm.submit();
	}
	function goBoardContentForm(b_no){
		$("[name=boardContentForm] [name=b_no]").val(b_no);

		document.boardContentForm.submit();
	}
	function goSearch(){
		var key1 = $("[name=boardListForm] [name=keyword1]").val();
		var key2 = $("[name=boardListForm] [name=keyword2]").val();
		key1 = $.trim(key1);
		key2 = $.trim(key2);
		key1 = key1.split(" ").join("");
		key2 = key2.split(" ").join("");
		if (key1 == "" && key2=="") {
			alert("키워드를 입력해 주십시요");
			$("[name=boardListForm] [name=keyword1]").val("");
			$("[name=boardListForm] [name=keyword2]").val("");
			$("[name=boardListForm] [name=keyword1]").focus();
			return;
		}
		alert(key1);
		document.boardListForm.submit();
		
	}
	function goSearchAll(){
		$("[name=boardListForm] [name=keyword1]").val("");
		$("[name=boardListForm] [name=keyword2]").val("");
		document.boardListForm.submit();
	}
</script>
<html>
<style>
		.style1{background-color:#CFCFE7; color:#000000;} 
		/*적용했다가 풀기 가능*/
	</style>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<title>게시판</title>
</head>
<body onKeyDown="if(event.keyCode==13){goSearch();}">
	<a href="javascript:location.replace('/z_jsp/loginForm.do')">로그아웃</a>
	<center>
		<!-- 키워드 폼 -->
		<form name = "boardListForm" method="post" action="/z_jsp/boardListForm.do">
			<input type="text" name="keyword1" value="${param.keyword1}">
			<select name="or_and" >	
				<option value="or" ${param.or_and=='or'?'selected':''}>or</option>
				<option value="and" ${param.or_and=='and'?'selected':''}>and</option>
			</select>	
			<input type="text" name="keyword2" value="${param.keyword2}">
			<input type="button" value = "검색" onClick="goSearch();">
			<input type="button" value = "모두검색" onClick="goSearchAll();"><br>
		</form>
		<br> <a href="javascript:goBoardRegForm();">[새 글쓰기]</a>
		<table id="board" border=1>
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
		<!--  상세보기 폼 -->
		<form name = "boardContentForm" method="post" action="/z_jsp/boardContentForm.do">
			<input type="hidden" name="keyword1" value="${param.keyword1}">
			<input type="hidden" name="keyword2" value="${param.keyword2}">
			<input type="hidden" name="or_and" value="${param.or_and}">
			<input type="hidden" name="b_no">
		
		</form>
		
	</center>
</body>
</html>