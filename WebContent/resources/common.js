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