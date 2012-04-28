/*
 * ConnectionKeeper.java
 *
 * Copyright(C) 2009, by ghh.
 */
package com.ghh.chat.client.core;

import java.util.List;

import com.ghh.chat.common.User;

/**
 *
 * @author haihua.gu
 * Created on Sep 22, 2009
 */

public class ConnectionKeeper implements Runnable {

	private ClientCore core;
	public ConnectionKeeper(ClientCore core) {
		this.core = core;
	}
	
	public void run() {
		List<User> userlist = core.getUserlist();
		for (User user : userlist) {
			try {
				core.sendNullMsg(user);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

