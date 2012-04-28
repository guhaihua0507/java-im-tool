/*
 * Customization.java
 *
 * Copyright(C) 2009, by ghh.
 */
package com.ghh.chat.client.config;

import java.io.File;
import java.util.Properties;

import com.ghh.chat.common.User;

/**
 *
 * @author haihua.gu
 * Created on Sep 20, 2009
 */

public class Customization {

	private String filePath = Config.base_path + "users" + File.separator;
	private User user;
	
	public Customization(String userID) {
		initialize(userID);
	}
	
	private void initialize(String userID) {
		filePath = filePath + userID + File.separator + Config.user_file_name;
		Properties p = Config.loadProperties(filePath);
		user = new User();
		user.setUserID(userID);
		user.setName(p.getProperty(Config.NAME_USER_NAME, userID));
		user.setIcon(p.getProperty(Config.NAME_USER_ICON, "1"));
	}
	
	public void saveCustomization() {
		Properties p = new Properties();
		p.setProperty(Config.NAME_USER_NAME, user.getName());
		p.setProperty(Config.NAME_USER_ICON, user.getIcon());
		Config.saveProperties(p, filePath);
	}
	
	public User getUser() {
		return user;
	}
}

