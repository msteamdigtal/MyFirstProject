package com.comtrade.view.common;

import java.awt.Color;
import java.util.Calendar;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.comtrade.constants.DbConstraints;
import com.comtrade.constants.HttpConstraints;
import com.comtrade.controller.FrontController;
import com.comtrade.database.entities.Arcticle;
import com.comtrade.database.entities.Bill;
import com.comtrade.database.entities.User;
import com.comtrade.transfer.TransferClass;

import java.awt.CardLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.io.IOException;
import java.sql.Timestamp;

import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class EditBillPanel extends JPanel {
	
	private String[] paymentTypes;
	private List<Arcticle> allArcticles;
	private List<Arcticle> billArcticles;
	private ArcticlesForBillTable arcticlesForEditing;
	
	/**
	 * Create the panel.
	 */
	public EditBillPanel(User user, Bill bill, String pathToImage, JTable tableOfBills, DefaultTableModel model, JPanel panel, int modelRow, List<Bill> bills) {
		
		setBounds(0, 0, 782, 150);
		setBackground(Color.WHITE);
		setLayout(null);
		
		paymentTypes = new String[]{"cash", "card", "check", "representation"};
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(622, 11, 150, 128);
		add(panel_1);
		panel_1.setLayout(new CardLayout(0, 0));
		arcticlesForEditing = new ArcticlesForBillTable(user, panel_1, pathToImage, bill.getArcticles(), true);
		panel_1.add(arcticlesForEditing, "arcticlesForEditing");
		CardLayout card = (CardLayout)panel.getLayout();
		card.show(panel, "arcticlesForEditing");
		
		JLabel lblTitleOfPanel = new JLabel("Bill for table "+bill.getTable().getRestaurantTableName());
		lblTitleOfPanel.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitleOfPanel.setForeground(Color.BLACK);
		lblTitleOfPanel.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblTitleOfPanel.setBounds(10, 11, 235, 24);
		add(lblTitleOfPanel);
		
		JLabel lblArcticleIcon = new JLabel("");
		lblArcticleIcon.setIcon(new ImageIcon(pathToImage+"\\icons\\groceries.png"));
		lblArcticleIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblArcticleIcon.setBounds(572, 11, 24, 24);
		add(lblArcticleIcon);
		
		String[] arcticleNames;
		try {
			
			Arcticle arcticle = new Arcticle();
			arcticle.setRestaurant(bill.getRestaurant());
			
			TransferClass transfer = FrontController.getInstance().execute(
					TransferClass.create(arcticle, HttpConstraints.GET_ONE, DbConstraints.ARCTICLE));
			allArcticles = (List<Arcticle>) transfer.getResponse();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
				
		arcticleNames = new String[allArcticles.size()+1];
		arcticleNames[0] = "";
		for(int i = 0; i < allArcticles.size(); i++) {
			arcticleNames[i+1] = allArcticles.get(i).getArcticleName();
		}
		
		JLabel lblTypePaymentIcon = new JLabel("");
		lblTypePaymentIcon.setIcon(new ImageIcon(pathToImage+"\\icons\\credit-card.png"));
		lblTypePaymentIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblTypePaymentIcon.setBounds(10, 51, 24, 24);
		add(lblTypePaymentIcon);
		
		JComboBox comboBoxPaymentType = new JComboBox(paymentTypes);
		comboBoxPaymentType.setBounds(57, 51, 150, 24);
		add(comboBoxPaymentType);
		
		JLabel lblBenathTypeOfPayment = new JLabel("type of payment");
		lblBenathTypeOfPayment.setHorizontalAlignment(SwingConstants.CENTER);
		lblBenathTypeOfPayment.setForeground(Color.LIGHT_GRAY);
		lblBenathTypeOfPayment.setBounds(67, 75, 126, 14);
		add(lblBenathTypeOfPayment);
		
		JLabel lblCurrentSum = new JLabel(""+bill.getBillSum());
		lblCurrentSum.setHorizontalAlignment(SwingConstants.CENTER);
		lblCurrentSum.setBounds(57, 101, 150, 24);
		add(lblCurrentSum);
		
		JLabel lblSumIcon = new JLabel("");
		lblSumIcon.setIcon(new ImageIcon(pathToImage+"\\icons\\sum.png"));
		lblSumIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblSumIcon.setBounds(10, 101, 24, 24);
		add(lblSumIcon);
		
		JLabel lblBeneathSum = new JLabel("current sum");
		lblBeneathSum.setHorizontalAlignment(SwingConstants.CENTER);
		lblBeneathSum.setForeground(Color.LIGHT_GRAY);
		lblBeneathSum.setBounds(67, 125, 126, 14);
		add(lblBeneathSum);
		
		JLabel lblEditBill = new JLabel("");
		lblEditBill.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
					bill.setProFormaInvoice(false);		
					bill.setTypeOfPayment((String) comboBoxPaymentType.getSelectedItem());
					
					Double sum = 0.0;
					for(Arcticle arcticle : bill.getArcticles()) {
						sum += arcticle.getPriceWithVAT();
					}
					Calendar calendar = Calendar.getInstance();
					int timeOfDay = calendar.get(Calendar.HOUR_OF_DAY);
					String shift = "";
					if(timeOfDay >= 8 && timeOfDay < 16)
						shift = "First";
					else if(timeOfDay >= 16 && timeOfDay < 24)
						shift = "Second";
					else if(timeOfDay >= 0 && timeOfDay < 8)
						shift = "Third";
					bill.setShift(shift);
					bill.setBillSum(sum);
					Timestamp currentDateTime = new Timestamp(System.currentTimeMillis());
					bill.setDateTime(currentDateTime);
					try {
						TransferClass transfer = FrontController.getInstance().execute(
								TransferClass.create(bill, HttpConstraints.PUT, DbConstraints.BILL));
						
						if(transfer.getMessage().equals("Successfully updated bill!")) {
							bills.set(modelRow, bill);
							if(user.isAdmin()) {
								model.setValueAt("Regular", modelRow, 0);
								model.setValueAt(bill.getBillSum(), modelRow, 1);
								model.setValueAt(bill.getDateTime(), modelRow, 2);
								model.setValueAt(bill.getTypeOfPayment(), modelRow, 3);
								model.setValueAt("details", modelRow, 6);
							}else {
								((DefaultTableModel)tableOfBills.getModel()).removeRow(modelRow);
			            		bills.remove(bill);
							}
							panel_1.remove(arcticlesForEditing);
							
							CardLayout card = (CardLayout)panel.getLayout();
							card.show(panel, "tableOfRestaurantTablesPanel");
							panel.remove(EditBillPanel.this);
						}
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
		});
		lblEditBill.setIcon(new ImageIcon(pathToImage+"\\icons\\cheque.png"));
		lblEditBill.setBounds(315, 89, 50, 50);
		add(lblEditBill);
		
		JLabel lblCancel = new JLabel("");
		lblCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CardLayout card = (CardLayout)panel.getLayout();
				card.show(panel, "billTablePanel");
				panel_1.remove(arcticlesForEditing);
				panel.remove(EditBillPanel.this);
			}
		});
		lblCancel.setIcon(new ImageIcon(pathToImage+"\\icons\\clear.png"));
		lblCancel.setBounds(415, 89, 50, 50);
		add(lblCancel);
		
		
		
	}

}
