package com.naver.erp;


// <참고> javax라는 패키지로 시작하는 클래스는 JDK에서 지원하지 않고
// 외부에서 다운받은 Servlet-api.jar 파일안에 들어있다.
// 외부에서 다운받은 클래스들은 WEB-INF/lib 폴더 안에 넣어둔다.
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

// loginForm.do로 접속하면 호출되는 requestPro(~) 메소드를 소유한
// loginFormAction 클래스 선언
// loginForm.do에 대응하는 클래스 정보는 /WEB-INF/CommandPro.properties 파일에서 읽어들인다.

public class LoginFormAction implements CommandAction{

	public String requestPro(HttpServletRequest request, HttpServletResponse response) 
	throws Throwable{
		// HttpSession 객체에 저장된 로그인 아이디 삭제하기
		
		// HttpServletRequest 객체의 getSession 메소드를 호출하여
		// HttpSession 객체의 메위주를 얻기
		HttpSession session = request.getSession();
		
		// HttpSession 객체의 removeAttribute() 메소드를 호출하여
		// admin_id라는 키값으로 저장된 놈을 지우기
		session.removeAttribute("admin_id");
		
		
		//호출할 jsp 페이지를 문자열로 리턴하기
		return "/WEB-INF/view/loginForm.jsp";
	}
}
