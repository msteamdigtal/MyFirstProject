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
import com.comtrade.database.entities.Arcticle;
import com.comtrade.database.entities.User;
import com.comtrade.database.entities.Warehouse;
import com.comtrade.helpers.ButtonColumn;
import com.comtrade.transfer.TransferClass;

public class ArcticleTablePanel extends JPanel {
	
	private JTable tableOfArcticles;
	private Arcticle arcticle;
	private JScrollPane scroll;
	private List<Arcticle> arcticles;
	
	/**
	 * Create the panel.
	 */
	public ArcticleTablePanel(User user, JPanel panel, String pathToImage) {

		setBounds(0, 0, 600, 150);
		setBackground(Color.WHITE);
		setLayout(null);
		
		arcticle = new Arcticle();
		arcticle.setRestaurant(user.getRestaurant());
		
		final String[] columnNames = {"Arcticle name", "Quantity", "Measure", "Purchase Price", "Price VAT", "Menu", "", ""};
	    Object[][] data = null;
	    
	    try {
			TransferClass transfer = FrontController.getInstance().execute(
					TransferClass.create(arcticle, HttpConstraints.GET, DbConstraints.ARCTICLE));
			arcticles = (List<Arcticle>) transfer.getResponse();
			data = new Object[arcticles.size()][columnNames.length];
			for(int i = 0; i < arcticles.size(); i++) {
				String arcticleName = arcticles.get(i).getArcticleName();
				Double quantity = arcticles.get(i).getQuantity();
				Double purchasePrice = arcticles.get(i).getPurchasePrice();
				Double priceWithVAT = arcticles.get(i).getPriceWithVAT();
				String quantityUnitMeasure = arcticles.get(i).getQuantityUntiMeasure();
				String menu = arcticles.get(i).getMenu().getMenuName();
				
				for(int j = 0; j < columnNames.length; j++) {
					if(j == 0)
						data[i][j] = arcticleName;
					if(j == 1)
						data[i][j] = quantity;
					if(j == 2)
						data[i][j] = quantityUnitMeasure;
					if(j == 3)
						data[i][j] = purchasePrice;
					if(j == 4)
						data[i][j] = priceWithVAT;
					if(j == 5)
						data[i][j] = menu;
					if(j == 6)
						data[i][j] = "edit";
					if(j == 7)
						data[i][j] = "delete";
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    DefaultTableModel model = new DefaultTableModel(data, columnNames);
	    tableOfArcticles = new JTable(model);
	    tableOfArcticles.setColumnSelectionAllowed(true);
	    tableOfArcticles.setRowSelectionAllowed(true);	    
		
	    Action edit = new AbstractAction()
	    {
	        public void actionPerformed(ActionEvent arg0)
	        {
	            JTable table = (JTable)arg0.getSource();
	            int modelRow = Integer.valueOf(arg0.getActionCommand());
	            Arcticle arcticle = arcticles.get(modelRow);
            	EditArcticlePanel editArcticlePanel = new EditArcticlePanel(arcticle, pathToImage, panel, model, modelRow, arcticles);
	            panel.add(editArcticlePanel, "editArcticlePanel");
	    	    CardLayout card = (CardLayout)panel.getLayout();
	    		card.show(panel, "editArcticlePanel");
	        }
	    };
	    
	    ButtonColumn editButton = new ButtonColumn(tableOfArcticles, edit, 6);
	    editButton.setMnemonic(KeyEvent.VK_D);
	    
	    Action delete = new AbstractAction()
	    {
	        public void actionPerformed(ActionEvent arg0)
	        {
	            JTable table = (JTable)arg0.getSource();
	            int modelRow = Integer.valueOf(arg0.getActionCommand());
	            Arcticle arcticle = arcticles.get(modelRow);
	            DeleteArcticlePanel deleteArcticlePanel = new DeleteArcticlePanel(arcticle, pathToImage, panel, tableOfArcticles, modelRow, arcticles);
	            panel.add(deleteArcticlePanel, "deleteArcticlePanel");
	            CardLayout card = (CardLayout)panel.getLayout();
	    		card.show(panel, "deleteArcticlePanel");
	        }
	    };
	     
	    ButtonColumn deleteButton = new ButtonColumn(tableOfArcticles, delete, 7);
	    deleteButton.setMnemonic(KeyEvent.VK_D);

	    scroll = new JScrollPane(tableOfArcticles);
	    scroll.setBounds(0, 0, 570, 150);
	    add(scroll);
	    
	}

}
