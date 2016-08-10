package evra;

import evra.Log;
import evra.ConsoleProc;
import evra.gui.GUIMain;
import evra.math.Roll;
import evra.testing.Test;

public class EvraMain {
	public static boolean CONSOLE = false;
	public static Modes mode = Modes.MAIN;
	public static void main(String args[]) {
		mode = Modes.MATH;
		if(args.length > 0) {
			if(args[0].equals("-c")) {
				//do console stuff
				CONSOLE = true;
				Log.error("Evra started in console mode.");
				new ConsoleProc();
			}
			else {
				Log.error("Unhandled program argument.");
				//other args?
			}
		}
		else {
			Log.error("Evra started in GUI mode.");
			CONSOLE = false;
			new GUIMain();
		}
	}
	
	public static boolean isConsole() {
		return CONSOLE;
	}
	
	public static void dispatch(final String cmd) {
		try {
			String[] result = cmd.split(" ", 2);
			if(result.length < 2) {
				switch(result[0].toLowerCase()) {
					case "clear":
					case "cls":
						if(!CONSOLE)
							GUIMain.clear(GUIMain.Handles.MAIN);
						else
							Log.error("Command disabled in console mode.");
						return;
					case "exit":
					case "quit":
					case "q":
					case "qqq":
					case "qq":
						System.exit(0);
						return;
					case "track":
						setMode(Modes.TRACK);
						return;
					case "math":
						setMode(Modes.MATH);
						return;	
					case "test":
						new Test();
						return;
					default:
						switch(mode) {
							case TRACK:
								//dispatch to track class
								return;
							case MATH:
								Roll.eval(result[0], true);
								return;
							default:
								Log.error("Unknown mode or unknown command.");
								return;
						}
				}
			}
			else {
				switch(result[0].toLowerCase()) {
					case "math":
						Roll.eval(result[1], true);
						return;
					case "track":
						//dispatch result[1] to track class
						return;
					default:
						Log.error("Unknown command.");
						return;
				}
			}
		}
		catch (RuntimeException ex) {
			if(!CONSOLE)
				Log.write(Log.red("Error: ") + ex.getLocalizedMessage());
			else
				Log.error(ex.getLocalizedMessage());
		}
	}
	
	public static void setMode(Modes m) {
		mode = m;
	}
	
	public static Modes getMode() {
		return mode;
	}
	
	public enum Modes {
		MAIN, MATH, TRACK
	}
}