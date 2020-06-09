package com.comtrade.view.admin;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.comtrade.constants.DbConstraints;
import com.comtrade.constants.HttpConstraints;
import com.comtrade.controller.FrontController;
import com.comtrade.database.entities.User;
import com.comtrade.transfer.TransferClass;

public class DeleteUserPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public DeleteUserPanel(User user, String pathToImage, JPanel panel, JTable table, int modelRow, List<User> users) {
		setBounds(0, 0, 537, 150);
		setBackground(Color.WHITE);
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Are you shure you want to delete "+user.getUserName()+" "+user.getUserSurname()+"?");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(83, 24, 370, 20);
		add(lblNewLabel);
		
		JLabel lblDeleteIt = new JLabel("");
		lblDeleteIt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
	            	TransferClass transfer = FrontController.getInstance().execute(
	    					TransferClass.create(user, HttpConstraints.DELETE, DbConstraints.USER));
	            	if(transfer.getMessage().equals("Successfully deactivated user!")) {
	            		((DefaultTableModel)table.getModel()).removeRow(modelRow);
	            		users.remove(user);
	            	}
	            }catch (ClassNotFoundException e) {
	    			e.printStackTrace();
	    		} catch (IOException e) {
	    			e.printStackTrace();
	    		}
				CardLayout card = (CardLayout)panel.getLayout();
				card.show(panel, "userTablePanel");
				panel.remove(DeleteUserPanel.this);
			}
		});
		lblDeleteIt.setIcon(new ImageIcon(pathToImage+"\\icons\\delete_user_32.png"));
		lblDeleteIt.setHorizontalAlignment(SwingConstants.CENTER);
		lblDeleteIt.setBounds(163, 74, 50, 50);
		add(lblDeleteIt);
		
		JLabel lblCancel = new JLabel("");
		lblCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CardLayout card = (CardLayout)panel.getLayout();
				card.show(panel, "userTablePanel");
				panel.remove(DeleteUserPanel.this);
			}
		});
		lblCancel.setIcon(new ImageIcon(pathToImage+"\\icons\\clear.png"));
		lblCancel.setHorizontalAlignment(SwingConstants.CENTER);
		lblCancel.setBounds(313, 74, 50, 50);
		add(lblCancel);
	}

}
