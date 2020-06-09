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
import com.comtrade.helpers.ButtonColumn;
import com.comtrade.transfer.TransferClass;

public class UserTablePanel extends JPanel {
	 
	private JTable tableOfUser;
	private JScrollPane scroll;
	private List<User> users;
	
	/**
	 * Create the panel.
	 */
	public UserTablePanel(User user, JPanel panel, String pathToImage) {
		
		setBounds(0, 0, 537, 150);
		setBackground(Color.WHITE);
		setLayout(null);
		
		final String[] columnNames = {"Name", "Surname", "Position", "Restaurant", "", ""};
	    Object[][] data = null;
	    
	    try {
			TransferClass transferForAllUsers = FrontController.getInstance().execute(
					TransferClass.create(user, HttpConstraints.GET, DbConstraints.USER));
			users = (List<User>) transferForAllUsers.getResponse();
			data = new Object[users.size()][columnNames.length];
			
			for(int i = 0; i < users.size(); i++) {
				String userName = users.get(i).getUserName();
				String userSurname = users.get(i).getUserSurname();
				boolean isAdmin = users.get(i).isAdmin();
				String position;
				if(isAdmin)
					position = "Manager";
				else
					position = "Waiter";
				String restaurant = users.get(i).getRestaurant().getRestaurantName();
				
				for(int j = 0; j < columnNames.length; j++) {
					if(j == 0)
						data[i][j] = userName;
					if(j == 1)
						data[i][j] = userSurname;
					if(j == 2)
						data[i][j] = position;
					if(j == 3)
						data[i][j] = restaurant;
					if(j == 4 && users.get(i).getUserId() != user.getUserId())
						data[i][j] = "edit";
					if(j == 5 && users.get(i).getUserId() != user.getUserId())
						data[i][j] = "delete";
				}
			}
			System.out.println(transferForAllUsers.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    DefaultTableModel model = new DefaultTableModel(data, columnNames);
	    tableOfUser = new JTable(model);
	    tableOfUser.setColumnSelectionAllowed(true);
	    tableOfUser.setRowSelectionAllowed(true);
	    
	    Action edit = new AbstractAction()
	    {
	        public void actionPerformed(ActionEvent arg0)
	        {
	            JTable table = (JTable)arg0.getSource();
	            int modelRow = Integer.valueOf(arg0.getActionCommand());
	            User userForUpdate = users.get(modelRow);
	            if(user.getUserId() != userForUpdate.getUserId()) {
		            EditUserPanel editUserPanel = new EditUserPanel(userForUpdate, pathToImage, panel, model, modelRow, users);
		            panel.add(editUserPanel, "editUserPanel");
		    	    CardLayout card = (CardLayout)panel.getLayout();
		    		card.show(panel, "editUserPanel");
	            }
	        }
	    };
	    
	    ButtonColumn editButton = new ButtonColumn(tableOfUser, edit, 4);
	    editButton.setMnemonic(KeyEvent.VK_D);
	    
	    Action delete = new AbstractAction()
	    {
	        public void actionPerformed(ActionEvent arg0)
	        {
	            JTable table = (JTable)arg0.getSource();
	            int modelRow = Integer.valueOf(arg0.getActionCommand());
	            User userForDeletion = users.get(modelRow);
	            if(user.getUserId() != userForDeletion.getUserId()) {
		            DeleteUserPanel deleteUserPanel = new DeleteUserPanel(userForDeletion, pathToImage, panel, tableOfUser, modelRow, users);
		            panel.add(deleteUserPanel, "deleteUserPanel");
		            CardLayout card = (CardLayout)panel.getLayout();
		    		card.show(panel, "deleteUserPanel");
	            }
	        }
	    };
	     
	    ButtonColumn deleteButton = new ButtonColumn(tableOfUser, delete, 5);
	    deleteButton.setMnemonic(KeyEvent.VK_D);

	    scroll = new JScrollPane(tableOfUser);
	    scroll.setBounds(0, 0, 537, 150);
	    add(scroll);
		
	}

}
