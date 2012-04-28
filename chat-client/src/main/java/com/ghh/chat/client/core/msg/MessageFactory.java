/*
 * MessageFactory.java
 *
 * Copyright(C) 2009, by ghh.
 */
package com.ghh.chat.client.core.msg;

import java.net.UnknownHostException;

import com.ghh.chat.client.core.UserContext;
import com.ghh.chat.common.Constants;
import com.ghh.chat.common.User;
import com.ghh.chat.common.msg.Message;
import com.ghh.chat.common.msg.MessageBuilder;

/**
 *
 * @author haihua.gu
 * Created on Sep 17, 2009
 */

public class MessageFactory {

	/**
	 * createLoginMsg
	 * @author haihua.gu 
	 * Create on Sep 17, 2009
	 * 
	 * @param msgbld
	 * @param user
	 * @return
	 */
	public static Message createLoginMsg(MessageBuilder msgbld, User user) {
		msgbld.buildProtocal(Constants.MSG_USER_LOGIN);
		msgbld.buildUserInfo(user);
		return msgbld.build();
	}
	/**
	 * createLogoutMsg
	 * @author haihua.gu 
	 * Create on Sep 21, 2009
	 * 
	 * @param msgbld
	 * @param user
	 * @return
	 */
	public static Message createLogoutMsg(MessageBuilder msgbld, User user) {
		msgbld.buildProtocal(Constants.MSG_USER_LOGOUT);
		msgbld.buildUserInfo(user);
		return msgbld.build();
	}
	
	/**
	 * createUpdateMsg
	 * @author haihua.gu 
	 * Create on Sep 24, 2009
	 * 
	 * @param msgbld
	 * @return
	 */
	public static Message createUpdateMsg(MessageBuilder msgbld) {
		msgbld.buildProtocal(Constants.MSG_USER_MODIFY);
		msgbld.buildUserAndAddress(UserContext.getCurrentClient());
		return msgbld.build();
	}
	
	/**
	 * createTextMsg
	 * @author haihua.gu 
	 * Create on Sep 22, 2009
	 * 
	 * @param text
	 * @param destn
	 * @return
	 */
	public static Message createTextMsg(String text, User destn) {
		try {
			MessageBuilder msgbld = new MessageBuilder(destn.getIp(), destn.getPort());
			msgbld.buildProtocal(Constants.MSG_USER_MESSAGE);
			msgbld.buildUserInfo(UserContext.getCustomization().getUser());
			msgbld.buildTextMsg(text);
			return msgbld.build();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * createNullMsg
	 * @author haihua.gu 
	 * Create on Sep 24, 2009
	 * 
	 * @param destn
	 * @return
	 */
	public static Message createNullMsg(User destn) {
		try {
			MessageBuilder msgbld = new MessageBuilder(destn.getIp(), destn.getPort());
			return msgbld.build();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Message createSessionKeeperMsg(MessageBuilder msgbld, User user) {
		msgbld.buildProtocal(Constants.MSG_MAINTAIN_SESSION);
		msgbld.buildUserInfo(user);
		return msgbld.build();
	}
}

