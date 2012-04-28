/*
 * NetworkSet.java
 *
 * Copyright(C) 2009, by ghh.
 */
package com.ghh.chat.client.config;

import java.util.Properties;

/**
 * 
 * @author haihua.gu Created on Sep 18, 2009
 */

public class NetworkSet {

	private String	file	= Config.base_path + Config.network_file_name;

	private String	ip;
	private int		port;

	public NetworkSet() {
		initialize();
	}

	private void initialize() {
		Properties p = Config.loadProperties(file);
		ip = p.getProperty(Config.NAME_SERVER_IP, "127.0.0.1");
		port = Integer.parseInt(p.getProperty(Config.NAME_SERVER_PORT, "7777"));
	}

	public void save() {
		Properties p = new Properties();
		p.setProperty(Config.NAME_SERVER_IP, ip);
		p.setProperty(Config.NAME_SERVER_PORT, String.valueOf(port));
		Config.saveProperties(p, file);
	}

	public String getServerIp() {
		return ip;
	}

	public void setServerIp(String ip) {
		this.ip = ip;
	}

	public int getServerPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}
