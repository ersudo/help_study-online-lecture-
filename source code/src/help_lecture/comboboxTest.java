package help_lms;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import java.awt.TextArea;

public class comboboxTest {
	public static JFrame frame;
	public static JLabel label;
	public static JComboBox combobox;
	public static String[] LectureNames;
	public static JButton button;
	public static List<String> resultList;
	public static Panel panel; 
	public static  JTable table;
	public static JScrollPane scrollpane;
	public static int size =0;
	public comboboxTest()
	{
		
	}
	public void SetcomboboxForm()
	{
		
		frame = new JFrame();
	    frame.setSize(450, 400);
	    frame.setTitle("DB search");
	    frame.setLayout(null);
	    combobox = new JComboBox(LectureNames);
	    combobox.setBounds(10, 10, 150, 20);
	    button = new JButton("검색");
	    button.setBounds(160, 10, 70, 20);
	    JPanel panel = new JPanel();
	    frame.add(combobox);
	    frame.add(button);
	    // frame.add(panel);

	    
	    button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				// TODO Auto-generated method stub
				resultList = new ArrayList<>();
				String lectureName = combobox.getItemAt(combobox.getSelectedIndex()).toString();
				System.out.println("you selected: "+ lectureName);
					DBconnection dbconnection = new DBconnection();
					dbconnection.FindLecture(lectureName);
					
			}
    	});
	    
	    frame.setVisible(true);
	}
	
	public void GetList(java.util.List<String> lectureNameList)
	{
		this.LectureNames = lectureNameList.toArray(new String[0]);
	}
	public void GetSize(List<String> LectureStatusList)
	{
		this.size = LectureStatusList.size();
	}
	public void ShowResult()
	{
		   for(String s: resultList)
		    {
		    	System.out.println("String in resultList" + s);
		    }
	}
	public void ShowTable(List<String> LectureStatusList,List<String> LectureFormList,List<String> LectureTitleList,List<String> LectureNameList)
	{
		  String header[] = {"강의형태","학습 현황","강의 제목", "강의"};
		  
		    DefaultTableModel tableModel = new DefaultTableModel(0, 0);
		    tableModel.setColumnIdentifiers(header);
		    JTable table = new JTable(tableModel);
		    table.setBounds(10,30,400,250);
		    for (int i = 0; i < LectureNameList.size(); i++){
		    		Vector<Object> data = new Vector<Object>();
					String status = LectureStatusList.get(i);
					data.add(status);
					String form = LectureFormList.get(i);
					data.add(form);
					String title =  LectureTitleList.get(i);
					data.add(title);
					String name = LectureNameList.get(i);
					data.add(name);
					// Object[] data = {form, status,title,name};
					tableModel.addRow(data);
		    }        
		    scrollpane = new JScrollPane(table);
		    scrollpane.setBounds(10,30,400,250);
		    scrollpane.setVisible(true);
		    scrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		    
		    frame.add(scrollpane); 
		    table.addMouseListener(new MouseListener() {
	            @Override
	            public void mouseClicked(MouseEvent e) {
	            	scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	                scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	            }

	            @Override
	            public void mousePressed(MouseEvent e) {
	            	scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	                scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	            }

	            @Override
	            public void mouseReleased(MouseEvent e) {
	            	scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	                scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	            }

	            @Override
	            public void mouseEntered(MouseEvent e) {
	                scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	                scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	            }

	            @Override
	            public void mouseExited(MouseEvent e) {
	                scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	                scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	            }
		    });

	}
	
}
