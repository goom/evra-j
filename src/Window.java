package evra;

import evra.math.Calc;
import evra.Log;

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
	private static HTMLDocument doc;
	private static HTMLEditorKit kit;
	private static JTextPane jtp;


	public static void main(String args[]) {
		// Create an instance of the test application
		new Window();
	}

	public Window() {
		f = new JFrame("Evra");
		f.setLayout(new BorderLayout());
		f.setSize(400,600);
		
		jtf = new JTextField(30);
		jtf.setActionCommand(enterText);
		jtf.addActionListener(this);
		
		kit = new HTMLEditorKit();
		doc = new HTMLDocument();
		jtp = new JTextPane();
		jtp.setEditorKit(kit);
		jtp.setDocument(doc);
		jtp.setEditable(false);
		jtp.setFocusable(false);
		jtp.setBackground(Color.black);
		
		//Create the scroll area and add the text pane to it
		JScrollPane scroller = new JScrollPane(jtp);
		scroller.setWheelScrollingEnabled(true);
		DefaultCaret caret = (DefaultCaret) jtp.getCaret(); //keep the scroll area at the bottom
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		f.add(scroller, BorderLayout.CENTER);
		f.add(jtf, BorderLayout.SOUTH);
		
		f.addWindowListener(new WindowAdapter() { //handle window close
        	public void windowClosing(WindowEvent windowEvent) {
	        	System.exit(0);
			}        
		});  
		f.setVisible(true);
		jtf.requestFocusInWindow();
		clear();
	}
	
	//clear all text in the JTextPane
	private void clear() {
		jtp.setText("");
		insertText("<font color=white>Test</font>");
	}
	
	//Adding text to the JTP; used by Log
	public static void insertText(String s) {
		try {
			kit.insertHTML(doc, doc.getLength(), s, 0, 0, null);
		}
		catch (BadLocationException ble) {
			System.err.println("Couldn't insert line in JTextField.");
		}
		catch (IOException ioe) {
			System.err.println("Incorrect HTML formatting in insertText()");
		}
	}
	
	//dispatch the commands to their handlers
	private void send(String cmd) {
		String[] result = cmd.split(" ", 2);
		if(result.length < 2) {
			switch(result[0].toLowerCase()) {
				case "clear":
				case "cls":
					clear();
					break;
				case "exit":
				case "quit":
				case "q":
					System.exit(0);
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
					
			}
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
}