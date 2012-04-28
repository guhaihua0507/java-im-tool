/*
 * Message2.java
 *
 * Copyright(C) 2009, by ghh.
 */
package com.ghh.chat.common.msg;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.json.JSONObject;

import com.ghh.chat.common.Constants;

/**
 *
 * @author haihua.gu
 * Created on Sep 20, 2009
 */

public class Message {

	private InetAddress ip;
	private int port;
	private byte[] data;
	
	public Message(DatagramPacket datagram) {
		ip = datagram.getAddress();
		port = datagram.getPort();
		data = new byte[datagram.getLength()];
		System.arraycopy(datagram.getData(), 0, data, 0, datagram.getLength());
	}
	
	public Message(InetAddress ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	public void send(DatagramSocket socket) throws IOException {
		if (data == null) {
			data = new byte[0];
		}
		DatagramPacket datagram = new DatagramPacket(data, data.length, ip, port);
		socket.send(datagram);
	}
	
	public String getProtocalCode() {
		if (data == null)
			return null;
		
		try {
			JSONObject json = new JSONObject(new String(data, Constants.CHARSET));
			return json.optString(Constants.PROPERTY_CODE_PROTOCOL);
		} catch (Exception e) {
			return null;
		}
	}
	
	public InetAddress getIp() {
		return ip;
	}

	public void setIp(InetAddress ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
}

