/*
 * MessageProcessor.java
 *
 * Copyright(C) 2009, by ghh.
 */
package com.ghh.chat.common.msg;

import com.ghh.chat.common.action.ChatActionListener;

/**
 *
 * @author haihua.gu
 * Created on Sep 20, 2009
 */

public interface MessageProcessor {
	
	public void process(ChatActionListener chatlistener, Message msg);
}

