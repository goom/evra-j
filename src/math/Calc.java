package evra.math;

import evra.Log;

import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Pattern;

public class Calc { 
	static Random rand = new Random();
	final static DecimalFormat format = new DecimalFormat("0.######");
	
	public static double eval(String str) {
		return eval(str, false);
	}
	public static double eval(final String str, boolean verbose) {
		str.toLowerCase();
		return new Object() {
			int pos = -1, ch;
			String regurg = new String("");

			void eatChar() {
				ch = (++pos < str.length()) ? str.charAt(pos) : -1;
				if (ch >= 0) regurg += (char)ch;
			}

			boolean eatChar(int ch) {
				if (this.ch == ch) {
					eatChar();
					return true;
				}
				return false;
			}

			void eatSpace() {
				while (Character.isWhitespace(ch)) eatChar();
			}

			double parse() {
				eatChar();
				double x = parseExpression();
				if (pos < str.length()) throw new RuntimeException("Incorrect formula. Unexpected: " + (char)ch);
				
				if (verbose) Log.write(regurg + " = " + format.format(x) + "\n");
				return x;
			}

			// Grammar:
			// expression = term | expression `+` term | expression `-` term
			// term = factor | term `*` factor | term `/` factor
			// factor = `+` factor | `-` factor | `(` expression `)`
			//        | number | functionName factor | factor `^` factor

			double parseExpression() {
				double x = parseTerm();
				for (;;) {
					eatSpace();
					if      (eatChar('+')) x += parseTerm(); // addition
					else if (eatChar('-')) x -= parseTerm(); // subtraction
					else return x;
				}
			}

			double parseTerm() {
				double x = parseDice();
				for (;;) {
					eatSpace();
					if      (eatChar('*')) x *= parseDice(); // multiplication
					else if (eatChar('/')) x /= parseDice(); // division
					else return x;
				}
			}
			
			double parseDice() {
				double x = parseFactor();
				for(;;) {
					eatSpace();
					if(eatChar('d')) {
						double y = parseFactor();
						if(ch >= 0) regurg = regurg.substring(0, regurg.length() - 1);
						y = roll(y, x);
						if(ch >= 0) regurg += (char)ch;
						return y;
					}
					else return x;
				}
			}

			double parseFactor() {
				eatSpace();
				if (eatChar('+')) return parseFactor(); // unary plus
				if (eatChar('-')) return -parseFactor(); // unary minus

				double x;
				int startPos = this.pos;
				if (eatChar('(')) { // parentheses
					x = parseExpression();
					if(!eatChar(')')) throw new RuntimeException("No ending parentheses in calculation.");
				} else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
					while ((ch >= '0' && ch <= '9') || ch == '.') eatChar();
					x = Double.parseDouble(str.substring(startPos, this.pos));
				} else if (ch >= 'a' && ch <= 'z') { // functions
					while (ch >= 'a' && ch <= 'z') eatChar();
					String func = str.substring(startPos, this.pos);
					x = parseFactor();
					if (func.equals("sqrt")) x = Math.sqrt(x);
					else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
					else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
					else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
					else if (func.equals("d")) {
						if(ch >= 0)
							regurg = regurg.substring(0, regurg.length() - 1); //it ate a command after dice, don't want to print it yet
						x = roll(x);
						if(ch >= 0) regurg += (char)ch;
					}
					else throw new RuntimeException("Unknown function: " + func);
				} else {
					throw new RuntimeException("Incorrect formula. Unexpected character: " + (char)ch);
				}

				eatSpace();
				if (eatChar('^')) x = Math.pow(x, parseFactor()); // exponentiation
				return x;
			}
			
			double roll(double sides) {
				return roll(sides, 1);
			}
			double roll(double sides, double dice) {
				int total = 0, cur = 0;
				regurg += "(";
				for(int x = 0; x < dice; x++) {
					cur = rand.nextInt((int)sides) + 1;
					if(cur == sides) {
						regurg += Log.green(Integer.toString(cur));
					}
					else if (cur == 1) {
						regurg += Log.red(Integer.toString(cur));
					}
					else regurg += Integer.toString(cur);
					total += cur;
					if((x + 1) < dice) regurg += ", ";
				}
				regurg += ")";
				return (double)total;
			}
		}.parse();
	}
} 