/*
 * Logger.java
 *
 * Copyright(C) 2009, by ghh.
 */
package com.ghh.chat.server;

import java.io.PrintStream;

/**
 *
 * @author haihua.gu
 * Created on Sep 20, 2009
 */

public class Logger {

	private static boolean debug = false;
	private static PrintStream out = System.out;
	
	public static void logout(String s) {
		if (!debug)
			return;
		
		out.println(s);
	}
	
	public static void sysout(String s) {
		out.println(s);
	}
	
	public static void setDebugMode(boolean b) {
		debug = b;
	}
	
	public static void setPrintStream(PrintStream out) {
		Logger.out = out;
	}
}

