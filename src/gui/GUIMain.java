package evra.gui;

import evra.math.Roll;
import evra.EvraMain;
import evra.Log;
import evra.gui.ScrollPane;
import evra.gui.TextField;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.html.*;

public class GUIMain extends JFrame {
	private static JFrame f;
	private static TextField jtf;
	private static ScrollPane spMain;
	private static ScrollPane spTrack;

	public GUIMain() {
		f = new JFrame("Evra");
		f.setLayout(new BorderLayout());
		f.setSize(600,600);
		
		jtf = new TextField();
		jtf.enableHotKeys();
		
		//initialize the different panes i'll be using
		spMain = new ScrollPane();
		spTrack = new ScrollPane();
		spTrack.setPreferredSize(new Dimension(150,600));
		
		//add panes to main JFrame
		f.add(spMain, BorderLayout.CENTER);
		f.add(jtf, BorderLayout.SOUTH);
		
		//handle window close
		f.addWindowListener(new WindowAdapter() { 
        	public void windowClosing(WindowEvent windowEvent) {
	        	System.exit(0);
			}        
		});  
		
		//final things to initialize
		f.setVisible(true); //show window
		spMain.clear(); //clear the main text window, or set default text
		EvraMain.initiate();
	}
	
	public static void togglePane(Handles h) {
		switch(h) {
			case TRACK:
				if(spTrack.getParent() == f.getContentPane()) f.remove(spTrack);
				else f.add(spTrack, BorderLayout.EAST);
				break;
			default:
				Log.error("Unknown handle passed to Window.togglePane()");
				break;
		}
		f.repaint();
		f.revalidate();
	}
	
	public static void addText(final String s, Handles h) {
		switch(h) {
			case MAIN:
				spMain.insert(s);
				break;
			case TRACK:
				spTrack.insert(s);
				break;
			case TEXT_FIELD:
				jtf.insert(s);
				break;
			default:
				Log.error("Unknown handle passed to Window.getText()");
				break;
		}
	}
	
	public static void clear(Handles h) {
		switch (h) {
			case MAIN:
				spMain.clear();
				break;
			case TRACK:
				spTrack.clear();
				break;
			default:
				Log.error("No known handle passed to clear()");
				break;
		}
	}
	
	public enum Handles {
		MAIN, TRACK, TEXT_FIELD
	}
}