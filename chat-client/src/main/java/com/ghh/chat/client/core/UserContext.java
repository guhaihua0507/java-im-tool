package com.ghh.chat.client.core;

import com.ghh.chat.client.config.Customization;
import com.ghh.chat.client.config.NetworkSet;
import com.ghh.chat.common.User;


public class UserContext {

	private static NetworkSet network = new NetworkSet();
	private static Customization custm;
	
	public static NetworkSet getNetworkSet() {
		return network;
	}
	
	public static Customization getCustomization() {
		return custm;
	}
	
	public static User getCurrentClient() {
		if (custm == null)
			return null;
		return custm.getUser();
	}
	
	public static void setClientUser(String userID) {
		custm = new Customization(userID);
	}
}
