<!--  현재 이 jsp 페이지 실행 후 생성되는 문서는 html 이고 이문서는 utf-8 방식으로 인코딩한다. -->

<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- 얘는 include directive에 포함되지 않는다. -->
	
<script src="/z_jsp/resources/jquery-1.11.0.min.js"></script>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link href="/z_jsp/resources/common.css" rel="stylesheet" type="text/css">

<!-- 사용자 정의 태그인 JSTL의 C 코어태그를 사용하여 변수 선언하기 -->
<!-- 변수 thColor 선언하고 데이터 #708090 저장하기 -->
<!-- C 코어 태그로 선언된 변수안의 꺼낼때는 EL문법으로 로 거낸다 -->
<c:set var="headerColor" value="#708090"/>
<c:set var="oddTrColor" value="#E8F0F4"/>
<c:set var="evenTrColor" value="#white"/>
<c:set var="mouseOverColor" value="#D0E0F4"/>

<script src="/z_jsp/resources/common.js"></script>
