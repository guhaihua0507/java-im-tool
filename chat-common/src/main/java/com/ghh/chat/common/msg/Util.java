/*
 * Util.java
 *
 * Copyright(C) 2009, by ghh.
 */
package com.ghh.chat.common.msg;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ghh.chat.common.Constants;
import com.ghh.chat.common.User;

/**
 * 
 * @author haihua.gu Created on Sep 20, 2009
 */

public class Util {

	/**
	 * parse2User
	 * @author haihua.gu 
	 * Create on Sep 21, 2009
	 * 
	 * @param json
	 * @return
	 */
	public static User parse2User(JSONObject json) {
		User user = new User();
		user.setUserID(json.optString(Constants.PROPERTY_CODE_USER_ID));
		user.setName(json.optString(Constants.PROPERTY_CODE_USER_NAME));
		user.setIcon(json.optString(Constants.PROPERTY_CODE_USER_ICON));
		user.setIp(json.optString(Constants.PROPERTY_CODE_ADDRESS_IP));
		user.setPort(json.optInt(Constants.PROPERTY_CODE_ADDRESS_PORT));
		return user;
	}
	
	/**
	 * getUserFromData
	 * @author haihua.gu 
	 * Create on Sep 21, 2009
	 * 
	 * @param data
	 * @return
	 */
	public static User getUserFromData(byte[] data) {
		if (data == null || data.length == 0)
			return null;
		
		try {
			User user = null;
			JSONObject json = new JSONObject(new String(data, Constants.CHARSET));
			JSONObject uj = json.optJSONObject(Constants.PROPERTY_CODE_USER);
			if (uj == null)
				return null;
			user = parse2User(uj);
			
			JSONObject addrjson = json.optJSONObject(Constants.PROPERTY_CODE_ADDRESS);
			if (addrjson != null) {
				user.setIp(addrjson.optString(Constants.PROPERTY_CODE_ADDRESS_IP));
				user.setPort(addrjson.optInt(Constants.PROPERTY_CODE_ADDRESS_PORT, 0));
			}
			return user;
		} catch (Exception e) {
			throw new RuntimeException("can not parse to json object!", e);
		}
	}
	
	/**
	 * getUsersFromData
	 * @author haihua.gu 
	 * Create on Sep 21, 2009
	 * 
	 * @param data
	 * @return
	 */
	public static List<User> getUsersFromData(byte[] data) {
		if (data == null || data.length == 0)
			return null;
		
		try {
			JSONObject json = new JSONObject(new String(data, Constants.CHARSET));
			JSONArray jus = json.optJSONArray(Constants.PROPERTY_CODE_MUTI_USERS);
			if (jus == null)
				return null;
			List<User> userlist = new ArrayList<User>();
			for(int i = 0; i < jus.length(); i++) {
				JSONObject usr = jus.optJSONObject(i);
				if (usr != null) {
					User user = parse2User(usr);
					if (user != null) {
						userlist.add(user);
					}
				}
			}
			return userlist;
		} catch (Exception e) {
			throw new RuntimeException("can not parse to json object!");
		}
	}
	
	/**
	 * toJSON
	 * @author haihua.gu 
	 * Create on Sep 21, 2009
	 * 
	 * @param user
	 * @return
	 * @throws JSONException
	 */
	public static JSONObject toJSON(User user) throws JSONException {
		JSONObject jsonu = new JSONObject();
		jsonu.put(Constants.PROPERTY_CODE_USER_ID, user.getUserID());
		jsonu.put(Constants.PROPERTY_CODE_USER_NAME, user.getName());
		jsonu.put(Constants.PROPERTY_CODE_USER_ICON, user.getIcon());
		jsonu.put(Constants.PROPERTY_CODE_ADDRESS_IP, user.getIp());
		jsonu.put(Constants.PROPERTY_CODE_ADDRESS_PORT, user.getPort());
		return jsonu;
	}
	/**
	 * getMsg
	 * @author haihua.gu 
	 * Create on Sep 21, 2009
	 * 
	 * @param data
	 * @param code
	 * @return
	 */
	public static String getMsg(byte[] data, String code) {
		JSONObject json;
		try {
			json = new JSONObject(new String(data, Constants.CHARSET));
			return json.optString(code);
		} catch (Exception e) {
			throw new RuntimeException("can not parse to json object!");
		}
	}
}
