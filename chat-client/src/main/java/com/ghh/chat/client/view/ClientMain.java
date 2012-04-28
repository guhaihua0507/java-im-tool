package com.ghh.chat.client.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.SocketException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import com.ghh.chat.client.core.ClientCore;
import com.ghh.chat.client.core.UserContext;
import com.ghh.chat.common.User;
import com.ghh.chat.common.action.ChatActionListener;

public class ClientMain implements ChatActionListener {

	private boolean						loginning		= false;
	private boolean						loginned		= false;

	private final int					width			= 250;
	private final int					height			= 600;
	private JFrame						jframe			= new JFrame(
																"chat main frame");

	/* login panel components */
	private JPanel						loginPanel;
	private JLabel						lb_account;
	private JLabel						lb_psd;
	private JTextField					tx_account;
	private JTextField					tx_psd;
	private JButton						bt_in;

	/* user list panel components */
	private JPanel						userPanel;
	private JList						jlist;
	private DefaultComboBoxModel		defaultModel	= new DefaultComboBoxModel();

	private ClientCore					core;
	private Map<String, Conversation>	csMap			= new HashMap<String, Conversation>();

	public ClientMain() {
		/*
		 * init view
		 */
		initFrame();
		initLoginFace();
		jframe.setVisible(true);

		try {
			core = new ClientCore(this);
			core.start();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	private void initFrame() {
		Toolkit tool = Toolkit.getDefaultToolkit();
		Dimension screen = tool.getScreenSize();

		jframe.setSize(new Dimension(width, height));
		jframe.setLocation((int) screen.getWidth() - width - 10, 10);
		jframe.setResizable(false);

		/* add frame closing listener */
		jframe.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				ClientMain.this.windowClosed();
			}
		});

		jframe.setLayout(new GridLayout(1, 1));
		setMenu();
	}

	/**
	 * setMenu
	 * 
	 * @author haihua.gu Create on Sep 18, 2009
	 * 
	 */
	private void setMenu() {
		JMenuBar mBar = new JMenuBar();
		mBar.setOpaque(true);

		JMenu menu1 = new JMenu("Tools");
		JMenuItem mItem1 = new JCheckBoxMenuItem("Always on top");
		JMenuItem mItem2 = new JMenuItem("Options");
		// JMenuItem mItem3 = new JMenuItem("close");

		mItem1.setActionCommand("top");
		mItem2.setActionCommand("set");
		// mItem3.setActionCommand("exit");

		ActionListener menuClicklistener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("set")) {
					new SetupWindow(loginned, core);
					return;
				}
				if (e.getActionCommand().equals("top")) {
					if (((JCheckBoxMenuItem) e.getSource()).isSelected()) {
						jframe.setAlwaysOnTop(true);
					} else {
						jframe.setAlwaysOnTop(false);
					}
					return;
				}
				// if (e.getActionCommand().equals("exit")) {
				// windowClosed();
				// return;
				// }
			}
		};
		mItem1.addActionListener(menuClicklistener);
		mItem2.addActionListener(menuClicklistener);
		// mItem3.addActionListener(menuClicklistener);

		menu1.add(mItem1);
		menu1.addSeparator();
		menu1.add(mItem2);
		// menu1.addSeparator();
		// menu1.add(mItem3);

		mBar.add(menu1);

		jframe.setJMenuBar(mBar);
	}

	/**
	 * initLoginFace
	 * 
	 * @author haihua.gu Create on Sep 18, 2009
	 * 
	 */
	private void initLoginFace() {
		loginPanel = new JPanel(new GridLayout(0, 1));
		jframe.add(loginPanel);

		lb_account = new JLabel("Account:");
		lb_psd = new JLabel("Password:");
		tx_account = new JTextField();
		tx_psd = new JTextField();
		bt_in = new JButton("Sign in");

		tx_psd.setEnabled(false);

		bt_in.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});

		JPanel p1 = new JPanel(new GridLayout(0, 1));

		tx_account.setPreferredSize(new Dimension(200, 20));
		tx_psd.setPreferredSize(new Dimension(200, 20));

		p1.add(createTextPanel(lb_account, tx_account));
		p1.add(createTextPanel(lb_psd, tx_psd));

		bt_in.setSize(100, 25);
		p1.add(createButtonJPanel(bt_in, new FlowLayout(FlowLayout.CENTER)));

		JPanel p = new JPanel();
		p.add(p1);

		loginPanel.setLayout(new GridLayout(0, 1));
		loginPanel.add(new JPanel());
		loginPanel.add(p);
		loginPanel.add(new JPanel());
	}

	private JPanel createTextPanel(JLabel lb, JTextField tx) {
		JPanel jp = new JPanel(new GridLayout(0, 1));
		jp.add(lb);
		jp.add(tx);
		return jp;
	}

	private JPanel createButtonJPanel(Component comp, LayoutManager layout) {
		JPanel jp = new JPanel(layout);
		jp.add(comp);
		return jp;
	}

	/**
	 * initializeWindow
	 * 
	 * @author haihua.gu <br>
	 *         Create on Sep 18, 2009
	 * 
	 */
	private void initializeWindow() {
		userPanel = new JPanel(new GridLayout(1, 1));
		jframe.add(userPanel);

		jlist = new JList(defaultModel);
		jlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jlist.setCellRenderer(new UserCellRenderer());
		jlist.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				ClientMain.this.startConversation(e);
			}
		});

		jlist.setBorder(BorderFactory.createTitledBorder("online"));
		userPanel.add(new JScrollPane(jlist));

		SwingUtilities.updateComponentTreeUI(jframe);
	}

	/**
	 * double click to start a chat
	 * 
	 * @param e
	 */
	private void startConversation(MouseEvent e) {
		if (e.getSource() == jlist) {
			if (e.getClickCount() == 2) {
				Conversation conv = (Conversation) jlist.getSelectedValue();
				if (conv != null) {
					conv.start();
					jlist.updateUI();
				}
			}
		}
	}

	/**
	 * login
	 * 
	 * @author haihua.gu Create on Sep 18, 2009
	 * 
	 */
	private void login() {
		String userID = tx_account.getText();
		if (userID == null) {
			return;
		}

		if (!userID.matches("[0-9]{8}")) {
			JOptionPane.showMessageDialog(jframe,
					"account must be number and the length is 8!", "warnning",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		tx_account.setEnabled(false);
		bt_in.setEnabled(false);

		UserContext.setClientUser(userID);
		try {
			loginning = true;
			core.login(UserContext.getCustomization().getUser());

			Executors.newScheduledThreadPool(1).schedule(new Runnable() {
				public void run() {
					if (!loginned) {
						if (loginning) {
							loginning = false;
							alertError("Login timeout, pls check your server set");

							tx_account.setEnabled(true);
							bt_in.setEnabled(true);
						}
					}
				}
			}, 20, TimeUnit.SECONDS);

		} catch (Exception e) {
			loginning = false;
			e.printStackTrace();
			alertError("can not login user " + userID);
		}
	}

	private void updateListUI() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				jlist.updateUI();
			}
		});
	}
	
	/**
	 * exit app
	 */
	private void windowClosed() {
		if (loginned) {
			try {
				core.logout(UserContext.getCustomization().getUser());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		jframe.dispose();
		System.exit(0);
	}

	public void onModifyUserInfo(User user) {
		core.updateUser(user);
		updateListUI();
	}

	public void onReceiveUserMsg(User user, String text) {
		User u = core.getUserByID(user.getUserID());
		if (u == null)
			return;

		Conversation conv = csMap.get(user.getUserID());
		if (conv != null) {
			conv.receiveMsg(text);
		}

		updateListUI();
	}

	public void onUserLogin(User user) {
		core.addUser(user);
		Conversation conv = new Conversation(core, user);
		csMap.put(user.getUserID(), conv);
		defaultModel.addElement(conv);

		try {
			core.sendNullMsg(user);
		} catch (Exception e) {
			System.out.println("can not send null msg");
		}

		System.out.println("new logined :" + user.getUserID() + ":"
				+ user.getIp() + ":" + user.getPort());
	}

	public void onUserLogout(User user) {
		core.remove(user);
		removeFromModelList(user.getUserID());
		Conversation conv = csMap.get(user.getUserID());
		if (conv != null) {
			csMap.remove(user.getUserID());
			conv.dispose();
		}

		System.out.println("log out user " + user.getUserID() + ":"
				+ user.getIp() + ":" + user.getPort());
	}

	private void removeFromModelList(String userID) {
		for (int i = 0; i < defaultModel.getSize(); i++) {
			Conversation conv = (Conversation) defaultModel.getElementAt(i);
			if (userID.equals(conv.getUser().getUserID())) {
				defaultModel.removeElementAt(i);
				return;
			}
		}
	}

	public void onLoginFailed(String msg) {
		if (!loginning) {
			return;
		}

		loginning = false;
		alertError(msg);

		tx_account.setEnabled(true);
		bt_in.setEnabled(true);
	}

	public void onLoginSuccess(List<User> users) {
		if (!loginning) {
			return;
		}

		loginning = false;
		loginned = true;

		core.addUser(users);
		/*
		 * update UI
		 */
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				jframe.remove(loginPanel);
				SwingUtilities.updateComponentTreeUI(jframe);
				initializeWindow();

				for (User u : core.getUserlist()) {
					Conversation conv = new Conversation(core, u);
					csMap.put(u.getUserID(), conv);
					defaultModel.addElement(conv);

					try {
						core.sendNullMsg(u);
					} catch (Exception e) {
						System.out.println("can not send null msg");
					}

					System.out.println(u.getUserID() + ":" + u.getIp() + ":"
							+ u.getPort());
				}
			}
		});
		core.maintenConnection();
	}

	public void onReceiveMaintainMsg(User user) {
		throw new RuntimeException("not support in client!");
	}

	private void alertError(String msg) {
		JOptionPane.showMessageDialog(jframe, msg, "error",
				JOptionPane.ERROR_MESSAGE);
	}
}
