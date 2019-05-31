package com.naver.erp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

public class BoardUpDelProcAction implements CommandAction {

	public String requestPro(HttpServletRequest request, HttpServletResponse response) 
	throws Throwable{
		try{
			// 파라미터 값 꺼내어 변수에 저장
			String upDel = request.getParameter("upDel");
			String b_no = request.getParameter("b_no");
			String subject = request.getParameter("subject");
			String writer = request.getParameter("writer");
			String content = request.getParameter("content");
			String pwd = request.getParameter("pwd");
			String email = request.getParameter("email");
			
			System.out.println("1");
			// 파라미터값을 BoardDTO 객체 저장하기
			BoardDTO boardDTO = new BoardDTO();
			boardDTO.setB_no(Integer.parseInt(b_no));
			boardDTO.setSubject(subject);
			boardDTO.setWriter(writer);
			boardDTO.setContent(content);
			boardDTO.setPwd(pwd);
			boardDTO.setEmail(email);

			// db 연동을 위해 BoardDAO 생성
			BoardDAO boardDAO = BoardDAO.getInstance();

			System.out.println("3");
			// 만약 수정모드면 updateBoard 메소드 호출하여 수정 적용행의 개수 얻기
			int boardUpDelCnt=0;
			if(upDel.equals("up")) {
				boardUpDelCnt=boardDAO.updateBoard(boardDTO);
			}
			
			// 만약 삭제모드면 
			else if(upDel.equals("del")) {
				boardUpDelCnt=boardDAO.deleteBoard2(boardDTO);
				
			}
			request.setAttribute("boardUpDelCnt",boardUpDelCnt);
			request.setAttribute("upDel",upDel);
			
		}catch(Exception e) {
			System.out.println("BoardUpDelProcAction 클래스에서 에러 발생");
			request.setAttribute("boardUpDelCnt", -99);
		}
		// 호출할 jsp 페이지명을 controllerAction 객체에 문자열로 리턴하기
		// controllerAction 객체는 이 문자열에 해당하는 jsp페이지를 호출
		// 호출하는 jsp 페이지로 리퀘스트, 리스폰, 세션 객체도 이동
		return "/WEB-INF/view/boardUpDelProc.jsp";
	}
}
