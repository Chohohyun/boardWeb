//비어있는지 확인
function is_empty(nameV){

	try{
		var flag = true;

		var obj = $("[name="+nameV+"]");
		if(obj.is(":checkbox")|| obj.is(":radio")){
			if(obj.filter(":checked").length>0){
				flag=false;

			}
		}
		else{
			var tmp=obj.val();
			tmp=$.trim(tmp);
			obj.val(tmp);
			tmp=tmp.split(" ").join("");
			if(tmp!=""){
				flag=false;
			}
		}
		return flag;
	}catch (e) {
		alert("is_empty('"+nameV+"')함수에서 에러 발생!");
		return false;
	}

}

//키워드에 특수문자가 들어가는지?
function is_special_char(nameV){
	try{
		var val=$("[name="+nameV+"]").val();
		val = $.trim(val);
		if(val==""){
			return false;
		}

		var is_special = new RegExp(/[^a-zA-Z가-힣0-9_]/).test(val);
		return is_special;

	}
	catch(e){
		alert("is_special_char 에서 에러");
		return false;
	}
}

function inputData(nameV,data){
	try{
		// 만약 데이터가 null 또는 undefined 면 함수 중단
		if(data==null || data == undefined){
			return;
		}
		// 만약 길이가 없거나 공백으로만 구성되어 있으면 함수 중단
		if((data+"").split(" ").join("")==""){
			return;
		}

		//name 값을 가진 입력 양식을 관리하는 JQuery 객체 생성하기
		var obj = $("[name="+nameV+"]");

		// 만약 입력 양식이 checkbox 또는 radio면
		// nameV 변수 안의 데이터를 가진 checkbox 또는 radio 를 체크하기
		if(obj.is(":checkbox") || obj.is(":radio")){
			obj.filter("[value="+data+"]").prop("checked",true);
		}

		// 만약 아니면 naveV 변수 안의 데이터를 삽입하기
		else{
			obj.val(data);
		}
	}
	catch(e){
		alert("inputData에서 에러 발생")
	}
}

//입력 양식에 value 값을 삭제하거나 체크 값을 풀어주는 함수 선언하기
function setEmpty(nameV){
	try {
		// name 값이라는 가진 양식을 관리하는 JQuery 객체 생성하기
		var obj = $("[name="+nameV+"]");

		if(obj.length==0){
			alert("name="+nameV+"을 가진 입력양식이 없습니다.");
			return;
		}

		// 만약 입력 양식이 checkbox 또는 radio면
		// nameV 변수 안의 데이터를 가진 checkbox 또는 radio 를 체크하기
		if(obj.is(":checkbox") || obj.is(":radio")){
			obj.prop("checked",false);
		}

		// 만약 아니면 naveV 변수 안의 데이터를 삽입하기
		else{
			obj.val("");
		}
	} catch (e) {
		alert("setEmpty에서 에러 발생")
		// TODO: handle exception
	}
}

function setEmpty2(selector){
	try {
		var jqueryObj = $(selector);
		jqueryObj.each(function(){
			var thisObj = $(this);
			if(thisObj.is(":checkbox") || thisObj.is(":radio")){
				thisObj.prop("checked",false);
			}
			else{
				thisObj.val("");
			}
		})
	} catch (e) {
		// TODO: handle exception
	}
}

function is_pattern(nameV, patternObj){
	try {

		var jqObj = $("[name="+nameV+"]");
		if(jqObj.is(":checkbox") || jqObj.is(":radio") || jqObj.is("select")){
			alert("checkbox 또는 radio 또는 select 는 is_pattern 함수의 호출 대상이 아닙니다.");
			return;
		}
		var value= jqObj.val();
		return patternObj.test(value);
	} catch (e) {
		alert("is_pattern('"+nameV+"' ~) 함수에서 에러 발생!");
		// TODO: handle exception
	}
}

function setTableTrBgColor(tableClassV,headerColor,oddBgColor,evenBgColor,mouseOnBgColor){
	try{
		alert("11111");
		var firstTrObj= $("."+tableClassV+" tr:eq(0)");
		var trObjs=firstTrObj.siblings("tr");
		firstTrObj.css('background',headerColor);
		alert("2222");
		trObjs.filter(":odd").css('background',evenBgColor);
		trObjs.filter(":even").css('background',oddBgColor);

		trObjs.hover(
				function(){
					$(this).css('background',mouseOnBgColor);
				},
				function(){
					if($(this).index()%2==0){
						$(this).css('background',evenBgColor);
					}else{

						$(this).css('background',oddBgColor);
					}
				});
	}catch(e){
		alert("setTableTrBgColor('"+tableClassV+"',~) 함수 호출시 에러발생");
	}
}
