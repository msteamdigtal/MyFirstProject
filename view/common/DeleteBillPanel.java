package com.comtrade.view.common;

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
import com.comtrade.database.entities.Bill;
import com.comtrade.transfer.TransferClass;


public class DeleteBillPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public DeleteBillPanel(Bill bill, String pathToImage, JPanel panel, JTable table, int modelRow, List<Bill> billList) {
		
		setBounds(0, 0, 782, 150);
		setBackground(Color.WHITE);
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Are you shure you want to delete this bill?");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(204, 25, 370, 20);
		add(lblNewLabel);
		
		JLabel lblDeleteIt = new JLabel("");
		lblDeleteIt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
	            	TransferClass transfer = FrontController.getInstance().execute(
	    					TransferClass.create(bill, HttpConstraints.DELETE, DbConstraints.BILL));
	            	if(transfer.getMessage().equals("Successfully deleted table!")) {
	            		((DefaultTableModel)table.getModel()).removeRow(modelRow);
	            		billList.remove(bill);
	            	}
	            }catch (ClassNotFoundException e) {
	    			e.printStackTrace();
	    		} catch (IOException e) {
	    			e.printStackTrace();
	    		}
				CardLayout card = (CardLayout)panel.getLayout();
				card.show(panel, "billTablePanel");
				panel.remove(DeleteBillPanel.this);
			}
		});
		lblDeleteIt.setIcon(new ImageIcon(pathToImage+"\\icons\\outcome.png"));
		lblDeleteIt.setHorizontalAlignment(SwingConstants.CENTER);
		lblDeleteIt.setBounds(284, 75, 50, 50);
		add(lblDeleteIt);
		
		JLabel lblCancel = new JLabel("");
		lblCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CardLayout card = (CardLayout)panel.getLayout();
				card.show(panel, "billTablePanel");
				panel.remove(DeleteBillPanel.this);
			}
		});
		lblCancel.setIcon(new ImageIcon(pathToImage+"\\icons\\clear.png"));
		lblCancel.setHorizontalAlignment(SwingConstants.CENTER);
		lblCancel.setBounds(434, 75, 50, 50);
		add(lblCancel);
		
	}

}
