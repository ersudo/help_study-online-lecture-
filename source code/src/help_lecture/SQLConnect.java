package help_lms;
/*
 * 설명: MySQL 과 연동 구현 
 * 필요한 사항: 필요한 파일들은 현재 다운로드 되어 있으며, MySQL에서 기본적인 baseTable 구성 필요
 * 작성일: 2022.04.01
 * 작성자: ersudo
 * 
 * */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SQLConnect {
		public void GetConnection(List<String> LectureName, List<String> LectureClass, List<String> LectureCode){ 
			Connection conn = null; // 연결을 위한 설정 
			PreparedStatement pstmt = null; // SQL 쿼리의 Statement, Prepared Statement
			ResultSet res = null; // ResultSet: SELECT문의 결과를 저장하는 객체 Java 에서 JDBC를 사용할때 필수다.
			String url = "jdbc:mysql://localhost/lecturedb?serverTimezone=UTC"; // localHost 를 통해 연결 구조는 jdbc:mysql://localhost/[db이름]?serverTimezone=UTC  
			String user = "root"; // DB user id
			String password = "root"; // DB password
			String sql_insert = "INSERT into lecture (Name,ClassCode, LectureCode) VALUES(?, ?, ?)"; // 삽입하는 query문 실행 
			try 
			{				
				Class.forName("com.mysql.cj.jdbc.Driver"); // 드리아버 정보 거져오기 
				System.out.println("driver 정상 동작"); // 연결 확인 
				conn = DriverManager.getConnection(url, user, password); // 연결을 위한 MySQL URL, ID, PW 정보 넘겨주기
				conn.setAutoCommit(false); // 자동으로 commit 하는 행위 중지
				System.out.println("연결 확인"); // 연결 확인
				pstmt = conn.prepareStatement(sql_insert); //  삽입하는 SQL 문장 선택
				ResultSet rs = pstmt.executeQuery("SELECT * from lecture"); // 데이터 베이스 확인
				if (rs.next() == false) {  // 비어있는지 확인
					for(int i=0;i<LectureName.size();i++) 
					{	
							  	pstmt.setString(1, LectureName.get(i)); // 과목 이름 
							    pstmt.setString(2, LectureClass.get(i)); // 과목 분반
							    pstmt.setString(3, LectureCode.get(i));	 // 과목 코드  
							    pstmt.executeUpdate(); // update 실행 
							    pstmt.clearParameters(); // PreparedStatement 의 SQL 문 삭제 
					}
					conn.commit(); // 현재까지 저장된 내용들 commit					      
				}
				else 
				{
					System.out.println("ResultSet is exist");
				}
			} 
			catch(Exception e) // 예외 처리
			{
				e.printStackTrace();
			} 
			finally
			{
				try  // 에외 처리
				{ 
					if (res !=null) res.close(); // select 문 오류 
					if (pstmt != null) pstmt.close(); // query문 전송 오류 
					if (conn != null) conn.close(); // 연결 오류
					System.out.println("연결 종료"); 
				}  
				catch(SQLException ex){  // SQL 예외 처리 
		            System.out.println("SQLException" + ex);
		            ex.printStackTrace();
		        }
				catch(Exception ex){  // 그외 예외 처리 한번 더 
		            System.out.println("Exception:" + ex);
		            ex.printStackTrace();
		        }
			}
		}
}
