package com.comtrade.view.common;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.comtrade.constants.DbConstraints;
import com.comtrade.constants.HttpConstraints;
import com.comtrade.controller.FrontController;
import com.comtrade.database.entities.Menu;
import com.comtrade.database.entities.User;
import com.comtrade.transfer.TransferClass;


public class DeleteMenuPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public DeleteMenuPanel(Menu menu, String pathToImage, JPanel panel, JTable table, int modelRow, List<Menu> menus, JComboBox comboBox, JPanel arcticlesPanel, ArcticleTablePanel arcticleTablePanel, User user) {
		
		setBounds(0, 0, 213, 150);
		setBackground(Color.WHITE);
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Do you really want to delete ");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 11, 193, 20);
		add(lblNewLabel);
		
		JLabel lblMenuName = new JLabel(menu.getMenuName()+"?");
		lblMenuName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblMenuName.setHorizontalAlignment(SwingConstants.CENTER);
		lblMenuName.setBounds(10, 32, 193, 20);
		add(lblMenuName);
		
		JLabel lblDeleteIt = new JLabel("");
		lblDeleteIt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
	            	TransferClass transfer = FrontController.getInstance().execute(
	    					TransferClass.create(menu, HttpConstraints.DELETE, DbConstraints.MENU));
	            	if(transfer.getMessage().equals("Successfully deleted Menu!")) {
	            		((DefaultTableModel)table.getModel()).removeRow(modelRow);
	            		menus.remove(menu);
	            		comboBox.removeItem(menu.getMenuName());
	            	}
	            }catch (ClassNotFoundException e) {
	    			e.printStackTrace();
	    		} catch (IOException e) {
	    			e.printStackTrace();
	    		}
				CardLayout card = (CardLayout)panel.getLayout();
				card.show(panel, "menuTablePanel");
				panel.remove(DeleteMenuPanel.this);
				
				arcticlesPanel.remove(arcticleTablePanel);
				ArcticleTablePanel addNewArcticle = new ArcticleTablePanel(user, arcticlesPanel, pathToImage);
				arcticlesPanel.add(addNewArcticle, "arcticleTablePanel");
			    CardLayout card1 = (CardLayout)arcticlesPanel.getLayout();
				card1.show(arcticlesPanel, "arcticleTablePanel");
			}
		});
		lblDeleteIt.setIcon(new ImageIcon(pathToImage+"\\icons\\trash.png"));
		lblDeleteIt.setHorizontalAlignment(SwingConstants.CENTER);
		lblDeleteIt.setBounds(36, 89, 50, 50);
		add(lblDeleteIt);
		
		JLabel lblCancel = new JLabel("");
		lblCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CardLayout card = (CardLayout)panel.getLayout();
				card.show(panel, "menuTablePanel");
				panel.remove(DeleteMenuPanel.this);
			}
		});
		lblCancel.setIcon(new ImageIcon(pathToImage+"\\icons\\clear.png"));
		lblCancel.setHorizontalAlignment(SwingConstants.CENTER);
		lblCancel.setBounds(129, 89, 50, 50);
		add(lblCancel);
		
	}
}
