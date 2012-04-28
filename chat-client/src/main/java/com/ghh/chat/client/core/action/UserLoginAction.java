package com.ghh.chat.client.core.action;

import com.ghh.chat.common.User;
import com.ghh.chat.common.action.ChatAction;
import com.ghh.chat.common.action.ChatActionListener;
import com.ghh.chat.common.msg.Message;
import com.ghh.chat.common.msg.Util;

public class UserLoginAction implements ChatAction {

	public void execute(Message msg, ChatActionListener listener) {
		User user = Util.getUserFromData(msg.getData());
		if (user != null)
			listener.onUserLogin(user);
	}
}
