package help_lms;
/*
 * ----------------------------------------------------- *
 * 시간 함수를 time 관련 사용을 위한 import
 * java file, list 관련 사용을 위한 import 
 * 작성날찌: 2022.03.26
 * 작성자:  ersudo
 * ----------------------------------------------------- *
 * */ 
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Alert;



public class loginClass {
    public String student_ID; 
    public String student_name;                  
    public String student_pw ; 
    public WebElement LoginElement;

    public String mainPageWindow;
    
    public void TryLogin(ChromeDriver driver,String student_ID,String student_pw){
        try {
            driver.switchTo().frame(driver.findElement(By.cssSelector("frame")));
            //System.out.println("Getting the page source " + driver.getPageSource());
            LoginElement = driver.findElement(By.id("BeforeLoginForm"));

            LoginElement = LoginElement.findElement(By.xpath("//*[@id=\"BeforeLoginForm\"]/div[1]/div/input"));
            //System.out.println("UserId:"+LoginElement.toString());
            LoginElement.click();
            LoginElement.sendKeys(student_ID);

            //Thread.sleep(3000);

            LoginElement = driver.findElement(By.id("BeforeLoginForm"));
            LoginElement = LoginElement.findElement(By.xpath("//*[@id=\"BeforeLoginForm\"]/div[2]/div/input"));
            LoginElement.click();
            LoginElement.sendKeys(student_pw);
            this.student_pw = student_pw;
        
            //Thread.sleep(3000);

            LoginElement = driver.findElement(By.id("BeforeLoginForm"));
            LoginElement = LoginElement.findElement(By.xpath("//*[@id=\"BeforeLoginForm\"]/button"));
            LoginElement.click();

            Thread.sleep(2000);
            // 이 부분에서 다시 한번 메세지 확인 이후 아래 내용 진행이 필요하다.
            // 아니면 다시 출력창을 띄우는게 맞다.
            
            Alert alert = driver.switchTo().alert();
            alert.getText(); // 팝업 메세지 확인
            
            alert.accept();
            Thread.sleep(2000);

            mainPageWindow = driver.getWindowHandle();  // 메인 페이지 정보 저장 
			 
			 for (String windHandle: driver.getWindowHandles())  // 팝업창으로 이동   
			 {
				 if(!windHandle.equals(mainPageWindow))  // 메인 페이지와 일치하면 
				 {
					 driver.switchTo().window(windHandle); // 팝업창을 찾아서 switch 
				 }
			 }
            Thread.sleep(2000);

            driver.findElement(By.id("noMoreToday")).click();

            driver.switchTo().window(mainPageWindow); 	 // 기존 페이지로 다시 돌아가기
			System.out.println(mainPageWindow.toString());

            driver.switchTo().frame(driver.findElement(By.cssSelector("frame")));
            LoginElement = driver.findElement(By.xpath("//*[@id=\"wrap\"]/div/div[1]/div[1]/div[1]/div/form"));
            student_name = LoginElement.getText().toString();
            student_name = student_name.substring(0,4);
            // System.out.println("출력 : "+student_name);

            driver.switchTo().window(mainPageWindow);


        } catch (Exception e) {
    		e.printStackTrace();   		
        }
    }
    public String GetStudentName()
    {
    	return this.student_name;
    }
    public String GetStudentID()
    {
    	return this.student_ID;
    }
    public String GetStudentPW()
    {
    	return this.student_pw;
    }
}

