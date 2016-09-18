package evra.gui;

import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.html.*;

class ScrollPane extends JScrollPane {
	private HTMLDocument doc;
	private HTMLEditorKit kit;
	private JTextPane jtp;
	
	public ScrollPane() {
		kit = new HTMLEditorKit();
		doc = new HTMLDocument();
		jtp = new JTextPane();
		jtp.setEditorKit(kit);
		jtp.setDocument(doc);
		jtp.setEditable(false);
		jtp.setFocusable(false);
		jtp.setBackground(Color.black);
		//doc.setParser(new HTMLEditorKit.ParseDelegator());//negatory
		
		this.setViewportView(jtp);
		this.setWheelScrollingEnabled(true);
		DefaultCaret caret = (DefaultCaret) jtp.getCaret(); //keep the scroll area at the bottom
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		clear();
	}
	
	public void clear() {
		try {
			jtp.setText("");
			kit.insertHTML(doc, doc.getLength(), "<div id=\"JTP\"></div>",0,0,null);
		}
		catch (BadLocationException ble) {
			System.err.println("Couldn't insert line in JTextPane.");
		}
		catch (IOException ioe) {
			System.err.println("Incorrect HTML formatting in insertText()");
		}
	}
	
	public void insert(String s) {
		try {
			//kit.insertHTML(doc, doc.getLength() - 2, s, 0, 0, null);
			doc.insertBeforeEnd(doc.getElement("id=\"JTP\""),s);
		}
		catch (BadLocationException ble) {
			System.err.println("Couldn't insert line in JTextPane.");
		}
		catch (IOException ioe) {
			System.err.println("Incorrect HTML formatting in insertText()");
		}
	}
}