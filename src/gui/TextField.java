package evra.gui;

import evra.Log;
import evra.EvraMain;
import evra.gui.GUIMain;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.text.*;

public class TextField extends JTextField {
	public TextField() {		
		this.addActionListener(new AbstractAction() {
			@Override public void actionPerformed(ActionEvent e) {
				EvraMain.dispatch(getText());
				setText("");
			}
		});
		
		insert("Enter command");
	}
	
	public void insert(final String s) {
		setText(s);
		selectAll();
		requestFocusInWindow(); //make the JTextField the currently focused component
	}
	
	public void enableHotKeys() {
		//handle up and down keys for JTextField
		InputMap iMap = getInputMap();
		iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "up"); 
		iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "down");
		ActionMap aMap = getActionMap();
		aMap.put("up", new AbstractAction() {
			@Override public void actionPerformed(ActionEvent e) {
				Log.error("Up pressed.");
			}
		});
		aMap.put("down", new AbstractAction() {
			@Override public void actionPerformed(ActionEvent e) {
				Log.error("Down pressed.");
			}
		});
	}
}