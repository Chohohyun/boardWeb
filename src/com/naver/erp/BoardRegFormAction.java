package com.naver.erp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

public class BoardRegFormAction implements CommandAction{

	public String requestPro(HttpServletRequest request, HttpServletResponse response) 
			throws Throwable{
		try {

			HttpSession session = request.getSession();
			session.setAttribute("uri", "boardRegForm");
			// 파라미터값 꺼내어 변수에 저장
			String b_no = request.getParameter("b_no");
			
			// HttpServletRequest 객체에 주인글의 고유번호를 저장하기.
			// HttpServletRequest 객체에 저장된 데이터는 호출되는
			// JSP 페이지에서 꺼낼 수 있다.
			// request.setAttribute("b_no", b_no);
		}
		catch(Exception e) {
			System.out.println("BoardRegFormAction 클래스에서 에러발생");
		}
		return "/WEB-INF/view/boardRegForm.jsp";

	}
}
