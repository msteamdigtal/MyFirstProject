package com.comtrade.view.admin;

import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.comtrade.constants.DbConstraints;
import com.comtrade.constants.HttpConstraints;
import com.comtrade.controller.FrontController;
import com.comtrade.database.entities.User;
import com.comtrade.transfer.TransferClass;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;

public class EditUserPanel extends JPanel {
	

	/**
	 * Create the panel.
	 */
	public EditUserPanel(User user, String pathToImage, JPanel panel, DefaultTableModel model, int modelRow, List<User> users) {
		
		setBounds(0, 0, 537, 150);
		setBackground(Color.WHITE);
		setLayout(null);
		
		JLabel lblNameSurname = new JLabel(user.getUserName()+" "+user.getUserSurname());
		lblNameSurname.setHorizontalAlignment(SwingConstants.CENTER);
		lblNameSurname.setForeground(Color.BLACK);
		lblNameSurname.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNameSurname.setBounds(20, 20, 240, 24);
		add(lblNameSurname);
		
		JLabel lblChange = new JLabel("Change "+user.getUserName()+"'s position: ");
		lblChange.setHorizontalAlignment(SwingConstants.CENTER);
		lblChange.setBounds(20, 60, 240, 24);
		add(lblChange);
		
		String[] position = {"Manager", "Waiter"};
		JComboBox comboBox = new JComboBox(position);
		if(user.isAdmin()) {
			comboBox.setSelectedItem("Manager");
		}else {
			comboBox.setSelectedItem("Waiter");
		}
		comboBox.setBounds(346, 60, 140, 24);
		add(comboBox);
		
		JLabel lblUpdateUser = new JLabel("");
		lblUpdateUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				User userForUpdate = new User();
				userForUpdate.setUserId(user.getUserId());
				
				if(comboBox.getSelectedItem().equals("Manager")) {
					user.setAdmin(true);
					userForUpdate.setAdmin(true);
				}else {
					user.setAdmin(false);
					userForUpdate.setAdmin(false);
				}
				try {
					TransferClass transfer = FrontController.getInstance().execute(
							TransferClass.create(userForUpdate, HttpConstraints.PUT, DbConstraints.USER));
					users.set(modelRow, user);
					if(user.isAdmin())
						model.setValueAt("Manager", modelRow, 2);
					else
						model.setValueAt("Waiter", modelRow, 2);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				CardLayout card = (CardLayout)panel.getLayout();
				card.show(panel, "userTablePanel");
				panel.remove(EditUserPanel.this);
			}
		});
		lblUpdateUser.setIcon(new ImageIcon(pathToImage+"\\icons\\person.png"));
		lblUpdateUser.setHorizontalAlignment(SwingConstants.CENTER);
		lblUpdateUser.setBounds(346, 89, 50, 50);
		add(lblUpdateUser);
		
		JLabel lblCancel = new JLabel("");
		lblCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CardLayout card = (CardLayout)panel.getLayout();
				card.show(panel, "userTablePanel");
				panel.remove(EditUserPanel.this);
			}
		});
		lblCancel.setIcon(new ImageIcon(pathToImage+"\\icons\\clear.png"));
		lblCancel.setHorizontalAlignment(SwingConstants.CENTER);
		lblCancel.setBounds(436, 89, 50, 50);
		add(lblCancel);
		
		
		
	}
}
