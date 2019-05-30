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
			conn=getConnection();
			conn.setAutoCommit(false);
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
}
