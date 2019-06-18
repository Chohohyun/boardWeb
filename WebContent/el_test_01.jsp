<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
	<%@include file="WEB-INF/view/common.jsp" %>
   
	<%@ page import="com.naver.erp.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--  HttpServletRequest 객체에 문자열, Integer, Double 저장하기 -->


<!-- JSP 기술의 한 종류인 JSP Scriptlet 을 사용하여 자바 코딩 영역을 연다 -->
<%
	// HttpServletRequest 객체의 setAttribute를 사용하여 문자열 Integer, Double 저장하기
	request.setAttribute("s_name","박재");
	//request.setAttribute("age",new Integer(21));
	//request.setAttribute("weight",new Double(85.5));
	request.setAttribute("age",21);
	request.setAttribute("weight",85.5);
	
	// HttpServlet 객체의 setAttribute를 사용하여 HaspMap 객체 생성하기
	HashMap family_map = new HashMap();
	family_map.put("brother","이강인");
	family_map.put("sister","설현");
	family_map.put("dog","복실이");
	request.setAttribute("family",family_map);
	//-----------------------------------------------------
	// HttpServlet 객체의 setAttribute를 사용하여 DTO 객체인 Family 객체 저장하기
	Family family_dto = new Family();
	family_dto.setBrother("이강인");
	family_dto.setSister("설현");
	family_dto.setDog("복실이");
	request.setAttribute("family2",family_dto);
	
	//----------------------------------------------------
	ArrayList empList = new ArrayList();
	HashMap emp_map = new HashMap();
	emp_map.put("e_no",1);
	emp_map.put("e_name","이성계");
	emp_map.put("age",1000);
	empList.add(emp_map);
	
	emp_map = new HashMap();
	emp_map.put("e_no",2);
	emp_map.put("e_name","이방언");
	emp_map.put("age",900);
	empList.add(emp_map);
	

	emp_map = new HashMap();
	emp_map.put("e_no",3);
	emp_map.put("e_name","김종서");
	emp_map.put("age",600);
	empList.add(emp_map);
	
	request.setAttribute("empList",empList);
	
	ArrayList empDTOList = new ArrayList();
	EmpDTO empDTO = new EmpDTO();
	
	empDTO.setE_no("1"); empDTO.setE_name("박보검"); empDTO.setAge(26); empDTOList.add(empDTO); empDTO = new EmpDTO();
	empDTO.setE_no("2"); empDTO.setE_name("싸이"); empDTO.setAge(39); empDTOList.add(empDTO); empDTO = new EmpDTO();
	empDTO.setE_no("3"); empDTO.setE_name("박진영"); empDTO.setAge(26); empDTOList.add(empDTO); empDTO = new EmpDTO();
	
	request.setAttribute("empDTOList", empDTOList);
	
	ArrayList addrList = new ArrayList();
	HashMap addr_map = new HashMap();
	addr_map.put("addr_code","1");
	addr_map.put("addr_name","서울");
	addrList.add(addr_map);
	addr_map = new HashMap();
	
	addr_map.put("addr_code","2");
	addr_map.put("addr_name","경기");
	addrList.add(addr_map);
	addr_map = new HashMap();
	
	addr_map.put("addr_code","3");
	addr_map.put("addr_name","인천");
	addrList.add(addr_map);
	addr_map = new HashMap();
	
	request.setAttribute("addrList",addrList);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- EL을 사용하여 HttpServletRequest 객체의 setAttribute로 저장된 데이터 표현하기 -->
	이름 : ${requestScope.s_name} <br>
	나이 : ${requestScope.age} <br>
	무게 : ${requestScope.weight} <br>
	<hr>
	<%
		out.print( (String)request.getAttribute("s_name")+"<br>");
		out.print( (Integer)request.getAttribute("age")+"<br>");
		out.print( (Double)request.getAttribute("weight")+"<br>");
	%>
	
	<hr>
	형제 : ${requestScope.family.brother} <br>
	자매 : ${requestScope.family.sister} <br>
	강아지 : ${requestScope.family.dog} <br>
	<hr>
	형제 : ${requestScope.family2.brother} <br>
	자매 : ${requestScope.family2.sister} <br>
	강아지 : ${requestScope.family2.dog} <br>
	<hr>
	<c:forEach items="${requestScope.empList}" var="emp">
		[직원번호] : ${emp.e_no} , [이름] : ${emp.e_name}, [나이] : ${emp.age} <br>
		
	</c:forEach>
	
	<hr>
	<%
		// EL 과 커스텀 태그인 <c:forEach 를 사용하여 직원목록을 출력했는데
		// 만약 자바 문법을 사용 직원 목록을 출력하면?
		ArrayList empList2 = (ArrayList)request.getAttribute("empList");
		if(empList2!=null){
			for(int i =0; i<empList2.size(); i++){
				 HashMap map = (HashMap)empList2.get(i);
				 out.print("[직원번호] : " + map.get("e_no")+ ", ");
				 out.print("[이름] : " + map.get("e_name")+ ", ");
				 out.print("[나이] : " + map.get("age")+"<br>");
			}	
		}
		
	%>
	<hr>
	<c:forEach items="${requestScope.empDTOList}" var="emp">
		[직원번호] : ${emp.e_no} , [이름] : ${emp.e_name}, [나이] : ${emp.age} <br>
		
	</c:forEach>
	
	<select name="addr">
		<option value="">
		<c:forEach items="${requestScope.addrList}" var="addr">
			<option value="${addr.addr_code}">${addr.addr_name}
		</c:forEach>
	</select>
</body>
</html>