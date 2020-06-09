package com.comtrade.view.common;

import java.awt.Color;

import javax.swing.JPanel;

import java.awt.CardLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

import com.comtrade.constants.DbConstraints;
import com.comtrade.constants.HttpConstraints;
import com.comtrade.controller.FrontController;
import com.comtrade.database.entities.Recipe;
import com.comtrade.database.entities.RestaurantTable;
import com.comtrade.database.entities.User;
import com.comtrade.transfer.TransferClass;

import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class RestaurantTablePanel extends JPanel {
	
	private JTextField tfTableName;
	private TableOfRestaurantTablesPanel tableOfRestaurantTablesPanel;
	
	/**
	 * Create the panel.
	 */
	public RestaurantTablePanel(User user, String pathToImage, JPanel lowerPanel, BillPanel billPanel) {
		
		setBackground(Color.WHITE);
		setBounds(0, 0, 859, 482);
		setLayout(null);
		
		JPanel tablePanelOfTables = new JPanel();
		tablePanelOfTables.setBackground(Color.WHITE);
		tablePanelOfTables.setBounds(46, 122, 537, 309);
		add(tablePanelOfTables);
		tablePanelOfTables.setLayout(new CardLayout(0, 0));
		
		tableOfRestaurantTablesPanel = new TableOfRestaurantTablesPanel(user, tablePanelOfTables, pathToImage, lowerPanel, billPanel);
		tablePanelOfTables.add(tableOfRestaurantTablesPanel, "tableOfRestaurantTablesPanel");
	    CardLayout card = (CardLayout)tablePanelOfTables.getLayout();
		card.show(tablePanelOfTables, "tableOfRestaurantTablesPanel");
		
		JLabel lblAddNewTabel = new JLabel("Add new table:");
		lblAddNewTabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddNewTabel.setForeground(Color.BLACK);
		lblAddNewTabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblAddNewTabel.setBounds(634, 122, 197, 24);
		add(lblAddNewTabel);
		
		JLabel lblIcon = new JLabel("");
		lblIcon.setIcon(new ImageIcon(pathToImage+"\\icons\\dining-table_24.png"));
		lblIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblIcon.setBounds(634, 180, 24, 24);
		add(lblIcon);
		
		tfTableName = new JTextField();
		tfTableName.setColumns(10);
		tfTableName.setBounds(681, 180, 150, 24);
		add(tfTableName);

		JLabel lblTableName = new JLabel("table name");
		lblTableName.setHorizontalAlignment(SwingConstants.CENTER);
		lblTableName.setForeground(Color.LIGHT_GRAY);
		lblTableName.setBounds(691, 204, 126, 14);
		add(lblTableName);
		
		JLabel lblAddNewTable = new JLabel("");
		lblAddNewTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				RestaurantTable restaurantTable = new RestaurantTable();
						
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
					restaurantTable.setUser(user);
					restaurantTable.setRestaurant(user.getRestaurant());
					
					try {
						TransferClass transfer = FrontController.getInstance().execute(
								TransferClass.create(restaurantTable, HttpConstraints.POST, DbConstraints.TABLE));
						if(transfer.getMessage().equals("Successfully added table!")) {
							tablePanelOfTables.remove(tableOfRestaurantTablesPanel);
							TableOfRestaurantTablesPanel addNewTable = new TableOfRestaurantTablesPanel(user, tablePanelOfTables, pathToImage, lowerPanel, billPanel);
							
							tablePanelOfTables.add(addNewTable, "tableOfRestaurantTablesPanel");
						    CardLayout card = (CardLayout)tablePanelOfTables.getLayout();
							card.show(tablePanelOfTables, "tableOfRestaurantTablesPanel");
							tfTableName.setText("");
						}
					}catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
				
			}
		});
		lblAddNewTable.setIcon(new ImageIcon(pathToImage+"\\icons\\more.png"));
		lblAddNewTable.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddNewTable.setForeground(Color.WHITE);
		lblAddNewTable.setBounds(681, 243, 50, 50);
		add(lblAddNewTable);
		
		
		JLabel lblTableIcon = new JLabel("");
		lblTableIcon.setIcon(new ImageIcon(pathToImage+"\\icons\\dining-table_64.png"));
		lblTableIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblTableIcon.setBounds(128, 41, 70, 70);
		add(lblTableIcon);
		
	}
}
