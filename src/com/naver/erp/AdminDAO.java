package com.naver.erp;

import java.sql.*;
import javax.servlet.http.HttpSession;
public class AdminDAO {
	private static AdminDAO instance = new AdminDAO();
	private static Connection connection = null;

	public static AdminDAO getInstance() {
		return instance;
	}

	private Connection getConnection( ) throws Exception{
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:XE";
		String id = "system";
		String pw = "123";

		Class.forName("oracle.jdbc.driver.OracleDriver");

		return DriverManager.getConnection(url,id,pw);
	}
	// 로그인 한 아이디 암호의 존재 개수 리턴하는 메소드 선언
	public int getAdminCnt(String admin_id, String pwd) throws Exception{
		// db연동에 사용되는 connection 객체, preparedStatement객체, resultset 객체의
		// 메위주를 저장할 변수 선언

		// connection 객체 (db 연결하고 상태관리)
		// preparedstatement 기능 => sql 구문을 관리하고 sql을 실행하는 객체
		// resultset 객체 기능 => select sql 구문의 실행 결과값을 소유하고 있는 객체

		Connection conn = null; PreparedStatement pstm = null; ResultSet rs = null;

		// sql 구문 문자열을 저장할 수 있는 변수 sql 선언
		String sql = null;

		//예외처리 구문 try catch finally 구문 선언
		try {

			// 동료 메소드 getConnection()을 호출하여 Connection 객체를 생성하고
			// Connection 객체의 메위주를 얻는다
			conn = getConnection();

			// 아이디 암호의 존재개수를 검색하는 select 구문의 문자열을 sql 변수에 저장
			sql = "select count(*) from admin where admin_id = ? and pwd= ?";

			// Connection 객체가 소유한 prepareStatement 메소드를 호출하여
			// PreparedStatement 객체를 생성하기
			pstm = conn.prepareStatement(sql);
			
			// PreparedStatement 객체가 소유한 sql 구문의 1번째 물음표에 삽입할 문자 설정
			// 만약 문자가 삽입될 경우 자동으로 '가 양쪽에 붙어 삽입된다.
			// <참고> pstm.setInt(물음표순서번호,정수)라면 '가 없이 정수가 삽입된다.
			// <참고> pstm.setDouble(물음표순서번호,실수)라면 '가 없이 실수가 삽입된다.
			pstm.setString(1, admin_id);
			// PreparedStatement 객체가 소유한 sql 구문의 1번째 물음표에 삽입할 문자 설정
			// 만약 문자가 삽입될 경우 자동으로 '가 양쪽에 붙어 삽입된다.
			// <참고> pstm.setInt(물음표순서번호,정수)라면 '가 없이 정수가 삽입된다.
			// <참고> pstm.setDouble(물음표순서번호,실수)라면 '가 없이 실수가 삽입된다.
			pstm.setString(2, pwd);
			
			// PreparedStatement 객체 소유의 [select문] 실행하여
			// 아이디 암호 존재개수를 소유한 Resultset 객체 생성하기
			// resultset 객체에 select 결과물을 저장하고 resultset 객체의 메위주를 리턴하기
			rs=pstm.executeQuery();
			
			// 아이디 암호 존재 개수를 저장할 admin_idCnt 변수를 선언하기
			int admin_idCnt=0;
			
			// ResultSet 객체에서 아이디 암호 존재개수를 꺼내어 admin_idcnt 변수에 저장하기
			while(rs.next()) {
				// rs를 한칸씩 내리면서 값이 있는동안.
				// 값이 있으면 1열의 값을 int로 받는다 2를 넣으면 2열
				admin_idCnt=rs.getInt(1);
			}
			// 아이디 암호존재개수 리턴하기
			return admin_idCnt;
		}
		catch(Exception e) {
			// 예외발생시 도스창에 경고 메세지를 출력하기
			System.out.println("getAdminCnt(~) 메소드에서 에러발생");
			return -1;
		}finally {
			// 예외 발생 여부에 상관없이 반드시 실행할 구문 설정하기
			
			// resultSet 객체 닫기
			if(rs != null) { 
				try {
					rs.close();
				}catch(SQLException sqle) {

				}
			}
			// PreparedStatement 객체 닫기
			if(pstm != null) { 
				try {
					pstm.close();
				}catch(SQLException sqle) {

				}
			}
			// Connection 객체 닫기
			if(conn != null) { 
				try {
					conn.close();
				}catch(SQLException sqle) {

				}
			}
		}
	}

}
