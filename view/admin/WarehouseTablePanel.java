package com.comtrade.view.admin;

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
import com.comtrade.database.entities.User;
import com.comtrade.database.entities.Warehouse;
import com.comtrade.helpers.ButtonColumn;
import com.comtrade.transfer.TransferClass;

public class WarehouseTablePanel extends JPanel {
	
	private JTable tableOfGoods;
	private Warehouse warehouse;
	private JScrollPane scroll;
	private List<Warehouse> goods;

	/**
	 * Create the panel.
	 */
	public WarehouseTablePanel(User user, JPanel panel, String pathToImage) {
		
		setBounds(0, 0, 537, 150);
		setBackground(Color.WHITE);
		setLayout(null);
		
		warehouse = new Warehouse();
		warehouse.setRestaurant(user.getRestaurant());
		final String[] columnNames = {"Good Name", "Good Quantity", "QU Measure", "Restaurant", "", ""};
	    Object[][] data = null;
	    
	    try {
			TransferClass transfer = FrontController.getInstance().execute(
					TransferClass.create(warehouse, HttpConstraints.GET, DbConstraints.WAREHOUSE));
			goods = (List<Warehouse>) transfer.getResponse();
			data = new Object[goods.size()][columnNames.length];
			for(int i = 0; i < goods.size(); i++) {
				String goodName = goods.get(i).getGoodName();
				Double goodQuantity = goods.get(i).getGoodQuantity();
				String quantityUnitMeasure = goods.get(i).getQuantityUnitMeasure();
				String restaurant = goods.get(i).getRestaurant().getRestaurantName();
				
				for(int j = 0; j < columnNames.length; j++) {
					if(j == 0)
						data[i][j] = goodName;
					if(j == 1)
						data[i][j] = goodQuantity;
					if(j == 2)
						data[i][j] = quantityUnitMeasure;
					if(j == 3)
						data[i][j] = restaurant;
					if(j == 4)
						data[i][j] = "edit";
					if(j == 5)
						data[i][j] = "delete";
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    DefaultTableModel model = new DefaultTableModel(data, columnNames);
	    tableOfGoods = new JTable(model);
	    tableOfGoods.setColumnSelectionAllowed(true);
	    tableOfGoods.setRowSelectionAllowed(true);
	    
	    Action edit = new AbstractAction()
	    {
	        public void actionPerformed(ActionEvent arg0)
	        {
	            JTable table = (JTable)arg0.getSource();
	            int modelRow = Integer.valueOf(arg0.getActionCommand());
	            Warehouse warehouseForEdit = goods.get(modelRow);
            	EditWarehousePanel editWarehousePanel = new EditWarehousePanel(warehouseForEdit, pathToImage, panel, model, modelRow, goods);
	            panel.add(editWarehousePanel, "editWarehousePanel");
	    	    CardLayout card = (CardLayout)panel.getLayout();
	    		card.show(panel, "editWarehousePanel");
	        }
	    };
	    
	    ButtonColumn editButton = new ButtonColumn(tableOfGoods, edit, 4);
	    editButton.setMnemonic(KeyEvent.VK_D);
	    
	    Action delete = new AbstractAction()
	    {
	        public void actionPerformed(ActionEvent arg0)
	        {
	            JTable table = (JTable)arg0.getSource();
	            int modelRow = Integer.valueOf(arg0.getActionCommand());
	            Warehouse warehouseForDeletion = goods.get(modelRow);
	            DeleteWarehousePanel deleteWarehousePanel = new DeleteWarehousePanel(warehouseForDeletion, pathToImage, panel, tableOfGoods, modelRow, goods);
	            panel.add(deleteWarehousePanel, "deleteWarehousePanel");
	            CardLayout card = (CardLayout)panel.getLayout();
	    		card.show(panel, "deleteWarehousePanel");
	        }
	    };
	     
	    ButtonColumn deleteButton = new ButtonColumn(tableOfGoods, delete, 5);
	    deleteButton.setMnemonic(KeyEvent.VK_D);

	    scroll = new JScrollPane(tableOfGoods);
	    scroll.setBounds(0, 0, 537, 150);
	    add(scroll);
	    
	}

}
