package com.comtrade.view.common;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.comtrade.constants.DbConstraints;
import com.comtrade.constants.HttpConstraints;
import com.comtrade.controller.FrontController;
import com.comtrade.database.entities.Arcticle;
import com.comtrade.database.entities.Menu;
import com.comtrade.database.entities.User;
import com.comtrade.helpers.ButtonColumn;
import com.comtrade.transfer.TransferClass;

public class ArcticlesForBillTable extends JPanel {
	
	private JScrollPane scroll;
	private JTable tableOfArcticles;
	private String[] columnNames;
	
	/**
	 * Create the panel.
	 */
	public ArcticlesForBillTable(User user, JPanel panel, String pathToImage, List<Arcticle> arcticles, boolean isViewBill) {
		setBounds(0, 0, 150, 128);
		setBackground(Color.WHITE);
		setLayout(null);
		
		if(isViewBill) {
			columnNames = new String[]{"Arcticle name"};
		}else {
			columnNames = new String[]{"Arcticle name", ""};
		}
		
		Object[][] data = new Object[arcticles.size()][columnNames.length];
	    for(int i = 0; i < arcticles.size(); i++) {
			String arcticleName = arcticles.get(i).getArcticleName();
			
			for(int j = 0; j < columnNames.length; j++) {
				if(j == 0)
					data[i][j] = arcticleName;
				if(!isViewBill) {
					if(j == 1)
						data[i][j] = "remove";
				}
			}
	    }
	    
	    
	    DefaultTableModel model = new DefaultTableModel(data, columnNames);
	    tableOfArcticles = new JTable(model);
	    tableOfArcticles.setColumnSelectionAllowed(true);
	    tableOfArcticles.setRowSelectionAllowed(true);			
		
	    if(!isViewBill) {
		    Action remove = new AbstractAction()
		    {
		        public void actionPerformed(ActionEvent arg0)
		        {
		            JTable table = (JTable)arg0.getSource();
		            int modelRow = Integer.valueOf(arg0.getActionCommand());
		            Arcticle arcticle  = arcticles.get(modelRow);
		            ((DefaultTableModel)table.getModel()).removeRow(modelRow);
		            arcticles.remove(arcticle);
		            CardLayout card = (CardLayout)panel.getLayout();
		    		card.show(panel, "arcticlesForBillTable");
		        }
		    };
		     
		    ButtonColumn deleteButton = new ButtonColumn(tableOfArcticles, remove, 1);
		    deleteButton.setMnemonic(KeyEvent.VK_D);
	    }

	    scroll = new JScrollPane(tableOfArcticles);
	    scroll.setBounds(0, 0, 150, 128);
	    add(scroll);
	}

}
