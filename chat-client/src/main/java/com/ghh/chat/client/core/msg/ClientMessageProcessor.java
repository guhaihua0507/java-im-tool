/*
 * ClientMessageProcessor.java
 *
 * Copyright(C) 2009, by ghh.
 */
package com.ghh.chat.client.core.msg;

import com.ghh.chat.client.core.action.ChatActionFactory;
import com.ghh.chat.common.action.ActionInvoker;
import com.ghh.chat.common.action.ChatAction;
import com.ghh.chat.common.action.ChatActionListener;
import com.ghh.chat.common.msg.Message;
import com.ghh.chat.common.msg.MessageProcessor;

/**
 * 
 * @author haihua.gu Created on Sep 20, 2009
 */

public class ClientMessageProcessor implements MessageProcessor {

	public void process(ChatActionListener chatlistener, Message msg) {
		ChatAction chatAction = ChatActionFactory.getAction(msg
				.getProtocalCode());
		if (chatAction == null) {
			return;
		}
		new Thread(new ActionInvoker(chatAction, chatlistener, msg)).start();
	}
}
