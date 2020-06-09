package com.comtrade.view.common;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.comtrade.constants.DbConstraints;
import com.comtrade.constants.HttpConstraints;
import com.comtrade.controller.FrontController;
import com.comtrade.database.entities.User;
import com.comtrade.helpers.ButtonColumn;
import com.comtrade.transfer.TransferClass;
import com.comtrade.view.admin.UserTablePanel;
import com.comtrade.view.admin.WarehousesPanel;

public class UsersPanel extends JPanel {
	
	private JTextField tfUserNameSurname;
	private JTextField tfPassword;
    
	/**
	 * Create the panel.
	 */
	public UsersPanel(User user, String pathToImage, JPanel contentPane, JPanel lowerPanel, WarehousesPanel warehousesPanel, ArcticlesPanel arcticlesPanel, RecipesPanel recipesPanel, RestaurantTablePanel restaurantTablePanel, BillPanel billPanel, JPanel secondMainPanel) {
		
		setBackground(Color.WHITE);
		setBounds(0, 0, 859, 482);
		setLayout(null);
		
		JLabel label = new JLabel("My data:");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(Color.BLACK);
		label.setFont(new Font("Times New Roman", Font.BOLD, 20));
		label.setBounds(667, 70, 150, 24);
		add(label);
		
		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(pathToImage+"\\icons\\manager.png"));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(620, 70, 24, 24);
		add(label_1);
		
		JLabel label_2 = new JLabel("");
		label_2.setIcon(new ImageIcon(pathToImage+"\\icons\\name.png"));
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(620, 110, 24, 24);
		add(label_2);
		
		String information = "";
		if(user.isAdmin()) {
			information = "Manager of "+user.getRestaurant().getRestaurantName();
		}else {
			information = "Waiter in "+user.getRestaurant().getRestaurantName();
		}
		JLabel lblManagerOfRestaurant = new JLabel(information);
		lblManagerOfRestaurant.setHorizontalAlignment(SwingConstants.CENTER);
		lblManagerOfRestaurant.setBounds(667, 190, 150, 24);
		add(lblManagerOfRestaurant);
		
		JLabel label_4 = new JLabel("");
		label_4.setIcon(new ImageIcon(pathToImage+"\\icons\\cutlery.png"));
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setBounds(620, 190, 24, 24);
		add(label_4);
		
		tfUserNameSurname = new JTextField(user.getUserName()+" "+user.getUserSurname());
		tfUserNameSurname.setColumns(10);
		tfUserNameSurname.setBounds(667, 110, 150, 24);
		add(tfUserNameSurname);
		
		tfPassword = new JTextField(user.getPassword());
		tfPassword.setColumns(10);
		tfPassword.setBounds(667, 150, 150, 24);
		add(tfPassword);
		
		JLabel lblUpdateMessage = new JLabel("");
		lblUpdateMessage.setForeground(Color.RED);
		lblUpdateMessage.setHorizontalAlignment(SwingConstants.CENTER);
		lblUpdateMessage.setBounds(620, 307, 197, 24);
		add(lblUpdateMessage);
		
		JLabel label_5 = new JLabel("");
		label_5.setIcon(new ImageIcon(pathToImage+"\\icons\\lock.png"));
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		label_5.setBounds(620, 150, 24, 24);
		add(label_5);
		
		JLabel lblUpdateAdminData = new JLabel("");
		lblUpdateAdminData.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				User updateUser = new User();
				updateUser.setUserId(user.getUserId());
				String[] nameSurname = tfUserNameSurname.getText().split(" ");
				StringBuilder surname = new StringBuilder();
				updateUser.setUserName(nameSurname[0]);
				
				for(int i = 1; i < nameSurname.length; i++) {
					if(nameSurname.length-1 != i)
						surname.append(nameSurname[i]+ " ");
					else
						surname.append(nameSurname[i]);
				}
				updateUser.setUserSurname(surname.toString());
				updateUser.setPassword(tfPassword.getText());
				
				try {
					TransferClass transfer = FrontController.getInstance().execute(
							TransferClass.create(updateUser, HttpConstraints.PUT, DbConstraints.USER));
					lblUpdateMessage.setText(transfer.getMessage());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		
		});
		lblUpdateAdminData.setIcon(new ImageIcon(pathToImage+"\\icons\\user.png"));
		lblUpdateAdminData.setHorizontalAlignment(SwingConstants.CENTER);
		lblUpdateAdminData.setBounds(630, 230, 50, 50);
		add(lblUpdateAdminData);
		
		JLabel lblLogOut = new JLabel("");
		lblLogOut.addMouseListener(new MouseAdapter() {
					
			@Override
			public void mouseClicked(MouseEvent arg0) {
				tfUserNameSurname.setText("");
				tfPassword.setText("");
				lblUpdateMessage.setText("");
				
				Component[] components = lowerPanel.getComponents();

				for(int i = 0; i < components.length; i++) {
					lowerPanel.remove(components[i]);
				}
				
				contentPane.remove(secondMainPanel);
				CardLayout card = (CardLayout)contentPane.getLayout();
				card.show(contentPane, "loginPanel");
			}
			
		});
		lblLogOut.setIcon(new ImageIcon(pathToImage+"\\icons\\exit.png"));
		lblLogOut.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogOut.setBounds(730, 230, 50, 50);
		add(lblLogOut);
		
		if(user.isAdmin()) {
			JPanel panel = new JPanel();
			panel.setBounds(31, 190, 537, 150);
			add(panel);
			panel.setLayout(new CardLayout(0, 0));
	
		    UserTablePanel userTablePanel = new UserTablePanel(user, panel, pathToImage);
		    panel.add(userTablePanel, "userTablePanel");
		    CardLayout card = (CardLayout)panel.getLayout();
			card.show(panel, "userTablePanel");
		}
		
		JLabel lblRestaurantIcon = new JLabel("");
		lblRestaurantIcon.setIcon(new ImageIcon(pathToImage+"\\icons\\cutlery.png"));
		lblRestaurantIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblRestaurantIcon.setBounds(31, 70, 24, 24);
		add(lblRestaurantIcon);
		
		JLabel lblRestaurant = new JLabel(user.getRestaurant().getRestaurantName()+" data:");
		lblRestaurant.setHorizontalAlignment(SwingConstants.CENTER);
		lblRestaurant.setForeground(Color.BLACK);
		lblRestaurant.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblRestaurant.setBounds(78, 70, 170, 24);
		add(lblRestaurant);
		
		JLabel label_3 = new JLabel("");
		label_3.setIcon(new ImageIcon(pathToImage+"\\icons\\vibrate.png"));
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setBounds(31, 110, 24, 24);
		add(label_3);
		
		JLabel label_6 = new JLabel(user.getRestaurant().getRestaurantTelephone());
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		label_6.setBounds(78, 110, 170, 24);
		add(label_6);
		
		JLabel label_7 = new JLabel("");
		label_7.setIcon(new ImageIcon(pathToImage+"\\icons\\email.png"));
		label_7.setHorizontalAlignment(SwingConstants.CENTER);
		label_7.setBounds(272, 110, 24, 24);
		add(label_7);
		
		JLabel label_8 = new JLabel(user.getRestaurant().getEmail());
		label_8.setHorizontalAlignment(SwingConstants.CENTER);
		label_8.setBounds(319, 110, 249, 24);
		add(label_8);
		
		if(user.isAdmin()) {
			JLabel label_9 = new JLabel(user.getRestaurant().getRestaurantTIN());
			label_9.setHorizontalAlignment(SwingConstants.CENTER);
			label_9.setBounds(78, 150, 170, 24);
			add(label_9);
			
			JLabel label_10 = new JLabel("");
			label_10.setIcon(new ImageIcon(pathToImage+"\\icons\\taxes.png"));
			label_10.setHorizontalAlignment(SwingConstants.CENTER);
			label_10.setBounds(31, 150, 24, 24);
			add(label_10);
		}
	    
	}
}
