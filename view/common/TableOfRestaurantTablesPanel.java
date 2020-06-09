package com.comtrade.view.common;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.comtrade.constants.DbConstraints;
import com.comtrade.constants.HttpConstraints;
import com.comtrade.controller.FrontController;
import com.comtrade.database.entities.Recipe;
import com.comtrade.database.entities.RestaurantTable;
import com.comtrade.database.entities.User;
import com.comtrade.helpers.ButtonColumn;
import com.comtrade.transfer.TransferClass;

import java.awt.Rectangle;

public class TableOfRestaurantTablesPanel extends JPanel {
	
	private JTable tableOfTables;
	private RestaurantTable restaurantTable;
	private JScrollPane scroll;
	private List<RestaurantTable> restaurantTables;
	String[] columnNames;
	private BillPanel localBillPanel;
	
	/**
	 * Create the panel.
	 */
	public TableOfRestaurantTablesPanel(User user, JPanel panel, String pathToImage, JPanel lowerPanel, BillPanel billPanel) {
		
		localBillPanel = billPanel;
		setBounds(0, 0, 537, 309);
		setBackground(Color.WHITE);
		setLayout(null);
		
		restaurantTable = new RestaurantTable();
		restaurantTable.setRestaurant(user.getRestaurant());
		restaurantTable.setUser(user);
		if(user.isAdmin()){	
			columnNames = new String[]{"Table name", "Serving by", "", "", ""};
		}else {
			columnNames = new String[]{"Table name", "Serving by", "", ""};
		}
		Object[][] data = null;
		
	    try {
			TransferClass transfer = FrontController.getInstance().execute(
					TransferClass.create(restaurantTable, HttpConstraints.GET, DbConstraints.TABLE));
			restaurantTables = (List<RestaurantTable>) transfer.getResponse();
			data = new Object[restaurantTables.size()][columnNames.length];
			for(int i = 0; i < restaurantTables.size(); i++) {
				String tableName = restaurantTables.get(i).getRestaurantTableName();
				String userNameSurname = restaurantTables.get(i).getUser().getUserName()+" "+restaurantTables.get(i).getUser().getUserSurname();
				
				for(int j = 0; j < columnNames.length; j++) {
					if(j == 0)
						data[i][j] = tableName;
					if(j == 1)
						data[i][j] = userNameSurname;
					if(j == 2)
						data[i][j] = "bills";
					if(j == 3)
						data[i][j] = "edit";
					if(j == 4 && user.isAdmin())
						data[i][j] = "delete";
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    DefaultTableModel model = new DefaultTableModel(data, columnNames);
	    tableOfTables = new JTable(model);
	    tableOfTables.setColumnSelectionAllowed(true);
	    tableOfTables.setRowSelectionAllowed(true);
	    
	    Action bills = new AbstractAction()
	    {
	        public void actionPerformed(ActionEvent arg0)
	        {
	            JTable table = (JTable)arg0.getSource();
	            int modelRow = Integer.valueOf(arg0.getActionCommand());
	            RestaurantTable tableForBills = restaurantTables.get(modelRow);
            	
	            if(billPanel != null) {
	            	lowerPanel.remove(billPanel);
	            }
	            localBillPanel = new BillPanel(user, pathToImage, lowerPanel, tableForBills);
            	lowerPanel.add(localBillPanel, "billPanel");
            	
	    	    CardLayout card = (CardLayout)lowerPanel.getLayout();
	    		card.show(lowerPanel, "billPanel");
	        }
	    };
	    
	    ButtonColumn billsButton = new ButtonColumn(tableOfTables, bills, 2);
	    billsButton.setMnemonic(KeyEvent.VK_D);
	    
	    Action edit = new AbstractAction()
	    {
	        public void actionPerformed(ActionEvent arg0)
	        {
	            JTable table = (JTable)arg0.getSource();
	            int modelRow = Integer.valueOf(arg0.getActionCommand());
	            RestaurantTable tableForEdit = restaurantTables.get(modelRow);
            	EditTablePanel editTablePanel = new EditTablePanel(tableForEdit, pathToImage, panel, model, modelRow, restaurantTables, user);
	            panel.add(editTablePanel, "editTablePanel");
	    	    CardLayout card = (CardLayout)panel.getLayout();
	    		card.show(panel, "editTablePanel");
	        }
	    };
	    
	    ButtonColumn editButton = new ButtonColumn(tableOfTables, edit, 3);
	    editButton.setMnemonic(KeyEvent.VK_D);
	    
	    if(user.isAdmin()) {
		    Action delete = new AbstractAction()
		    {
		        public void actionPerformed(ActionEvent arg0)
		        {
		            JTable table = (JTable)arg0.getSource();
		            int modelRow = Integer.valueOf(arg0.getActionCommand());
		            RestaurantTable tableForDelete = restaurantTables.get(modelRow);
		            DeleteTablePanel deleteTablePanel = new DeleteTablePanel(tableForDelete, pathToImage, panel, tableOfTables, modelRow, restaurantTables);
		            panel.add(deleteTablePanel, "deleteTablePanel");
		            CardLayout card = (CardLayout)panel.getLayout();
		    		card.show(panel, "deleteTablePanel");
		        }
		    };
		     
		    ButtonColumn deleteButton = new ButtonColumn(tableOfTables, delete, 4);
		    deleteButton.setMnemonic(KeyEvent.VK_D);
	    }

	    scroll = new JScrollPane(tableOfTables);
	    scroll.setBounds(0, 0, 537, 309);
	    add(scroll);
	    
	}

}
