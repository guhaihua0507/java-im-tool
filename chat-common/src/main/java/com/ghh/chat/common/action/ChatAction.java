package com.ghh.chat.common.action;

import com.ghh.chat.common.msg.Message;

public interface ChatAction {

	public void execute(Message msg, ChatActionListener listener);
}
