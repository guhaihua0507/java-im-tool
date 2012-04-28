/*
 * ChatWindow.java
 *
 * Copyright(C) 2009, by ghh.
 */
package com.ghh.chat.client.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

import com.ghh.chat.client.core.UserContext;
import com.ghh.chat.common.User;

/**
 * 
 * @author haihua.gu Created on Sep 22, 2009
 */

public class ChatWindow implements ActionListener {

	private JFrame			jframe	= new JFrame();
	private JTextArea		tx_show	= new JTextArea(10, 0);
	private JTextArea		tx_send	= new JTextArea(4, 0);
	private JButton			bt_send	= new JButton("send");
	private JButton			bt_exit	= new JButton("close");

	private Conversation	conversation;
	private User user;

	public ChatWindow(Conversation conversation) {
		this.conversation = conversation;
		user = conversation.getUser();
		initFrame();
	}

	/**
	 * initFrame
	 * 
	 * @author haihua.gu Create on Sep 22, 2009
	 * 
	 */
	private void initFrame() {
		jframe.setTitle("chat with " + user.getName() + "(" + user.getUserID()
				+ ")");
		jframe.setSize(420, 350);
		jframe.setResizable(false);
		/*
		 * put the window in the middle
		 */
		Toolkit tool = Toolkit.getDefaultToolkit();
		Dimension screen = tool.getScreenSize();
		jframe.setLocation((screen.width - 420) / 2, (screen.height - 350) / 2);

		jframe.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				ChatWindow.this.exit();
			}
		});

		bt_send.setPreferredSize(new Dimension(70, 30));
		bt_send.setActionCommand("send");
		bt_send.addActionListener(this);
		bt_exit.setPreferredSize(new Dimension(70, 30));
		bt_exit.setActionCommand("exit");
		bt_exit.addActionListener(this);

		tx_show.setLineWrap(true);
		tx_show.setFont(new Font("", Font.BOLD, 12));
		tx_show.setEditable(false);

		tx_send.setLineWrap(true);
		tx_send.setFont(new Font("", Font.BOLD, 12));
		tx_send.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent k) {
				if (k.isControlDown() && k.getKeyCode() == KeyEvent.VK_ENTER)
					send();
			}
		});

		JScrollPane scrol_show = new JScrollPane(tx_show);
		JScrollPane scrol_send = new JScrollPane(tx_send);
		/*
		 * split the to scroll pane
		 */
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				scrol_show, scrol_send);
		splitPane.setOneTouchExpandable(true);
		splitPane.setResizeWeight(0.5);
		splitPane.setDividerLocation(0.8);

		Container cp = jframe.getContentPane();
		cp.setLayout(new BorderLayout());

		JPanel p1 = new JPanel(new GridLayout(1, 1));
		p1.setBorder(BorderFactory.createEtchedBorder());
		p1.add(splitPane);

		JPanel box1 = new JPanel();
		box1.setBorder(BorderFactory.createEtchedBorder());
		box1.add(bt_send);
		box1.add(bt_exit);

		cp.add(BorderLayout.CENTER, p1);
		cp.add(BorderLayout.SOUTH, box1);
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand().equals("send")) {
			send();
			return;
		}
		if (ae.getActionCommand().equals("exit")) {
			exit();
			return;
		}
	}
	
	/**
	 * appendTextMsg
	 * @author haihua.gu 
	 * Create on Sep 22, 2009
	 * 
	 * @param text
	 */
	public void appendTextMsg(String text) {
		if (!tx_show.getText().equals("")) {
			tx_show.append("\n");
		}
		tx_show.append(user.getName() + " said:\n");
		tx_show.append(text);
		tx_show.setCaretPosition(tx_show.getDocument().getLength());
	}

	/**
	 * send
	 * @author haihua.gu 
	 * Create on Sep 22, 2009
	 *
	 */
	private void send() {
		String content = tx_send.getText();
		if (content.length() > 0) {
			if (!tx_show.getText().equals(""))
				tx_show.append("\n");
			
			User currentUser = UserContext.getCustomization().getUser();
			tx_show.append(currentUser.getName() + " said:\n");
			tx_show.append(content);
			tx_send.setText("");
			
			conversation.sendTextMsg(content);
		}
		
		tx_show.setCaretPosition(tx_show.getDocument().getLength());
	}

	private void exit() {
		conversation.dispose();
	}

	public boolean isVisible() {
		return jframe.isVisible();
	}
	
	public void dispose() {
		conversation = null;
		jframe.setVisible(false);
		jframe.dispose();
	}
	/**
	 * setVisible
	 * 
	 * @author haihua.gu Create on Sep 22, 2009
	 * 
	 * @param b
	 */
	public void setVisible(boolean b) {
		jframe.setVisible(b);
	}
}
