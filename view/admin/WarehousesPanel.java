package com.comtrade.view.admin;

import java.awt.Color;

import javax.swing.JPanel;

import java.awt.CardLayout;

import javax.swing.ImageIcon;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.text.NumberFormatter;

import com.comtrade.constants.DbConstraints;
import com.comtrade.constants.HttpConstraints;
import com.comtrade.controller.FrontController;
import com.comtrade.database.entities.User;
import com.comtrade.database.entities.Warehouse;
import com.comtrade.transfer.TransferClass;

import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.NumberFormat;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class WarehousesPanel extends JPanel {
	private JTextField tfGoodName;
	private JTextField tfQuantity;
	private JTextField tfQUM;
	private WarehouseTablePanel warehouseTablePanel;

	/**
	 * Create the panel.
	 */
	public WarehousesPanel(User user, String pathToImage, JPanel lowerAdminPanel) {
		setBackground(Color.WHITE);
		setBounds(0, 0, 859, 482);
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(30, 170, 537, 150);
		add(panel);
		panel.setLayout(new CardLayout(0, 0));
		
		warehouseTablePanel = new WarehouseTablePanel(user, panel, pathToImage);
	    panel.add(warehouseTablePanel, "warehouseTablePanel");
	    CardLayout card = (CardLayout)panel.getLayout();
		card.show(panel, "warehouseTablePanel");
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(pathToImage+"\\icons\\groceries.png"));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(629, 120, 24, 24);
		add(label);
		
		tfGoodName = new JTextField((String) null);
		tfGoodName.setColumns(10);
		tfGoodName.setBounds(676, 120, 150, 24);
		add(tfGoodName);
		
		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(pathToImage+"\\icons\\measuring.png"));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(629, 170, 24, 24);
		add(label_1);
		
		JLabel lblQuantityOfGood = new JLabel("quantity of good");
		lblQuantityOfGood.setHorizontalAlignment(SwingConstants.CENTER);
		lblQuantityOfGood.setForeground(Color.LIGHT_GRAY);
		lblQuantityOfGood.setBounds(686, 195, 126, 14);
		add(lblQuantityOfGood);
		
		tfQuantity = new JTextField();
		tfQuantity.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				try {
					double quantity = Double.parseDouble(tfQuantity.getText());
					lblQuantityOfGood.setForeground(Color.LIGHT_GRAY);
					lblQuantityOfGood.setText("quantity of good");
				}catch (NumberFormatException e) {
					lblQuantityOfGood.setForeground(Color.RED);
					lblQuantityOfGood.setText("invalid number");
				}
			}
		});
		tfQuantity.setColumns(10);
		tfQuantity.setBounds(676, 170, 150, 24);
		add(tfQuantity);
		
		JLabel label_2 = new JLabel("");
		label_2.setIcon(new ImageIcon(pathToImage+"\\icons\\quantity.png"));
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(629, 220, 24, 24);
		add(label_2);
		
		tfQUM = new JTextField((String) null);
		tfQUM.setColumns(10);
		tfQUM.setBounds(676, 220, 150, 24);
		add(tfQUM);
		
		JLabel addNewGood = new JLabel("Add new good:");
		addNewGood.setHorizontalAlignment(SwingConstants.CENTER);
		addNewGood.setForeground(Color.BLACK);
		addNewGood.setFont(new Font("Times New Roman", Font.BOLD, 20));
		addNewGood.setBounds(629, 70, 197, 24);
		add(addNewGood);
		
		JLabel label_4 = new JLabel("");
		label_4.setIcon(new ImageIcon(pathToImage+"\\icons\\pizza.png"));
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setBounds(160, 70, 70, 70);
		add(label_4);
		
		JLabel lblNewLabel = new JLabel("good name");
		lblNewLabel.setForeground(Color.LIGHT_GRAY);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(686, 143, 126, 14);
		add(lblNewLabel);
		
		JLabel lblMeasure = new JLabel("measurment");
		lblMeasure.setHorizontalAlignment(SwingConstants.CENTER);
		lblMeasure.setForeground(Color.LIGHT_GRAY);
		lblMeasure.setBounds(676, 245, 150, 14);
		add(lblMeasure);
		
		JLabel lblAddNewGood = new JLabel("");
		lblAddNewGood.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				Warehouse warehouse = new Warehouse();
				
				String goodName = null;
				if(!tfGoodName.getText().equals("")) {
					goodName = tfGoodName.getText();
					lblNewLabel.setText("quantity of good");
					lblNewLabel.setForeground(Color.LIGHT_GRAY);
				}else {
					lblNewLabel.setText("required field");
					lblNewLabel.setForeground(Color.RED);
				}
				
				Double goodQuantity = null;
				if(!tfQuantity.getText().equals("") && !lblQuantityOfGood.getText().equals("invalid number")) {
					goodQuantity = Double.parseDouble(tfQuantity.getText());
					lblQuantityOfGood.setText("quantity of good");
					lblQuantityOfGood.setForeground(Color.LIGHT_GRAY);
				}else {
					lblQuantityOfGood.setText("required field");
					lblQuantityOfGood.setForeground(Color.RED);
				}

				String quantityUnitOfMeasure = null;
				if(!tfQUM.getText().equals("")) {
					quantityUnitOfMeasure = tfQUM.getText();
					lblMeasure.setText("measurment");
					lblMeasure.setForeground(Color.LIGHT_GRAY);
				}else {
					lblMeasure.setText("required field");
					lblMeasure.setForeground(Color.RED);
				}
				
				if(goodName != null && goodQuantity != null && quantityUnitOfMeasure != null && lblQuantityOfGood.getText().equals("quantity of good")) {
					
					warehouse.setGoodName(goodName);
					warehouse.setGoodQuantity(goodQuantity);
					warehouse.setQuantityUnitMeasure(quantityUnitOfMeasure);
					warehouse.setRestaurant(user.getRestaurant());
					
					try {
						TransferClass transfer = FrontController.getInstance().execute(
								TransferClass.create(warehouse, HttpConstraints.POST, DbConstraints.WAREHOUSE));
						if(transfer.getMessage().equals("Successfully added good!")) {
							panel.remove(warehouseTablePanel);
							WarehouseTablePanel addNewGood = new WarehouseTablePanel(user, panel, pathToImage);
						    panel.add(addNewGood, "warehouseTablePanel");
						    CardLayout card = (CardLayout)panel.getLayout();
							card.show(panel, "warehouseTablePanel");
							tfGoodName.setText("");
							tfQuantity.setText("");
							tfQUM.setText("");
						}
					}catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		lblAddNewGood.setIcon(new ImageIcon(pathToImage+"\\icons\\cart.png"));
		lblAddNewGood.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddNewGood.setBounds(676, 270, 50, 50);
		add(lblAddNewGood);
	}
}
