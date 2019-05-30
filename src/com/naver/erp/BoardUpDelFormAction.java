package com.naver.erp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

public class BoardUpDelFormAction implements CommandAction{

	public String requestPro(HttpServletRequest request, HttpServletResponse response) 
			throws Throwable{
		try {
			String b_no = request.getParameter("b_no");
			
			BoardDAO boardDAO = BoardDAO.getInstance();
			
			BoardDTO boardDTO = boardDAO.getBoard(Integer.parseInt(b_no,10));
			
			int sonBoardCnt = boardDAO.getSonBoardCnt(Integer.parseInt(b_no,10));
			
			request.setAttribute("boardDTO", boardDTO);
			request.setAttribute("b_no", b_no);
			request.setAttribute("sonBoardCnt", sonBoardCnt);
			
		}
		catch(Exception e) {
			System.out.println("BoardUpDelFormAction 클래스에서 에러발생");
		}
		return "/WEB-INF/view/boardUpDelForm.jsp";
	}
}
