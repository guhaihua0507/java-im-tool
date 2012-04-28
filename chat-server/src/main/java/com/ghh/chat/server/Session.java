/*
 * Session.java
 *
 * Copyright(C) 2009, by ghh.
 */
package com.ghh.chat.server;

import com.ghh.chat.common.User;

/**
 *
 * @author haihua.gu
 * Created on Sep 22, 2009
 */

public class Session {

	private User user;
	private long lastUpdateTime = System.currentTimeMillis();

	public Session(User user) {
		this.user = user;
	}

	public long getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void updateTime() {
		this.lastUpdateTime = System.currentTimeMillis();
	}

	public User getUser() {
		return user;
	}
}

