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