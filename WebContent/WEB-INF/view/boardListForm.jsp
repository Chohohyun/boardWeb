<!--  현재 이 jsp 페이지 실행 후 생성되는 문서는 html 이고 이문서는 utf-8 방식으로 인코딩한다. -->

<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="common.jsp"%>
<!--  JSTL 커스텀 태그와 EL을 사용하여 HttpSession 객체에 로그인 아이디가 없으면 경고하고 로그인 화면으로 이동 시키기-->
<!--  만약 세션객체에 admin_id라는 키값으로 저장된 놈이 없다면.. -->

<%@include file="checkLogin.jsp"%>
<script>
$(document).ready(function(){
	
	//alert('${param.date}');
	//var values = '${param.date}'.split();
	//for(var i=0; i<values.length; i++){
	//	if(values == "오늘") $("[name=boardListForm] [id=date1]").prop("checked",true);
	//	if(values == "어제") $("[name=boardListForm] [id=date2]").prop("checked",true);
	//}
	//if('${param.date}' == "오늘") $("[name=boardListForm] [id=date1]").prop("checked",true);
	//if('${param.date}' == "어제") $("[name=boardListForm] [id=date2]").prop("checked",true);
	//id = "staff" 가 있는 태그 후손의 thead 안의 후손의 tr요소들에 지정한 css적용
	
	//$('#staff tr:eq(0)').css('background','#888888'); 같은 말
	//$('#staff tr').eq(0).css('background','#888888'); 같은 말

	//for(var i = 0; i<$('#staff tbody tr').length; i+=2){
		
//		$('#staff tbody tr:eq('+i+')').css('background','#999999');
//	}
	//var evenTrColor = "white";
	//var oddTrColor = "#EBF0F4";
	//var headerColor="#708090";
//----------------------------------------------------------
	var evenTrColor = "${evenTrColor}";
	var oddTrColor ="${oddTrColor}";
	var headerColor="${headerColor}";
	// 짝수 홀수 배경색 바꾸기
	$('#board tr:odd').css('background',oddTrColor);
	$('#board tr:even').css('background',evenTrColor);
	$('#board tr').eq(0).css('background',headerColor);
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

	// 검색 조건의 흔적 남기기
	
	 //keyword1 라는 파라미터명의 파라미터값을 name=keyword1 가진 입력 양식에 넣어주기
	 $("[name=boardListForm] [name=keyword1]").val("${param.keyword1}");

	 //keyword2 라는 파라미터명의 파라미터값을 name=keyword2 가진 입력 양식에 넣어주기
	 $("[name=boardListForm] [name=keyword2]").val("${param.keyword2}");
	
	 // or_and 라는 파라미터명의 파라미터값을 name=or_and 가진 입력 양식에 넣어주기
	 // 단 파라미터 값이 비어 있으면 문자열 or 넣어주기
	 $("[name=boardListForm] [name=or_and]").val("${empty param.or_and? 'or' : param.or_and}");
	
	 // date 라는 파라미터명의 파라미터값을 name=date 가진 입력 양식에 체크해주기
	 <c:forEach items="${paramValues.date}" var="date">
	 	$("[name=date]").filter("[value=${date}]").prop("checked",true);
	 </c:forEach>
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
		
		if(is_empty("keyword1") && is_empty("keyword2") && is_empty("date")){
			alert("입력된 검색 조건이 모두 없어 검색을 하지 않습니다.");
			$(".keyword1,.keyword2").val("");
			return;
		}
		document.boardListForm.submit();
		//var key1 = $("[name=boardListForm] [name=keyword1]").val();
		//var key2 = $("[name=boardListForm] [name=keyword2]").val();
		//var checkChk = $("[name=boardListForm] [name=date]").is(":checked");
		//alert(checkChk);
		//key1 = $.trim(key1);
		//key2 = $.trim(key2);
		//key1 = key1.split(" ").join("");
		//key2 = key2.split(" ").join("");
		//if (key1 == "" && key2=="" && checkChk==false) {
			//alert("키워드를 입력하거나 체크박스를 체크해주십시오");
			//$("[name=boardListForm] [name=keyword1]").val("");
			//$("[name=boardListForm] [name=keyword2]").val("");
			//$("[name=boardListForm] [name=keyword1]").focus();
			//return;
		//}
		//alert(key1);
		//document.boardListForm.submit();
		
	}
	function goSearchAll(){
		$("[name=boardListForm] [name=keyword1]").val("");
		$("[name=boardListForm] [name=keyword2]").val("");
		$("[name=boardListForm] [name=date]").prop("checked",false);
		document.boardListForm.submit();
	}
</script>
<html>
<style>
.style1 {
	background-color: #CFCFE7;
	color: #000000;
}
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
		<form name="boardListForm" method="post"
			action="/z_jsp/boardListForm.do">
			<input type="text" name="keyword1"> <select name="or_and">
				<option value="or">or</option>
				<option value="and">and</option>
			</select> <input type="text" name="keyword2"> <input type="checkbox"
				name="date" id="date1" value="오늘">오늘 <input type="checkbox"
				name="date" id="date2" value="어제">어제 <input type="button"
				value="검색" onClick="goSearch();"> <input type="button"
				value="모두검색" onClick="goSearchAll();"><br>
		</form>
		<br> <a href="javascript:goBoardRegForm();">[새 글쓰기]</a>
		<table class="tbcss2" id="board" border=0 cellpadding=5 cellspacing=0>
			<tr>
				<th>번호
				<th>제목
				<th>글쓴이
				<th>등록일
				<th>조회수 <c:forEach items="${boardList}" var="board"
						varStatus="loopTagStatus">
						<tr style="cursor: pointer"
							onClick="goBoardContentForm(${board.b_no});">
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
		<form name="boardRegForm" method="post"
			action="/z_jsp/boardRegForm.do"></form>
		<!--  상세보기 폼 -->
		<form name="boardContentForm" method="post"
			action="/z_jsp/boardContentForm.do">
			<input type="hidden" name="keyword1" value="${param.keyword1}">
			<input type="hidden" name="keyword2" value="${param.keyword2}">
			<input type="hidden" name="or_and" value="${param.or_and}"> <input
				type="hidden" name="b_no">

		</form>

	</center>
</body>
</html>