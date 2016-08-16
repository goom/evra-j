package evra;

import evra.EvraMain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class ConsoleProc {
	public ConsoleProc() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s = new String();
		EvraMain.initiate();
		while(true) { //main console loop for input
			System.out.print("> ");
			try {
				s = br.readLine();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
			EvraMain.dispatch(s);
		}
	}
}