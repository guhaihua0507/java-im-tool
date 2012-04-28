/*
 * LoginSuccessAction.java
 *
 * Copyright(C) 2009, by ghh.
 */
package com.ghh.chat.client.core.action;

import java.util.List;

import com.ghh.chat.common.User;
import com.ghh.chat.common.action.ChatAction;
import com.ghh.chat.common.action.ChatActionListener;
import com.ghh.chat.common.msg.Message;
import com.ghh.chat.common.msg.Util;

/**
 *
 * @author haihua.gu
 * Created on Sep 21, 2009
 */

public class LoginSuccessAction implements ChatAction {

	public void execute(Message msg, ChatActionListener listener) {
		List<User> users = Util.getUsersFromData(msg.getData());
		listener.onLoginSuccess(users);
	}
}

