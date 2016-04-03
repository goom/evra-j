package evra;

import evra.math.Calc;
import evra.Log;
import evra.Window;

import java.io.*;

public class Handler {
	public static Modes mode;
	
	public Handler(final String cmd) {
		try {
			String[] result = cmd.split(" ", 2);
			if(result.length < 2) {
				switch(result[0].toLowerCase()) {
					case "clear":
					case "cls":
						Window.clear(Window.Handles.MAIN);
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
	
	public static void setMode(Modes m) {
		mode = m;
	}
	
	public static Modes getMode() {
		return mode;
	}
	
	public enum Modes {
		MATH, TRACK
	}
}