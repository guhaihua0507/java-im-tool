/*
 * ActionFactory.java
 *
 * Copyright(C) 2009, by ghh.
 */
package com.ghh.chat.server.action;

import java.util.HashMap;
import java.util.Map;

import com.ghh.chat.common.Constants;
import com.ghh.chat.common.action.ChatAction;

/**
 *
 * @author haihua.gu
 * Created on Sep 20, 2009
 */

public class ActionFactory {

private final static Map<String, ChatAction> actions = new HashMap<String, ChatAction>();
	
	static {
		actions.put(Constants.MSG_USER_LOGIN, new UserLoginAction());
		actions.put(Constants.MSG_USER_LOGOUT, new UserLogoutAction());
		actions.put(Constants.MSG_USER_MODIFY, new ModifyUserAction());
		actions.put(Constants.MSG_MAINTAIN_SESSION, new SessionMaintenanceAction());
	}
	
	public static ChatAction getAction(String code) {
		return actions.get(code);
	}
}

