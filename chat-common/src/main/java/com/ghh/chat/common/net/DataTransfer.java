/*
 * DataTransfer.java
 *
 * Copyright(C) 2009, by ghh.
 */
package com.ghh.chat.common.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import com.ghh.chat.common.action.ChatActionListener;
import com.ghh.chat.common.msg.Message;
import com.ghh.chat.common.msg.MessageProcessor;

/**
 * 
 * @author haihua.gu Created on Sep 20, 2009
 */

public class DataTransfer implements Runnable {
	private boolean				running	= false;
	private DatagramSocket		socket	= null;
	private ChatActionListener	chatlistener;
	private MessageProcessor	processor;

	public DataTransfer(ChatActionListener chatlistener,
			MessageProcessor processor) throws SocketException {
		this.chatlistener = chatlistener;
		this.processor = processor;
		running = true;
	}

	public DataTransfer(ChatActionListener chatlistener,
			MessageProcessor processor, int port) throws SocketException {
		this(chatlistener, processor);
		socket = new DatagramSocket(port);
	}

	public void setSocket(DatagramSocket socket) {
		this.socket = socket;
	}

	public DatagramSocket getSocket() {
		return this.socket;
	}

	public void run() {
		try {
			if (socket == null) {
				throw new RuntimeException("socket is not initilazed!");
			}
			while (running) {
				DatagramPacket data = new DatagramPacket(new byte[1024], 1024);
				socket.receive(data);
				processor.process(chatlistener, new Message(data));
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		running = false;
	}
}
