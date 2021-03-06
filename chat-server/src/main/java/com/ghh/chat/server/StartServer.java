package com.ghh.chat.server;
import java.net.SocketException;
import java.util.concurrent.Executors;


/*
 * StartServer.java
 *
 * Copyright(C) 2009, by ghh.
 */

/**
 *
 * @author haihua.gu
 * Created on Sep 24, 2009
 */

public class StartServer {
	
	public static void main(String[] args) throws SocketException {
		ServerCore  core = new ServerCore();
		Executors.newSingleThreadExecutor().execute(new Console(core));
		core.start();
	}
}

