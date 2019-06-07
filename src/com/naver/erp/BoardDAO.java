package com.naver.erp;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
public class BoardDAO {
	private static BoardDAO instance = new BoardDAO();
	private static Connection connection = null;

	public static BoardDAO getInstance() {
		return instance;
	}

	private Connection getConnection( ) throws Exception{
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:XE";
		String id = "system";
		String pw = "123";

		Class.forName("oracle.jdbc.driver.OracleDriver");

		return DriverManager.getConnection(url,id,pw);
	}
	public int getBoardListAllCnt(BoardSearchDTO boardSearchDTO) throws Exception{
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs =null;

		StringBuffer sql = new StringBuffer();
		
		String keyword1 = boardSearchDTO.getKeyword1();
		String keyword2 = boardSearchDTO.getKeyword2();
		String orAnd = boardSearchDTO.getOr_and();
		String[] checkDate = boardSearchDTO.getDate();
		try {
			conn = getConnection();

			sql.append("select ");
			sql.append(" count(*) ");
			sql.append(" from board b ");
			sql.append(" where 1=1");
			if(checkDate!=null) {
				sql.append(" and (");
				sql.append("b.group_no in (select group_no from board where");
				for(int i =0; i<checkDate.length;i++) {
					if(i>0) {
						sql.append(" or " );
					}
					if(checkDate[i].equals("오늘")) {
						sql.append("  to_char(reg_date,'yyyy-mm-dd') = to_char(sysdate,'yyyy-mm-dd') ");
					}
					else if(checkDate[i].equals("어제")) {
						sql.append(" to_char(reg_date,'yyyy-mm-dd') = to_char(sysdate-4,'yyyy-mm-dd') ");
					}
				}
				sql.append(" )) ");
			}


			System.out.println("여기까222222진된다.");
			if( (keyword1!=null && keyword1.length()>0) ||  (keyword2!=null && keyword2.length()>0)) {
				sql.append(" and ( ");
			}
			/*	if( keyword1!=null && keyword1.length()>0 &&  keyword2!=null && keyword2.length()>0) {
				sql.append(" and (");	
				sql.append("  b.group_no in (select group_no from board where (upper(subject) like upper( '%"+ keyword1 +"%') or upper(writer) like upper( '%"+ keyword1 +"%') "); 
				sql.append("                                                                      or upper(content) like upper( '%"+ keyword1 +"%') or upper(email) like upper( '%"+ keyword1 +"%') ");
				sql.append("                                                                    or upper(to_char(reg_date,'YYYY-MM-DD AM HH:MI:SS')) like upper( '%"+ keyword1 +"%')) "+orAnd+" (upper(subject) like upper( '%"+ keyword2 +"%') ");
				sql.append("                                                                       or upper(writer) like upper( '%"+ keyword2 +"%') or upper(content) like upper( '%"+ keyword2 +"%') ");  
				sql.append("                                                                     or upper(email) like upper( '%"+ keyword2 +"%') or upper(to_char(reg_date,'YYYY-MM-DD AM HH:MI:SS')) like upper( '%"+ keyword2 +"%')))) ");
			}*/
			if( keyword1!=null && keyword1.length()>0 ) {

				sql.append("  b.group_no in (select group_no from board where (upper(subject) like upper( '%"+ keyword1 +"%') "); 
				sql.append("                                                                      or upper(writer) like upper( '%"+ keyword1 +"%') ");
				sql.append("                                                                    or upper(content) like upper( '%"+ keyword1 +"%') ");
				sql.append("                                                                       or upper(email) like upper( '%"+ keyword1 +"%') ");  
				sql.append("                                                                     or upper(to_char(reg_date,'YYYY-MM-DD AM HH:MI:SS')) like upper( '%"+ keyword1 +"%')) ");  

			}
			else if (keyword2!=null && keyword2.length()>0) {
				sql.append("  b.group_no in (select group_no from board where");
			}

			if( keyword1!=null && keyword1.length()>0 &&  keyword2!=null && keyword2.length()>0) {
				sql.append(orAnd);
			}

			if(keyword2!=null && keyword2.length()>0) {

				sql.append("   (upper(subject) like upper( '%"+ keyword2 +"%') "); 
				sql.append("                                                                      or upper(writer) like upper( '%"+ keyword2 +"%') ");
				sql.append("                                                                    or upper(content) like upper( '%"+ keyword2 +"%') ");
				sql.append("                                                                       or upper(email) like upper( '%"+ keyword2 +"%') ");  
				sql.append("                                                                     or upper(to_char(reg_date,'YYYY-MM-DD AM HH:MI:SS')) like upper( '%"+ keyword2 +"%'))");

			}
			if( (keyword1!=null && keyword1.length()>0) || (keyword2!=null && keyword2.length()>0)) {
				sql.append("))");
			}
			
			sql.append(" order by group_no desc, print_no asc ");
			System.out.println(sql);
			pstm = conn.prepareStatement(sql.toString());
			// PreparedStatement 객체 소유의 select 문을 실행하여
			// 게시판 글 목록을 얻어와서 resultSet 객체 생성하고
			// ResultSet 객체에 select 결과물을 저장하고 ResultSet 객체의 메위주를 리턴하기
			rs=pstm.executeQuery();

			int boardListAllCnt= 0;
			while(rs.next()) {
				boardListAllCnt=rs.getInt(1);
			}
			System.out.println(boardListAllCnt);
			return boardListAllCnt;
		} catch (Exception e) {
			System.out.println("getBoardListAllCnt() 메소드에서 예외발생");
			return -1;
			// TODO: handle exception
		}finally {
			if(rs != null) {
				try {
					rs.close();
				}catch(SQLException sqle) {

				}
			}
			if(pstm != null) {
				try {
					pstm.close();
				}catch(SQLException sqle) {

				}
			}
			if(conn != null) {
				try {
					conn.close();
				}catch(SQLException sqle) {

				}
			}
		}


	}
	public List<Map<String,String>> getBoardList(String keyword) throws Exception{
		// DB 연동에 사용되는 Connection 객체, PreparedStatement 객체, Resultset 객체의 메위주를 저장할 변수 선언

		// connection 객체 (db 연결하고 상태관리)
		// preparedstatement 기능 => sql 구문을 관리하고 sql을 실행하는 객체
		// resultset 객체 기능 => select sql 구문의 실행 결과값을 소유하고 있는 객체

		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs =null;

		// select SQL 구문 문자열을 저장할 StringBuffer 객체 생성하기
		StringBuffer sql = new StringBuffer();
		try {
			conn = getConnection();

			sql.append("select ");
			sql.append(" b_no, ");
			sql.append(" subject, ");
			sql.append(" writer, ");
			sql.append(" to_char(reg_date,'YYYY-MM-DD AM HH:MI:SS') reg_date, ");
			sql.append(" readcount, ");
			sql.append(" content, ");
			sql.append(" pwd, ");
			sql.append(" email, ");
			sql.append(" group_no, ");
			sql.append(" print_no, ");
			sql.append(" print_level ");
			sql.append(" from board b ");
			sql.append(" where b.group_no in (select group_no from board where subject in (select subject from board where upper(subject) like upper( '%' || ? || '%')) "); 
			sql.append("                                                                      or writer in (select writer from board where upper(writer) like upper( '%' || ? || '%')) ");
			sql.append("                                                                    or content in (select content from board where upper(content) like upper( '%' || ? || '%')) ");
			sql.append("                                                                       or email in (select email from board where upper(email) like upper( '%' || ? || '%')) ");  
			sql.append("                                                                     or to_char(reg_date,'YYYY-MM-DD AM HH:MI:SS') in (select to_char(reg_date,'YYYY-MM-DD AM HH:MI:SS') from board where upper(to_char(reg_date,'YYYY-MM-DD AM HH:MI:SS')) like upper( '%' || ? || '%')))");  
			sql.append(" order by group_no desc, print_no asc ");
			System.out.println(sql);
			pstm = conn.prepareStatement(sql.toString());
			//keyword="\'%" + keyword +"%\'";
			System.out.println(keyword);
			pstm.setString(1,keyword);
			pstm.setString(2,keyword);
			pstm.setString(3,keyword);
			pstm.setString(4,keyword);
			pstm.setString(5,keyword);
			// PreparedStatement 객체 소유의 select 문을 실행하여
			// 게시판 글 목록을 얻어와서 resultSet 객체 생성하고
			// ResultSet 객체에 select 결과물을 저장하고 ResultSet 객체의 메위주를 리턴하기
			rs=pstm.executeQuery();
			//resultSet 객체에 저장된 n행 m열의 데이터 중에
			// 한행의 데이터를 저장할 hashmap 객체의 메위주가 저장될 변수 boardMap 선언하기
			Map<String,String> boardMap = null;
			// 다량의 HashMap 객체가 저장될 ArrayList 객체를 생성하기
			List<Map<String,String>> boardList = new ArrayList<Map<String,String>>();

			System.out.println("여기됨");
			//resultset 객체에서 게시판 글목록을 꺼내어
			//
			while (rs.next()) {
				boardMap = new HashMap<String,String>();
				boardMap.put("b_no",rs.getString("b_no"));
				boardMap.put("subject",rs.getString("subject"));
				boardMap.put("writer",rs.getString("writer"));
				boardMap.put("reg_date",rs.getString("reg_date"));
				boardMap.put("readcount",rs.getString("readcount"));
				boardMap.put("content",rs.getString("content"));
				boardMap.put("pwd",rs.getString("pwd"));
				boardMap.put("email",rs.getString("email"));
				boardMap.put("group_no",rs.getString("group_no"));
				boardMap.put("print_no",rs.getString("print_no"));
				boardMap.put("print_level",rs.getString("print_level"));
				boardList.add(boardMap);
			}
			return boardList;
		}catch(Exception e) {
			System.out.println("에러발생");
			return null;
		}finally {
			if(rs != null) {
				try {
					rs.close();
				}catch(SQLException sqle) {

				}
			}
			if(pstm != null) {
				try {
					pstm.close();
				}catch(SQLException sqle) {

				}
			}
			if(conn != null) {
				try {
					conn.close();
				}catch(SQLException sqle) {

				}
			}
		}
	}
	public List<Map<String,String>> getBoardList2(String keyword1,String keyword2,String orAnd) throws Exception{
		// DB 연동에 사용되는 Connection 객체, PreparedStatement 객체, Resultset 객체의 메위주를 저장할 변수 선언

		// connection 객체 (db 연결하고 상태관리)
		// preparedstatement 기능 => sql 구문을 관리하고 sql을 실행하는 객체
		// resultset 객체 기능 => select sql 구문의 실행 결과값을 소유하고 있는 객체

		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs =null;

		// select SQL 구문 문자열을 저장할 StringBuffer 객체 생성하기
		StringBuffer sql = new StringBuffer();
		try {
			conn = getConnection();

			sql.append("select ");
			sql.append(" b_no, ");
			sql.append(" subject, ");
			sql.append(" writer, ");
			sql.append(" to_char(reg_date,'YYYY-MM-DD AM HH:MI:SS') reg_date, ");
			sql.append(" readcount, ");
			sql.append(" content, ");
			sql.append(" pwd, ");
			sql.append(" email, ");
			sql.append(" group_no, ");
			sql.append(" print_no, ");
			sql.append(" print_level ");
			sql.append(" from board b ");
			sql.append(" where 1=1");

			if( (keyword1!=null && keyword1.length()>0) ||  (keyword2!=null && keyword2.length()>0)) {
				sql.append(" and ( ");
			}
			/*	if( keyword1!=null && keyword1.length()>0 &&  keyword2!=null && keyword2.length()>0) {
				sql.append(" and (");	
				sql.append("  b.group_no in (select group_no from board where (upper(subject) like upper( '%"+ keyword1 +"%') or upper(writer) like upper( '%"+ keyword1 +"%') "); 
				sql.append("                                                                      or upper(content) like upper( '%"+ keyword1 +"%') or upper(email) like upper( '%"+ keyword1 +"%') ");
				sql.append("                                                                    or upper(to_char(reg_date,'YYYY-MM-DD AM HH:MI:SS')) like upper( '%"+ keyword1 +"%')) "+orAnd+" (upper(subject) like upper( '%"+ keyword2 +"%') ");
				sql.append("                                                                       or upper(writer) like upper( '%"+ keyword2 +"%') or upper(content) like upper( '%"+ keyword2 +"%') ");  
				sql.append("                                                                     or upper(email) like upper( '%"+ keyword2 +"%') or upper(to_char(reg_date,'YYYY-MM-DD AM HH:MI:SS')) like upper( '%"+ keyword2 +"%')))) ");
			}*/
			if( keyword1!=null && keyword1.length()>0 ) {

				sql.append("  b.group_no in (select group_no from board where (upper(subject) like upper( '%"+ keyword1 +"%') "); 
				sql.append("                                                                      or upper(writer) like upper( '%"+ keyword1 +"%') ");
				sql.append("                                                                    or upper(content) like upper( '%"+ keyword1 +"%') ");
				sql.append("                                                                       or upper(email) like upper( '%"+ keyword1 +"%') ");  
				sql.append("                                                                     or upper(to_char(reg_date,'YYYY-MM-DD AM HH:MI:SS')) like upper( '%"+ keyword1 +"%')) ");  

			}
			else if (keyword2!=null && keyword2.length()>0) {
				sql.append("  b.group_no in (select group_no from board where");
			}

			if( keyword1!=null && keyword1.length()>0 &&  keyword2!=null && keyword2.length()>0) {
				sql.append(orAnd);
			}

			if(keyword2!=null && keyword2.length()>0) {

				sql.append("   (upper(subject) like upper( '%"+ keyword2 +"%') "); 
				sql.append("                                                                      or upper(writer) like upper( '%"+ keyword2 +"%') ");
				sql.append("                                                                    or upper(content) like upper( '%"+ keyword2 +"%') ");
				sql.append("                                                                       or upper(email) like upper( '%"+ keyword2 +"%') ");  
				sql.append("                                                                     or upper(to_char(reg_date,'YYYY-MM-DD AM HH:MI:SS')) like upper( '%"+ keyword2 +"%'))");

			}
			if( (keyword1!=null && keyword1.length()>0) || (keyword2!=null && keyword2.length()>0)) {
				sql.append("))");
			}

			sql.append(" order by group_no desc, print_no asc ");
			System.out.println(sql);
			pstm = conn.prepareStatement(sql.toString());
			//keyword="\'%" + keyword +"%\'";
			System.out.println(keyword1);

			//if(keyword!=null && keyword.length()>0 ) {
			//	pstm.setString(1,keyword);
			//	pstm.setString(2,keyword);
			//	pstm.setString(3,keyword);
			//	pstm.setString(4,keyword);
			//	pstm.setString(5,keyword);
			//	}
			// PreparedStatement 객체 소유의 select 문을 실행하여
			// 게시판 글 목록을 얻어와서 resultSet 객체 생성하고
			// ResultSet 객체에 select 결과물을 저장하고 ResultSet 객체의 메위주를 리턴하기
			rs=pstm.executeQuery();
			//resultSet 객체에 저장된 n행 m열의 데이터 중에
			// 한행의 데이터를 저장할 hashmap 객체의 메위주가 저장될 변수 boardMap 선언하기
			Map<String,String> boardMap = null;
			// 다량의 HashMap 객체가 저장될 ArrayList 객체를 생성하기
			List<Map<String,String>> boardList = new ArrayList<Map<String,String>>();

			System.out.println("여기됨");
			//resultset 객체에서 게시판 글목록을 꺼내어
			//
			while (rs.next()) {
				boardMap = new HashMap<String,String>();
				boardMap.put("b_no",rs.getString("b_no"));
				boardMap.put("subject",rs.getString("subject"));
				boardMap.put("writer",rs.getString("writer"));
				boardMap.put("reg_date",rs.getString("reg_date"));
				boardMap.put("readcount",rs.getString("readcount"));
				boardMap.put("content",rs.getString("content"));
				boardMap.put("pwd",rs.getString("pwd"));
				boardMap.put("email",rs.getString("email"));
				boardMap.put("group_no",rs.getString("group_no"));
				boardMap.put("print_no",rs.getString("print_no"));
				boardMap.put("print_level",rs.getString("print_level"));
				boardList.add(boardMap);
			}
			return boardList;
		}catch(Exception e) {
			System.out.println("BoardDAO getBoardList2 에서 에러발생");
			return null;
		}finally {
			if(rs != null) {
				try {
					rs.close();
				}catch(SQLException sqle) {

				}
			}
			if(pstm != null) {
				try {
					pstm.close();
				}catch(SQLException sqle) {

				}
			}
			if(conn != null) {
				try {
					conn.close();
				}catch(SQLException sqle) {

				}
			}
		}
	}
	public List<Map<String,String>> getBoardList3(BoardSearchDTO boardSearchDTO) throws Exception{
		// DB 연동에 사용되는 Connection 객체, PreparedStatement 객체, Resultset 객체의 메위주를 저장할 변수 선언

		// connection 객체 (db 연결하고 상태관리)
		// preparedstatement 기능 => sql 구문을 관리하고 sql을 실행하는 객체
		// resultset 객체 기능 => select sql 구문의 실행 결과값을 소유하고 있는 객체

		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs =null;

		// select SQL 구문 문자열을 저장할 StringBuffer 객체 생성하기
		StringBuffer sql = new StringBuffer();

		String keyword1 = boardSearchDTO.getKeyword1();
		String keyword2 = boardSearchDTO.getKeyword2();
		String orAnd = boardSearchDTO.getOr_and();
		String[] checkDate = boardSearchDTO.getDate();
		try {
			conn = getConnection();

			sql.append("select ");
			sql.append(" b_no, ");
			sql.append(" subject, ");
			sql.append(" writer, ");
			sql.append(" to_char(reg_date,'YYYY-MM-DD AM HH:MI:SS') reg_date, ");
			sql.append(" readcount, ");
			sql.append(" content, ");
			sql.append(" pwd, ");
			sql.append(" email, ");
			sql.append(" group_no, ");
			sql.append(" print_no, ");
			sql.append(" print_level ");
			sql.append(" from board b ");
			sql.append(" where 1=1");
			System.out.println("여기까진된다.");

			if(checkDate!=null) {
				sql.append(" and (");
				sql.append("b.group_no in (select group_no from board where");
				for(int i =0; i<checkDate.length;i++) {
					if(i>0) {
						sql.append(" or " );
					}
					if(checkDate[i].equals("오늘")) {
						sql.append("  to_char(reg_date,'yyyy-mm-dd') = to_char(sysdate,'yyyy-mm-dd') ");
					}
					else if(checkDate[i].equals("어제")) {
						sql.append(" to_char(reg_date,'yyyy-mm-dd') = to_char(sysdate-4,'yyyy-mm-dd') ");
					}
				}
				sql.append(" )) ");
			}


			System.out.println("여기까222222진된다.");
			if( (keyword1!=null && keyword1.length()>0) ||  (keyword2!=null && keyword2.length()>0)) {
				sql.append(" and ( ");
			}
			/*	if( keyword1!=null && keyword1.length()>0 &&  keyword2!=null && keyword2.length()>0) {
				sql.append(" and (");	
				sql.append("  b.group_no in (select group_no from board where (upper(subject) like upper( '%"+ keyword1 +"%') or upper(writer) like upper( '%"+ keyword1 +"%') "); 
				sql.append("                                                                      or upper(content) like upper( '%"+ keyword1 +"%') or upper(email) like upper( '%"+ keyword1 +"%') ");
				sql.append("                                                                    or upper(to_char(reg_date,'YYYY-MM-DD AM HH:MI:SS')) like upper( '%"+ keyword1 +"%')) "+orAnd+" (upper(subject) like upper( '%"+ keyword2 +"%') ");
				sql.append("                                                                       or upper(writer) like upper( '%"+ keyword2 +"%') or upper(content) like upper( '%"+ keyword2 +"%') ");  
				sql.append("                                                                     or upper(email) like upper( '%"+ keyword2 +"%') or upper(to_char(reg_date,'YYYY-MM-DD AM HH:MI:SS')) like upper( '%"+ keyword2 +"%')))) ");
			}*/
			if( keyword1!=null && keyword1.length()>0 ) {

				sql.append("  b.group_no in (select group_no from board where (upper(subject) like upper( '%"+ keyword1 +"%') "); 
				sql.append("                                                                      or upper(writer) like upper( '%"+ keyword1 +"%') ");
				sql.append("                                                                    or upper(content) like upper( '%"+ keyword1 +"%') ");
				sql.append("                                                                       or upper(email) like upper( '%"+ keyword1 +"%') ");  
				sql.append("                                                                     or upper(to_char(reg_date,'YYYY-MM-DD AM HH:MI:SS')) like upper( '%"+ keyword1 +"%')) ");  

			}
			else if (keyword2!=null && keyword2.length()>0) {
				sql.append("  b.group_no in (select group_no from board where");
			}

			if( keyword1!=null && keyword1.length()>0 &&  keyword2!=null && keyword2.length()>0) {
				sql.append(orAnd);
			}

			if(keyword2!=null && keyword2.length()>0) {

				sql.append("   (upper(subject) like upper( '%"+ keyword2 +"%') "); 
				sql.append("                                                                      or upper(writer) like upper( '%"+ keyword2 +"%') ");
				sql.append("                                                                    or upper(content) like upper( '%"+ keyword2 +"%') ");
				sql.append("                                                                       or upper(email) like upper( '%"+ keyword2 +"%') ");  
				sql.append("                                                                     or upper(to_char(reg_date,'YYYY-MM-DD AM HH:MI:SS')) like upper( '%"+ keyword2 +"%'))");

			}
			if( (keyword1!=null && keyword1.length()>0) || (keyword2!=null && keyword2.length()>0)) {
				sql.append("))");
			}

			sql.append(" order by group_no desc, print_no asc ");
			System.out.println(sql);
			pstm = conn.prepareStatement(sql.toString());
			//keyword="\'%" + keyword +"%\'";
			System.out.println(keyword1);

			//if(keyword!=null && keyword.length()>0 ) {
			//	pstm.setString(1,keyword);
			//	pstm.setString(2,keyword);
			//	pstm.setString(3,keyword);
			//	pstm.setString(4,keyword);
			//	pstm.setString(5,keyword);
			//	}
			// PreparedStatement 객체 소유의 select 문을 실행하여
			// 게시판 글 목록을 얻어와서 resultSet 객체 생성하고
			// ResultSet 객체에 select 결과물을 저장하고 ResultSet 객체의 메위주를 리턴하기
			rs=pstm.executeQuery();
			//resultSet 객체에 저장된 n행 m열의 데이터 중에
			// 한행의 데이터를 저장할 hashmap 객체의 메위주가 저장될 변수 boardMap 선언하기
			Map<String,String> boardMap = null;
			// 다량의 HashMap 객체가 저장될 ArrayList 객체를 생성하기
			List<Map<String,String>> boardList = new ArrayList<Map<String,String>>();

			System.out.println("여기됨");
			//resultset 객체에서 게시판 글목록을 꺼내어
			//
			while (rs.next()) {
				boardMap = new HashMap<String,String>();
				boardMap.put("b_no",rs.getString("b_no"));
				boardMap.put("subject",rs.getString("subject"));
				boardMap.put("writer",rs.getString("writer"));
				boardMap.put("reg_date",rs.getString("reg_date"));
				boardMap.put("readcount",rs.getString("readcount"));
				boardMap.put("content",rs.getString("content"));
				boardMap.put("pwd",rs.getString("pwd"));
				boardMap.put("email",rs.getString("email"));
				boardMap.put("group_no",rs.getString("group_no"));
				boardMap.put("print_no",rs.getString("print_no"));
				boardMap.put("print_level",rs.getString("print_level"));
				boardList.add(boardMap);
			}
			return boardList;
		}catch(Exception e) {
			System.out.println("BoardDAO getBoardList2 에서 에러발생");
			return null;
		}finally {
			if(rs != null) {
				try {
					rs.close();
				}catch(SQLException sqle) {

				}
			}
			if(pstm != null) {
				try {
					pstm.close();
				}catch(SQLException sqle) {

				}
			}
			if(conn != null) {
				try {
					conn.close();
				}catch(SQLException sqle) {

				}
			}
		}
	}
	public List<Map<String,String>> getBoardList() throws Exception{
		// DB 연동에 사용되는 Connection 객체, PreparedStatement 객체, Resultset 객체의 메위주를 저장할 변수 선언

		// connection 객체 (db 연결하고 상태관리)
		// preparedstatement 기능 => sql 구문을 관리하고 sql을 실행하는 객체
		// resultset 객체 기능 => select sql 구문의 실행 결과값을 소유하고 있는 객체

		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs =null;

		// select SQL 구문 문자열을 저장할 StringBuffer 객체 생성하기
		StringBuffer sql = new StringBuffer();
		try {
			conn = getConnection();
			sql.append("select ");
			sql.append(" b_no, ");
			sql.append(" subject, ");
			sql.append(" writer, ");
			sql.append(" to_char(reg_date,'YYYY-MM-DD AM HH:MI:SS') reg_date, ");
			sql.append(" readcount, ");
			sql.append(" content, ");
			sql.append(" pwd, ");
			sql.append(" email, ");
			sql.append(" group_no, ");
			sql.append(" print_no, ");
			sql.append(" print_level ");
			sql.append(" from board b ");
			sql.append(" where 1=1 ");
			sql.append(" order by group_no desc, print_no asc ");
			pstm = conn.prepareStatement(sql.toString());
			// PreparedStatement 객체 소유의 select 문을 실행하여
			// 게시판 글 목록을 얻어와서 resultSet 객체 생성하고
			// ResultSet 객체에 select 결과물을 저장하고 ResultSet 객체의 메위주를 리턴하기
			rs=pstm.executeQuery();
			//resultSet 객체에 저장된 n행 m열의 데이터 중에
			// 한행의 데이터를 저장할 hashmap 객체의 메위주가 저장될 변수 boardMap 선언하기
			Map<String,String> boardMap = null;
			// 다량의 HashMap 객체가 저장될 ArrayList 객체를 생성하기
			List<Map<String,String>> boardList = new ArrayList<Map<String,String>>();


			//resultset 객체에서 게시판 글목록을 꺼내어
			//
			while (rs.next()) {
				boardMap = new HashMap<String,String>();
				boardMap.put("b_no",rs.getString("b_no"));
				boardMap.put("subject",rs.getString("subject"));
				boardMap.put("writer",rs.getString("writer"));
				boardMap.put("reg_date",rs.getString("reg_date"));
				boardMap.put("readcount",rs.getString("readcount"));
				boardMap.put("content",rs.getString("content"));
				boardMap.put("pwd",rs.getString("pwd"));
				boardMap.put("email",rs.getString("email"));
				boardMap.put("group_no",rs.getString("group_no"));
				boardMap.put("print_no",rs.getString("print_no"));
				boardMap.put("print_level",rs.getString("print_level"));
				boardList.add(boardMap);
			}
			return boardList;
		}catch(Exception e) {
			System.out.println("에러발생");
			return null;
		}finally {
			if(rs != null) {
				try {
					rs.close();
				}catch(SQLException sqle) {

				}
			}
			if(pstm != null) {
				try {
					pstm.close();
				}catch(SQLException sqle) {

				}
			}
			if(conn != null) {
				try {
					conn.close();
				}catch(SQLException sqle) {

				}
			}
		}
	}
	public int insertBoard(BoardDTO board) throws Exception{
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs =null;
		StringBuffer sql = new StringBuffer();
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			// 만약 댓글일 경우 [같은 그룹 내에 후위 동료 댓글 출력 순서 수정] 하기
			if ( board.getB_no()>0 ) {
				// 수정 SQL 구문 문자열 저장하기
				String upSql = "update board set print_no = print_no + 1 where group_no =(select group_no from board where b_no=?)"
						+ "and print_no>(select print_no from board where b_no=?)";
				// [수정 SQL 구문]을 관리하는 PreparedStatement 객체 생성
				pstm = conn.prepareStatement( upSql );
				// 1,2 번째 물음표에 저우로서 board.getB_no( ) 의 리턴값을 대체하기
				pstm.setInt(1, board.getB_no() );
				pstm.setInt(2, board.getB_no() );
				// [수정 SQL 구문]을 실행하기
				pstm.executeUpdate();
			}
			sql.append( "insert into board (b_no,subject,writer,content,pwd,email,group_no,print_no,print_level) values(");
			sql.append("(select nvl(max(b_no),0)+1 from board)");
			sql.append(",?");
			sql.append(",?");
			sql.append(",?");
			sql.append(",?");
			sql.append(",?");
			if (board.getB_no()==0 ) {
				sql.append( ",(select nvl(max(b_no), 0)+1 from board)" );
				sql.append( ",0" );
				sql.append( ",0" );
			}else {
				sql.append( ",(select group_no from board where b_no="+board.getB_no()+")" );
				sql.append( ",(select print_no+1 from board where b_no="+board.getB_no()+")" );
				sql.append( ",(select print_level+1 from board where b_no="+board.getB_no()+")" );
			}
			sql.append(")");

			pstm = conn.prepareStatement(sql.toString());

			pstm.setString(1,board.getSubject());
			pstm.setString(2,board.getWriter());
			pstm.setString(3,board.getContent());
			pstm.setString(4,board.getPwd());
			pstm.setString(5,board.getEmail());

			int boardRegCnt = pstm.executeUpdate();
			System.out.println("확인해주셈"+boardRegCnt);
			conn.commit();
			return boardRegCnt;

		}catch(Exception e) {
			conn.rollback(); return -1;
		}
		finally {
			if(rs != null) {
				try {
					rs.close();
				}catch(SQLException sqle) {

				}
			}
			if(pstm != null) {
				try {
					pstm.close();
				}catch(SQLException sqle) {

				}
			}
			if(conn != null) {
				try {
					conn.close();
				}catch(SQLException sqle) {

				}
			}
		}
	}
	public BoardDTO getBoard(int b_no) throws Exception{
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs =null;
		StringBuffer sql = new StringBuffer();
		try {
			// Connection 객체 생성
			conn=getConnection();
			conn.setAutoCommit(false);



			pstm=conn.prepareStatement("select count(*) from board where b_no=?");
			pstm.setInt(1, b_no);
			//PreparedStatement 객체 소유의 select문을 실행하여
			//ResultSet 객체에 select 결과물을 저장하고 ResultSet 객체의 메위주를 리턴하기
			rs = pstm.executeQuery();
			// 게시판 글의 존재 개수를 저장할 변수 선언하기
			int boardCnt=0;
			// ResultSet 객체에서 게시판 글의 존재 개수를 꺼내기
			while(rs.next()) {
				boardCnt=rs.getInt(1);
			}
			if(boardCnt==0) {
				return null;
			}

			pstm.close();


			pstm=conn.prepareStatement("update board set readcount=readcount+1 where b_no=?");
			pstm.setInt(1, b_no);
			pstm.executeUpdate();

			sql.append("select ");
			sql.append(" b_no, ");
			sql.append(" subject, ");
			sql.append(" writer, ");
			sql.append(" to_char(reg_date,'YYYY-MM-DD AM HH:MI:SS') reg_date, ");
			sql.append(" readcount, ");
			sql.append(" content, ");
			sql.append(" pwd, ");
			sql.append(" email, ");
			sql.append(" group_no, ");
			sql.append(" print_no, ");
			sql.append(" print_level ");
			sql.append(" from board where b_no=" +b_no);

			pstm = conn.prepareStatement(sql.toString());
			rs = pstm.executeQuery();
			BoardDTO board = new BoardDTO();
			while(rs.next()) {
				board.setB_no(rs.getInt("b_no"));
				board.setSubject(rs.getString("subject"));
				board.setWriter(rs.getString("writer"));
				board.setReg_date(rs.getString("reg_date"));
				board.setReadCount(rs.getInt("readcount"));
				board.setContent(rs.getString("content"));
				board.setPwd(rs.getString("pwd"));
				board.setEmail(rs.getString("email"));
				board.setGroup_no(rs.getInt("group_no"));
				board.setPrint_no(rs.getInt("print_no"));
				board.setPrint_level(rs.getInt("print_level"));

			}

			conn.commit();
			return board;

		} catch (Exception e) {
			conn.rollback();
			return null;
		}
		finally {
			if(rs !=  null) {
				try {
					rs.close();
				}catch(SQLException sqle) {

				}
			}
			if(pstm != null) {
				try {
					pstm.close();
				}catch(SQLException sqle) {

				}
			}
			if(conn != null) {
				try {
					conn.close();
				}catch(SQLException sqle) {

				}
			}
		}
	}
	public int getSonBoardCnt(int b_no) throws Exception{
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs=null;
		StringBuffer sql = new StringBuffer();
		try {
			conn = getConnection();
			//StringBuffer 객체에 아들 댓글의 개수 검색 sql 구문 저장하기
			System.out.println("여긴될까?");
			sql.append(" select count(*) from board where ");
			sql.append(" group_no = (select group_no from board where b_no="+b_no+") ");
			sql.append(" and print_level = (select print_level+1 from board where b_no="+b_no+") ");
			sql.append(" and print_no = (select print_no+1 from board where b_no="+b_no+") ");
			// 아들 댓글의 개수 검색 sql 구문을 관리하는 PreraredStatement 객체 생성
			pstm = conn.prepareStatement(sql.toString());

			System.out.println("여긴될까?");
			// 아들 댓글 개수 검색 sql 구문을 실행하여 검색 결과물 가진 ResultSet 객체 생성
			rs = pstm.executeQuery();
			// ResultSet 객체에서 아들 댓글의 개수를 꺼내어 sonBoardCnt에 저장하기
			int sonBoardCnt = 0;

			while(rs.next()) {
				sonBoardCnt= rs.getInt(1);
			}

			// 검색 결과물을 가진 sonBoardCnt 변수 데이터를 리턴하기
			return sonBoardCnt;

		} catch (Exception e) {
			// 예외 발생 시 도스 창에 경고 메세지 출력하기
			System.out.println("getSonBoardCnt(~) 메소드에서 예외발생");// TODO: handle exception
			return -1;
		}
		finally {
			if(rs !=  null) {
				try {
					rs.close();
				}catch(SQLException sqle) {

				}
			}
			if(pstm != null) {
				try {
					pstm.close();
				}catch(SQLException sqle) {

				}
			}
			if(conn != null) {
				try {
					conn.close();
				}catch(SQLException sqle) {

				}
			}
		}
	}

	public int updateBoard(BoardDTO board) throws Exception{
		// DB 연동에 사용되는 Connection 객체, PreparedStatement 객체, Resultset 객체의 메위주를 저장할 변수 선언

		// connection 객체 (db 연결하고 상태관리)
		// preparedstatement 기능 => sql 구문을 관리하고 sql을 실행하는 객체
		// resultset 객체 기능 => select sql 구문의 실행 결과값을 소유하고 있는 객체

		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs =null;


		// select SQL 구문 문자열을 저장할 StringBuffer 객체 생성하기
		String sql = null;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);

			// 수정할 글의 개수 검색 sql 구문 저장하기
			sql = "select count(*) from board where b_no=?";

			// 검색 sql 구문을 관리하는 preparedstatement객체 생성
			pstm = conn.prepareStatement(sql);

			// 1번째 물음표에 정수로서 board.getB_no()의 리턴값 대체하기	
			pstm.setInt(1, board.getB_no());

			// PreparedStatement객체 소유의 select문을 실행하여
			// select 문 결과물을 얻어와서 resultset 객체 생성하고
			// resultset 객체에 select 결과물을 저장하고 resultset 객체의 메위주를 리턴하기
			rs= pstm.executeQuery();

			// 수정할 글의 개수를 저장할 변수 선언하기
			int boardCnt=0;

			// resultset 객체에서 수정할 글의 개수를 꺼내기
			while(rs.next()) {
				boardCnt = rs.getInt(1);
			}

			// 만약 수정할 글의 개수가 0이면, 즉 수정할 대상의 글이 없으면 -1 리턴하기
			if(boardCnt==0) {
				return -1;
			}
			pstm.close();


			sql = "update board set subject=?, writer=?, content=?, email=?, reg_date=sysdate where b_no=? and pwd=?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, board.getSubject());
			pstm.setString(2, board.getWriter());
			pstm.setString(3, board.getContent());
			pstm.setString(4, board.getEmail());
			pstm.setInt(5, board.getB_no());
			pstm.setString(6, board.getPwd());

			int boardUpCnt = pstm.executeUpdate();

			conn.commit();

			return boardUpCnt;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("updateBoard(~) 메소드에서 예외발생");
			conn.rollback();
			return -2;
		} finally {
			if(rs !=  null) {
				try {
					rs.close();
				}catch(SQLException sqle) {

				}
			}
			if(pstm != null) {
				try {
					pstm.close();
				}catch(SQLException sqle) {

				}
			}
			if(conn != null) {
				try {
					conn.close();
				}catch(SQLException sqle) {

				}
			}
		}
	}

	public int deleteBoard(BoardDTO board) throws Exception {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs =null;

		String sql = null;

		try {
			int b_no = board.getB_no();
			conn=getConnection();
			conn.setAutoCommit(false);
			sql="select count(*) from board where b_no=?";
			pstm = conn.prepareStatement(sql);

			pstm.setInt(1, board.getB_no());

			rs=pstm.executeQuery();

			int boardCnt=0;
			while(rs.next()) {
				boardCnt=rs.getInt(1);

			}
			if(boardCnt==0) {
				return -1;
			}
			int sonBoardCnt = getSonBoardCnt(b_no);
			if(sonBoardCnt>0) {
				return -2;
			}

			pstm = conn.prepareStatement("update board set print_no=print_no-1 " + " where group_no=(select group_no from board where b_no=? and pwd=?)" +
					" and print_no>(select print_no from board where b_no=? and pwd=?)");
			pstm.setInt(1, board.getB_no());
			pstm.setString(2, board.getPwd());
			pstm.setInt(3, board.getB_no());
			pstm.setString(4, board.getPwd());
			int upPrint_noCnt=pstm.executeUpdate();


			pstm=conn.prepareStatement("delete from board where b_no=? and pwd=?");
			pstm.setInt(1, board.getB_no());
			pstm.setString(2, board.getPwd());
			int delCnt=pstm.executeUpdate();
			if(delCnt==0) {
				return -3;
			}


			conn.commit();
			return 1;

		} catch (Exception e) {
			// TODO: handle exception
			conn.rollback();
			System.out.println("deleteBoard()에서 에러발생");
			return -4;
		} finally {
			if(rs !=  null) {
				try {
					rs.close();
				}catch(SQLException sqle) {

				}
			}
			if(pstm != null) {
				try {
					pstm.close();
				}catch(SQLException sqle) {

				}
			}
			if(conn != null) {
				try {
					conn.close();
				}catch(SQLException sqle) {

				}
			}
		}
	}

	public int deleteBoard2(BoardDTO board) throws Exception {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs =null;

		String sql = null;

		try {
			int b_no = board.getB_no();
			conn=getConnection();
			conn.setAutoCommit(false);
			sql="select count(*) from board where b_no=?";
			pstm = conn.prepareStatement(sql);

			pstm.setInt(1, board.getB_no());

			rs=pstm.executeQuery();
			// 게시판 글의 개수가 저장되는 변수 선언하기
			int boardCnt=0;
			// ResultSet 객체에서 게시판 글의 개수를 꺼내기
			while(rs.next()) {
				boardCnt=rs.getInt(1);

			}
			// 만약 게시판 글의 개수가 0이면 -1 리턴하기
			if(boardCnt==0) {
				return -1;
			}

			// 암호에 따른 행 개수 검색 sql 구문 저장하기
			sql = "select count(*) from board where b_no=? and pwd=?";
			// 검색 sql 구문을 관리하는 preparedStatement 객체 생성
			pstm = conn.prepareStatement(sql);
			//1번째 물음표에 정수로서 board.getB_no()의 리턴값을 대체하기
			//2번째 물음표에 문자로서 board.getPwd()의 리턴값을 대체하기
			pstm.setInt(1, board.getB_no());
			pstm.setString(2, board.getPwd());
			// PreparedStatement 객체 소유의 select문을 실행하여
			// select문 결과물을 저장한 ResultSet 객체의 메위주를 리턴하기
			rs=pstm.executeQuery();
			// 암호에 따른행 개수 저장할 변수 선언하기
			int pwdCnt=0;
			// ResultSet 객체에서 암호에 따른 행 개수를 꺼내기
			while(rs.next()) {
				pwdCnt=rs.getInt(1);
			}

			// 만약 암호에 따른 행 개수가 0개면 -2를 꺼내기
			if(pwdCnt==0) {
				return -2;
			}
			// 아들 글의 개수 구하기
			// 만약 아들글이 있다면 -3 리턴
			int sonBoardCnt = getSonBoardCnt(b_no);
			if(sonBoardCnt>0) {
				return -3;
			}
			// update sql 구문을 관리하는 PreparedStatement 객체 생성하기
			// 지워지는 게시판 글의 뒤 글들의 출력순서번호를 1씩 앞당기는 수정
			pstm = conn.prepareStatement("update board set print_no=print_no-1 " + " where group_no=(select group_no from board where b_no=?) " +
					" and print_no>(select print_no from board where b_no=?)");

			pstm.setInt(1, board.getB_no());
			pstm.setInt(2, board.getB_no());
			int upPrint_noCnt=pstm.executeUpdate();


			pstm=conn.prepareStatement("delete from board where b_no=? ");
			pstm.setInt(1, board.getB_no());
			// PreparedStatement 객체 소유의 삭제 sql문을 실행하고
			// 삭제 행의 적용 개수를 리턴하여 변수에 저장하기
			int delCnt=pstm.executeUpdate();
			// 트랜잭션 작업 인정하기
			conn.commit();
			// 1 리턴하기, 1을 리턴하면 모든 작업은 제대로 되었음을 말한다.
			return 1;

		} catch (Exception e) {
			// 트랜잭션 작업 취소하기
			conn.rollback();
			// 예외 발생 시 도스창에 경고 메세지를 출력하기
			System.out.println("deleteBoard()에서 에러발생");
			// -4 러틴하기, -4을 리턴하면 예외가 발생했음을 말한다.
			return -4;
		} finally {
			if(rs !=  null) {
				try {
					rs.close();
				}catch(SQLException sqle) {

				}
			}
			if(pstm != null) {
				try {
					pstm.close();
				}catch(SQLException sqle) {

				}
			}
			if(conn != null) {
				try {
					conn.close();
				}catch(SQLException sqle) {

				}
			}
		}
	}
}
