/*
 * Config.java
 *
 * Copyright(C) 2009, by ghh.
 */
package com.ghh.chat.client.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 
 * @author haihua.gu Created on Sep 18, 2009
 */

public class Config {

	public static final String	base_path;
	public static final String	icon_path;
	public static final String	network_file_name	= "network.cfg";
	public static final String	user_file_name		= "user.cfg";

	public static final String	NAME_SERVER_IP		= "server_ip";
	public static final String	NAME_SERVER_PORT	= "server_port";
	public static final String	NAME_USER_NAME		= "user_name";
	public static final String	NAME_USER_ICON		= "user_icon";
	static {
		File base = new File(Config.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParentFile().getParentFile();
		try {
			base_path = base.getCanonicalPath() + File.separator;
			icon_path = base_path + "icon" + File.separator;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * loadProperties
	 * 
	 * @author haihua.gu Create on Sep 18, 2009
	 * 
	 * @param filedir
	 * @return
	 */
	public static Properties loadProperties(String filedir) {
		File file = new File(filedir);
		Properties properties = new Properties();
		try {
			if (file.exists() && file.isFile()) {
				FileInputStream in = new FileInputStream(file);
				properties.load(in);
				in.close();
			}
		} catch (Exception e) {
			System.out.println("can not load config file");
			e.printStackTrace();
		}
		return properties;
	}

	/**
	 * saveProperties
	 * 
	 * @author haihua.gu Create on Sep 18, 2009
	 * 
	 * @param p
	 * @param filedir
	 * @return
	 */
	public static void saveProperties(Properties p, String filedir) {
		try {
			File file = new File(filedir);
			if (!file.exists()) {
				file.getParentFile().mkdirs();
			}
			FileOutputStream out = new FileOutputStream(file);
			p.store(out, null);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
