package com.ghh.chat.client.core.action;

import java.util.HashMap;
import java.util.Map;

import com.ghh.chat.common.Constants;
import com.ghh.chat.common.action.ChatAction;


public class ChatActionFactory {

	private final static Map<String, ChatAction> actions = new HashMap<String, ChatAction>();
	
	static {
		actions.put(Constants.MSG_USER_LOGIN, new UserLoginAction());
		actions.put(Constants.MSG_USER_LOGIN_SUCCESS, new LoginSuccessAction());
		actions.put(Constants.MSG_USER_LOGIN_FAIL, new LoginFailedAction());
		actions.put(Constants.MSG_USER_LOGOUT, new UserLogoutAction());
		actions.put(Constants.MSG_USER_MODIFY, new ModifyUserAction());
		actions.put(Constants.MSG_USER_MESSAGE, new ReceiveTextMsgAction());
	}
	
	public static ChatAction getAction(String code) {
		return actions.get(code);
	}
}
