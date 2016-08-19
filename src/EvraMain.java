package evra;

import evra.gui.GUIMain;
import evra.proc.*;
import evra.testing.Test;
import evra.database.*;
import java.util.*;

public class EvraMain {
	public static boolean CONSOLE = false;
	public static Modes mode = Modes.MAIN;

	public static ArrayList<Database> dbs;
	public static boolean initiated = false;

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

	public static void initiate() {
		dbs = new ArrayList<Database>();
		dbs.add(new Database("spells"));
		dbs.add(new Database("items"));
		dbs.add(new Database("monsters"));
		initiated = true;
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
					case "search": 
						setMode(Modes.SEARCH);
						return;
					case "test":
						new Test();
						return;
					default:
						switch(mode) {
							case TRACK:
								//dispatch to track class
								//new TrackProc(result[0]);
								return;
							case MATH:
								new MathProc(result[0]);
								return;
							case SEARCH:
								/*if(!search) {
									search = spells.query(result[0]);
								}
								else {
									spells.write(spells.followUp(result[0]));
									search = false;
								}*/
								new SearchProc(result[0]);
								return;
							default:
								Log.error("Unknown mode.");
								return;
						}
				}
			}
			else {
				switch(result[0].toLowerCase()) {
					case "math":
						new MathProc(result[1]);
						return;
					case "track":
						//dispatch result[1] to track class
						//new TrackProc(result[1]);
						return;
					case "search":
						//can only search for spells atm
						/*if(!search) {
							search = spells.query(result[1]);
						}
						else {
							spells.write(spells.followUp(result[1]));
							search = false;
						}*/
						new SearchProc(result[1]);
						return;
					case "export":
						JImport.save(result[1], JImport.arrayToObject(result[1]));
						return;
					default:
						switch(mode) {
							case MATH:
								new MathProc(cmd);
								return;
							case TRACK:
								//new TrackProc(cmd);
								return;
							case SEARCH:
								new SearchProc(cmd);
								return;
							default:
								Log.error("Unknown mode.");
								return;
						}
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

	public static String getModeString() {
		switch (getMode()) {
			case MATH:
				return "Math";
			case TRACK:
				return "Track";
			case SEARCH:
				return "Search";
			default:
				return "None";
		}
	}

	public static Database getDB(String s) {
		for(Database x : dbs) {
			if(x.name.equals(s))
				return x;
		}

		return null;
	}

	public static void listDB() {
		for(Database x : dbs) {
			Log.writel(x.name);
		}
	}
	
	public enum Modes {
		MAIN, MATH, TRACK, SEARCH
	}
}