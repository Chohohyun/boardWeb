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
			String selectPageNo = request.getParameter("selectPageNo");
			if(selectPageNo==null || selectPageNo.length()==0) {
				selectPageNo = "1";
			}
			String rowCntPerPage = request.getParameter("rowCntPerPage");

			if(rowCntPerPage==null || rowCntPerPage.length()==0) {
				rowCntPerPage = "10";
			}
			
			
			

			// HttpSession 객체에서 키값 uri로 저장된 문자열 꺼내기
			// 키값 uri에 저장된 문자열은 현재 클래스에 오기 전에 
			// 어느 클래스에 있었냐에 대한 정보가 들어 있다.
			HttpSession session = request.getSession();
			String uri = (String)session.getAttribute("uri");
			
			
			// 만약 전에 들렸던 클래스가 없거나
			// 전에 들렸던 클래스가 가칭 boardListForm 라면
			// Session 객체에 파라미터 값을 저장하기
			if(uri ==null || uri.equals("boardListForm")) {		
				session.setAttribute("keyword1",keyword1);
				session.setAttribute("keyword2",keyword2);
				session.setAttribute("or_and",orAnd);
				session.setAttribute("date",checkDate);
				session.setAttribute("rowCntPerPage", rowCntPerPage);
				session.setAttribute("selectPageNo", selectPageNo);
				
			}
			
			// 만약에 전에 들렸던 클래스가 있고
			// 전에 들렸던 클래스가 boardListForm이 아니라면
			// Session 객체에서 파라미터값 꺼내서 저장하기
			else {
				keyword1 = (String)session.getAttribute("keyword1");
				keyword2 = (String)session.getAttribute("keyword2");
				orAnd = (String)session.getAttribute("or_and");
				checkDate = (String[])session.getAttribute("date");
				rowCntPerPage = (String)session.getAttribute("rowCntPerPage");
				selectPageNo=(String)session.getAttribute("selectPageNo");
			}
			session.setAttribute("uri","boardListForm");
			//BoardSearchDTO에 파라미터값들을 저장하기
			BoardSearchDTO boardSearchDTO = new BoardSearchDTO();
			boardSearchDTO.setKeyword1(keyword1);
			boardSearchDTO.setKeyword2(keyword2);
			boardSearchDTO.setOr_and(orAnd);
			boardSearchDTO.setDate(checkDate);
			boardSearchDTO.setRowCntPerPage(Integer.parseInt(rowCntPerPage,10));
			boardSearchDTO.setSelectPageNo(Integer.parseInt(selectPageNo,10));
			
			System.out.println("rowCntPerPage : "+rowCntPerPage );
			System.out.println("selectPageNo : "+selectPageNo );
				
			System.out.println("keyword1+"+keyword1);
			/* 2가지 오버로딩
			 List<Map<String,String>> boardList = null;
			if(keyword!=null && keyword.length()>0) {
				 boardList = boardDAO.getBoardList(keyword);
			}
			else {
				boardList = boardDAO.getBoardList();
			}
			 */
			int boardListAllCnt = boardDAO.getBoardListAllCnt(boardSearchDTO);
			int lastPageNo = boardListAllCnt / boardSearchDTO.getRowCntPerPage();
			if( boardListAllCnt % boardSearchDTO.getRowCntPerPage()>0) {
				lastPageNo++;
			}
			//lastPageNo += boardListAllCnt % boardSearchDTO.getRowCntPerPage()>0?1:0;
			System.out.println();
			if( lastPageNo < boardSearchDTO.getSelectPageNo() ){
			//if( boardListAllCnt<=(boardSearchDTO.getSelectPageNo()-1)*boardSearchDTO.getRowCntPerPage() ){

				session.setAttribute("selectPageNo", "1");
				boardSearchDTO.setSelectPageNo(1);
				
			}
			List<Map<String,String>> boardList = boardDAO.getBoardList4(boardSearchDTO);
			request.setAttribute("boardList", boardList);

			request.setAttribute("boardListAllCnt", boardListAllCnt);
	
			
			System.out.println("selectPageNo :" + session.getAttribute("selectPageNo"));
			//request.setAttribute("keyword", keyword);



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
