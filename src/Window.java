package evra;

import evra.math.Calc;
import evra.Log;
import evra.ScrollPane;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.html.*;

public class Window extends JFrame {
	public Modes mode;
	
	private static JFrame f;
	private static JTextField jtf;
	private static ScrollPane spMain;
	private static ScrollPane spTrack;


	public static void main(String args[]) {
		// Create an instance of the test application
		new Window();
	}

	public Window() {
		mode = Modes.MATH;
		f = new JFrame("Evra");
		f.setLayout(new BorderLayout());
		f.setSize(600,600);
		
		jtf = new JTextField(30);
		
		//handle enter button action for JTextField
		jtf.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				JTextField src = (JTextField) e.getSource();
				send(jtf.getText());
				jtf.setText("");
			}
		});
		
		//handle up and down keys for JTextField
		InputMap iMap = jtf.getInputMap();
		iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "up"); 
		iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "down");
		ActionMap aMap = jtf.getActionMap();
		aMap.put("up", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				Log.write("Up pressed.");
			}
		});
		aMap.put("down", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				Log.write("Down pressed.");
			}
		});
		
		//initiale the different panes i'll be using
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
		jtf.requestFocusInWindow(); //make the JTextField the currently focused component
		spMain.clear(); //clear the main text window, or set default text
	}
	
	private void setMode(Modes m) {
		mode = m;
	}
	
	private void toggleTrack() {
		if(spTrack.getParent() == f.getContentPane()) {
			f.remove(spTrack);
			f.repaint();
			f.revalidate();
		}
		else {
			f.add(spTrack, BorderLayout.EAST);
			f.repaint();
			f.revalidate();
		}
	}
	
	public static void addText(final String s, Handles h) {
		switch(h) {
			case MAIN:
				spMain.insert(s);
				break;
			case TRACK:
				spTrack.insert(s);
				break;
		}
	}
	
	//dispatch the commands to their handlers
	private void send(String cmd) {
		try {
			String[] result = cmd.split(" ", 2);
			if(result.length < 2) {
				switch(result[0].toLowerCase()) {
					case "clear":
					case "cls":
						spMain.clear();
						break;
					case "exit":
					case "quit":
					case "q":
						System.exit(0);
						break;
					case "track":
						setMode(Modes.TRACK);
						break;
					case "math":
						setMode(Modes.MATH);
						break;	
					default:
						switch(mode) {
							case TRACK:
								//dispatch to track class
								break;
							case MATH:
								Calc.eval(result[0], true);
								break;
							default:
								Log.error("No mode or no known mode specified.");
								break;
						}
						break;	
				}
			}
			else {
				switch(result[0].toLowerCase()) {
					case "math":
						Calc.eval(result[1], true);
						break;
					case "track":
						//dispatch result[1] to track class
						break;
					default:
						Log.warning("Unknown command.");
						break;
				}
			}
		}
		catch (RuntimeException ex) {
			Log.error(ex.getLocalizedMessage());
		}
	}
	
	public enum Handles {
		MAIN, TRACK
	}
	
	public enum Modes {
		MATH, TRACK
	}
}