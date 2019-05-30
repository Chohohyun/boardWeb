package com.naver.erp;

import java.io.*; 
import java.util.*; 
import javax.servlet.*; 
import javax.servlet.http.*; 
import com.naver.erp.CommandAction;

public class ControllerAction extends HttpServlet {
	// Command.properties 파일에 설정된 <URL>과 <Model 클래스명>를 쌍으로 저장할 Map 객참변 선언
	private Map commandMap = new HashMap( );  

	// 서블릿 실행 시 자동으로 처음 실행하는 메소드 선언. 
	// Command.properties 파일을 읽어 [가상 URL] 과 [모델] 클래스명을 쌍으로 Map 객체에 저장
	//*****************************************************************
	public void init(ServletConfig config) throws ServletException { 
		//*****************************************************************  	
		// web.xml에서 "propertyConfig" 이름의 Command.properties 파일명 읽고
		// 웹서버 운영 체제의 실제 경로 붙이기         
		String path = config.getServletContext( ).getRealPath(
			config.getInitParameter( "propertyConfig" )
		);
		Properties pr = new Properties( );                          // 명령어와 처리클래스의 매핑정보를 저장할 Properties 객체 생성
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(path);     // Command.properties 파일의 내용을 읽어옴
			pr.load(fis);                        // Command.properties 파일의 정보를  Properties 객체에 저장
		} catch (IOException e) {
			throw new ServletException(e);
		} finally {
			if (fis != null) try { fis.close( ); } catch(IOException ex) { }
		}
		Iterator keyIter = pr.keySet( ).iterator( );   // Iterator객체는 Enumeration 객체를 확장시킨 개념의 객체

		while( keyIter.hasNext( ) ) {   // 객체를 하나씩 꺼내서 그 객체명으로 Properties 객체에 저장된 객체에 접근
			String command = ((String)keyIter.next( )).trim();
			String className = (pr.getProperty(command)).trim();   
			try {
				Class commandClass = Class.forName(className);          // 해당 문자열을 클래스로 만든다.
				Object commandInstance = commandClass.newInstance( );   // 해당 클래스의 객체를 생성
				commandMap.put(command, commandInstance);             // Map 객체인 commandMap에 객체 저장
			} catch (ClassNotFoundException e) {
				System.out.println( command+ "라는 URL 주소에 대응하는 "+className + " 라는 클래스가 없다내요..참나...");
				throw new ServletException(e);
			} catch (InstantiationException e) {
				throw new ServletException(e);
			} catch (IllegalAccessException e) {
				throw new ServletException(e);
			}
		}
	}

	// 클라이언트가 get 방식으로 접속 시 init(~) 메소드 다음으로 실행 할메소드 선언
	//*****************************************************************
	public void doGet(  HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	//*****************************************************************
		exe(request, response);
	}
	// 클라이언트가 post 방식으로 접속 시 init(~) 메소드 다음으로 실행 할메소드 선언
	//*****************************************************************
	protected void doPost( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	//*****************************************************************
		exe(request, response);
	}

	// 사용자의 요청을 처리할 [모델] 객체를 생성, 메소드 호출로 요청처리, JSP 페이지 호출할 메소드 선언
	//*****************************************************************
	private void exe( HttpServletRequest  request, HttpServletResponse response ) throws ServletException, IOException {
	//*****************************************************************
		request.setCharacterEncoding( "UTF-8" );
		//response.setContentType("text/html;charset=UTF-8");
		String view = null;             // 요청 처리 결과물을 보여줄 JSP 페이지명 저장 변수 선언
		CommandAction com = null;   // [모델] 객체 저장할 객참변수 선언.
		String command = null;
		try {
			// 가상 URL 에서 URI 만 구하기.
			command = request.getRequestURI( );
			// URI 중에 컨텍스트패스명 이후 가상 주소 구하기
			if( command.indexOf(request.getContextPath( )) == 0 ) {
				command = command.substring(request.getContextPath( ).length( ));
			}
			// [컨텍스트패스명 이후 가상 주소]와 매칭되는 [모델] 객체 구하기
			com = (CommandAction)commandMap.get(command);  
			// [모델] 객체의 requestPro(~,~) 메소드 호출하고 호출 JSP 페이지명 구하기
			view = com.requestPro(request, response);
		} catch(Throwable e) {
			System.out.println( command+ " 라는 URL 주소에 대응하는 "+com + " 라는 클래스가 없다내요..참나...");
			throw new ServletException(e);
		}   
		// 호출 JSP 페이지를 호출하면서 HttpServletRequest 객체도 JSP 페이지로 이동해두는 RequestDispatcher 객체 생성.
		RequestDispatcher dispatcher = request.getRequestDispatcher(view);
		// JSP 페이지 호출. 이때 HttpServletRequest 객체도 JSP 페이지로 이동함
		dispatcher.forward(request, response);
	}
}
