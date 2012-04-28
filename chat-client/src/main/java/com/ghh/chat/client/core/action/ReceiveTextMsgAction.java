/*
 * ReceiveTextMsgAction.java
 *
 * Copyright(C) 2009, by ghh.
 */
package com.ghh.chat.client.core.action;

import com.ghh.chat.common.Constants;
import com.ghh.chat.common.User;
import com.ghh.chat.common.action.ChatAction;
import com.ghh.chat.common.action.ChatActionListener;
import com.ghh.chat.common.msg.Message;
import com.ghh.chat.common.msg.Util;

/**
 *
 * @author haihua.gu
 * Created on Sep 22, 2009
 */

public class ReceiveTextMsgAction implements ChatAction {

	public void execute(Message msg, ChatActionListener listener) {
		User user = Util.getUserFromData(msg.getData());
		String text = Util.getMsg(msg.getData(), Constants.PROPERTY_CODE_TEXTMSG);
		listener.onReceiveUserMsg(user, text);
	}
}

