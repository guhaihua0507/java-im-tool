/*
 * ClientService.java
 *
 * Copyright(C) 2009, by ghh.
 */
package com.ghh.chat.client.core;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.ghh.chat.client.config.NetworkSet;
import com.ghh.chat.client.core.msg.ClientMessageProcessor;
import com.ghh.chat.client.core.msg.MessageFactory;
import com.ghh.chat.common.Constants;
import com.ghh.chat.common.User;
import com.ghh.chat.common.action.ChatActionListener;
import com.ghh.chat.common.msg.Message;
import com.ghh.chat.common.msg.MessageBuilder;
import com.ghh.chat.common.net.DataTransfer;

/**
 * 
 * @author haihua.gu Created on Sep 17, 2009
 */

public class ClientCore {

	private ChatActionListener	listener;
	private DataTransfer		dataTsf;
	private DatagramSocket		socket;

	private List<User>			users	= new ArrayList<User>();
	private ConnectionKeeper	ck;
	private ServerSessionKeeper	ssk;

	public ClientCore(ChatActionListener listener) throws SocketException {
		this.listener = listener;

		socket = new DatagramSocket();
		dataTsf = new DataTransfer(this.listener, new ClientMessageProcessor());
		dataTsf.setSocket(socket);
		ck = new ConnectionKeeper(this);
		ssk = new ServerSessionKeeper(this);
	}

	public void start() {
		Executors.newSingleThreadExecutor().execute(dataTsf);
	}

	public void maintenConnection() {
		System.out.println("========================start mainten");
		Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(ck, 0, 300, TimeUnit.SECONDS);
		Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(ssk, Constants.sessionTimeout / 3, Constants.sessionTimeout / 3, TimeUnit.SECONDS);
	}

	public DataTransfer getClientDataTransfer() {
		return this.dataTsf;
	}

	public List<User> getUserlist() {
		return users;
	}

	public void addUser(User user) {
		users.add(user);
	}

	public void addUser(List<User> ulist) {
		users.addAll(ulist);
	}

	public void remove(User user) {
		for (User u : users) {
			if (u.getUserID().equals(user.getUserID())) {
				users.remove(u);
				return;
			}
		}
	}

	public void updateUser(User user) {
		User u = getUserByID(user.getUserID());
		if (u == null)
			return;

		u.setName(user.getName());
		u.setIcon(user.getIcon());
	}

	public User getUserByID(String id) {
		for (User user : users) {
			if (user.getUserID().equals(id))
				return user;
		}
		return null;
	}

	/**
	 * send data
	 * 
	 * @author haihua.gu Create on Sep 17, 2009
	 * 
	 * @param msg
	 * @throws IOException
	 */
	public void send(Message msg) throws IOException {
		msg.send(socket);
	}

	/**
	 * sendUpdateMsg
	 * 
	 * @author haihua.gu Create on Sep 24, 2009
	 * @throws Exception
	 * @throws IOException
	 * 
	 */
	public void sendUpdateMsg() throws Exception {
		MessageFactory.createUpdateMsg(createDefaultMsgBuilder()).send(socket);
	}

	/**
	 * sendTextMsg
	 * 
	 * @author haihua.gu Create on Sep 22, 2009
	 * 
	 * @param text
	 * @param user
	 * @throws Exception
	 */
	public void sendTextMsg(String text, User user) throws Exception {
		Message msg = MessageFactory.createTextMsg(text, user);
		if (msg != null) {
			msg.send(socket);
		}
	}

	/**
	 * login
	 * 
	 * @author haihua.gu Create on Sep 17, 2009
	 * 
	 * @param user
	 * @throws Exception
	 */
	public void login(User user) throws Exception {
		MessageFactory.createLoginMsg(createDefaultMsgBuilder(), user).send(socket);
	}

	/**
	 * logout
	 * 
	 * @author haihua.gu Create on Sep 17, 2009
	 * 
	 * @param user
	 * @throws Exception
	 */
	public void logout(User user) throws Exception {
		MessageFactory.createLogoutMsg(createDefaultMsgBuilder(), user).send(socket);
	}

	/**
	 * sendNullMsg
	 * 
	 * @author haihua.gu Create on Sep 22, 2009
	 * 
	 * @param user
	 * @throws Exception
	 */
	public void sendNullMsg(User user) throws Exception {
		MessageFactory.createNullMsg(user).send(socket);
	}

	/**
	 * sendKeepSessionMsg
	 * 
	 * @author haihua.gu Create on Oct 8, 2009
	 * 
	 * @throws Exception
	 */
	public void sendKeepSessionMsg() throws Exception {
		User user = UserContext.getCurrentClient();
		MessageFactory.createSessionKeeperMsg(createDefaultMsgBuilder(), user).send(socket);
	}

	/**
	 * createDefaultMsgBuilder
	 * 
	 * @author haihua.gu Create on Sep 18, 2009
	 * 
	 * @return
	 * @throws Exception
	 */
	private MessageBuilder createDefaultMsgBuilder() throws Exception {
		NetworkSet network = UserContext.getNetworkSet();
		MessageBuilder msgBld = new MessageBuilder(network.getServerIp(), network.getServerPort());
		return msgBld;
	}
}
