package com.ghh.chat.common.action;

import java.util.List;

import com.ghh.chat.common.User;

public interface ChatActionListener {

	public void onUserLogin(User user);
	
	public void onUserLogout(User user);
	
	public void onReceiveUserMsg(User user, String text);
	
	public void onModifyUserInfo(User user);
	
	public void onLoginSuccess(List<User> users);
	
	public void onLoginFailed(String msg);
	
	public void onReceiveMaintainMsg(User user);
}
