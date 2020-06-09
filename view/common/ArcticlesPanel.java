package com.comtrade.view.common;

import java.awt.CardLayout;
import java.awt.Color;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.text.NumberFormatter;

import com.comtrade.constants.DbConstraints;
import com.comtrade.constants.HttpConstraints;
import com.comtrade.controller.FrontController;
import com.comtrade.database.entities.Arcticle;
import com.comtrade.database.entities.Menu;
import com.comtrade.database.entities.Restaurant;
import com.comtrade.database.entities.User;
import com.comtrade.database.entities.Warehouse;
import com.comtrade.transfer.TransferClass;
import com.comtrade.view.admin.WarehouseTablePanel;

import java.awt.Font;
import java.io.IOException;
import java.text.NumberFormat;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ArcticlesPanel extends JPanel {
	
	private JTextField tfArcticleName;
	private JTextField tfPurchasePrice;
	private JTextField tfQuantity;
	private JTextField tfMeasurment;
	private ArcticleTablePanel arcticleTablePanel;
	private List<Menu> menuList;
	private JTextField textField;
	private MenuTablePanel menuTablePanel;
	private JComboBox menusComboBox;
		
	/**
	 * Create the panel.
	 */
	public ArcticlesPanel(User user, String pathToImage, JPanel lowerPanel) {
		
		setBackground(Color.WHITE);
		setBounds(0, 0, 859, 482);
		setLayout(null);
		
		JPanel arcticlesPanel = new JPanel();
		arcticlesPanel.setBackground(Color.WHITE);
		arcticlesPanel.setBounds(10, 80, 600, 150);
		add(arcticlesPanel);
		arcticlesPanel.setLayout(new CardLayout(0, 0));
		
		arcticleTablePanel = new ArcticleTablePanel(user, arcticlesPanel, pathToImage);
		arcticlesPanel.add(arcticleTablePanel, "arcticleTablePanel");
	    CardLayout card = (CardLayout)arcticlesPanel.getLayout();
		card.show(arcticlesPanel, "arcticleTablePanel");
		
		JPanel menusPanel = new JPanel();
		menusPanel.setBackground(Color.WHITE);
		menusPanel.setBounds(630, 80, 213, 150);
		add(menusPanel);
		menusPanel.setLayout(new CardLayout(0, 0));
		
		JLabel lblArcticleIcon = new JLabel("");
		lblArcticleIcon.setIcon(new ImageIcon(pathToImage+"\\icons\\beverage.png"));
		lblArcticleIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblArcticleIcon.setBounds(90, 19, 50, 50);
		add(lblArcticleIcon);
		
		JLabel lblMenuIcon = new JLabel("");
		lblMenuIcon.setIcon(new ImageIcon(pathToImage+"\\icons\\menu.png"));
		lblMenuIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblMenuIcon.setBounds(660, 19, 50, 50);
		add(lblMenuIcon);
		
		JLabel lblAddNewArcticle = new JLabel("Add new arcticle");
		lblAddNewArcticle.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddNewArcticle.setForeground(Color.BLACK);
		lblAddNewArcticle.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblAddNewArcticle.setBounds(131, 241, 197, 24);
		add(lblAddNewArcticle);
		
		JLabel lblGoodNameIcon = new JLabel("");
		lblGoodNameIcon.setIcon(new ImageIcon(pathToImage+"\\icons\\groceries.png"));
		lblGoodNameIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblGoodNameIcon.setBounds(30, 291, 24, 24);
		add(lblGoodNameIcon);
		
		tfArcticleName = new JTextField((String) null);
		tfArcticleName.setColumns(10);
		tfArcticleName.setBounds(77, 291, 150, 24);
		add(tfArcticleName);
		
		JLabel lblArcticleName = new JLabel("arcticle name");
		lblArcticleName.setHorizontalAlignment(SwingConstants.CENTER);
		lblArcticleName.setForeground(Color.LIGHT_GRAY);
		lblArcticleName.setBounds(87, 314, 126, 14);
		add(lblArcticleName);
		
		tfPurchasePrice = new JTextField();
		tfPurchasePrice.setColumns(10);
		tfPurchasePrice.setBounds(77, 341, 150, 24);
		add(tfPurchasePrice);
		
		JLabel lblPPIcon = new JLabel("");
		lblPPIcon.setIcon(new ImageIcon(pathToImage+"\\icons\\price-tag.png"));
		lblPPIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblPPIcon.setBounds(30, 341, 24, 24);
		add(lblPPIcon);
		
		JLabel lblPurchasePrice = new JLabel("purchase price");
		lblPurchasePrice.setHorizontalAlignment(SwingConstants.CENTER);
		lblPurchasePrice.setForeground(Color.LIGHT_GRAY);
		lblPurchasePrice.setBounds(87, 366, 126, 14);
		add(lblPurchasePrice);
		
		JLabel lblQUMIcon = new JLabel("");
		lblQUMIcon.setIcon(new ImageIcon(pathToImage+"\\icons\\measuring.png"));
		lblQUMIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblQUMIcon.setBounds(258, 291, 24, 24);
		add(lblQUMIcon);
		
		JLabel lblQuantity = new JLabel("quantity");
		lblQuantity.setHorizontalAlignment(SwingConstants.CENTER);
		lblQuantity.setForeground(Color.LIGHT_GRAY);
		lblQuantity.setBounds(305, 314, 150, 14);
		add(lblQuantity);
		
		tfQuantity = new JTextField();
		tfQuantity.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				try {
					double quantity = Double.parseDouble(tfQuantity.getText());
					lblQuantity.setForeground(Color.LIGHT_GRAY);
					lblQuantity.setText("quantity");
				}catch (NumberFormatException e) {
					lblQuantity.setForeground(Color.RED);
					lblQuantity.setText("invalid number");
				}				
			}
		});
		tfQuantity.setColumns(10);
		tfQuantity.setBounds(305, 291, 150, 24);
		add(tfQuantity);
		
		JLabel lblPriceVATIcon = new JLabel("");
		lblPriceVATIcon.setIcon(new ImageIcon(pathToImage+"\\icons\\quantity.png"));
		lblPriceVATIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblPriceVATIcon.setBounds(258, 341, 24, 24);
		add(lblPriceVATIcon);
		
		tfMeasurment = new JTextField((String) null);
		tfMeasurment.setColumns(10);
		tfMeasurment.setBounds(305, 341, 150, 24);
		add(tfMeasurment);
		
		JLabel lblMeasurment = new JLabel("measurment");
		lblMeasurment.setHorizontalAlignment(SwingConstants.CENTER);
		lblMeasurment.setForeground(Color.LIGHT_GRAY);
		lblMeasurment.setBounds(305, 366, 150, 14);
		add(lblMeasurment);
		
		String[] menus;
		try {
			
			Menu menu = new Menu();
			menu.setRestaurant(user.getRestaurant());
			
			TransferClass transfer = FrontController.getInstance().execute(
					TransferClass.create(menu, HttpConstraints.GET, DbConstraints.MENU));
			menuList = (List<Menu>) transfer.getResponse();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
				
		menus = new String[menuList.size()+1];
		menus[0] = "";
		for(int i = 0; i < menuList.size(); i++) {
			menus[i+1] = menuList.get(i).getMenuName();
		}
		
		menusComboBox = new JComboBox(menus);
		menusComboBox.setBounds(77, 391, 150, 24);
		add(menusComboBox);
		
		menuTablePanel = new MenuTablePanel(user, menusPanel, pathToImage, menusComboBox, arcticlesPanel, arcticleTablePanel);
		menusPanel.add(menuTablePanel, "menuTablePanel");
	    CardLayout card1 = (CardLayout)menusPanel.getLayout();
	    card1.show(menusPanel, "menuTablePanel");
		
		JLabel lblMenu24 = new JLabel("");
		lblMenu24.setIcon(new ImageIcon(pathToImage+"\\icons\\menu_24.png"));
		lblMenu24.setHorizontalAlignment(SwingConstants.CENTER);
		lblMenu24.setBounds(30, 391, 24, 24);
		add(lblMenu24);
		
		JLabel lblMenu = new JLabel("menu");
		lblMenu.setHorizontalAlignment(SwingConstants.CENTER);
		lblMenu.setForeground(Color.LIGHT_GRAY);
		lblMenu.setBounds(87, 415, 126, 14);
		add(lblMenu);
		
		JLabel lblAddArcticle = new JLabel("");
		lblAddArcticle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				Arcticle arcticle = new Arcticle();
				
				String arcticleName = null;
				if(!tfArcticleName.getText().equals("")) {
					arcticleName = tfArcticleName.getText();
					lblQuantity.setText("arcticle name");
					lblQuantity.setForeground(Color.LIGHT_GRAY);
				}else {
					lblQuantity.setText("required field");
					lblQuantity.setForeground(Color.RED);
				}
				
				String quantityUnitOfMeasurment = null;
				if(!tfMeasurment.getText().equals("")) {
					quantityUnitOfMeasurment = tfMeasurment.getText();
					lblMeasurment.setText("measurment");
					lblMeasurment.setForeground(Color.LIGHT_GRAY);
				}else {
					lblMeasurment.setText("required field");
					lblMeasurment.setForeground(Color.RED);
				}
				
				Double purchasePrice = null;
				if(!tfPurchasePrice.getText().equals("")) {
					purchasePrice = Double.parseDouble(tfPurchasePrice.getText());
					lblPurchasePrice.setText("purchase price");
					lblPurchasePrice.setForeground(Color.LIGHT_GRAY);
				}else {
					lblPurchasePrice.setText("required field");
					lblPurchasePrice.setForeground(Color.RED);
				}
				Double quantity = null;
				if(!tfQuantity.getText().equals("")) {
					quantity = Double.parseDouble(tfQuantity.getText());
					lblQuantity.setText("quantity");
					lblQuantity.setForeground(Color.LIGHT_GRAY);
				}else {
					lblQuantity.setText("required field");
					lblQuantity.setForeground(Color.RED);
				}
				
				boolean isMenuSelected = false;
				if(!menusComboBox.getSelectedItem().equals("")) {
					isMenuSelected = true;
					lblMenu.setText("menu");
					lblMenu.setForeground(Color.LIGHT_GRAY);
				}else {
					isMenuSelected = false;
					lblMenu.setText("required field");
					lblMenu.setForeground(Color.RED);
				}
				
				if(arcticleName != null && quantityUnitOfMeasurment != null && purchasePrice != null && quantity != null && lblQuantity.getText().equals("quantity") && isMenuSelected) {
					
					arcticle.setArcticleName(arcticleName);
					arcticle.setQuantityUntiMeasure(quantityUnitOfMeasurment);
					arcticle.setPurchasePrice(purchasePrice);
					arcticle.setPriceWithVAT(purchasePrice*1.2);
					arcticle.setQuantity(quantity);
					arcticle.setRestaurant(user.getRestaurant());
					
					try {
						
						Menu menu = new Menu();
						menu.setRestaurant(user.getRestaurant());
						
						TransferClass transfer = FrontController.getInstance().execute(
								TransferClass.create(menu, HttpConstraints.GET, DbConstraints.MENU));
						menuList = (List<Menu>) transfer.getResponse();
					}catch (ClassNotFoundException e) {
						e.printStackTrace();
					}catch (IOException e) {
						e.printStackTrace();
					}
					
					for(Menu menu: menuList) {
						if(menu.getMenuName().equals(menusComboBox.getSelectedItem())) {
							arcticle.setMenu(menu);
							break;
						}
					}
					
					try {
						TransferClass transfer = FrontController.getInstance().execute(
								TransferClass.create(arcticle, HttpConstraints.POST, DbConstraints.ARCTICLE));
						if(transfer.getMessage().equals("Successfully added Arcticle!")) {
							arcticlesPanel.remove(arcticleTablePanel);
							ArcticleTablePanel addNewArcticle = new ArcticleTablePanel(user, arcticlesPanel, pathToImage);
							arcticlesPanel.add(addNewArcticle, "arcticleTablePanel");
						    CardLayout card = (CardLayout)arcticlesPanel.getLayout();
							card.show(arcticlesPanel, "arcticleTablePanel");
							tfArcticleName.setText("");
							tfQuantity.setText("");
							tfPurchasePrice.setText("");
							tfMeasurment.setText("");
							menusComboBox.setSelectedItem("");
						}
					}catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		lblAddArcticle.setIcon(new ImageIcon(pathToImage+"\\icons\\stamina.png"));
		lblAddArcticle.setForeground(Color.WHITE);
		lblAddArcticle.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddArcticle.setBounds(354, 391, 50, 50);
		add(lblAddArcticle);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(pathToImage+"\\icons\\menu_24.png"));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(620, 291, 24, 24);
		add(label);
		
		textField = new JTextField("");
		textField.setColumns(10);
		textField.setBounds(667, 291, 150, 24);
		add(textField);
		
		JLabel lblMenuName = new JLabel("menu name");
		lblMenuName.setHorizontalAlignment(SwingConstants.CENTER);
		lblMenuName.setForeground(Color.LIGHT_GRAY);
		lblMenuName.setBounds(667, 314, 150, 14);
		add(lblMenuName);
		
		JLabel lblAddNewMenu = new JLabel("");
		lblAddNewMenu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Menu menu = new Menu();
				
				String menuName = null;
				if(!textField.getText().equals("")) {
					menuName = textField.getText();
					lblMenuName.setText("menu name");
					lblMenuName.setForeground(Color.LIGHT_GRAY);
				}else {
					lblMenuName.setText("required field");
					lblMenuName.setForeground(Color.RED);
				}
				
				if(menuName != null) {
					
					menu.setMenuName(menuName);
					menu.setRestaurant(user.getRestaurant());
					
					try {
						TransferClass transfer = FrontController.getInstance().execute(
								TransferClass.create(menu, HttpConstraints.POST, DbConstraints.MENU));
						
						if(transfer.getMessage().equals("Successfully added Menu!")) {
							menusPanel.remove(menuTablePanel);
							menuList.add(menu);
							MenuTablePanel addNewMenu = new MenuTablePanel(user, menusPanel, pathToImage, menusComboBox, arcticlesPanel, arcticleTablePanel);
							menusPanel.add(addNewMenu, "menuTablePanel");
						    CardLayout card = (CardLayout)menusPanel.getLayout();
							card.show(menusPanel, "menuTablePanel");
							textField.setText("");
							menusComboBox.addItem(menu.getMenuName());
							menusComboBox.setSelectedItem("");
						}
					}catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
				
			}
		});
		lblAddNewMenu.setIcon(new ImageIcon(pathToImage+"\\icons\\add.png"));
		lblAddNewMenu.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddNewMenu.setForeground(Color.WHITE);
		lblAddNewMenu.setBounds(718, 346, 50, 50);
		add(lblAddNewMenu);
		
		JLabel lblAddNewMenu_1 = new JLabel("Add new menu");
		lblAddNewMenu_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddNewMenu_1.setForeground(Color.BLACK);
		lblAddNewMenu_1.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblAddNewMenu_1.setBounds(620, 241, 197, 24);
		add(lblAddNewMenu_1);
	}
}
