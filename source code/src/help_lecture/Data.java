package help_lms;

import net.bytebuddy.asm.Advice.This;

public class Data {
	public static String LectureFormData;
	public static String LectureStatusData;
	public static String LectureTitleData;
	public static String LectureNameData;
    public Data() {
    	
    }
    public Data(String LectureFormData, String LectureStatusData,String LectureTitleData,String LectureNameData){
    	this.LectureFormData = LectureFormData;
    	this.LectureStatusData = LectureStatusData;
    	this.LectureTitleData = LectureTitleData; 
    	this.LectureNameData = LectureNameData;
    }
    public String GetForm()
    {
    	return LectureFormData;
    }
    public void SetForm(String Form)
    {
    	this.LectureFormData = Form;
    }
    public String GetStatus()
    {
    	return LectureStatusData;
    }
    public void SetStatus(String Status)
    {
    	this.LectureStatusData = Status;
    }
    public String GetTitle()
    {
    	return LectureTitleData;
    }
    public void SetTitle(String Title)
    {
    	this.LectureTitleData = Title;
    }
    public String GetName()
    {
    	return LectureNameData;
    }
    public void SetName(String Name)
    {
    	this.LectureNameData = Name;
    }

}
