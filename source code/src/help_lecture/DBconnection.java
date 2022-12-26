package help_lms;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class DBconnection {
	public static String driver_name = "com.mysql.cj.jdbc.Driver";
	public static String user = "root"; // DB user id
	public static String password = "root"; // DB password
	public static Connection conn = null; // 연결을 위한 설정 
	public static PreparedStatement pstmt = null; // SQL 쿼리의 Statement, Prepared Statement
	public static ResultSet res = null; // ResultSet: SELECT문의 결과를 저장하는 객체 Java 에서 JDBC를 사용할때 필수다.
	public static List<String> ALLResultList;
	public static List<String> ResultList;
	public void CreateTable() {
		String url = "jdbc:mysql://localhost:3306"; //  localHost 를 통해 연결 구조는 jdbc:mysql://localhost/[db이름]?serverTimezone=UTC  
		Statement stmt = null; // SQL 쿼리의 Statement, Prepared Statement 

		StringBuilder first_sql = new StringBuilder();
		StringBuilder second_sql = new StringBuilder();
		StringBuilder third_sql = new StringBuilder();
		
		String drop_table = "DROP DATABASE IF EXISTS `StudentDB`;";
		String create_table = "CREATE DATABASE IF NOT EXISTS `StudentDB`;";
		String use_db = "USE `StudentDB`;";
		
		String create_student_info = first_sql.append( "CREATE TABLE `studentInfo` (")
				.append("`id` INT NOT NULL AUTO_INCREMENT,")
				//.append("`student_id` INT(25) NOT NULL UNIQUE KEY,")
				.append("`student_id` INT(25) NOT NULL,")
				.append("`name` VARCHAR(20) NOT NULL,")
				.append("PRIMARY KEY(`id`)")
				.append(") ENGINE = InnoDB")
				.append(" DEFAULT CHARACTER SET utf8")
				.toString();
		
		String create_lecture_name_list = second_sql.append("CREATE TABLE `LectureNameList` (")
				.append("`id` INT NOT NULL AUTO_INCREMENT,")
				.append("`LectureName` VARCHAR(20) NOT NULL,")
				.append("`LectureClass`  VARCHAR(20) NOT NULL,")
				.append("`LectureCode` VARCHAR(20) NOT NULL,")
				.append("PRIMARY KEY(`id`)")
				 // .append("FOREIGN KEY (`student_id`) REFERENCES `studentInfo` (`student_id`)")
				.append(") ENGINE = InnoDB;")
				.toString();
		
		String create_lecture_list = third_sql.append("CREATE TABLE `LectureList` (")
				.append("`id` INT NOT NULL AUTO_INCREMENT,")
				.append("`LectureForm` VARCHAR(13) NOT NULL,")
				.append("`LectureStatus` VARCHAR(10) NOT NULL,")  // .append("`LectureTime` VARCHAR(60) NOT NULL,")
				.append("`LectureDay`  VARCHAR(60) NOT NULL,")  // Date 타입은 안될듯 
				.append("`LectureTitle` VARCHAR(100) NOT NULL,")
				.append("`LectureName` VARCHAR(100) NOT NULL,")
				.append("PRIMARY KEY(`id`)")
				.append(") ENGINE = InnoDB;")
				.toString();

		try 
		{			
			Class.forName(driver_name); // 드리아버 정보 거져오기 
			System.out.println("driver 정상 동작"); // 연결 확인 
			conn = DriverManager.getConnection(url, user, password); // 연결을 위한 MySQL URL, ID, PW 정보 넘겨주기
			conn.setAutoCommit(false); // 자동으로 commit 하는 행위 중지
			stmt =  conn.createStatement();
			stmt.executeUpdate(drop_table);
			stmt.executeUpdate(create_table);
			stmt.executeUpdate(use_db);
			stmt.executeUpdate(create_student_info);
			stmt.executeUpdate(create_lecture_name_list);
			stmt.executeUpdate(create_lecture_list);
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
				if (stmt != null) stmt.close(); // query문 전송 오류 
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
	
	public void FindLecture(String find_lecture)
	{
		ALLResultList = new ArrayList<>();
		List<String> list = new ArrayList<>();
		List<String> LectureStatusList = new ArrayList<>();
		List<String> LectureFormList = new ArrayList<>();
		List<String> LectureTitleList = new ArrayList<>();
		List<String> LectureNameList = new ArrayList<>();
		
		String url = "jdbc:mysql://localhost/StudentDB?serverTimezone=UTC"; // localHost 를 통해 연결 구조는 jdbc:mysql://localhost/[db이름]?serverTimezone=UTC
		String sql_check = "select id, LectureStatus, LectureForm, LectureDay, LectureTitle, LectureName from lecturelist where LectureName like ";
		// String sql_Find; // 삽입하는 query문 실행 			
		// StringBuilder find_build = new StringBuilder();
		// sql_Find = find_build.append("SELECT *FROM  )
		try 
		{			
			Class.forName(driver_name); // 드리아버 정보 거져오기 
			System.out.println("driver 정상 동작"); // 연결 확인 
			conn = DriverManager.getConnection(url, user, password); // 연결을 위한 MySQL URL, ID, PW 정보 넘겨주기
			
			
			// 콘솔에서 입력시 
			
	    	// System.out.println("찾고자 하는 강의를 입력하세요: ");
	    	// Scanner scan = new Scanner(System.in);
	    	// String find_lecture = scan.nextLine();
	    	// System.out.println("입력값: "+ find_lecture);
	    	
	    	// System.out.println("조합 글자"+ sql_check+"'" +find_lecture+ "%'");
	    	 
	    	//pstmt = conn.prepareStatement(sql_check+"'" +find_lecture+ "%'" + "and LectureForm = '온라인'" + "and LectureStatus = 'empty'"); // 완성
			
	    	// 추가적인 테스트 #1
	    	pstmt = conn.prepareStatement(sql_check+"'" +find_lecture+ "%'" + "and LectureStatus = 'empty'");
	    	
			res = pstmt.executeQuery();
			System.out.println("-------------------------------------------------------");
			while(res.next())
			{
				String LectureForm = res.getString("LectureForm");
				String LectureStatus = res.getString("LectureStatus");
				String LectureDay = res.getString("LectureDay");
				String LectureTitle = res.getString("LectureTitle");
				String LectureName = res.getString("LectureName");

				LectureStatusList.add(LectureStatus);
				LectureFormList.add(LectureForm);
				LectureTitleList.add(LectureTitle);
				LectureNameList.add(LectureName);
				
				// Object[] data = {LectureForm, LectureStatus,LectureDay,LectureName};
				
//				list.add(LectureForm);
//				list.add(LectureStatus);
//				list.add(LectureDay);
//				list.add(LectureName);
//				
//				ALLResultList.addAll(list); 
				// 여기서 그냥 처리하는게 낫다. 
				// dbconnection에서 comboboxTest 호출해서 화면 출력하기 
				comboboxTest combobox = new comboboxTest();
				combobox.GetSize(LectureStatusList);
				try {
				Thread.sleep(1000);
				combobox.ShowTable(LectureFormList,LectureStatusList,LectureTitleList, LectureNameList);
				}
				catch (Exception e) 
				{
					System.out.println(e.getMessage());
				}				
				System.out.println(LectureForm + " " + LectureStatus + " " + LectureTitle + " " + LectureName + " ");
			}
			// System.out.println(ALLResultList);

			res.close();
		}catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}		
		finally {
				try {
					pstmt.close();
					conn.close();
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
		}
	}
	
	public void InsertStudentInformation(String student_name, String student_id)
	{
		String url = "jdbc:mysql://localhost/StudentDB?serverTimezone=UTC"; // localHost 를 통해 연결 구조는 jdbc:mysql://localhost/[db이름]?serverTimezone=UTC  
		String sql_insert = "INSERT into studentInfo (student_id, name) VALUES(?,?)"; // 삽입하는 query문 실행
		try 
		{				
			Class.forName(driver_name); // 드리아버 정보 거져오기 
			System.out.println("driver 정상 동작"); // 연결 확인 
			conn = DriverManager.getConnection(url, user, password); // 연결을 위한 MySQL URL, ID, PW 정보 넘겨주기
			conn.setAutoCommit(false); // 자동으로 commit 하는 행위 중지
			System.out.println("연결 확인"); // 연결 확인
			pstmt = conn.prepareStatement(sql_insert); //  삽입하는 SQL 문장 선택
			res = pstmt.executeQuery("SELECT * from studentInfo"); // 데이터 베이스 확인
			if (res.next() == false){  // 비어있는지 확인
				pstmt.setString(1, student_id); // 아이디 (학번)
				pstmt.setString(2, student_name); // 이름  
				pstmt.executeUpdate(); // update 실행 
			    pstmt.clearParameters(); // PreparedStatement 의 SQL 문 삭제
			    conn.commit(); // 현재까지 저장된 내용들 commit
			}
			else {
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
	
	public void InsertlectureNameList(List<String> LectureName, List<String> LectureClass, List<String> LectureCode)
	{
		String url = "jdbc:mysql://localhost/StudentDB?serverTimezone=UTC"; // localHost 를 통해 연결 구조는 jdbc:mysql://localhost/[db이름]?serverTimezone=UTC  
		String sql_insert = "INSERT into LectureNameList (LectureName, LectureClass, LectureCode) VALUES(?,?,?)"; // 삽입하는 query문 실행
		
		try 
		{				
			Class.forName(driver_name); // 드리아버 정보 거져오기 
			System.out.println("driver 정상 동작"); // 연결 확인 
			conn = DriverManager.getConnection(url, user, password); // 연결을 위한 MySQL URL, ID, PW 정보 넘겨주기
			conn.setAutoCommit(false); // 자동으로 commit 하는 행위 중지
			System.out.println("연결 확인"); // 연결 확인
			pstmt = conn.prepareStatement(sql_insert); //  삽입하는 SQL 문장 선택
			res = pstmt.executeQuery("SELECT * from LectureNameList"); // 데이터 베이스 확인
			if (res.next() == false){  // 비어있는지 확인
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
			else {
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
	public void InsertLectureInformationList(List<String> LectureForm, List<String> LectureDay, List<String> LectureStatus,List<String> LectureTitle, List<String> LectureName,int number)
	{
		String url = "jdbc:mysql://localhost/StudentDB?serverTimezone=UTC"; // localHost 를 통해 연결 구조는 jdbc:mysql://localhost/[db이름]?serverTimezone=UTC
		String sql_insert = "INSERT into LectureList (LectureForm, LectureDay, LectureStatus, LectureTitle, LectureName) VALUES(?,?,?,?,?)"; // 삽입하는 query문 실행
		String current_class_name = LectureName.get(number);
		
		try 
		{				
			Class.forName(driver_name); // 드리아버 정보 거져오기 
			System.out.println("driver 정상 동작"); // 연결 확인 
			conn = DriverManager.getConnection(url, user, password); // 연결을 위한 MySQL URL, ID, PW 정보 넘겨주기
			conn.setAutoCommit(false); // 자동으로 commit 하는 행위 중지
			System.out.println("연결 확인"); // 연결 확인
			pstmt = conn.prepareStatement(sql_insert); //  삽입하는 SQL 문장 선택			
			// res = pstmt.executeQuery("SELECT * from LectureList"); // 데이터 베이스 확인
			for(int i=0;i<LectureForm.size();i++) 
			{
					pstmt.setString(1, LectureForm.get(i)); // 과목 형태 - 오프라인 / 온라인  
				    pstmt.setString(2, LectureDay.get(i)); // 과목 날짜 
				    pstmt.setString(3, LectureStatus.get(i)); //
				    // pstmt.setString(4, LectureTime.get(i)); 시간에 대한 변동이 많아서 제외 
				    pstmt.setString(4, LectureTitle.get(i));
				    pstmt.setString(5, current_class_name);
				    pstmt.executeUpdate(); // update 실행 
				    pstmt.clearParameters(); // PreparedStatement 의 SQL 문 삭제
			}
			conn.commit(); // 현재까지 저장된 내용들 commit
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
	
	public List<String> GetList()
	{
		return this.ResultList;
	}
	
	public void GetConnection(List<String> LectureName, List<String> LectureClass, List<String> LectureCode){ 
				Connection conn = null; // 연결을 위한 설정 
				PreparedStatement pstmt = null; // SQL 쿼리의 Statement, Prepared Statement
				ResultSet res = null; // ResultSet: SELECT문의 결과를 저장하는 객체 Java 에서 JDBC를 사용할때 필수다.
				String url = "jdbc:mysql://localhost/lecturedb?serverTimezone=UTC"; // localHost 를 통해 연결 구조는 jdbc:mysql://localhost/[db이름]?serverTimezone=UTC  
				String user = "root"; // DB user id
				String password = "root"; // DB password
				String sql_insert = "INSERT into lecture (Name, LectureClass, LectureCode) VALUES(?, ?, ?)"; // 삽입하는 query문 실행 
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