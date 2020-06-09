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


public class ViewBillPanel extends JPanel {
	
	private String[] paymentTypes;
	private List<Arcticle> allArcticles;
	private List<Arcticle> billArcticles;
	private ArcticlesForBillTable arcticlesForBillTable;
	private boolean isViewBill;
	/**
	 * Create the panel.
	 */
	public ViewBillPanel(User user, Bill bill, String pathToImage, JTable tableOfBills, DefaultTableModel model, JPanel panel, int modelRow, List<Bill> bills) {
		
		setBounds(0, 0, 782, 150);
		setBackground(Color.WHITE);
		setLayout(null);
		
		paymentTypes = new String[]{"cash", "card", "check", "representation"};
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(622, 11, 150, 128);
		add(panel_1);
		panel_1.setLayout(new CardLayout(0, 0));
		arcticlesForBillTable = new ArcticlesForBillTable(user, panel_1, pathToImage, bill.getArcticles(), true);
		panel_1.add(arcticlesForBillTable, "arcticlesForBillTable");
		CardLayout card = (CardLayout)panel.getLayout();
		card.show(panel, "arcticlesForBillTable");
		
		JLabel lblTitleOfPanel = new JLabel("Bill for table "+bill.getTable().getRestaurantTableName()+", for "+bill.getDateTime());
		lblTitleOfPanel.setHorizontalAlignment(SwingConstants.LEFT);
		lblTitleOfPanel.setForeground(Color.BLACK);
		lblTitleOfPanel.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblTitleOfPanel.setBounds(10, 11, 395, 24);
		add(lblTitleOfPanel);
		
		JLabel lblArcticleIcon = new JLabel("");
		lblArcticleIcon.setIcon(new ImageIcon(pathToImage+"\\icons\\groceries.png"));
		lblArcticleIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblArcticleIcon.setBounds(415, 11, 24, 24);
		add(lblArcticleIcon);
		
		JLabel arcticles = new JLabel("arcticles:");
		arcticles.setBounds(462, 11, 150, 24);
		arcticles.setHorizontalAlignment(SwingConstants.CENTER);
		add(arcticles);
		
		JLabel lblTypePaymentIcon = new JLabel("");
		lblTypePaymentIcon.setIcon(new ImageIcon(pathToImage+"\\icons\\credit-card.png"));
		lblTypePaymentIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblTypePaymentIcon.setBounds(10, 51, 24, 24);
		add(lblTypePaymentIcon);
		
		JLabel paymentType = new JLabel(bill.getTypeOfPayment());
		paymentType.setBounds(57, 51, 150, 24);
		paymentType.setHorizontalAlignment(SwingConstants.CENTER);
		add(paymentType);
		
		JLabel lblBenathTypeOfPayment = new JLabel("type of payment");
		lblBenathTypeOfPayment.setHorizontalAlignment(SwingConstants.CENTER);
		lblBenathTypeOfPayment.setForeground(Color.LIGHT_GRAY);
		lblBenathTypeOfPayment.setBounds(67, 75, 126, 14);
		add(lblBenathTypeOfPayment);
		
		JLabel lblCurrentSum = new JLabel(""+bill.getBillSum());
		lblCurrentSum.setHorizontalAlignment(SwingConstants.CENTER);
		lblCurrentSum.setBounds(315, 51, 150, 24);
		add(lblCurrentSum);
		
		JLabel lblSumIcon = new JLabel("");
		lblSumIcon.setIcon(new ImageIcon(pathToImage+"\\icons\\sum.png"));
		lblSumIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblSumIcon.setBounds(268, 51, 24, 24);
		add(lblSumIcon);
		
		JLabel lblBeneathSum = new JLabel("current sum");
		lblBeneathSum.setHorizontalAlignment(SwingConstants.CENTER);
		lblBeneathSum.setForeground(Color.LIGHT_GRAY);
		lblBeneathSum.setBounds(325, 75, 126, 14);
		add(lblBeneathSum);
		
		JLabel lblCancel = new JLabel("");
		lblCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CardLayout card = (CardLayout)panel.getLayout();
				card.show(panel, "billTablePanel");
				panel_1.remove(arcticlesForBillTable);
				panel.remove(ViewBillPanel.this);
			}
		});
		lblCancel.setIcon(new ImageIcon(pathToImage+"\\icons\\clear.png"));
		lblCancel.setBounds(510, 75, 50, 50);
		add(lblCancel);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(pathToImage+"\\icons\\shift.png"));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(10, 101, 24, 24);
		add(label);
		
		JLabel label_1 = new JLabel(bill.getShift());
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(57, 101, 150, 24);
		add(label_1);
		
		JLabel lblShift = new JLabel("shift");
		lblShift.setHorizontalAlignment(SwingConstants.CENTER);
		lblShift.setForeground(Color.LIGHT_GRAY);
		lblShift.setBounds(67, 125, 126, 14);
		add(lblShift);
		
		JLabel label_2 = new JLabel("");
		label_2.setIcon(new ImageIcon(pathToImage+"\\icons\\dining-table_24.png"));
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(268, 101, 24, 24);
		add(label_2);
		
		JLabel label_3 = new JLabel(bill.getTable().getRestaurantTableName());
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setBounds(315, 101, 150, 24);
		add(label_3);
		
		JLabel lblTable = new JLabel("table");
		lblTable.setHorizontalAlignment(SwingConstants.CENTER);
		lblTable.setForeground(Color.LIGHT_GRAY);
		lblTable.setBounds(325, 125, 126, 14);
		add(lblTable);
		
		
		
	}
}
