/*
 * SessionMaintenance.java
 *
 * Copyright(C) 2009, by ghh.
 */
package com.ghh.chat.server;

import java.util.ArrayList;
import java.util.List;

import com.ghh.chat.common.Constants;

/**
 * 
 * @author haihua.gu Created on Oct 8, 2009
 */

public class SessionMaintenance implements Runnable {
	private ServerCore	core;

	public SessionMaintenance(ServerCore core) {
		this.core = core;
	}

	public void run() {
		List<Session> timeoutSn = new ArrayList<Session>();
		List<Session> snlist = core.getAllSession();
		long currentTime = System.currentTimeMillis();
		for (Session sn : snlist) {
			if (currentTime - sn.getLastUpdateTime() > (Constants.sessionTimeout * 1000)) {
				timeoutSn.add(sn);
			}
		}

		if (timeoutSn.size() > 0) {
			snlist.removeAll(timeoutSn);
			for (Session sn : timeoutSn) {
				core.onUserLogout(sn.getUser());
			}
		}
	}
}
