package com.naver.erp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

public class BoardContentFormAction implements CommandAction{

	public String requestPro(HttpServletRequest request, HttpServletResponse response) 
			throws Throwable{
		try {
			// 클라이언트가 보낸 파라미터값 꺼내어 변수에 저장
			String b_no = request.getParameter("b_no");
			
			// DB 연동을 위해  BoardDAO 객체 생성
			BoardDAO boardDAO = BoardDAO.getInstance();
			
			// BoardDAO 객체의 getBoard 메소드 호출로 [게시판 1개 글 검색 결과]를 얻어 BoardDTO 객체에 저장
			BoardDTO board = boardDAO.getBoard(Integer.parseInt(b_no,10));
			
			// /WEB-INF/view/boardContentForm.jsp 페이지에서
			// 사용할 데이터를 HttpServletEquest 객체에 저장.
			request.setAttribute("board", board);
		}
		catch(Exception e) {
			System.out.println("BoardContentFormAction에서 에러발생");
		}
		return "/WEB-INF/view/boardContentForm.jsp";

	}
}
