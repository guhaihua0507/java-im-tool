/*
 * MessageFactory.java
 *
 * Copyright(C) 2009, by ghh.
 */
package com.ghh.chat.server.msg;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.json.JSONException;

import com.ghh.chat.common.Constants;
import com.ghh.chat.common.User;
import com.ghh.chat.common.msg.Message;
import com.ghh.chat.common.msg.MessageBuilder;

/**
 *
 * @author haihua.gu
 * Created on Sep 21, 2009
 */

public class MessageFactory {
	
	public static MessageBuilder createNewBuilder(InetAddress ip, int port) {
		return new MessageBuilder(ip, port);
	}
	
	public static MessageBuilder createNewBuilder(String ip, int port) throws UnknownHostException {
		return new MessageBuilder(ip, port);
	}
	
	public static MessageBuilder createNewBuilder(User user) throws UnknownHostException {
		return createNewBuilder(user.getIp(), user.getPort());
	}
	/**
	 * createUserLoginMsg
	 * @author haihua.gu 
	 * Create on Sep 21, 2009
	 * 
	 * @param msgbld
	 * @param user
	 * @return
	 */
	public static Message createUserLoginMsg(MessageBuilder msgbld, User user) {
		msgbld.buildProtocal(Constants.MSG_USER_LOGIN);
		msgbld.buildUserAndAddress(user);
		return msgbld.build();
	}
	/**
	 * createLoginSuccessMsg
	 * @author haihua.gu 
	 * Create on Sep 21, 2009
	 * 
	 * @param msgbld
	 * @param user
	 * @return
	 */
	public static Message createLoginSuccessMsg(MessageBuilder msgbld, List<User> userlist) {
		try {
			msgbld.buildProtocal(Constants.MSG_USER_LOGIN_SUCCESS);
			msgbld.buildUserlist(userlist);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return msgbld.build();
	}
	/**
	 * createLoginFailMsg
	 * @author haihua.gu 
	 * Create on Sep 21, 2009
	 * 
	 * @param msgbld
	 * @param msg
	 * @return
	 */
	public static Message createLoginFailMsg(MessageBuilder msgbld, String msg) {
		msgbld.buildProtocal(Constants.MSG_USER_LOGIN_FAIL);
		msgbld.set(Constants.PROPERTY_CODE_TEXTMSG, msg);
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
		msgbld.buildUserAndAddress(user);
		return msgbld.build();
	}
	/**
	 * createModifyUserMsg
	 * @author haihua.gu 
	 * Create on Sep 21, 2009
	 * 
	 * @param msgbld
	 * @param user
	 * @return
	 */
	public static Message createModifyUserMsg(MessageBuilder msgbld, User user) {
		msgbld.buildProtocal(Constants.MSG_USER_MODIFY);
		msgbld.buildUserAndAddress(user);
		return msgbld.build();
	}
}

