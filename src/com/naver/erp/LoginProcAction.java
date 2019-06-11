package com.naver.erp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

public class LoginProcAction implements CommandAction {

	public String requestPro(HttpServletRequest request, HttpServletResponse response) 
	throws Throwable{
		try{
			//htppservletrequest 객체의 메소드 getParameter("파라미터명")을 호출하여
			// 파라미터값을 꺼낸다
			String admin_id = request.getParameter("admin_id");
			String pwd = request.getParameter("pwd");
			String is_login = request.getParameter("is_login");
			// oracle db에서 아이디의 존재 개수 얻기
			// 일단 아이디 존재 개수가 1개라고 가정한다.
			
			AdminDAO adminDAO= AdminDAO.getInstance();

			int admin_idCnt= adminDAO.getAdminCnt(admin_id, pwd);
			
			// 아이디 암호가 DB에 존재하면 HttpSessin 객체에 아이디 저장하기
			// HttpSession 객체에 저장된 데이터는 추후 재 접속 시 다시 꺼내볼 수 있다.
			if(admin_idCnt==1) {
				// HttpServletRequest 객체의 getSession() 메소드를 호출하여
				// HttpSession 객체의 메위주를 얻기
				HttpSession session = request.getSession();
				
				// HttpSession 객체에 아이디 저장하기
				session.setAttribute("admin_id", admin_id);
				
				// 아이디 암호 저장 의사가 없을 경우 아이디 암호 관련 쿠키를 null로 덮어씌우고 수명 없애기
				// 그리고 이 쿠키를 HttpServletResponse 객체에 저장하기
				// --------------------------------------------------------------
				if(is_login==null) {
					// Cookie 객체 생성하고 쿠키명 cookie, 쿠기값 null로 설정
					Cookie cookie1 = new Cookie("admin_id",null);
					cookie1.setMaxAge(0);
					response.addCookie(cookie1);
					Cookie cookie2 = new Cookie("pwd",null);
					cookie2.setMaxAge(0);
					response.addCookie(cookie2);
				}
				else {
					// Cookie 객체 생성하고 쿠키명 admin_id, 쿠기값 admin_id, 수명 60*60*24로 설정
					Cookie cookie1 = new Cookie("admin_id",admin_id);
					cookie1.setMaxAge(60*60*24);
					response.addCookie(cookie1);

					// Cookie 객체 생성하고 쿠키명 pwd, 쿠기값 pwd, 수명 60*60*24로 설정
					Cookie cookie2 = new Cookie("pwd",pwd);
					cookie2.setMaxAge(60*60*24);
					response.addCookie(cookie2);
				}
			}
			// Httpservletrequest 객체에 로그인 아이디의 존재 개수 저장하기
			// Httpservletrequest 객체에 저장된 데이터는 호출되는
			// jsp 페이지에서 꺼낼 수 있다.
			request.setAttribute("admin_idCnt",admin_idCnt);
			
		}catch(Exception e) {
			System.out.println("LoginProcAction 클래스에서 에러 발생");
		}
		// 호출할 jsp 페이지명을 controllerAction 객체에 문자열로 리턴하기
		// controllerAction 객체는 이 문자열에 해당하는 jsp페이지를 호출
		// 호출하는 jsp 페이지로 리퀘스트, 리스폰, 세션 객체도 이동
		return "/WEB-INF/view/loginProc.jsp";
	}
}
