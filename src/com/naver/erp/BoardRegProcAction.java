package com.naver.erp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

public class BoardRegProcAction implements CommandAction {

	public String requestPro(HttpServletRequest request, HttpServletResponse response) 
	throws Throwable{
		try{
			// 게시판 새글 관련 파라미터값 꺼내어 변수 저장
			String b_no = request.getParameter("b_no");
			String subject = request.getParameter("subject");
			String writer = request.getParameter("writer");
			String content = request.getParameter("content");
			String pwd = request.getParameter("pwd");
			String email = request.getParameter("email");
			
			BoardDTO boardDTO = new BoardDTO();
			if ( b_no!=null && b_no.length()>0 ) {
				boardDTO.setB_no( Integer.parseInt(b_no,10) );
			}
			boardDTO.setSubject(subject);
			boardDTO.setWriter(writer);
			boardDTO.setContent(content);
			boardDTO.setPwd(pwd);
			boardDTO.setEmail(email);
			
			BoardDAO boardDAO = BoardDAO.getInstance();
			
			int boardRegCnt= boardDAO.insertBoard(boardDTO);
			request.setAttribute("boardRegCnt", boardRegCnt);
			System.out.println(boardRegCnt + "값이 몇일까?");
			
		}catch(Exception e) {
			System.out.println("BoardRegProcAction 클래스에서 에러 발생");
		}
		// 호출할 jsp 페이지명을 controllerAction 객체에 문자열로 리턴하기
		// controllerAction 객체는 이 문자열에 해당하는 jsp페이지를 호출
		// 호출하는 jsp 페이지로 리퀘스트, 리스폰, 세션 객체도 이동
		return "/WEB-INF/view/boardRegProc.jsp";
	}
}
