package com.ghh.chat.common.msg;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ghh.chat.common.Constants;
import com.ghh.chat.common.User;

public class MessageBuilder {

	private InetAddress	ip;
	private int			port;
	private JSONObject	json;

	public MessageBuilder(InetAddress ip, int port) {
		this.ip = ip;
		this.port = port;
		this.json = new JSONObject();
	}

	public MessageBuilder(String ip, int port) throws UnknownHostException {
		this.ip = InetAddress.getByName(ip);
		this.port = port;
		this.json = new JSONObject();
	}

	public void set(String code, String data) {
		try {
			json.put(code, data);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void buildUserAndAddress(User user) {
		try {
			JSONObject u = new JSONObject();
			u.put(Constants.PROPERTY_CODE_USER_ID, user.getUserID());
			u.put(Constants.PROPERTY_CODE_USER_NAME, user.getName());
			u.put(Constants.PROPERTY_CODE_USER_ICON, user.getIcon());
			u.put(Constants.PROPERTY_CODE_ADDRESS_IP, user.getIp());
			u.put(Constants.PROPERTY_CODE_ADDRESS_PORT, user.getPort());
			json.put(Constants.PROPERTY_CODE_USER, u);
		} catch (JSONException e) {
			throw new RuntimeException("can not build user!", e);
		}
	}

	public void buildUserInfo(User user) {
		try {
			JSONObject u = new JSONObject();
			u.put(Constants.PROPERTY_CODE_USER_ID, user.getUserID());
			u.put(Constants.PROPERTY_CODE_USER_NAME, user.getName());
			u.put(Constants.PROPERTY_CODE_USER_ICON, user.getIcon());
			json.put(Constants.PROPERTY_CODE_USER, u);
		} catch (JSONException e) {
			throw new RuntimeException("can not build user!", e);
		}
	}
	
	public void buildProtocal(String protocal) {
		try {
			json.put(Constants.PROPERTY_CODE_PROTOCOL, protocal);
		} catch (JSONException e) {
			throw new RuntimeException("can not build protocal!", e);
		}
	}

	public void buildAddress(String ip, int port) {
		try {
			JSONObject js = new JSONObject();
			js.put(Constants.PROPERTY_CODE_ADDRESS_IP, ip);
			js.put(Constants.PROPERTY_CODE_ADDRESS_PORT, port);
			json.put(Constants.PROPERTY_CODE_ADDRESS, js);
		} catch (JSONException e) {
			throw new RuntimeException("can not build address!", e);
		}
	}
	
	public void buildUserlist(List<User> userlist) throws JSONException {
		JSONArray ja = new JSONArray();
		for (User u : userlist) {
			JSONObject jo = Util.toJSON(u);
			ja.put(jo);
		}
		json.put(Constants.PROPERTY_CODE_MUTI_USERS, ja);
	}
	
	public void buildTextMsg(String text) {
		try {
			json.put(Constants.PROPERTY_CODE_TEXTMSG, text);
		} catch (JSONException e) {
			throw new RuntimeException("can not build text msg!", e);
		}
	}
	
	public Message build() {
		try {
			byte[] data = json.toString().getBytes(Constants.CHARSET);
			Message msg = new Message(ip, port);
			msg.setData(data);
			return msg;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
}
