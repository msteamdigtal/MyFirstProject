package com.comtrade.view.common;

import java.awt.Color;

import javax.swing.JPanel;
import java.awt.CardLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.text.NumberFormatter;

import com.comtrade.constants.DbConstraints;
import com.comtrade.constants.HttpConstraints;
import com.comtrade.controller.FrontController;
import com.comtrade.database.entities.Arcticle;
import com.comtrade.database.entities.Menu;
import com.comtrade.database.entities.Recipe;
import com.comtrade.database.entities.User;
import com.comtrade.database.entities.Warehouse;
import com.comtrade.transfer.TransferClass;
import com.comtrade.view.admin.WarehouseTablePanel;

import java.awt.Font;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class RecipesPanel extends JPanel {
	
	private JTextField tfGoodQuantity;
	private List<Arcticle> arcticles;
	private List<Warehouse> warehouses;
	private RecipeTablePanel recipeTablePanel;

	/**
	 * Create the panel.
	 */
	public RecipesPanel(User user, String pathToImage, JPanel lowerAdminPanel) {
		
		setBackground(Color.WHITE);
		setBounds(0, 0, 859, 482);
		setLayout(null);
		
		JPanel tablePanel = new JPanel();
		tablePanel.setBackground(Color.WHITE);
		tablePanel.setBounds(36, 139, 537, 251);
		add(tablePanel);
		tablePanel.setLayout(new CardLayout(0, 0));
		
		recipeTablePanel = new RecipeTablePanel(user, tablePanel, pathToImage);
		tablePanel.add(recipeTablePanel, "recipeTablePanel");
	    CardLayout card = (CardLayout)tablePanel.getLayout();
		card.show(tablePanel, "recipeTablePanel");
		
		JLabel lblRecipeIcon = new JLabel("");
		lblRecipeIcon.setIcon(new ImageIcon(pathToImage+"\\icons\\recipe-book.png"));
		lblRecipeIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblRecipeIcon.setBounds(164, 35, 70, 70);
		add(lblRecipeIcon);
		
		JLabel lbl = new JLabel("Add new recipe");
		lbl.setHorizontalAlignment(SwingConstants.CENTER);
		lbl.setForeground(Color.BLACK);
		lbl.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lbl.setBounds(635, 81, 197, 24);
		add(lbl);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(pathToImage+"\\icons\\measuring.png"));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(635, 139, 24, 24);
		add(label);

		JLabel lblQuantityOfGood = new JLabel("quantity of good");
		lblQuantityOfGood.setHorizontalAlignment(SwingConstants.CENTER);
		lblQuantityOfGood.setForeground(Color.LIGHT_GRAY);
		lblQuantityOfGood.setBounds(692, 164, 126, 14);
		add(lblQuantityOfGood);
		
		tfGoodQuantity = new JTextField();
		tfGoodQuantity.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				try {
					double quantity = Double.parseDouble(tfGoodQuantity.getText());
					lblQuantityOfGood.setForeground(Color.LIGHT_GRAY);
					lblQuantityOfGood.setText("quantity of good");
				}catch (NumberFormatException e) {
					lblQuantityOfGood.setForeground(Color.RED);
					lblQuantityOfGood.setText("invalid number");
				}
			}
		});
		tfGoodQuantity.setColumns(10);
		tfGoodQuantity.setBounds(682, 139, 150, 24);
		add(tfGoodQuantity);
		
		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(pathToImage+"\\icons\\beverage_24.png"));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(635, 206, 24, 24);
		add(label_1);
		

		String[] arcticleNames;
		try {
			
			Arcticle arcticle = new Arcticle();
			arcticle.setRestaurant(user.getRestaurant());
			
			TransferClass transfer = FrontController.getInstance().execute(
					TransferClass.create(arcticle, HttpConstraints.GET, DbConstraints.ARCTICLE));
			arcticles = (List<Arcticle>) transfer.getResponse();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		arcticleNames = new String[arcticles.size()+1];
		arcticleNames[0] = "";
		for(int i = 0; i < arcticles.size(); i++) {
			arcticleNames[i+1] = arcticles.get(i).getArcticleName();
		}
		
		JComboBox arcticleComboBox = new JComboBox(arcticleNames);
		arcticleComboBox.setBounds(682, 206, 150, 24);
		add(arcticleComboBox);
		
		JLabel lblArcticle = new JLabel("arcticle");
		lblArcticle.setHorizontalAlignment(SwingConstants.CENTER);
		lblArcticle.setForeground(Color.LIGHT_GRAY);
		lblArcticle.setBounds(692, 230, 126, 14);
		add(lblArcticle);
		
		JLabel label_2 = new JLabel("");
		label_2.setIcon(new ImageIcon(pathToImage+"\\icons\\groceries.png"));
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(635, 273, 24, 24);
		add(label_2);
		
		String[] warehouseNames;
		try {
			
			Warehouse warehouse = new Warehouse();
			warehouse.setRestaurant(user.getRestaurant());
			
			TransferClass transfer = FrontController.getInstance().execute(
					TransferClass.create(warehouse, HttpConstraints.GET, DbConstraints.WAREHOUSE));
			warehouses = (List<Warehouse>) transfer.getResponse();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		warehouseNames = new String[warehouses.size()+1];
		warehouseNames[0] = "";
		for(int i = 0; i < warehouses.size(); i++) {
			warehouseNames[i+1] = warehouses.get(i).getGoodName();
		}
		
		JComboBox goodComboBox = new JComboBox(warehouseNames);
		goodComboBox.setBounds(682, 273, 150, 24);
		add(goodComboBox);
		
		JLabel lblOfGood = new JLabel("good for recipe");
		lblOfGood.setHorizontalAlignment(SwingConstants.CENTER);
		lblOfGood.setForeground(Color.LIGHT_GRAY);
		lblOfGood.setBounds(692, 297, 126, 14);
		add(lblOfGood);
		
		JLabel lblAddNewRecipe = new JLabel("");
		lblAddNewRecipe.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				Recipe recipe = new Recipe();
				
				Double goodQuantity = null;
				if(!tfGoodQuantity.getText().equals("")) {
					goodQuantity = Double.parseDouble(tfGoodQuantity.getText());
					lblQuantityOfGood.setText("quantity of good");
					lblQuantityOfGood.setForeground(Color.LIGHT_GRAY);
				}else {
					lblQuantityOfGood.setText("required field");
					lblQuantityOfGood.setForeground(Color.RED);
				}
				
				boolean isArcticleSelected = false;
				if(!arcticleComboBox.getSelectedItem().equals("")) {
					isArcticleSelected = true;
					lblArcticle.setText("arcticle");
					lblArcticle.setForeground(Color.LIGHT_GRAY);
				}else {
					isArcticleSelected = false;
					lblArcticle.setText("required field");
					lblArcticle.setForeground(Color.RED);
				}
				
				boolean isGoodSelected = false;
				if(!goodComboBox.getSelectedItem().equals("")) {
					isGoodSelected = true;
					lblOfGood.setText("arcticle");
					lblOfGood.setForeground(Color.LIGHT_GRAY);
				}else {
					isGoodSelected = false;
					lblOfGood.setText("required field");
					lblOfGood.setForeground(Color.RED);
				}
				
				if(goodQuantity != null && lblQuantityOfGood.getText().equals("quantity of good") && isArcticleSelected && isGoodSelected) {
					
					recipe.setGoodQuantity(goodQuantity);
					recipe.setRestaurant(user.getRestaurant());
					
					for(Arcticle arcticle: arcticles) {
						if(arcticle.getArcticleName().equals(arcticleComboBox.getSelectedItem())) {
							recipe.setArcticle(arcticle);
							break;
						}
					}
					
					for(Warehouse warehouse: warehouses) {
						if(warehouse.getGoodName().equals(goodComboBox.getSelectedItem())) {
							recipe.setWarehouse(warehouse);
							break;
						}
					}
					
					
					try {
						TransferClass transfer = FrontController.getInstance().execute(
								TransferClass.create(recipe, HttpConstraints.POST, DbConstraints.RECIPE));
						if(transfer.getMessage().equals("Successfully added recipe!")) {
							tablePanel.remove(recipeTablePanel);
							RecipeTablePanel addNewRecipe = new RecipeTablePanel(user, tablePanel, pathToImage);
							tablePanel.add(addNewRecipe, "recipeTablePanel");
						    CardLayout card = (CardLayout)tablePanel.getLayout();
							card.show(tablePanel, "recipeTablePanel");
							tfGoodQuantity.setText("");
							arcticleComboBox.setSelectedItem("");
							goodComboBox.setSelectedItem("");
						}
					}catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			}
		});
		lblAddNewRecipe.setIcon(new ImageIcon(pathToImage+"\\icons\\add_recipe.png"));
		lblAddNewRecipe.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddNewRecipe.setForeground(Color.WHITE);
		lblAddNewRecipe.setBounds(682, 340, 50, 50);
		add(lblAddNewRecipe);
		
	}
}
