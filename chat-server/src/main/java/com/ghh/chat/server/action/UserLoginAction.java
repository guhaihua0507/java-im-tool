/*
 * UserLoginAction.java
 *
 * Copyright(C) 2009, by ghh.
 */
package com.ghh.chat.server.action;

import com.ghh.chat.common.User;
import com.ghh.chat.common.action.ChatAction;
import com.ghh.chat.common.action.ChatActionListener;
import com.ghh.chat.common.msg.Message;
import com.ghh.chat.common.msg.Util;

/**
 * 
 * @author haihua.gu Created on Sep 20, 2009
 */

public class UserLoginAction implements ChatAction {

	public void execute(Message msg, ChatActionListener listener) {
		User user = Util.getUserFromData(msg.getData());
		if (user != null) {
			user.setIp(msg.getIp().getHostAddress());
			user.setPort(msg.getPort());
			listener.onUserLogin(user);
		}
	}
}
