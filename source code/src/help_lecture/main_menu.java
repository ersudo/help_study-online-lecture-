package help_lms;

import org.openqa.selenium.chrome.ChromeDriver;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.*;
import javax.swing.*;

public class main_menu {
	
    protected ChromeDriver driver;
    protected WebElement element;
    protected static int size = 0;
    
    public static String WEB_DRIVER_ID = "webdriver.chrome.driver";
    public static String UserDir = System.getProperty("user.dir");
    public static String WEB_DRIVER_PATH = UserDir + "\\browserdriver\\chromedriver.exe";
    public static String url = "https://elearning.kumoh.ac.kr/"; 
    public static String student_name = " ";
    public static String student_id = " ";
    public static String student_pw = " ";
    public static boolean check =false;
    // 과목 목록 정보  
    public static List<String> LectureName = new ArrayList<String>(); // 과목명
    public static List<String> LectureClass = new ArrayList<String>(); // 과목 분반  
    public static List<String> LectureCode = new ArrayList<String>(); // 과목 코드
    
    // 과목 상세 정보 
    public static List<String> LectureForm = new ArrayList<String>(); // 오프라인 온라인 구분 
    public static List<String> LectureDay = new ArrayList<String>(); // 날짜 
    public static List<String> LectureStatus = new ArrayList<String>(); // 처리 상태
    public static List<String> LectureTime = new ArrayList<String>(); // 시청 시간
    public static List<String> LectureTitle = new ArrayList<String>(); // 차수 제목
    
    public static void main(String[] args) {    
    	loginTest login_form = new loginTest();
		gifTest gif_form = new gifTest();
		comboboxTest box_form = new comboboxTest();
		
    	try {
    		Thread.sleep(15000);
    		check = login_form.Get_login_success(); // 아이디 입력 받은 뒤 
    		if(check == true)
    		{
    			login_form.turnoff();
    			gif_form.SetgifForm(); // 로딩 화면으로 전환 -> 아직 진짜 로그인 된건 아님
    		}
    		student_id = login_form.GetStudentID();
        	student_pw = login_form.GetStudentPW();
        	System.out.println("logined");
        	// System.out.println("user:" + student_id + "pw" + student_pw);	
    	}
     	catch (Exception e) 
   		{	
   			e.printStackTrace();
   		}
    	
    	DBconnection dbconnection = new DBconnection();// db 연결
        dbconnection.CreateTable(); // 테이블 만들기 
    	
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
        
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:100.0) Gecko/20100101 Firefox/100.0");
        options.addArguments("--headless");
        // options.addArguments("--start-maximized");  -- 개발시에 사용 
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1400,800");  
	    options.addArguments("--disable-popup-blocking");    
	    options.addArguments("--disable-default-apps");

        ChromeDriver driver = new ChromeDriver(options);
        driver.get(url);
        try {
        	loginClass loginclass = new loginClass();
            loginclass.TryLogin(driver,student_id,student_pw);
            Thread.sleep(15000);
            
            student_name = loginclass.GetStudentName();
            // student_id = loginclass.GetStudentID(); -- 필요 없어짐
            
            System.out.println(student_name+ " "+ student_id);   
            dbconnection.InsertStudentInformation(student_name, student_id);
        }
        catch (Exception e) 
   		{	
   			e.printStackTrace();
   		}
        // db 연결 이후     
        // dbconnection.InsertStudentInformation(student_name, student_id);
        
        lecturelist lecturelist = new lecturelist();
        lecturelist.getList(driver);
        size = lecturelist.GetLectureSize(); 
        
        LectureName = lecturelist.GetLecutureNameList();
        LectureClass = lecturelist.GetLectureClassList();
        LectureCode = lecturelist.GetLectureCodeList();
        
        dbconnection.InsertlectureNameList(LectureName, LectureClass, LectureCode);
        box_form.GetList(LectureName); 
        // box_form.SetcomboboxForm(); 
        
        // 테스트 완료 - 삽입 과정 정상 진행 
        
        PageManagement management = new PageManagement();
        management.MovetoLecture(driver);
        try 
        {
               	int i = 2;
               	do {
               		management.ClickLectureMenu(driver);
                   	Thread.sleep(2000);
                   	management.GetLectureInformation(driver);
                   	
                   	LectureForm = management.GetLectureFormList();
           	    	LectureDay = management.GetLectureDayList();
           	    	LectureStatus = management.GetLectureStatusList();
           	    	LectureTime = management.GetLectureTimeList();
           	    	LectureTitle = management.GetLectureTitleList();
           	    	// db 연결 밑 삽입 
           	    	dbconnection.InsertLectureInformationList(LectureForm, LectureDay, LectureStatus, LectureTitle, LectureName,i-2);
                   	Thread.sleep(2000);
                   	
                   	management.ClearList();
                   	Thread.sleep(2000);
                   	i++;
                   	if(i == size + 2)
                   		break;
                   	
                       management.MoveToAnotherLecture(driver,i);
                   	Thread.sleep(2000);
               	}while(i<= size+1);
             Thread.sleep(3000);      
        }
           	catch (Exception e) 
       		{	
       			e.printStackTrace();
       		}
        gif_form.turnOFF();
        box_form.SetcomboboxForm(); 
        
    }
}
/*
이동 과정에서의 오류 수정 -- 화면 전환 
*/
