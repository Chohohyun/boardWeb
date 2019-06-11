package com.naver.erp;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class Util {
	public static void addCookie(HttpServletResponse response,String cookieName,String cookieVal,int cookieLifeTime) throws Exception {
		// 쿠키명과 쿠키값을 관리하는 Cookie 객체 생성하기
		// 쿠키의 생명 주기 정하기
		
		Cookie cookie1= new Cookie(cookieName,cookieVal);
		cookie1.setMaxAge(cookieLifeTime);
		response.addCookie(cookie1);
		
	}
	public static boolean isEmpty(String str) {
		if(str==null) {
			return true;
		}
		str = str.replaceAll(" ","");
		if( str.length()==0) {
			return true;
		}
		return false;
	}
}
