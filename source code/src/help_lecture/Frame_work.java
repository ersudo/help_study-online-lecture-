package help_lms;
import java.awt.*;

import java.awt.event.*;

import javax.swing.*;
import java.io.File;
import java.awt.TextField;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
public class Frame_work extends JFrame{
	public static JFrame frame;
	public static String UserDir = System.getProperty("user.dir");
	public Frame_work()
	{
	
	}
}

/*
 * 
 * public void LoadImage() throws IOException
	{
		String path = UserDir +"\\image\\loading_image.gif"; //  이미지 gif 
		String output_path = UserDir +"\\image\\temp.gif"; 
		InputStream targetStream = new FileInputStream(new File(path));
		GifDecoder gifDecoder = new GifDecoder();
		gifDecoder.read(targetStream);
		BufferedImage image = gifDecoder.getFrame(0);
		int DELAY_TIME  = 100;
		ImageOutputStream imageOutputStream = new FileImageOutputStream(new File("output_path"));
		GifSequenceWriter gifSequenceWriter = new GifSequenceWriter(imageOutputStream, gifDecoder.getFrame(0).getType(), DELAY_TIME, true);
		gifSequenceWriter.writeToSequence(image);
		
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel label = new JLabel(new ImageIcon(image));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(label);
		frame.pack();
		frame.setLocation(200,200);
		frame.setVisible(true);
		
	}
 * 
 * */
 