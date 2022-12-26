package help_lms;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;


public class lecturelist {
    public WebElement LectureElement;
    protected List<String> LectureName = new ArrayList<String>(); // 과목명
	protected List<String> LectureClass = new ArrayList<String>(); // 과목 분반  
	protected List<String> LectureCode = new ArrayList<String>(); // 과목 코드
    
    public void getList(ChromeDriver driver){
        driver.switchTo().frame(driver.findElement(By.cssSelector("frame")));
        LectureElement = driver.findElement(By.className("my-class"));

        List<WebElement> allElements = driver.findElements(By.xpath("//*[@id=\"mCSB_1_container\"]/ul/li"));
        for (WebElement element: allElements) {    
			String s[] = element.getText().toString().split("/");
			LectureName.add(s[0]);   	// 과목명
			LectureClass.add(s[1]);     // 과목 분반 
			LectureCode.add(s[2]);      // 과목 코드
		}
        for(String s: LectureName) {
			System.out.println(s);
        }
        // To do : send or Do DB_Connection in here 
    }
    public int GetLectureSize()
    {
    	return LectureName.size();
    }
    public List<String> GetLecutureNameList()
    {
    	return LectureName;
    }
    public List<String> GetLectureClassList()
    {
    	return LectureClass;
    }
    public List<String> GetLectureCodeList()
    {
    	return LectureCode;
    }
}
