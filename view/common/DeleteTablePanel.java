package com.comtrade.view.common;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.comtrade.constants.DbConstraints;
import com.comtrade.constants.HttpConstraints;
import com.comtrade.controller.FrontController;
import com.comtrade.database.entities.RestaurantTable;
import com.comtrade.transfer.TransferClass;


public class DeleteTablePanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public DeleteTablePanel(RestaurantTable restaurantTable, String pathToImage, JPanel panel, JTable table, int modelRow, List<RestaurantTable> restaurantTables) {
		
		setBounds(0, 0, 537, 309);
		setBackground(Color.WHITE);
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Are you shure you want to delete "+restaurantTable.getRestaurantTableName()+" table?");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(80, 68, 370, 20);
		add(lblNewLabel);
		
		JLabel lblDeleteIt = new JLabel("");
		lblDeleteIt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
	            	TransferClass transfer = FrontController.getInstance().execute(
	    					TransferClass.create(restaurantTable, HttpConstraints.DELETE, DbConstraints.TABLE));
	            	if(transfer.getMessage().equals("Successfully deleted table!")) {
	            		((DefaultTableModel)table.getModel()).removeRow(modelRow);
	            		restaurantTables.remove(restaurantTable);
	            	}
	            }catch (ClassNotFoundException e) {
	    			e.printStackTrace();
	    		} catch (IOException e) {
	    			e.printStackTrace();
	    		}
				CardLayout card = (CardLayout)panel.getLayout();
				card.show(panel, "tableOfRestaurantTablesPanel");
				panel.remove(DeleteTablePanel.this);
			}
		});
		lblDeleteIt.setIcon(new ImageIcon(pathToImage+"\\icons\\trash.png"));
		lblDeleteIt.setHorizontalAlignment(SwingConstants.CENTER);
		lblDeleteIt.setBounds(160, 118, 50, 50);
		add(lblDeleteIt);
		
		JLabel lblCancel = new JLabel("");
		lblCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CardLayout card = (CardLayout)panel.getLayout();
				card.show(panel, "tableOfRestaurantTablesPanel");
				panel.remove(DeleteTablePanel.this);
			}
		});
		lblCancel.setIcon(new ImageIcon(pathToImage+"\\icons\\clear.png"));
		lblCancel.setHorizontalAlignment(SwingConstants.CENTER);
		lblCancel.setBounds(310, 118, 50, 50);
		add(lblCancel);
		
	}

}
