package evra.gui;

import evra.Log;
import evra.EvraMain;
import evra.gui.GUIMain;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.text.*;

public class TextField extends JTextField {
	private java.util.ArrayList<String> history;
	//Previously used ListIterator, but it doubled on itself when changing directions...
	private int iter;

	public TextField() {		
		//The history stores previously entered commands
		//Be default, the last command is autopopulated in the box after execution, so going 'back' in history will show your second to last command
		history = new java.util.ArrayList<String>();
		this.addActionListener(new AbstractAction() {
			@Override public void actionPerformed(ActionEvent e) {
				history.add(getText());
				iter = history.size() - 1;
				EvraMain.dispatch(getText());
				selectAll();
			}
		});

		insert("Enter command");
		history.add("Enter command");
		iter = 0;
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
				if(iter > 0) {
					insert(history.get(--iter));
				}
				else
					Log.error("No previous command");
				Log.error("Up pressed.");
			}
		});
		aMap.put("down", new AbstractAction() {
			@Override public void actionPerformed(ActionEvent e) {
				if(iter < history.size() - 1) {
					insert(history.get(++iter));
				}
				else
					Log.error("Can't go forward in history");
				Log.error("Down pressed.");
			}
		});
	}
}