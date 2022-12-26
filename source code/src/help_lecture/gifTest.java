package help_lms;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class gifTest {
	public static JFrame frame;
	public static JLabel label;
	public gifTest()
	{
		
	}
	public void SetgifForm()
	{
		frame = new JFrame();
	    label = new JLabel(new ImageIcon(("image/loading.gif")));
	    frame.setSize(350, 200);
	    frame.add(label);
	    frame.setVisible(true);
	}
	public void turnOFF()
	{
		frame.setVisible(false);
	}
}
