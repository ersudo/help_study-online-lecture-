package help_lms;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.*;

public class loginTest 
{
	public static String ID=" ";
	public static String Password=" ";
	public static boolean login_success ;
	public static JPanel panel = new JPanel();
	public static JFrame frame = new JFrame();
    public loginTest()
    {
    	
    	frame.setSize(350,200);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.add(panel);
    	
    	panel.setLayout(null);
    	
    	JLabel userlabel = new JLabel("user");
    	userlabel.setBounds(10, 20, 80, 25); // x y width height
    	panel.add(userlabel);
    	
    	JTextField userText = new JTextField(20);
    	userText.setBounds(100, 20, 165, 25);
    	panel.add(userText);
    	
    	JLabel passwordlabel = new JLabel("password");
    	passwordlabel.setBounds(10, 50, 80, 25);
    	panel.add(passwordlabel);

    	JTextField passwordText = new JPasswordField();
    	passwordText.setBounds(100, 50, 165, 25);
    	panel.add(passwordText);

    	JButton button = new JButton("login");
    	button.setBounds(140,80,80,25);
   
    	button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ID = userText.getText();
				Password = passwordText.getText();
				login_success = true;
				
			}
    	});
    	panel.add(button);
    	
    	JLabel success = new JLabel("");
    	success.setBounds(10, 100, 300,25);
    	panel.add(success);    	
    	frame.setVisible(true);   	
    }
    public void SetName(String ID)
    {
    	ID = ID;
    }
    public void SetPW(String PassWord)
    {
    	Password = PassWord;
    }
    public String GetStudentID()
    {
    	return ID;
    }
    public String GetStudentPW()
    {
    	return Password;
    }
    public void turnoff()
    {
    	frame.setVisible(false);
    }
    public boolean Get_login_success()
    {
    	return true;
    }
    public void turnOn()
    {
    	frame.setVisible(true);
    }
    // gifTest gif_form = new gifTest();
}
