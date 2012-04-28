/*
 * Console.java
 *
 * Copyright(C) 2009, by ghh.
 */
package com.ghh.chat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.List;

import com.ghh.chat.common.User;

/**
 *
 * @author haihua.gu
 * Created on Sep 24, 2009
 */

public class Console implements Runnable {

	private PrintStream out = System.out;
	private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	private ServerCore core;
	private String title = "Console>";
	
	public Console(ServerCore core) {
		this.core = core;
	}
	
	public void run() {
		println("console started");
		while(true) {
			printTitle();
			try {
				String cmd = in.readLine();
				processCommand(cmd);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void processCommand(String cmd) {
		String[] params = getCommand(cmd);
		if (params == null)
			return;
		String command = params[0];
		
		if ("debug".equalsIgnoreCase(command)) {
			debug(params);
			return;
		}
		
		if ("listAllUser".equalsIgnoreCase(command)) {
			listAllUser();
			return;
		}
		if ("user".equalsIgnoreCase(command)) {
			viewUserInfo(params);
			return;
		}
		if ("help".equalsIgnoreCase(command)) {
			help();
			return;
		}
		
		println("\"" + command + "\" is not recognized as a command");
	}
	
	private void help() {
		println("debug\t\t\tturn on|off dubug mode.");
		println("listAllUser\t\tlist all user signed in.");
		println("user\t\t\tview the specified user info.");
	}
	
	private void viewUserInfo(String[] params) {
		if (params.length != 2) {
			println("usage: user userID");
			return;
		}
		Session sn = core.getSessionByUserID(params[1]);
		if (sn == null) {
			println("can not find user " + params[1]);
		} else {
			println(getUserInfo(sn));
		}
	}

	private void debug(String[] params) {
		if (params.length != 2) {
			println("usage: debug on|off");
			return;
		}
		if ("on".equalsIgnoreCase(params[1])) {
			Logger.setDebugMode(true);
			return;
		}
		if ("off".equalsIgnoreCase(params[1])) {
			Logger.setDebugMode(false);
			return;
		}
		println("usage: debug on|off");
	}
	
	private void listAllUser() {
		List<Session> users = core.getAllSession();
		if (users == null || users.size() == 0) {
			println("no user signed!");
			return;
		}
		for (Session sn : users) {
			println(getUserInfo(sn));
		}
	}
	
	private String getUserInfo(Session sn) {
		User user = sn.getUser();
		StringBuffer sb = new StringBuffer();
		sb.append("ID:" + user.getUserID() + " ");
		sb.append("NAME:" + user.getName() + " ");
		sb.append("IP:" + user.getIp() + ":" + user.getPort());
		return sb.toString();
	}
	
	private String[] getCommand(String cmd) {
		if (cmd == null || cmd.trim().length() == 0)
			return null;
		
		return cmd.trim().split(" ");
	}
	
	private void println(String msg) {
		out.println(msg);
	}
	
	private void printTitle() {
		out.print(title);
	}
}

