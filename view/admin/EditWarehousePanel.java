package com.comtrade.view.admin;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import com.comtrade.constants.DbConstraints;
import com.comtrade.constants.HttpConstraints;
import com.comtrade.controller.FrontController;
import com.comtrade.database.entities.Warehouse;
import com.comtrade.transfer.TransferClass;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class EditWarehousePanel extends JPanel {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Create the panel.
	 */
	public EditWarehousePanel(Warehouse warehouse, String pathToImage, JPanel panel, DefaultTableModel model, int modelRow, List<Warehouse> goods) {
		
		setBounds(0, 0, 537, 150);
		setBackground(Color.WHITE);
		setLayout(null);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(pathToImage+"\\icons\\groceries.png"));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(30, 11, 24, 24);
		add(label);
		
		textField = new JTextField(warehouse.getGoodName());
		textField.setColumns(10);
		textField.setBounds(77, 11, 150, 24);
		add(textField);
		
		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(pathToImage+"\\icons\\measuring.png"));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(301, 11, 24, 24);
		add(label_1);
		

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(433, 46, 65, 14);
		add(lblNewLabel);
		
		textField_1 = new JTextField(""+warehouse.getGoodQuantity());
		textField_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				try {
					double quantity = Double.parseDouble(textField_1.getText());
					lblNewLabel.setText("");
				}catch (NumberFormatException e) {
					lblNewLabel.setText("invalid number");
				}
			}
		});
		textField_1.setColumns(10);
		textField_1.setBounds(348, 11, 150, 24);
		add(textField_1);
		
		JLabel label_2 = new JLabel("");
		label_2.setIcon(new ImageIcon(pathToImage+"\\icons\\quantity.png"));
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(301, 46, 24, 24);
		add(label_2);
		
		textField_2 = new JTextField(warehouse.getQuantityUnitMeasure());
		textField_2.setColumns(10);
		textField_2.setBounds(348, 46, 75, 24);
		add(textField_2);
		
		JLabel lblUpdateWarehouse = new JLabel("");
		lblUpdateWarehouse.setIcon(new ImageIcon(pathToImage+"\\icons\\bottle.png"));
		lblUpdateWarehouse.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String goodName = null;
				Double goodQuantity = null;
				String quantityUnitMeasure = null;
				
				if(!textField.getText().equals("")) {
					goodName = textField.getText();
				}
				
				if(!textField_1.getText().equals("")) {
					goodQuantity = Double.parseDouble(textField_1.getText());
				}
				
				if(!textField_2.getText().equals("")) {
					quantityUnitMeasure = textField_2.getText();
				}
				
				if(goodName != null && goodQuantity != null && lblNewLabel.getText().equals("") && quantityUnitMeasure != null) {
					warehouse.setGoodName(goodName);
					warehouse.setGoodQuantity(goodQuantity);
					warehouse.setQuantityUnitMeasure(quantityUnitMeasure);
					try {
						TransferClass transfer = FrontController.getInstance().execute(
								TransferClass.create(warehouse, HttpConstraints.PUT, DbConstraints.WAREHOUSE));
						goods.set(modelRow, warehouse);
						model.setValueAt(textField.getText(), modelRow, 0);
						model.setValueAt(textField_1.getText(), modelRow, 1);
						model.setValueAt(textField_2.getText(), modelRow, 2);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					CardLayout card = (CardLayout)panel.getLayout();
					card.show(panel, "warehouseTablePanel");
					panel.remove(EditWarehousePanel.this);
				}
			}
		});
		lblUpdateWarehouse.setHorizontalAlignment(SwingConstants.CENTER);
		lblUpdateWarehouse.setBounds(198, 89, 50, 50);
		add(lblUpdateWarehouse);
		
		JLabel lblCancel = new JLabel("");
		lblCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CardLayout card = (CardLayout)panel.getLayout();
				card.show(panel, "warehouseTablePanel");
				panel.remove(EditWarehousePanel.this);
			}
		});
		lblCancel.setIcon(new ImageIcon(pathToImage+"\\icons\\clear.png"));
		lblCancel.setHorizontalAlignment(SwingConstants.CENTER);
		lblCancel.setBounds(288, 89, 50, 50);
		add(lblCancel);
		
	}
}
