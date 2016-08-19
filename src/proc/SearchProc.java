package evra.proc;

import evra.*;
import evra.database.*;

public class SearchProc {
    //Holds loop/procedure for search mode
    public static Search mode = Search.BASE;
    private static Database currentDB = null;

    public SearchProc(String cmd) {
        String results[] = cmd.split(" ", 2);
        if(currentDB == null)
            Log.error("No database loaded.");
        if(results.length < 2) {
            switch(results[0].toLowerCase()) {
                case "list":
                    EvraMain.listDB();
                    return;
                case "cancel":
                    setMode(Search.BASE);
                    Log.writel("Canceling search.");
                    return;
                default:
                    if(mode == Search.BASE) {
                        boolean found = currentDB.query(cmd);
                        if(found) setMode(Search.RESULTS);
                    }
                    else if(mode == Search.RESULTS) {
                        currentDB.write(currentDB.followUp(cmd));
                        setMode(Search.BASE);
                    }
                    return;
            }
        }
        else
        {
            switch(results[0].toLowerCase()) {
                case "db":
                    currentDB = EvraMain.getDB(results[1]);
                    if(currentDB == null)
                        Log.error("No such database.");
                    return;
                case "search":
                default:
                    if(mode == Search.BASE) {
                        boolean found = currentDB.query(cmd);
                        if(found) setMode(Search.RESULTS);
                    }
                    else if(mode == Search.RESULTS) {
                        currentDB.write(currentDB.followUp(cmd));
                        setMode(Search.BASE);
                    }
                    return;
            }
        }
    }

    public static void setMode(Search m) {
        mode = m;
    }

    private enum Search {
        BASE, RESULTS, DETAILS
    }
}