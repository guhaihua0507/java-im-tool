package com.ghh.chat.common;

public class Constants {

	private Constants(){}
	
	public static final String CHARSET = "UTF-8";
	
	public static final long sessionTimeout = 300l;
	
	/*
	 * property
	 */
	public static final String PROPERTY_CODE_PROTOCOL = "0000";
	
	public static final String PROPERTY_CODE_ADDRESS = "0100";
	public static final String PROPERTY_CODE_ADDRESS_IP = "0101";
	public static final String PROPERTY_CODE_ADDRESS_PORT = "0102";
	
	public static final String PROPERTY_CODE_USER = "0200";
	public static final String PROPERTY_CODE_USER_ID = "0201";
	public static final String PROPERTY_CODE_USER_NAME = "0202";
	public static final String PROPERTY_CODE_USER_ICON = "0203";

	public static final String PROPERTY_CODE_MUTI_USERS = "0300";
	
	public static final String PROPERTY_CODE_TEXTMSG = "0400";
	/*
	 * msg type (protocol)
	 */
	public static final String MSG_SYS_ERROR_MESSAGE = "00";
	public static final String MSG_USER_LOGIN = "01";
	public static final String MSG_USER_LOGOUT = "02";
	public static final String MSG_USER_MESSAGE = "03";
	public static final String MSG_USER_MODIFY = "04";
	public static final String MSG_SERVER_MESSAGE = "05";
	public static final String MSG_USER_LOGIN_SUCCESS = "06";
	public static final String MSG_USER_LOGIN_FAIL = "07";
	public static final String MSG_MAINTAIN_SESSION = "08";
}
