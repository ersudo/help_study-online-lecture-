package help_lms;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.mysql.cj.x.protobuf.MysqlxNotice.Frame;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import com.mysql.cj.x.protobuf.MysqlxNotice.Frame;

public class PageManagement{
	protected List<String> list = new ArrayList<String>();       // 강의 목록
	// 필요한 정보들: 구분, 차사명, 학습 현황, 학습시간/ 수업일자 , 출결 정보(기간내), 출결 정보(기간후) 
	
	protected List<String> LectureForm = new ArrayList<String>(); // 오프라인 온라인 구분 
	protected List<String> LectureDay = new ArrayList<String>(); // 날짜 
	protected List<String> LectureStatus = new ArrayList<String>(); // 처리 상태
	protected List<String> LectureTime = new ArrayList<String>(); // 시청 시간
	protected List<String> LectureTitle = new ArrayList<String>(); // 차수 제목 
	
	protected String full_gif = "/lmsdata/img_common/icon/set1/icon_full_print.gif"; // 시청 완료
	protected String half_gif = "/lmsdata/img_common/icon/set1/icon_half_print.gif"; // 시청 중
	protected String empty_gif = "/lmsdata/img_common/icon/set1/icon_empty_print.gif"; // 시청x
	
	

	protected PageManagement()
	{
	
	}
 
	public void removeAll(List<String> list, String element) {
	    while (list.contains(element)) {
	        list.remove(element);
	    }
	}
	
	public void MovetoLecture(ChromeDriver driver)
	{
	
		System.out.println(driver.toString());
		try
		{
			driver.findElement(By.xpath("//*[@id=\"mCSB_1_container\"]/ul/li[1]/a/span")).click();
			Thread.sleep(2000);
		}
		catch (Exception e) 
		{ 
			e.printStackTrace();
		}
	
	}  
	public void ClickLectureMenu(ChromeDriver driver)
	{
		try
		{
		
		driver.findElement(By.xpath("//*[@id=\"bodyContent\"]/form/div/div[2]/ul/li[2]/h3")).click();
		Thread.sleep(2000);
		}
		catch (Exception e) 
		{	
			e.printStackTrace();
		}
	}
	public static <T> List<List<T>> CH_(List<T> list, final int L) { 
	    List<List<T>> parts = new ArrayList<List<T>>();
	    final int N = list.size();
	    for (int i = 0; i < N; i += L) {
	        parts.add(new ArrayList<T>(
	            list.subList(i, Math.min(N, i + L)))
	        );
	    }
	    return parts;
	}
	
	
	public void GetLectureInformation(ChromeDriver driver)
	{
		try
		{
			System.out.println("current driver fame: "+ driver.toString());
			Thread.sleep(2000);
			String professorName = driver.findElement(By.xpath("//*[@id=\"headerContent\"]/div/ul[2]/li[2]/table/tbody/tr/td[1]")).getText().toString();
			System.out.println("current professorName: "+ professorName+"\n");
			Thread.sleep(2000);


			for(WebElement element : driver.findElements(By.xpath("//td")))
			{
				if(element.findElements(By.xpath(".//img")).size() > 0)  
				{
					String src = element.findElement(By.xpath(".//img")).getAttribute("src");
					String temp =" ";
					if(src.contains(full_gif))
					{
						temp = "full";
						list.add(temp);
					}
					else if(src.contains(half_gif))
					{
						temp = "half";
						list.add(temp);
					}
					else if(src.contains(empty_gif))
					{
						temp = "empty";
						list.add(temp);
					}
					else 
					{
						String tr = "img is Blank";
						list.add(tr);	
					}
				}	
				else 
				{
					String temp = element.getText().toString(); // s n 
					if(temp.contains("학점") || temp.contains("ac.kr") || temp.contains("이메일") || temp.contains("대표교수")|| temp.contains(professorName)
					   || temp.contains("전공선택") || temp.contains("조회하기") || temp.contains("학습 하기"))
							continue;
					else 
						list.add(element.getText());
				}

			}
			
			for(String element : list)
			{
				if(element.equals("온라인") || element.equals("오프라인"))
					LectureForm.add(element); //  구분 
				else if(element.contains("full") || element.contains("half")|| element.contains("empty") || element.equals("-"))
					LectureStatus.add(element); //  학습 현황
				else if(element.contains("TOTAL: " + " 분" + " 초") || element.contains("분") && element.contains("초") || element.contains("미처리"))
					LectureTime.add(element); //  출결 정보 (기간 내)
				else if(element.contains("2022") || ((element.contains("분") && !element.contains("초")) && !element.contains("TOTAL: ")
						&& !element.contains("분할") && !element.contains("분반") && !element.contains("분석")))
					LectureDay.add(element); //   학습시간 / 수업일자
				else if(element.length()>= 2  && !element.contains("10") && !element.contains("TOTAL: ") && !element.contains("2022") && !element.contains(" "+"초")
						&& !element.equals("교안") && !element.equals("미처리") || element.contains("pdf") || element.contains("분할") || element.contains("분반") )
					LectureTitle.add(element); //  차수 제목
			}
//			for(String InList: LectureTime)
//			{
//				System.out.println(InList);
//			}
			Thread.sleep(2000);  
			
			// 내부에서 db connection 이 나을까? 
			
		}
		catch (Exception e) 
		{	
			e.printStackTrace();
		}	
	}
	
	public List<String> GetLectureFormList()
	{
		return LectureForm;
	}
	public List<String> GetLectureStatusList()
	{
		return LectureStatus;
	}
	public List<String> GetLectureTimeList()
	{
		return LectureTime;
	}
	public List<String> GetLectureDayList()
	{
		return LectureDay;
	}
	public List<String> GetLectureTitleList()
	{
		return LectureTitle;
	}
	
	public void ClearList()
	{
		list.clear();
		LectureForm.clear();
		LectureDay.clear(); 
		LectureStatus.clear(); 
		LectureTime.clear();
		LectureTitle.clear();
	}
	public void MoveToAnotherLecture(ChromeDriver driver, int i)  
	{
		try
		{ 			 			
				Thread.sleep(2000);  
				driver.findElement(By.xpath("//*[@id=\"header\"]/div/ul/li[1]/div/fieldset/div/a/div/b")).click(); 
				String MenuSelect = "//*[@id=\"header\"]/div/ul/li[1]/div/fieldset/div/div/ul/li["+i+"]";   
				driver.findElement(By.xpath(MenuSelect)).click(); 
				Thread.sleep(2000);  
		}
		catch (Exception e) 
		{	
			e.printStackTrace();
		}	
	}
	
}


/*
 * 공지사항 및 파일 다운로드 기능 구현 예정
 * GUI도 단순화 해서 구현  
 * 작성자: ersudo
 * 작성날짜: 2022.04.22
 * */
