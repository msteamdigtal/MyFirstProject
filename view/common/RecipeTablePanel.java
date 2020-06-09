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
import com.comtrade.database.entities.User;
import com.comtrade.database.entities.Warehouse;
import com.comtrade.helpers.ButtonColumn;
import com.comtrade.transfer.TransferClass;
import com.comtrade.view.admin.DeleteWarehousePanel;
import com.comtrade.view.admin.EditWarehousePanel;

public class RecipeTablePanel extends JPanel {

	private JTable tableOfRecipes;
	private Recipe recipe;
	private JScrollPane scroll;
	private List<Recipe> recipes;
	
	/**
	 * Create the panel.
	 */
	public RecipeTablePanel(User user, JPanel panel, String pathToImage) {
		
		setBounds(0, 0, 537, 150);
		setBackground(Color.WHITE);
		setLayout(null);
		
		recipe = new Recipe();
		recipe.setRestaurant(user.getRestaurant());
		final String[] columnNames = {"Good Quantity", "Good", "For Arcticle", "", ""};
	    Object[][] data = null;
		
	    try {
			TransferClass transfer = FrontController.getInstance().execute(
					TransferClass.create(recipe, HttpConstraints.GET, DbConstraints.RECIPE));
			recipes = (List<Recipe>) transfer.getResponse();
			data = new Object[recipes.size()][columnNames.length];
			for(int i = 0; i < recipes.size(); i++) {
				Double goodQuantity = recipes.get(i).getGoodQuantity();
				String good = recipes.get(i).getWarehouse().getGoodName();
				String arcticle = recipes.get(i).getArcticle().getArcticleName();
				
				for(int j = 0; j < columnNames.length; j++) {
					if(j == 0)
						data[i][j] = goodQuantity;
					if(j == 1)
						data[i][j] = good;
					if(j == 2)
						data[i][j] = arcticle;
					if(j == 3)
						data[i][j] = "edit";
					if(j == 4)
						data[i][j] = "delete";
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    DefaultTableModel model = new DefaultTableModel(data, columnNames);
	    tableOfRecipes = new JTable(model);
	    tableOfRecipes.setColumnSelectionAllowed(true);
	    tableOfRecipes.setRowSelectionAllowed(true);
	    
	    Action edit = new AbstractAction()
	    {
	        public void actionPerformed(ActionEvent arg0)
	        {
	            JTable table = (JTable)arg0.getSource();
	            int modelRow = Integer.valueOf(arg0.getActionCommand());
	            Recipe recipeForEdit = recipes.get(modelRow);
            	EditRecipePanel editRecipePanel = new EditRecipePanel(recipeForEdit, pathToImage, panel, model, modelRow, recipes);
	            panel.add(editRecipePanel, "editRecipePanel");
	    	    CardLayout card = (CardLayout)panel.getLayout();
	    		card.show(panel, "editRecipePanel");
	        }
	    };
	    
	    ButtonColumn editButton = new ButtonColumn(tableOfRecipes, edit, 3);
	    editButton.setMnemonic(KeyEvent.VK_D);
	    
	    Action delete = new AbstractAction()
	    {
	        public void actionPerformed(ActionEvent arg0)
	        {
	            JTable table = (JTable)arg0.getSource();
	            int modelRow = Integer.valueOf(arg0.getActionCommand());
	            Recipe recipeForDelete = recipes.get(modelRow);
	            DeleteRecipePanel deleteRecipePanel = new DeleteRecipePanel(recipeForDelete, pathToImage, panel, tableOfRecipes, modelRow, recipes);
	            panel.add(deleteRecipePanel, "deleteRecipePanel");
	            CardLayout card = (CardLayout)panel.getLayout();
	    		card.show(panel, "deleteRecipePanel");
	        }
	    };
	     
	    ButtonColumn deleteButton = new ButtonColumn(tableOfRecipes, delete, 4);
	    deleteButton.setMnemonic(KeyEvent.VK_D);

	    scroll = new JScrollPane(tableOfRecipes);
	    scroll.setBounds(0, 0, 537, 150);
	    add(scroll);
	    
	}
}
