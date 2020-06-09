package com.comtrade.view.common;

import java.awt.CardLayout;
import java.awt.Color;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.comtrade.constants.DbConstraints;
import com.comtrade.constants.HttpConstraints;
import com.comtrade.controller.FrontController;
import com.comtrade.database.entities.Arcticle;
import com.comtrade.database.entities.RestaurantTable;
import com.comtrade.database.entities.User;
import com.comtrade.database.entities.Warehouse;
import com.comtrade.transfer.TransferClass;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;


public class EditTablePanel extends JPanel {

	private List<User> users;
	private JTextField tfGoodQuantity;
	private JTextField tfTableName;
	private JComboBox usersComboBox;
	
	/**
	 * Create the panel.
	 */
	public EditTablePanel(RestaurantTable restaurantTable, String pathToImage, JPanel panel, DefaultTableModel model, int modelRow, List<RestaurantTable> restaurantTables, User user) {
		
		setBounds(0, 0, 537, 309);
		setBackground(Color.WHITE);
		setLayout(null);
		
		JLabel lblTableNameIcon = new JLabel("");
		lblTableNameIcon.setIcon(new ImageIcon(pathToImage+"\\icons\\dining-table_24.png"));
		lblTableNameIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblTableNameIcon.setBounds(153, 31, 24, 24);
		add(lblTableNameIcon);
		
		tfTableName = new JTextField(restaurantTable.getRestaurantTableName());
		tfTableName.setColumns(10);
		tfTableName.setBounds(200, 31, 150, 24);
		add(tfTableName);
		
		JLabel lblTableName = new JLabel("table name");
		lblTableName.setHorizontalAlignment(SwingConstants.CENTER);
		lblTableName.setForeground(Color.LIGHT_GRAY);
		lblTableName.setBounds(210, 58, 126, 14);
		add(lblTableName);
				
		if(user.isAdmin()) {
			JLabel lblUserIcon = new JLabel("");
			lblUserIcon.setIcon(new ImageIcon(pathToImage+"\\icons\\waiter_24.png"));
			lblUserIcon.setHorizontalAlignment(SwingConstants.CENTER);
			lblUserIcon.setBounds(153, 81, 24, 24);
			add(lblUserIcon);
			
			String[] userNames;
			try {
				
				TransferClass transfer = FrontController.getInstance().execute(
						TransferClass.create(restaurantTable.getUser(), HttpConstraints.GET, DbConstraints.USER));
				users = (List<User>) transfer.getResponse();
			}catch (ClassNotFoundException e) {
				e.printStackTrace();
			}catch (IOException e) {
				e.printStackTrace();
			}
			
			userNames = new String[users.size()];
			userNames[0] = restaurantTable.getUser().getUserName()+" "+restaurantTable.getUser().getUserSurname();
			for(int i = 0; i < users.size(); i++) {
				if(i > 0 && users.get(i).getUserId() != restaurantTable.getUser().getUserId())
					userNames[i] = users.get(i).getUserName()+" "+users.get(i).getUserSurname();
			}
			
			usersComboBox = new JComboBox(userNames);
			usersComboBox.setBounds(200, 81, 150, 24);
			add(usersComboBox);
			
			JLabel lblUser = new JLabel("user");
			lblUser.setHorizontalAlignment(SwingConstants.CENTER);
			lblUser.setForeground(Color.LIGHT_GRAY);
			lblUser.setBounds(210, 105, 126, 14);
			add(lblUser);
		}

		JLabel lblEditTable = new JLabel("");
		lblEditTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				String tableName = null;
				if(!tfTableName.getText().equals("")) {
					tableName = tfTableName.getText();
					lblTableName.setText("table name");
					lblTableName.setForeground(Color.LIGHT_GRAY);
				}else {
					lblTableName.setText("required field");
					lblTableName.setForeground(Color.RED);
				}
				
				if(tableName != null) {
					
					restaurantTable.setRestaurantTableName(tableName);
					if(user.isAdmin()) {
						for(User curr: users) {
							String nameSurname = curr.getUserName()+" "+curr.getUserSurname();
							if(nameSurname.equals(usersComboBox.getSelectedItem()) && curr.getUserId() != restaurantTable.getUser().getUserId()) {
								restaurantTable.setUser(curr);
								break;
							}
						}
					}
					
					try {
						TransferClass transfer = FrontController.getInstance().execute(
								TransferClass.create(restaurantTable, HttpConstraints.PUT, DbConstraints.TABLE));
						restaurantTables.set(modelRow, restaurantTable);
						model.setValueAt(restaurantTable.getRestaurantTableName(), modelRow, 0);
						model.setValueAt(restaurantTable.getUser().getUserName()+" "+restaurantTable.getUser().getUserSurname(), modelRow, 1);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					CardLayout card = (CardLayout)panel.getLayout();
					card.show(panel, "tableOfRestaurantTablesPanel");
					panel.remove(EditTablePanel.this);
					
				}
			}
		});
		lblEditTable.setIcon(new ImageIcon(pathToImage+"\\icons\\document.png"));
		lblEditTable.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditTable.setForeground(Color.WHITE);
		lblEditTable.setBounds(193, 186, 50, 50);
		add(lblEditTable);
		
		JLabel lblCancel = new JLabel("");
		lblCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CardLayout card = (CardLayout)panel.getLayout();
				card.show(panel, "tableOfRestaurantTablesPanel");
				panel.remove(EditTablePanel.this);
			}
		});
		lblCancel.setIcon(new ImageIcon(pathToImage+"\\icons\\clear.png"));
		lblCancel.setHorizontalAlignment(SwingConstants.CENTER);
		lblCancel.setForeground(Color.WHITE);
		lblCancel.setBounds(286, 186, 50, 50);
		add(lblCancel);
		
	}
}
