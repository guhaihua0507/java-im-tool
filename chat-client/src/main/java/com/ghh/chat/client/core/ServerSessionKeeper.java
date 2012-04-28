/*
 * ServerSessionKeeper.java
 *
 * Copyright(C) 2009, by ghh.
 */
package com.ghh.chat.client.core;

/**
 *
 * @author haihua.gu
 * Created on Oct 8, 2009
 */

public class ServerSessionKeeper implements Runnable {

	private ClientCore core;
	
	public ServerSessionKeeper(ClientCore core) {
		this.core = core;
	}
	
	public void run() {
		try {
			core.sendKeepSessionMsg();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

