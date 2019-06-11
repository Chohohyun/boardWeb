<!--  현재 이 jsp 페이지 실행 후 생성되는 문서는 html 이고 이문서는 utf-8 방식으로 인코딩한다. -->

<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="common.jsp"%>
<!--  JSTL 커스텀 태그와 EL을 사용하여 HttpSession 객체에 로그인 아이디가 없으면 경고하고 로그인 화면으로 이동 시키기-->
<!--  만약 세션객체에 admin_id라는 키값으로 저장된 놈이 없다면.. -->

<%@include file="checkLogin.jsp"%>
<%@include file="menubar.jsp"%>
<script>
$(document).ready(function(){
	
	// name=boardRegForm을 가진 form 태그와
	// name=boardContentForm을 가진 form 태그를 안보이게 하기
	$("[name=boardRegForm], [name=boardContentForm]").hide();
	
	$("[name=rowCntPerPage]").change(function(){
		goSearch();
	});
	
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
	var mouseOverColor="${mouseOverColor}";
	
	setTableTrBgColor("boardList",headerColor,evenTrColor,oddTrColor,mouseOverColor);
	
	
	// 페이징 처리 관련 html 소스를 class= pagingNumber 가진 태그 안에 삽입하기
	$(".pagingNumber").html(
		getPagingNumber(
		"${requestScope.boardListAllCnt}", // 검색 결과 총 행 개수
		"${sessionScope.selectPageNo}", // 선택된 현재 페이지 번호
		"${sessionScope.rowCntPerPage}", // 페이지 당 출력행의 개수
		"10", // 페이지 당 보여줄 페이징번호 개수
		"goSearch();" // 페이지 번호 클릭 실행할 자스 코드
		)
	);
	
	/*짝수 홀수 배경색 바꾸기
	//$('#board tr:odd').css('background',oddTrColor);
	//$('#board tr:even').css('background',evenTrColor);
	//$('#board tr').eq(0).css('background',headerColor);
	// 마우스 갖다대면 배경색 바꾸기 
	//$('#board tr').hover(
	//function(){
		// 마우스를 갖다댄 tr 태그 후손의 td태그에 class="style1" 삽입
		//$(this).find('td').addClass('style1');
//	},
	//function(){
	//	$(this).find('td').removeClass('style1');
//	});

//	$('#board tr th').hover(
//	function(){
		//순서번호를 저장해준다.
	//	var no = $(this).index()+1;

		// td 중에 no번째 자식들만.
	//	$('#board tr td:nth-child('+no+')').addClass('style1');

	//},
	
	//function(){
	
	//	$('#board tr td').removeClass('style1');
	//});
	*/
	
	
	
	// 검색 조건의 흔적 남기기
	
	 //keyword1 라는 파라미터명의 파라미터값을 name=keyword1 가진 입력 양식에 넣어주기
	// $("[name=boardListForm] [name=keyword1]").val("${param.keyword1}");

	 //keyword2 라는 파라미터명의 파라미터값을 name=keyword2 가진 입력 양식에 넣어주기
	// $("[name=boardListForm] [name=keyword2]").val("${param.keyword2}");
	
	 // or_and 라는 파라미터명의 파라미터값을 name=or_and 가진 입력 양식에 넣어주기
	 // 단 파라미터 값이 비어 있으면 문자열 or 넣어주기
	// $("[name=boardListForm] [name=or_and]").val("${empty param.or_and? 'or' : param.or_and}");
	
	 // date 라는 파라미터명의 파라미터값을 name=date 가진 입력 양식에 체크해주기
	// <c:forEach items="${paramValues.date}" var="date">
	// 	$("[name=date]").filter("[value=${date}]").prop("checked",true);
	// </c:forEach>
	 
	// 검색 조건 흔적 남기기 2
	inputData("keyword1","${sessionScope.keyword1}");
	inputData("keyword2","${sessionScope.keyword2}");
	inputData("or_and","${sessionScope.or_and}");
	<c:forEach items="${sessionScope.date}" var="date">
	inputData("date","${date}");
	</c:forEach>
	inputData("selectPageNo","${sessionScope.selectPageNo}");
	inputData("rowCntPerPage","${sessionScope.rowCntPerPage}");
	 
	
	
	
});

	function goBoardRegForm(){
	
		document.boardRegForm.submit();
		
	}
	function goBoardContentForm(b_no){
		$("[name=boardContentForm] [name=b_no]").val(b_no);

		document.boardContentForm.submit();
	}
	function goSearch(){
		if(is_special_char("keyword1") || is_special_char("keyword2")){
			alert("키워드에는 영문,숫자,한글,_ 만 가능합니다.");
			$(".keyword1,.keyword2").val("");
			return;
		}
		
		if(is_empty("keyword1") && is_empty("keyword2") && is_empty("date")){
			//alert("입력된 검색 조건이 모두 없어 모두 검색을 실행합니다.");
			$(".keyword1,.keyword2").val("");
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
	// 모두검색 키워드 없애기
	function goSearchAll(){
		// 기존 방식
		//$("[name=boardListForm] [name=keyword1]").val("");
		//$("[name=boardListForm] [name=keyword2]").val("");
		//$("[name=boardListForm] [name=date]").prop("checked",false);
		
		// 공용함수 setEmpty 활용
		//setEmpty("keyword1");
		//setEmpty("keyword2");
		//setEmpty("date");
		
		// 공용함수 setEmpty2 활용
		setEmpty2( "[name=keyword1], [name=keyword2], [name=date]");
		inputData("selectPageNo","1");
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
				<input type="hidden" name="selectPageNo">
				<select name="rowCntPerPage">
					<option value="10">10
					<option value="15">15
					<option value="20">20
					<option value="25">25
					<option value="30">30
				</select>행보기
		</form>
		<table  border = 0>
			<tr>
				<td align=right>
						[총 개수] : ${requestScope.boardListAllCnt} &nbsp;&nbsp;&nbsp;
						<a href="javascript:goBoardRegForm();">[새 글쓰기]</a>
			<tr>
				<th><span class = "pagingNumber"></span>
				
					
				
			<tr>
			<td><table class="tbcss2 boardList" id="board" border=0 cellpadding=5 cellspacing=0>
			<tr>
				<th>번호
				<th>제목
				<th>글쓴이
				<th>등록일
				<th>조회수 <c:forEach items="${boardList}" var="board"
						varStatus="loopTagStatus">
						<tr style="cursor: pointer"
							onClick="goBoardContentForm(${board.b_no});">
							<td>${requestScope.boardListAllCnt-((sessionScope.selectPageNo-1)*sessionScope.rowCntPerPage)-loopTagStatus.index}
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
		</table>
		
		${requestScope.boardListAllCnt==0?'검색된 글이 없습니다.' : ''} 
		<form name="boardRegForm" method="post" action="/z_jsp/boardRegForm.do">
		
			</form> 
		<!--  상세보기 폼 -->
		<form name="boardContentForm" method="post"
			action="/z_jsp/boardContentForm.do">
			 
			<input type="hidden" name="b_no">

		</form>

	</center>
</body>
</html>