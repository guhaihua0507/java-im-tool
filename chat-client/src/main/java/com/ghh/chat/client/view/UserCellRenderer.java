/*
 * UserCellRenderer.java
 *
 * Copyright(C) 2009, by ghh.
 */
package com.ghh.chat.client.view;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JList;

import com.ghh.chat.client.config.Config;
import com.ghh.chat.common.User;

/**
 * 
 * @author haihua.gu Created on Sep 22, 2009
 */

public class UserCellRenderer extends DefaultListCellRenderer {
	private static final long	serialVersionUID	= -231479620399068107L;

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

		Conversation conversation = (Conversation) value;
		User user = conversation.getUser();

		String strIcon = Config.icon_path + user.getIcon() + ".jpg";
		
		ImageIcon icon = new ImageIcon(strIcon);

		this.setIcon(icon);

		if (conversation.hasUnReadMsg()) {
			this.setText("<html><font color='red'>" + user.getName() + "</fon></html>");
		} else {
			this.setText("<html><font color='black'>" + user.getName() + "</fon></html>");
		}

		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}
		return this;
	}
}
