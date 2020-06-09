package com.comtrade.view.common;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import com.comtrade.constants.DbConstraints;
import com.comtrade.constants.HttpConstraints;
import com.comtrade.controller.FrontController;
import com.comtrade.database.entities.Menu;
import com.comtrade.database.entities.User;
import com.comtrade.transfer.TransferClass;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;


public class EditMenuPanel extends JPanel {
	
	private JTextField textField;
	private String[] menuNames;

	/**
	 * Create the panel.
	 */
	public EditMenuPanel(Menu menu, String pathToImage, JPanel panel, DefaultTableModel model, int modelRow, List<Menu> menus, JComboBox comboBox, JPanel arcticlesPanel, ArcticleTablePanel arcticleTablePanel, User user) {
		
		setBounds(0, 0, 213, 150);
		setBackground(Color.WHITE);
		setLayout(null);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(pathToImage+"\\icons\\menu_24.png"));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(10, 24, 24, 24);
		add(label);
		
		textField = new JTextField(menu.getMenuName());
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setColumns(10);
		textField.setBounds(57, 24, 146, 24);
		add(textField);
		
		JLabel lblMenuName = new JLabel("menu name");
		lblMenuName.setHorizontalAlignment(SwingConstants.CENTER);
		lblMenuName.setForeground(Color.LIGHT_GRAY);
		lblMenuName.setBounds(57, 48, 146, 14);
		add(lblMenuName);
		
		JLabel label_1 = new JLabel("");
		label_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
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
					try {
						comboBox.removeItem(menu.getMenuName());
						menu.setMenuName(menuName);
						TransferClass transfer = FrontController.getInstance().execute(
								TransferClass.create(menu, HttpConstraints.PUT, DbConstraints.MENU));
						menus.set(modelRow, menu);
						model.setValueAt(menuName, modelRow, 0);
						comboBox.addItem(menuName);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					CardLayout card = (CardLayout)panel.getLayout();
					card.show(panel, "menuTablePanel");
					panel.remove(EditMenuPanel.this);
					
	
		    		arcticlesPanel.remove(arcticleTablePanel);
					ArcticleTablePanel addNewArcticle = new ArcticleTablePanel(user, arcticlesPanel, pathToImage);
					arcticlesPanel.add(addNewArcticle, "arcticleTablePanel");
				    CardLayout card1 = (CardLayout)arcticlesPanel.getLayout();
					card1.show(arcticlesPanel, "arcticleTablePanel");
				}
			}
		});
		label_1.setIcon(new ImageIcon(pathToImage+"\\icons\\paper.png"));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setForeground(Color.WHITE);
		label_1.setBounds(35, 85, 40, 40);
		add(label_1);
		
		JLabel label_2 = new JLabel("");
		label_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CardLayout card = (CardLayout)panel.getLayout();
				card.show(panel, "menuTablePanel");
				panel.remove(EditMenuPanel.this);
			}
		});
		label_2.setIcon(new ImageIcon(pathToImage+"\\icons\\clear.png"));
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setForeground(Color.WHITE);
		label_2.setBounds(145, 84, 40, 40);
		add(label_2);
		
	}
}
