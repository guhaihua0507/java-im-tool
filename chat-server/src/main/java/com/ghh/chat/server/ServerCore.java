/*
 * ServerCore.java
 *
 * Copyright(C) 2009, by ghh.
 */
package com.ghh.chat.server;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.ghh.chat.common.Constants;
import com.ghh.chat.common.User;
import com.ghh.chat.common.action.ChatActionListener;
import com.ghh.chat.common.msg.Message;
import com.ghh.chat.common.msg.MessageBuilder;
import com.ghh.chat.common.net.DataTransfer;
import com.ghh.chat.server.msg.MessageFactory;
import com.ghh.chat.server.msg.ServerMessageProcessor;

/**
 * 
 * @author haihua.gu Created on Sep 20, 2009
 */

public class ServerCore implements ChatActionListener {

	private int				port		= 7777;

	private List<Session>	sessionlist	= new ArrayList<Session>();
	private DataTransfer	dataTsf;
	private DatagramSocket	socket;
	private SessionMaintenance sm;

	public ServerCore() throws SocketException {
		initialize();
	}

	public ServerCore(int port) throws SocketException {
		this.port = port;
		initialize();
	}

	private void initialize() throws SocketException {
		socket = new DatagramSocket(port);
		dataTsf = new DataTransfer(this, new ServerMessageProcessor());
		dataTsf.setSocket(socket);
		sm = new SessionMaintenance(this);
		
		Logger.sysout("start...");
	}

	public void start() {
		Executors.newSingleThreadExecutor().execute(dataTsf);
		Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(sm,
				Constants.sessionTimeout, Constants.sessionTimeout / 2, TimeUnit.SECONDS);
	}

	public void removeUser(User user) {
		for (Session sn : sessionlist) {
			User u = sn.getUser();
			if (u.getUserID().equals(user.getUserID())) {
				sessionlist.remove(u);
				return;
			}
		}
	}

	private User getUserByID(String userID) {
		Session sn = getSessionByUserID(userID);
		return sn == null ? null : sn.getUser();
	}

	public Session getSessionByUserID(String userID) {
		for (Session sn : sessionlist) {
			User user = sn.getUser();
			if (user.getUserID().equals(userID))
				return sn;
		}
		return null;
	}
	
	public void onModifyUserInfo(User user) {
		for (Session sn : sessionlist) {
			User u = sn.getUser();
			try {
				MessageFactory.createModifyUserMsg(
						MessageFactory.createNewBuilder(u), user).send(socket);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		updateUser(user);
	}

	/**
	 * update user info
	 * @author haihua.gu 
	 * Create on Sep 22, 2009
	 * 
	 * @param user
	 */
	private void updateUser(User user) {
		User org = getUserByID(user.getUserID());
		if (org == null)
			return;

		org.setName(user.getName());
		org.setIcon(user.getIcon());
	}

	public void onUserLogin(User user) {
		try {
			if (canLogin(user)) {
				sendSuccessMsg(user);
				sessionlist.add(new Session(user));
				Logger.logout("user login --> " + getUserString(user));
				notifyOthersNewLogin(user);
			} else {
				MessageFactory.createLoginFailMsg(
						MessageFactory.createNewBuilder(user),
						"ID " + user.getUserID()
								+ " has logined, pls change another ID.").send(
						socket);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * send success login msg to user.<br>
	 * the main data is users list of signed users
	 * @author haihua.gu 
	 * Create on Sep 22, 2009
	 * 
	 * @param user
	 * @throws IOException
	 */
	private void sendSuccessMsg(User user) throws IOException {
		MessageFactory.createLoginSuccessMsg(
				MessageFactory.createNewBuilder(user), getUserList()).send(socket);
	}

	private List<User> getUserList() {
		List<User> users = new ArrayList<User>();
		for (Session sn : sessionlist) {
			users.add(sn.getUser());
		}
		return users;
	}
	
	private void notifyOthersNewLogin(User user) {
		for (Session sn : sessionlist) {
			User u = sn.getUser();
			if (u.getUserID().equals(user.getUserID()))
				continue;
			MessageBuilder builder;
			try {
				builder = MessageFactory.createNewBuilder(u.getIp(), u
						.getPort());
				Message msg = MessageFactory.createUserLoginMsg(builder, user);
				msg.send(socket);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * check if the new signed user can login.<br>
	 * if the ID already signed, return false
	 * @author haihua.gu 
	 * Create on Sep 22, 2009
	 * 
	 * @param user
	 * @return
	 */
	private boolean canLogin(User user) {
		for (Session sn : sessionlist) {
			User u = sn.getUser();
			if (u.getUserID().equals(user.getUserID())) {
				return false;
			}
		}
		return true;
	}

	public List<Session> getAllSession() {
		return this.sessionlist;
	}
	
	public void onUserLogout(User user) {
		int index = -1;
		for (int i = 0; i < sessionlist.size(); i++) {
			User u = sessionlist.get(i).getUser();
			if (!u.getUserID().equals(user.getUserID())) {
				try {
					MessageFactory.createLogoutMsg(
							MessageFactory.createNewBuilder(u), user).send(
							socket);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				index = i;
			}
		}
		if (index >= 0)
			sessionlist.remove(index);

		Logger.logout("user logout --> " + getUserString(user));
	}

	private String getUserString(User user) {
		return "ID=" + user.getUserID() + " Name=" + user.getName() + " IP="
				+ user.getIp() + ":" + user.getPort();
	}

	public void onReceiveUserMsg(User user, String text) {
		throw new RuntimeException("not support in server!");
	}
	
	public void onLoginFailed(String msg) {
		throw new RuntimeException("not support in server!");
	}

	public void onLoginSuccess(List<User> users) {
		throw new RuntimeException("not support in server!");
	}

	public void onReceiveMaintainMsg(User user) {
		Logger.logout("receive maintenance msg from " + user.getUserID());
		Session us = getSessionByUserID(user.getUserID());
		if (us == null)
			return;
		us.updateTime();
	}
}
