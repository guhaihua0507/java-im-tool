package com.ghh.chat.common.action;

import com.ghh.chat.common.msg.Message;

public class ActionInvoker implements Runnable {

	private ChatAction chatAction;
	private ChatActionListener listener;
	private Message msg;

	public ActionInvoker(ChatAction chatAction, ChatActionListener listener, Message msg) {
		this.chatAction = chatAction;
		this.listener = listener;
		this.msg = msg;
	}
	
	public void run() {
		chatAction.execute(msg, listener);
	}
}
