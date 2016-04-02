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

public class Window extends JFrame implements ActionListener {
	protected static final String enterText = "enterText";
	protected static final String nl = "\n";
	
	private static JFrame f;
	private static JTextField jtf;
	private static ScrollPane spMain;
	private static ScrollPane spTrack;


	public static void main(String args[]) {
		// Create an instance of the test application
		new Window();
	}

	public Window() {
		f = new JFrame("Evra");
		f.setLayout(new BorderLayout());
		f.setSize(600,600);
		
		jtf = new JTextField(30);
		jtf.setActionCommand(enterText);
		jtf.addActionListener(this);
		
		spMain = new ScrollPane();
		spTrack = new ScrollPane();
		spTrack.setPreferredSize(new Dimension(150,600));
		
		f.add(spMain, BorderLayout.CENTER);
		f.add(jtf, BorderLayout.SOUTH);
		
		f.addWindowListener(new WindowAdapter() { //handle window close
        	public void windowClosing(WindowEvent windowEvent) {
	        	System.exit(0);
			}        
		});  
		f.setVisible(true);
		jtf.requestFocusInWindow();
		spMain.clear();
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
						toggleTrack();
						break;
					default:
						Log.warning("No such command: " + result[0] + "\n");
						break;	
				}
			}
			else {
				switch(result[0].toLowerCase()) {
					case "math":
						Calc.eval(result[1], true);
						break;
					default:
						break;
				}
			}
		}
		catch (RuntimeException ex) {
			Log.error(ex.getLocalizedMessage());
		}
	}
	
	//Handle actions
	public void actionPerformed(ActionEvent e) {
		String c = e.getActionCommand();
		if(c == enterText)
		{
			JTextField src = (JTextField) e.getSource();
			send(src.getText());
			src.setText("");
		}
	}
	
	public enum Handles {
		MAIN, TRACK
	}
}