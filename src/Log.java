package evra;

import evra.gui.GuiMain;
import evra.EvraMain;

public class Log {
	
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	
	public static void write(String s) {
		StringBuilder x = new StringBuilder(s);
		
		if(EvraMain.isConsole())
			System.out.print(x.toString()); 
		else
			EvraMain.gui.addText(white(x.toString()));
	}

	public static void writel(String s) {
		StringBuilder x = new StringBuilder(s);

		if(EvraMain.isConsole())
			System.out.println(x.toString());
		else
			EvraMain.gui.addText(white(x.toString()) + "<br>");
	}
	
	public static String red(final String s) {
		String x = "<font color=\"red\">" + s + "</font>";
		return EvraMain.isConsole() ? s : x;
	}
	
	public static String blue(final String s) {
		String x = "<font color=\"blue\">" + s + "</font>";
		return EvraMain.isConsole() ? s : x;
	}
	
	public static String white(final String s) {
		String x = "<font color=\"white\">" + s + "</font>";
		return EvraMain.isConsole() ? s : x;
	}
	
	public static String green(final String s) {
		String x = "<font color=\"green\">" + s + "</font>";
		return EvraMain.isConsole() ? s : x;
	}
	
	public static void error(String s) {
		System.err.println(s);
	}
}