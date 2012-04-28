/*
 * Conversation.java
 *
 * Copyright(C) 2009, by ghh.
 */
package com.ghh.chat.client.view;

import com.ghh.chat.client.core.ClientCore;
import com.ghh.chat.common.User;

/**
 *
 * @author haihua.gu
 * Created on Sep 22, 2009
 */

public class Conversation {

	private boolean hasUnReadMsg = false;
	
	private ClientCore core;
	private ChatWindow chatter;
	private User user;
	
	public Conversation(ClientCore core, User user) {
		this.core = core;
		this.user = user;
	}
	
	public void start() {
		if (chatter == null) {
			chatter = new ChatWindow(this);
		}
		hasUnReadMsg = false;
		chatter.setVisible(true);
	}
	
	public void receiveMsg(String msg) {
		if (chatter == null) {
			chatter = new ChatWindow(this);
		}
		hasUnReadMsg = true;
		chatter.appendTextMsg(msg);
	}

	public void sendTextMsg(String text) {
		try {
			core.sendTextMsg(text, user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean hasUnReadMsg() {
		if (hasUnReadMsg == false)
			return false;
		
		if (chatter == null) {
			return true;
		}
		if (chatter.isVisible()) {
			return false;
		} else {
			return true;
		}
	}
	
	public void dispose() {
		if (chatter != null) {
			chatter.dispose();
			chatter = null;
		}
		hasUnReadMsg = false;
	}
	
	public ChatWindow getChatter() {
		return chatter;
	}

	public User getUser() {
		return user;
	}
}

