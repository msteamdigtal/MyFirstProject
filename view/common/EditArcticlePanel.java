package com.comtrade.view.common;

import java.awt.CardLayout;
import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.comtrade.constants.DbConstraints;
import com.comtrade.constants.HttpConstraints;
import com.comtrade.controller.FrontController;
import com.comtrade.database.entities.Arcticle;
import com.comtrade.database.entities.Menu;
import com.comtrade.transfer.TransferClass;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class EditArcticlePanel extends JPanel {
	
	private JTextField tfArcticleName;
	private JTextField tfMeasurment;
	private JTextField tfPurchasePrice;
	private JTextField tfQuantity;
	private List<Menu> menuList;
	
	/**
	 * Create the panel.
	 */
	public EditArcticlePanel(Arcticle arcticle, String pathToImage, JPanel panel, DefaultTableModel model, int modelRow, List<Arcticle> arcticles) {
		
		setBounds(0, 0, 570, 150);
		setBackground(Color.WHITE);
		setLayout(null);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(pathToImage+"\\icons\\groceries.png"));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(50, 11, 24, 24);
		add(label);
		
		tfArcticleName = new JTextField(arcticle.getArcticleName());
		tfArcticleName.setColumns(10);
		tfArcticleName.setBounds(97, 11, 150, 24);
		add(tfArcticleName);
		
		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(pathToImage+"\\icons\\quantity.png"));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(285, 11, 24, 24);
		add(label_1);
		
		tfMeasurment = new JTextField(arcticle.getQuantityUntiMeasure());
		tfMeasurment.setColumns(10);
		tfMeasurment.setBounds(332, 11, 150, 24);
		add(tfMeasurment);
		
		JLabel label_2 = new JLabel("");
		label_2.setIcon(new ImageIcon(pathToImage+"\\icons\\price-tag.png"));
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(50, 46, 24, 24);
		add(label_2);
		
		tfPurchasePrice = new JTextField(""+arcticle.getPurchasePrice());
		tfPurchasePrice.setColumns(10);
		tfPurchasePrice.setBounds(97, 46, 150, 24);
		add(tfPurchasePrice);
		
		JLabel label_3 = new JLabel("");
		label_3.setIcon(new ImageIcon(pathToImage+"\\icons\\measuring.png"));
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setBounds(285, 46, 24, 24);
		add(label_3);
		
		JLabel lblQuantity = new JLabel("");
		lblQuantity.setHorizontalAlignment(SwingConstants.CENTER);
		lblQuantity.setForeground(Color.LIGHT_GRAY);
		lblQuantity.setBounds(332, 70, 150, 14);
		add(lblQuantity);
		
		tfQuantity = new JTextField(""+arcticle.getQuantity());
		tfQuantity.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				try {
					double quantity = Double.parseDouble(tfQuantity.getText());
					lblQuantity.setForeground(Color.LIGHT_GRAY);
					lblQuantity.setText("");
				}catch (NumberFormatException e) {
					lblQuantity.setForeground(Color.RED);
					lblQuantity.setText("invalid number");
				}	
			}
		});
		tfQuantity.setColumns(10);
		tfQuantity.setBounds(332, 46, 150, 24);
		add(tfQuantity);
		
		JLabel label_4 = new JLabel("");
		label_4.setIcon(new ImageIcon(pathToImage+"\\icons\\menu_24.png"));
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setBounds(50, 88, 24, 24);
		add(label_4);
		
		String[] menus;
		try {
			
			TransferClass transfer = FrontController.getInstance().execute(
					TransferClass.create(arcticle.getMenu(), HttpConstraints.GET, DbConstraints.MENU));
			menuList = (List<Menu>) transfer.getResponse();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		menus = new String[menuList.size()];
		menus[0] = arcticle.getMenu().getMenuName();
		for(int i = 0; i < menuList.size(); i++) {
			if(i > 0 && menuList.get(i).getMenuId() != arcticle.getMenu().getMenuId())
				menus[i] = menuList.get(i).getMenuName();
		}
		
		JComboBox comboBox = new JComboBox(menus);
		comboBox.setBounds(97, 88, 150, 24);
		add(comboBox);
		
		JLabel lblEditArcticle = new JLabel("");
		lblEditArcticle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
								
				String arcticleName = null;
				if(!tfArcticleName.getText().equals("")) {
					arcticleName = tfArcticleName.getText();
				}
				
				String quantityUnitOfMeasurment = null;
				if(!tfMeasurment.getText().equals("")) {
					quantityUnitOfMeasurment = tfMeasurment.getText();
				} 
				
				Double purchasePrice = null;
				if(!tfPurchasePrice.getText().equals("")) {
					purchasePrice = Double.parseDouble(tfPurchasePrice.getText());
				}
				
				Double quantity = null;
				if(!tfQuantity.getText().equals("")) {
					quantity = Double.parseDouble(tfQuantity.getText());
					lblQuantity.setForeground(Color.LIGHT_GRAY);
					lblQuantity.setText("");
				}else {
					lblQuantity.setText("all fields required");
					lblQuantity.setForeground(Color.RED);
				}
				
				boolean isMenuSelected = false;
				if(!comboBox.getSelectedItem().equals("")) {
					isMenuSelected = true;
				}
				
				if(arcticleName != null && quantityUnitOfMeasurment != null && purchasePrice != null && quantity != null & lblQuantity.getText().equals("") && isMenuSelected){
					
					arcticle.setArcticleName(arcticleName);
					arcticle.setQuantityUntiMeasure(quantityUnitOfMeasurment);
					arcticle.setPurchasePrice(purchasePrice);
					arcticle.setPriceWithVAT(arcticle.getPurchasePrice()*1.2);
					arcticle.setQuantity(quantity);
					
					for(Menu menu: menuList) {
						if(menu.getMenuName().equals(comboBox.getSelectedItem()) && arcticle.getMenu().getMenuId() != menu.getMenuId()) {
							arcticle.setMenu(menu);
							break;
						}
					}
					
					try {
						TransferClass transfer = FrontController.getInstance().execute(
								TransferClass.create(arcticle, HttpConstraints.PUT, DbConstraints.ARCTICLE));
						arcticles.set(modelRow, arcticle);
						model.setValueAt(arcticle.getArcticleName(), modelRow, 0);
						model.setValueAt(arcticle.getQuantityUntiMeasure(), modelRow, 1);
						model.setValueAt(arcticle.getPurchasePrice(), modelRow, 2);
						model.setValueAt(arcticle.getPriceWithVAT(), modelRow, 3);
						model.setValueAt(arcticle.getMenu().getMenuName(), modelRow, 4);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					CardLayout card = (CardLayout)panel.getLayout();
					card.show(panel, "arcticleTablePanel");
					panel.remove(EditArcticlePanel.this);
				}
			}
		});
		lblEditArcticle.setIcon(new ImageIcon(pathToImage+"\\icons\\design.png"));
		lblEditArcticle.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditArcticle.setForeground(Color.WHITE);
		lblEditArcticle.setBounds(332, 89, 40, 40);
		add(lblEditArcticle);
		
		JLabel lblCancelEdit = new JLabel("");
		lblCancelEdit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CardLayout card = (CardLayout)panel.getLayout();
				card.show(panel, "arcticleTablePanel");
				panel.remove(EditArcticlePanel.this);
			}
		});
		lblCancelEdit.setIcon(new ImageIcon(pathToImage+"\\icons\\clear.png"));
		lblCancelEdit.setHorizontalAlignment(SwingConstants.CENTER);
		lblCancelEdit.setForeground(Color.WHITE);
		lblCancelEdit.setBounds(442, 88, 40, 40);
		add(lblCancelEdit);
		
		
				
		
	}
}
