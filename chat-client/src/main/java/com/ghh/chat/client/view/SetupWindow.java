/*
 * SetupWindow.java
 *
 * Copyright(C) 2009, by ghh.
 */
package com.ghh.chat.client.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

import com.ghh.chat.client.config.Config;
import com.ghh.chat.client.config.NetworkSet;
import com.ghh.chat.client.core.ClientCore;
import com.ghh.chat.client.core.UserContext;
import com.ghh.chat.common.User;

/**
 * 
 * @author haihua.gu Created on Sep 23, 2009
 */

public class SetupWindow {
	private boolean			loginned	= false;
	private int				iconIndex	= 1;

	private JFrame			frame;
	private JTabbedPane		tabbedPane;

	private JTextField		tx_ip;
	private JTextField		tx_pt;

	private JTextField		tx_name;
	private List<JLabel>	iconlist;

	/*
	 * icon border
	 */
	private Border			bd_icon1	= BorderFactory.createEtchedBorder();
	private Border			bd_icon2	= BorderFactory.createLineBorder(
												Color.BLUE, 2);
	
	private ClientCore core;

	/**
	 * ¹¹Ôìº¯Êý
	 * 
	 */
	public SetupWindow(boolean loginned, ClientCore core) {
		this.loginned = loginned;
		this.core = core;
		initialize();
	}

	private void initialize() {
		frame = new JFrame("setup");
		frame.setSize(410, 300);
		frame.setResizable(false);
		/*
		 * locate in the middle
		 */
		Toolkit tool = Toolkit.getDefaultToolkit();
		Dimension screen = tool.getScreenSize();
		frame.setLocation((screen.width - frame.getWidth()) / 2,
				(screen.height - frame.getHeight()) / 2);

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				frame.dispose();
			}
		});

		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Server Set", createNetworkPanel());
		if (loginned) {
			User user = UserContext.getCurrentClient();
			iconIndex = Integer.parseInt(user.getIcon()) - 1;
			tabbedPane.addTab("Use info", createUserInfoPanel());
			tx_name.setText(user.getName());
		}

		frame.add(tabbedPane, BorderLayout.CENTER);
		frame.setVisible(true);
	}

	/**
	 * createNetworkPanel
	 * 
	 * @author haihua.gu Create on Sep 23, 2009
	 * 
	 * @return
	 */
	private JComponent createNetworkPanel() {
		JPanel jpanel = new JPanel();
		jpanel.setLayout(new BorderLayout());
		jpanel.setBorder(BorderFactory.createEtchedBorder());

		JPanel p1 = new JPanel();

		tx_ip = new JTextField(12);
		tx_pt = new JTextField(5);

		p1.add(new JLabel("server:"));
		p1.add(tx_ip);
		p1.add(tx_pt);

		JPanel p2 = new JPanel();

		JButton bt_ok = new JButton("OK");
		JButton bt_cl = new JButton("Cancel");
		bt_ok.setActionCommand("ok");
		bt_cl.setActionCommand("cancel");

		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (loginned) {
					frame.dispose();
					return;
				}
				if ("ok".equals(e.getActionCommand())) {
					if (saveNetworkSet()) {
						frame.dispose();
					}
					return;
				}
				if ("cancel".equals(e.getActionCommand())) {
					frame.dispose();
					return;
				}
			}
		};

		bt_ok.addActionListener(listener);
		bt_cl.addActionListener(listener);

		p2.add(bt_ok);
		p2.add(bt_cl);
		jpanel.add(BorderLayout.CENTER, p1);
		jpanel.add(BorderLayout.SOUTH, p2);

		initNetworkValue();

		if (loginned) {
			tx_ip.setEditable(false);
			tx_pt.setEditable(false);
		}

		return jpanel;
	}

	private void initNetworkValue() {
		NetworkSet network = UserContext.getNetworkSet();
		tx_ip.setText(network.getServerIp());
		tx_pt.setText(String.valueOf(network.getServerPort()));
	}

	/**
	 * saveNetworkSet
	 * 
	 * @author haihua.gu Create on Sep 23, 2009
	 * 
	 */
	private boolean saveNetworkSet() {
		String serverIP = tx_ip.getText();
		String strPort = tx_pt.getText();

		/*
		 * check ip format
		 */
		if (!serverIP.matches("([\\d]{1,3}\\.){3}[\\d]{1,3}")) {
			JOptionPane.showMessageDialog(frame, "ip format is not correct!",
					"", JOptionPane.OK_OPTION);
			return false;
		}
		if (strPort.matches("[\\d]+") == false) {
			JOptionPane.showMessageDialog(frame, "port must be number", "",
					JOptionPane.OK_OPTION);
			return false;
		}

		int port = Integer.parseInt(strPort);
		if (port > 65535 || port < 1024) {
			JOptionPane.showMessageDialog(frame,
					"port must between 1024 and 65535", "",
					JOptionPane.OK_OPTION);
			return false;
		}

		NetworkSet network = UserContext.getNetworkSet();
		network.setServerIp(serverIP);
		network.setPort(port);
		network.save();

		return true;
	}

	/**
	 * createUserInfoPanel
	 * 
	 * @author haihua.gu Create on Sep 23, 2009
	 * 
	 * @return
	 */
	/**
	 * createUserInfoPanel
	 * 
	 * @author haihua.gu Create on Sep 24, 2009
	 * 
	 * @return
	 */
	private JComponent createUserInfoPanel() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEtchedBorder());
		panel.setLayout(new BorderLayout());

		/*
		 * user name
		 */
		JPanel p1 = new JPanel();
		tx_name = new JTextField(15);
		p1.add(new JLabel("name: "));
		p1.add(tx_name);

		/*
		 * icon
		 */
		JPanel p2 = new JPanel(new GridLayout(0, 6));
		iconlist = getIconLabel();

		MouseAdapter mouseclick = new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				JLabel icon = (JLabel) me.getSource();
				selectIcon(icon);
			}
		};
		for (int i = 0; i < iconlist.size(); i++) {
			JLabel icon = iconlist.get(i);
			if (iconIndex == i)
				icon.setBorder(bd_icon2);
			else
				icon.setBorder(bd_icon1);
			icon.addMouseListener(mouseclick);
			p2.add(icon);
		}

		panel.add(BorderLayout.NORTH, p1);

		JScrollPane scroll_icon = new JScrollPane(p2);
		scroll_icon.setBorder(BorderFactory.createTitledBorder("select icon"));
		panel.add(BorderLayout.CENTER, scroll_icon);
		/*
		 * buttons
		 */
		JPanel p3 = new JPanel();
		JButton bt_ok = new JButton("Ok");
		JButton bt_cl = new JButton("Cancel");
		bt_ok.setActionCommand("ok");
		bt_cl.setActionCommand("cancel");
		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ("ok".equals(e.getActionCommand())) {
					if (!saveUserSet()) {
						return;
					} else {
						sendUpdateMsg();
					}
					frame.dispose();
					return;
				}

				if ("cancel".equals(e.getActionCommand())) {
					frame.dispose();
					return;
				}
			}
		};
		bt_ok.addActionListener(listener);
		bt_cl.addActionListener(listener);
		
		p3.add(bt_ok);
		p3.add(bt_cl);
		
		panel.add(BorderLayout.SOUTH, p3);

		return panel;
	}

	private List<JLabel> getIconLabel() {
		List<JLabel> labellist = new ArrayList<JLabel>();
		for (int i = 0; i < 24; i++) {
			JLabel icon = new JLabel(new ImageIcon(Config.icon_path + (i + 1)
					+ ".jpg"));
			labellist.add(icon);
		}
		return labellist;
	}

	private void selectIcon(JLabel icon) {
		JLabel pre = iconlist.get(iconIndex);
		pre.setBorder(bd_icon1);
		iconIndex = iconlist.indexOf(icon);
		icon.setBorder(bd_icon2);
	}

	/**
	 * saveUserSet
	 * @author haihua.gu 
	 * Create on Sep 24, 2009
	 * 
	 * @return
	 */
	private boolean saveUserSet() {
		String name = tx_name.getText();
		if (name == null || name.length() == 0) {
			JOptionPane.showMessageDialog(frame, "please input your name", "",
					JOptionPane.OK_OPTION);
			return false;
		}
		if (name.length() >= 20) {
			JOptionPane.showMessageDialog(frame, "name's length should be less than 20", "",
					JOptionPane.OK_OPTION);
			return false;
		}
		
		User user = UserContext.getCurrentClient();
		user.setName(name);
		user.setIcon(String.valueOf(iconIndex + 1));
		UserContext.getCustomization().saveCustomization();
		
		return true;
	}
	
	private void sendUpdateMsg() {
		try {
			core.sendUpdateMsg();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(frame, "can not send update msg!", "error",
					JOptionPane.ERROR);
		}
	}

}
