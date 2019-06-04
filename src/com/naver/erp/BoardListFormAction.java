package com.naver.erp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

public class BoardListFormAction implements CommandAction{

	public String requestPro(HttpServletRequest request, HttpServletResponse response) 
	throws Throwable{
		try {
			HttpSession session = request.getSession();
			
			//String admin_id= (String)session.getAttribute("admin_id");
			//if(admin_id==null) {
				//return "/WEB-INF/view/loginForm.jsp";
			//}
			BoardDAO boardDAO = BoardDAO.getInstance();
			/*List<Map<String,String>> boardList = new ArrayList<Map<String,String>>();
			Map<String,String> article = new HashMap<String,String>();
			article.put("b_no","1");
			article.put("subject","코딩 에러를 못찾는 증상에 대해서");
			article.put("writer","반장");
			article.put("reg_date","2019-05-21");
			article.put("readCount","23");
			boardList.add(article);
			
			article = new HashMap<String,String>();
			article.put("b_no","2");
			article.put("subject","토끼 엄마의 따뜻한 맘씨에 대해서 고마워서");
			article.put("writer","박토끼");
			article.put("reg_date","2019-05-21");
			article.put("readCount","5");
			boardList.add(article);
			*/
			// httpserveletrequest 객체에 게시판 목록 저장하기
			String keyword1 = request.getParameter("keyword1");
			String keyword2 = request.getParameter("keyword2");
			String orAnd = request.getParameter("or_and");
			String[] checkDate = null;
			checkDate = request.getParameterValues("date");
			
			System.out.println(keyword1);
			/* 2가지 오버로딩
			 List<Map<String,String>> boardList = null;
			if(keyword!=null && keyword.length()>0) {
				 boardList = boardDAO.getBoardList(keyword);
			}
			else {
				boardList = boardDAO.getBoardList();
			}
			 */

			List<Map<String,String>> boardList = boardDAO.getBoardList3(keyword1,keyword2,orAnd,checkDate);
			request.setAttribute("boardList", boardList);
			if(boardList!=null) {
				request.setAttribute("boardListCnt", boardList.size());
				//request.setAttribute("keyword", keyword);
			}
			
			
			// httpserveletrequest 객체에 저장된 데이터는 호출되는
			// jsp 페이지에서 꺼낼 수 있다.
						
			
			// 게시판 목록 개수 저장
			// httpservletrequest 객체에 게시판 목록 개수 저장하기
			// httpservletrequest 객체에 저장된 데디터는 호출되는
			// jsp 페이지에서 꺼낼 수 있다.
			
			
		}
		catch(Exception e) {
			// 예외 발생시 도스창에 경고 메세지를 출력하기
			System.out.println("BoardListFormActiom 클래스에서 에러발생");
		}
		return "/WEB-INF/view/boardListForm.jsp";
		
	}
}
