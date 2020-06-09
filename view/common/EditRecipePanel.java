package com.comtrade.view.common;

import java.awt.CardLayout;
import java.awt.Color;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;

import com.comtrade.constants.DbConstraints;
import com.comtrade.constants.HttpConstraints;
import com.comtrade.controller.FrontController;
import com.comtrade.database.entities.Arcticle;
import com.comtrade.database.entities.Menu;
import com.comtrade.database.entities.Recipe;
import com.comtrade.database.entities.Warehouse;
import com.comtrade.transfer.TransferClass;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JFormattedTextField;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class EditRecipePanel extends JPanel {
	
	private List<Arcticle> arcticleList;
	private List<Warehouse> warehouseList;
	private JTextField tfGoodQuantity;
	
	/**
	 * Create the panel.
	 */
	public EditRecipePanel(Recipe recipe, String pathToImage, JPanel panel, DefaultTableModel model, int modelRow, List<Recipe> recipes) {
		
		setBounds(0, 0, 537, 150);
		setBackground(Color.WHITE);
		setLayout(null);
		
		JLabel lblQuantityIcon = new JLabel("");
		lblQuantityIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblQuantityIcon.setBounds(10, 11, 24, 24);
		add(lblQuantityIcon);

		JLabel label_1 = new JLabel("quantity of good");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setForeground(Color.LIGHT_GRAY);
		label_1.setBounds(67, 36, 126, 14);
		add(label_1);
		
		tfGoodQuantity = new JTextField(""+recipe.getGoodQuantity());
		tfGoodQuantity.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				try {
					double quantity = Double.parseDouble(tfGoodQuantity.getText());
					label_1.setForeground(Color.LIGHT_GRAY);
					label_1.setText("quantity of good");
				}catch (NumberFormatException e) {
					label_1.setForeground(Color.RED);
					label_1.setText("invalid number");
				}
			}
		});
		tfGoodQuantity.setColumns(10);
		tfGoodQuantity.setBounds(57, 11, 150, 24);
		add(tfGoodQuantity);
		
		
		JLabel lblArcticleIcon = new JLabel("");
		lblArcticleIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblArcticleIcon.setBounds(301, 11, 24, 24);
		add(lblArcticleIcon);
		
		String[] arcticles;
		try {
			
			TransferClass transfer = FrontController.getInstance().execute(
					TransferClass.create(recipe.getArcticle(), HttpConstraints.GET, DbConstraints.ARCTICLE));
			arcticleList = (List<Arcticle>) transfer.getResponse();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		arcticles = new String[arcticleList.size()];
		arcticles[0] = recipe.getArcticle().getArcticleName();
		for(int i = 0; i < arcticleList.size(); i++) {
			if(i > 0 && arcticleList.get(i).getArcticleId() != recipe.getArcticle().getArcticleId())
				arcticles[i] = arcticleList.get(i).getArcticleName();
		}
		
		JComboBox arcticleComboBox = new JComboBox(arcticles);
		arcticleComboBox.setBounds(348, 11, 150, 24);
		add(arcticleComboBox);
		
		JLabel label_2 = new JLabel("arcticle");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setForeground(Color.LIGHT_GRAY);
		label_2.setBounds(358, 35, 126, 14);
		add(label_2);
		
		JLabel lblWarehouseIcon = new JLabel("");
		lblWarehouseIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblWarehouseIcon.setBounds(10, 61, 24, 24);
		add(lblWarehouseIcon);
		
		String[] warehouses;
		try {
			
			TransferClass transfer = FrontController.getInstance().execute(
					TransferClass.create(recipe.getWarehouse(), HttpConstraints.GET, DbConstraints.WAREHOUSE));
			warehouseList = (List<Warehouse>) transfer.getResponse();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		warehouses = new String[warehouseList.size()];
		warehouses[0] = recipe.getWarehouse().getGoodName();
		for(int i = 0; i < warehouseList.size(); i++) {
			if(i > 0 && warehouseList.get(i).getWarehouseId() != recipe.getWarehouse().getWarehouseId())
				warehouses[i] = warehouseList.get(i).getGoodName();
		}
		
		JComboBox goodComboBox = new JComboBox(warehouses);
		goodComboBox.setBounds(57, 61, 150, 24);
		add(goodComboBox);
		
		JLabel label_3 = new JLabel("good for recipe");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setForeground(Color.LIGHT_GRAY);
		label_3.setBounds(67, 85, 126, 14);
		add(label_3);
		
		JLabel lblEditRecipe = new JLabel("");
		lblEditRecipe.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				Double goodQuantity = null;
				if(!tfGoodQuantity.getText().equals("")) {
					goodQuantity = Double.parseDouble(tfGoodQuantity.getText());
					label_1.setText("quantity of good");
					label_1.setForeground(Color.LIGHT_GRAY);
				}else {
					label_1.setText("required field");
					label_1.setForeground(Color.RED);
				}
				
				if(goodQuantity != null && label_1.getText().equals("quantity of good")) {
					
					recipe.setGoodQuantity(goodQuantity);
					
					for(Arcticle arcticle: arcticleList) {
						if(arcticle.getArcticleName().equals(arcticleComboBox.getSelectedItem()) && arcticle.getArcticleId() != recipe.getArcticle().getArcticleId()) {
							recipe.setArcticle(arcticle);
							break;
						}
					}
					
					for(Warehouse warehouse: warehouseList) {
						if(warehouse.getGoodName().equals(goodComboBox.getSelectedItem()) && warehouse.getWarehouseId() != recipe.getWarehouse().getWarehouseId()) {
							recipe.setWarehouse(warehouse);
							break;
						}
					}
					
					try {
						TransferClass transfer = FrontController.getInstance().execute(
								TransferClass.create(recipe, HttpConstraints.PUT, DbConstraints.RECIPE));
						recipes.set(modelRow, recipe);
						model.setValueAt(recipe.getGoodQuantity(), modelRow, 0);
						model.setValueAt(recipe.getWarehouse().getGoodName(), modelRow, 1);
						model.setValueAt(recipe.getArcticle().getArcticleName(), modelRow, 2);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					CardLayout card = (CardLayout)panel.getLayout();
					card.show(panel, "recipeTablePanel");
					panel.remove(EditRecipePanel.this);
				}
			}
		});
		lblEditRecipe.setIcon(new ImageIcon(pathToImage+"\\icons\\test.png"));
		lblEditRecipe.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditRecipe.setForeground(Color.WHITE);
		lblEditRecipe.setBounds(355, 60, 50, 50);
		add(lblEditRecipe);
		
		JLabel lblCancel = new JLabel("");
		lblCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				CardLayout card = (CardLayout)panel.getLayout();
				card.show(panel, "recipeTablePanel");
				panel.remove(EditRecipePanel.this);
			}
		});
		lblCancel.setIcon(new ImageIcon(pathToImage+"\\icons\\clear.png"));
		lblCancel.setHorizontalAlignment(SwingConstants.CENTER);
		lblCancel.setForeground(Color.WHITE);
		lblCancel.setBounds(448, 60, 50, 50);
		add(lblCancel);
		
	}

}
