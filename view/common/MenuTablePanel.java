package com.comtrade.view.common;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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

public class MenuTablePanel extends JPanel {
	
	private JTable tableOfMenus;
	private JScrollPane scroll;
	private Menu menu;
	private List<Menu> menus;
	
	/**
	 * Create the panel.
	 */
	public MenuTablePanel(User user, JPanel panel, String pathToImage, JComboBox comboBox, JPanel arcticlesPanel, ArcticleTablePanel arcticleTablePanel) {
		
		setBounds(0, 0, 213, 150);
		setBackground(Color.WHITE);
		setLayout(null);
		
		menu = new Menu();
		menu.setRestaurant(user.getRestaurant());
		
		final String[] columnNames = {"Menu name", "", ""};
		Object[][] data = null;
		try {
			TransferClass transfer = FrontController.getInstance().execute(
					TransferClass.create(menu, HttpConstraints.GET, DbConstraints.MENU));
			menus = (List<Menu>) transfer.getResponse();
		    data = new Object[menus.size()][columnNames.length];
		    for(int i = 0; i < menus.size(); i++) {
				String menuName = menus.get(i).getMenuName();
				
				for(int j = 0; j < columnNames.length; j++) {
					if(j == 0)
						data[i][j] = menuName;
					if(j == 1)
						data[i][j] = "edit";
					if(j == 2)
						data[i][j] = "delete";
				}
		    }
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    DefaultTableModel model = new DefaultTableModel(data, columnNames);
	    tableOfMenus = new JTable(model);
	    tableOfMenus.setColumnSelectionAllowed(true);
	    tableOfMenus.setRowSelectionAllowed(true);	    
		
	    Action edit = new AbstractAction()
	    {
	        public void actionPerformed(ActionEvent arg0)
	        {
	            JTable table = (JTable)arg0.getSource();
	            int modelRow = Integer.valueOf(arg0.getActionCommand());
	            Menu menu = menus.get(modelRow);
            	EditMenuPanel editMenuPanel = new EditMenuPanel(menu, pathToImage, panel, model, modelRow, menus, comboBox, arcticlesPanel, arcticleTablePanel, user);
	            panel.add(editMenuPanel, "editMenuPanel");
	    	    CardLayout card = (CardLayout)panel.getLayout();
	    		card.show(panel, "editMenuPanel");
	        }
	    };
	    
	    ButtonColumn editButton = new ButtonColumn(tableOfMenus, edit, 1);
	    editButton.setMnemonic(KeyEvent.VK_D);
	    
	    Action delete = new AbstractAction()
	    {
	        public void actionPerformed(ActionEvent arg0)
	        {
	            JTable table = (JTable)arg0.getSource();
	            int modelRow = Integer.valueOf(arg0.getActionCommand());
	            Menu menu = menus.get(modelRow);
	            DeleteMenuPanel deleteMenuPanel = new DeleteMenuPanel(menu, pathToImage, panel, tableOfMenus, modelRow, menus, comboBox, arcticlesPanel, arcticleTablePanel, user);
	            panel.add(deleteMenuPanel, "deleteMenuPanel");
	            CardLayout card = (CardLayout)panel.getLayout();
	    		card.show(panel, "deleteMenuPanel");
	        }
	    };
	     
	    ButtonColumn deleteButton = new ButtonColumn(tableOfMenus, delete, 2);
	    deleteButton.setMnemonic(KeyEvent.VK_D);

	    scroll = new JScrollPane(tableOfMenus);
	    scroll.setBounds(0, 0, 213, 150);
	    add(scroll);
	    
	}

}
