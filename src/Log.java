package evra;

import evra.Window;

import java.awt.*;
import javax.swing.text.*;
import javax.swing.text.rtf.*;

public class Log {
	public static void write(String s) {
		write(s, Color.white);
	}
	public static void write(String s, Color color) {
		write(s, color, Types.NORMAL);
	}
	public static void write(String s, Types t) {
		write(s, Color.white, t);
	}
	public static void write(String s, Color color, Types t) {
		StringBuilder x = new StringBuilder(s);
		switch (t) {
			case ERROR:
				x.insert(0, "<font color=red>ERROR: </font>");
				break;
			case WARNING:
				x.insert(0, "<font color=yellow>Warning: </font>");
				break;
			default:
				break;
		}
		
		Window.insertText("<font color=white>" + x.toString() + "</font>");
	}
	
	public static void error(String s) {
		write(s, Types.ERROR);
	}
	public static void warning(String s) {
		write(s, Types.WARNING);
	}
	
	public enum Types {
		ERROR, WARNING, NORMAL
	}
}