package com.comtrade.view.common;

import java.awt.Color;

import javax.swing.JPanel;
import java.awt.CardLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Rectangle;

import com.comtrade.constants.DbConstraints;
import com.comtrade.constants.HttpConstraints;
import com.comtrade.controller.FrontController;
import com.comtrade.database.entities.Arcticle;
import com.comtrade.database.entities.Bill;
import com.comtrade.database.entities.Menu;
import com.comtrade.database.entities.RestaurantTable;
import com.comtrade.database.entities.User;
import com.comtrade.transfer.TransferClass;
import com.toedter.calendar.JDateChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class BillPanel extends JPanel  implements ItemListener{
	private Calendar calendar;
	private String shift;
	
	private String[] paymentTypes, paymentTypes1;
	private List<Arcticle> arcticles;
	private List<RestaurantTable> restaurantTables;
	
	private JComboBox billTypeComboBox, paymentTypeComboBox, tablesComboBox, tablesForBillsComboBox, billsShiftComboBox, lblPaymentTypeComboBox;
	private JLabel lblPaymentTypeIcon, lblTypeOfPayment, lblRestaurantTable, lblTablesIcon;
	
	private List<Arcticle> arcticlesForBill;
	private ArcticlesForBillTable arcticlesForBillTable;
	private BillTablePanel billTablePanel;
	private JDateChooser dateChooser;
	
	private User user;
	private String pathToImage;
	private JPanel lowerPanel;
	private RestaurantTable restaurantTable;
	private JPanel tableOfBills;
		
	/**
	 * Create the panel.
	 */
	public BillPanel(User user, String pathToImage, JPanel lowerPanel, RestaurantTable restaurantTable) {
		
		this.user = user;
		this.pathToImage = pathToImage;
		this.lowerPanel = lowerPanel;
		this.restaurantTable = restaurantTable;
		
		setBackground(Color.WHITE);
		setBounds(0, 0, 859, 482);
		setLayout(null);
		
		paymentTypes = new String[]{"cash", "card", "check", "representation"};
		paymentTypes1 = new String[]{"", "cash", "card", "check", "representation"};
		arcticlesForBill = new ArrayList<Arcticle>();
		
		tableOfBills = new JPanel();
		tableOfBills.setBackground(Color.WHITE);
		tableOfBills.setBounds(54, 99, 782, 150);
		add(tableOfBills);
		tableOfBills.setLayout(new CardLayout(0, 0));
		
		billTablePanel = new BillTablePanel(user, tableOfBills, pathToImage, restaurantTable, null, null, null, null);
		tableOfBills.add(billTablePanel, "billTablePanel");
		CardLayout card = (CardLayout)tableOfBills.getLayout();
		card.show(tableOfBills, "arcticlesForBillTable");
		
		
		
		if(user.isAdmin()) {
			dateChooser = new JDateChooser();
			dateChooser.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent arg0) {
					String date = ((JTextField)dateChooser.getDateEditor().getUiComponent()).getText();
					if(!date.isEmpty()) {
						tableOfBills.remove(billTablePanel);
						billTablePanel = new BillTablePanel(user, tableOfBills, pathToImage, restaurantTable, date, null, null, null);
						tableOfBills.add(billTablePanel, "billTablePanel");
						CardLayout card = (CardLayout)tableOfBills.getLayout();
						card.show(tableOfBills, "arcticlesForBillTable");
					}
				}
			});
			dateChooser.setBounds(140, 54, 130, 20);
			add(dateChooser);
			
			billsShiftComboBox = new JComboBox(new String[]{"","First", "Second", "Third"});
			billsShiftComboBox.setBounds(472, 52, 130, 24);
			add(billsShiftComboBox);
			billsShiftComboBox.addItemListener(BillPanel.this);
			
			lblPaymentTypeComboBox = new JComboBox(paymentTypes1);
			lblPaymentTypeComboBox.setBounds(634, 52, 130, 24);
			add(lblPaymentTypeComboBox);
			lblPaymentTypeComboBox.addItemListener(BillPanel.this);
			
			if(restaurantTable == null) {
				lblTablesIcon = new JLabel("");
				lblTablesIcon.setIcon(new ImageIcon(pathToImage+"\\icons\\dining-table_24.png"));
				lblTablesIcon.setHorizontalAlignment(SwingConstants.CENTER);
				lblTablesIcon.setBounds(308, 357, 24, 24);
				add(lblTablesIcon);
				
				lblRestaurantTable = new JLabel("table");
				lblRestaurantTable.setHorizontalAlignment(SwingConstants.CENTER);
				lblRestaurantTable.setForeground(Color.LIGHT_GRAY);
				lblRestaurantTable.setBounds(365, 381, 126, 14);
				add(lblRestaurantTable);
				
				String[] tableNames;
				try {
					RestaurantTable resTable = new RestaurantTable();
					resTable.setRestaurant(user.getRestaurant());
					resTable.setUser(user);
					TransferClass transfer = FrontController.getInstance().execute(
							TransferClass.create(resTable, HttpConstraints.GET, DbConstraints.TABLE));
					restaurantTables = (List<RestaurantTable>) transfer.getResponse();
				}catch (ClassNotFoundException e) {
					e.printStackTrace();
				}catch (IOException e) {
					e.printStackTrace();
				}
						
				tableNames = new String[restaurantTables.size()+1];
				tableNames[0] = "";
				for(int i = 0; i < restaurantTables.size(); i++) {
					tableNames[i+1] = restaurantTables.get(i).getRestaurantTableName();
				}
				
				tablesComboBox = new JComboBox(tableNames);
				tablesComboBox.setBounds(355, 357, 150, 24);
				add(tablesComboBox);
				
				tablesForBillsComboBox = new JComboBox(tableNames);
				tablesForBillsComboBox.setBounds(310, 52, 130, 24);
				add(tablesForBillsComboBox);
				tablesForBillsComboBox.addItemListener(BillPanel.this);
			}
			
			JLabel lblClearAll = new JLabel("");
			lblClearAll.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(user.isAdmin()) {
						dateChooser.setDate(null);
						billsShiftComboBox.setSelectedItem("");
						lblPaymentTypeComboBox.setSelectedItem("");
						if(restaurantTable == null) {
							tablesForBillsComboBox.setSelectedItem("");
						}
					}
					tableOfBills.remove(billTablePanel);
					billTablePanel = new BillTablePanel(user, tableOfBills, pathToImage, restaurantTable, null, null, null, null);
					tableOfBills.add(billTablePanel, "billTablePanel");
					CardLayout card = (CardLayout)tableOfBills.getLayout();
					card.show(tableOfBills, "arcticlesForBillTable");
				}
			});
			lblClearAll.setIcon(new ImageIcon(pathToImage+"\\icons\\remove_shift.png"));
			lblClearAll.setVerticalAlignment(SwingConstants.BOTTOM);
			lblClearAll.setHorizontalAlignment(SwingConstants.CENTER);
			lblClearAll.setBounds(796, 41, 40, 40);
			add(lblClearAll);
		}
		
		JLabel justLabel = new JLabel("Add new bill");
		justLabel.setHorizontalAlignment(SwingConstants.CENTER);
		justLabel.setForeground(Color.BLACK);
		justLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		justLabel.setBounds(194, 273, 197, 24);
		add(justLabel);
		
		JLabel lblProFormaIcon = new JLabel("");
		lblProFormaIcon.setIcon(new ImageIcon(pathToImage+"\\icons\\pay.png"));
		lblProFormaIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblProFormaIcon.setBounds(54, 308, 24, 24);
		add(lblProFormaIcon);
		
		String[] billType = {"regular bill", "pro forma invoice"};
		
		billTypeComboBox = new JComboBox(billType);
		billTypeComboBox.setBounds(101, 308, 150, 24);
		add(billTypeComboBox);
		billTypeComboBox.addItemListener(BillPanel.this);
		
		JLabel lblTypeof = new JLabel("type of bill");
		lblTypeof.setHorizontalAlignment(SwingConstants.CENTER);
		lblTypeof.setForeground(Color.LIGHT_GRAY);
		lblTypeof.setBounds(111, 332, 126, 14);
		add(lblTypeof);
		
		lblPaymentTypeIcon = new JLabel("");
		lblPaymentTypeIcon.setIcon(new ImageIcon(pathToImage+"\\icons\\credit-card.png"));
		lblPaymentTypeIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblPaymentTypeIcon.setBounds(54, 421, 24, 24);
		add(lblPaymentTypeIcon);
				
		paymentTypeComboBox = new JComboBox(paymentTypes);
		paymentTypeComboBox.setBounds(101, 421, 150, 24);
		add(paymentTypeComboBox);
		
		lblTypeOfPayment = new JLabel("type of payment");
		lblTypeOfPayment.setHorizontalAlignment(SwingConstants.CENTER);
		lblTypeOfPayment.setForeground(Color.LIGHT_GRAY);
		lblTypeOfPayment.setBounds(111, 445, 126, 14);
		add(lblTypeOfPayment);
		
		calendar = Calendar.getInstance();
		int timeOfDay = calendar.get(Calendar.HOUR_OF_DAY);
		
		if(timeOfDay >= 8 && timeOfDay < 16)
			shift = "First";
		else if(timeOfDay >= 16 && timeOfDay < 24)
			shift = "Second";
		else if(timeOfDay >= 0 && timeOfDay < 8)
			shift = "Third";
		
		JLabel lblCurrentShift = new JLabel(shift);
		lblCurrentShift.setHorizontalAlignment(SwingConstants.CENTER);
		lblCurrentShift.setBounds(101, 357, 150, 24);
		add(lblCurrentShift);
		
		JLabel lblCurrentShift_1 = new JLabel("current shift");
		lblCurrentShift_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblCurrentShift_1.setForeground(Color.LIGHT_GRAY);
		lblCurrentShift_1.setBounds(111, 381, 126, 14);
		add(lblCurrentShift_1);
		
		JLabel lblShiftIcon = new JLabel("");
		lblShiftIcon.setIcon(new ImageIcon(pathToImage+"\\icons\\shift.png"));
		lblShiftIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblShiftIcon.setBounds(54, 357, 24, 24);
		add(lblShiftIcon);
		
		JLabel lblArcticlesIcon = new JLabel("");
		lblArcticlesIcon.setIcon(new ImageIcon(pathToImage+"\\icons\\groceries.png"));
		lblArcticlesIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblArcticlesIcon.setBounds(308, 308, 24, 24);
		add(lblArcticlesIcon);
		
		String[] arcticleNames;
		try {
			
			Arcticle arcticle = new Arcticle();
			arcticle.setRestaurant(user.getRestaurant());
			
			TransferClass transfer = FrontController.getInstance().execute(
					TransferClass.create(arcticle, HttpConstraints.GET_ONE, DbConstraints.ARCTICLE));
			arcticles = (List<Arcticle>) transfer.getResponse();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
				
		arcticleNames = new String[arcticles.size()+1];
		if(arcticles.size() != 0)
			arcticleNames[0] = "";
		else
			arcticleNames[0] = "only arcticles with recipe";
		for(int i = 0; i < arcticles.size(); i++) {
			arcticleNames[i+1] = arcticles.get(i).getArcticleName();
		}
		
		JComboBox arcticlesComboBox = new JComboBox(arcticleNames);
		arcticlesComboBox.setBounds(355, 308, 150, 24);
		add(arcticlesComboBox);
		
		JLabel lblArcticles = new JLabel("arcticle");
		lblArcticles.setHorizontalAlignment(SwingConstants.CENTER);
		lblArcticles.setForeground(Color.LIGHT_GRAY);
		lblArcticles.setBounds(365, 332, 126, 14);
		add(lblArcticles);
		
		JPanel panel = new JPanel();
		panel.setBounds(new Rectangle(0, 0, 150, 128));
		panel.setBackground(Color.WHITE);
		panel.setBounds(686, 308, 150, 128);
		add(panel);
		panel.setLayout(new CardLayout(0, 0));
		arcticlesForBillTable = new ArcticlesForBillTable(user, panel, pathToImage, arcticlesForBill, false);
		panel.add(arcticlesForBillTable, "arcticlesForBillTable");
		CardLayout card1 = (CardLayout)panel.getLayout();
		card1.show(panel, "arcticlesForBillTable");
		
		JLabel lblCurrentArcticlesOn = new JLabel("Current arcticles");
		lblCurrentArcticlesOn.setHorizontalAlignment(SwingConstants.CENTER);
		lblCurrentArcticlesOn.setForeground(Color.BLACK);
		lblCurrentArcticlesOn.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblCurrentArcticlesOn.setBounds(686, 254, 150, 24);
		add(lblCurrentArcticlesOn);
		
		JLabel lblOnBill = new JLabel("on bill");
		lblOnBill.setHorizontalAlignment(SwingConstants.CENTER);
		lblOnBill.setForeground(Color.BLACK);
		lblOnBill.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblOnBill.setBounds(686, 273, 150, 24);
		add(lblOnBill);
		
		JLabel lblAddArcticle = new JLabel("");
		lblAddArcticle.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddArcticle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				if(!arcticlesComboBox.getSelectedItem().equals("")) {
										
					for(Arcticle arcticle: arcticles) {
						if(arcticlesComboBox.getSelectedItem().equals(arcticle.getArcticleName())) {
							arcticlesForBill.add(arcticle);
							break;
						}
					}
					arcticlesForBillTable = new ArcticlesForBillTable(user, panel, pathToImage, arcticlesForBill, false);
					panel.add(arcticlesForBillTable, "arcticlesForBillTable");
					CardLayout card = (CardLayout)panel.getLayout();
					card.show(panel, "arcticlesForBillTable");
				}
			}
		});
		lblAddArcticle.setIcon(new ImageIcon(pathToImage+"\\icons\\stamina.png"));
		lblAddArcticle.setBounds(583, 308, 40, 40);
		add(lblAddArcticle);
		
		JLabel lblAddArcticle_1 = new JLabel("add arcticle");
		lblAddArcticle_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddArcticle_1.setForeground(Color.LIGHT_GRAY);
		lblAddArcticle_1.setBounds(555, 348, 97, 14);
		add(lblAddArcticle_1);
		
		JLabel lblAddBill = new JLabel("");
		lblAddBill.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddBill.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Bill newBill = new Bill();
				newBill.setRestaurant(user.getRestaurant());
				if(billTypeComboBox.isVisible()) {
					if(billTypeComboBox.getSelectedItem().equals("pro forma invoice"))
						newBill.setProFormaInvoice(true);
					else {
						newBill.setProFormaInvoice(false);
						newBill.setTypeOfPayment((String)paymentTypeComboBox.getSelectedItem());
					}
				}else {
					newBill.setProFormaInvoice(null);
				}
				newBill.setShift(shift);
				
				boolean isTableSetted;
				if(restaurantTable == null) {
					if(tablesComboBox.getSelectedItem().equals("")) {
						isTableSetted = false;
						lblRestaurantTable.setText("required field");
						lblRestaurantTable.setForeground(Color.RED);
					}else {
						isTableSetted = true;
						lblRestaurantTable.setText("table");
						lblRestaurantTable.setForeground(Color.LIGHT_GRAY);
						for(RestaurantTable table: restaurantTables) {
							if(tablesComboBox.getSelectedItem().equals(table.getRestaurantTableName())) {
								newBill.setTable(table);
								break;
							}
						}
					}
				}else {
					isTableSetted = true;
					newBill.setTable(restaurantTable);
				}
				
				Double billSum = 0.0;
								
				if(arcticlesForBill.size() == 0) {
					lblArcticles.setText("choose arcticle!");
					lblArcticles.setForeground(Color.RED);
				}else {
					for(Arcticle arcticle: arcticlesForBill) {
						billSum += arcticle.getPriceWithVAT();
					}
					lblArcticles.setText("arcticle");
					lblArcticles.setForeground(Color.LIGHT_GRAY);
					newBill.setArcticles(arcticlesForBill);
					newBill.setBillSum(billSum);
				}
				
				if(arcticlesForBill.size() != 0 && isTableSetted) {
					try {
						TransferClass transfer = FrontController.getInstance().execute(
								TransferClass.create(newBill, HttpConstraints.POST, DbConstraints.BILL));
						if(transfer.getMessage().equals("Successfully added bill!")) {
							TransferClass transfer1 = FrontController.getInstance().execute(
									TransferClass.create(newBill, HttpConstraints.GET_ONE, DbConstraints.BILL));
							if(transfer1.getMessage().equals("All operation went great!")) {
								tableOfBills.remove(billTablePanel);
								BillTablePanel addBill;
								if(user.isAdmin()) {
									addBill = new BillTablePanel(user, tableOfBills, pathToImage, null, null, null, null, null);
								}else {
									addBill = new BillTablePanel(user, tableOfBills, pathToImage, restaurantTable, null, null, null, null);
								}
								tableOfBills.add(addBill, "billTablePanel");
							    CardLayout card = (CardLayout)tableOfBills.getLayout();
								card.show(tableOfBills, "billTablePanel");
								
								if(restaurantTable == null)
									tablesComboBox.setSelectedItem("");
								arcticlesComboBox.setSelectedItem("");
								arcticlesForBill = new ArrayList<Arcticle>();
								arcticlesForBillTable = new ArcticlesForBillTable(user, panel, pathToImage, arcticlesForBill, false);
								panel.add(arcticlesForBillTable, "arcticlesForBillTable");
								CardLayout card1 = (CardLayout)panel.getLayout();
								card1.show(panel, "arcticlesForBillTable");
							}
						}
					}catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		lblAddBill.setIcon(new ImageIcon(pathToImage+"\\icons\\bill.png"));
		lblAddBill.setBounds(577, 395, 50, 50);
		add(lblAddBill);
		
		JLabel lblCreateBill = new JLabel("create bill");
		lblCreateBill.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreateBill.setForeground(Color.LIGHT_GRAY);
		lblCreateBill.setBounds(555, 445, 97, 14);
		add(lblCreateBill);
		
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getSource() == billTypeComboBox && billTypeComboBox.getSelectedItem().equals("pro forma invoice")) {
			paymentTypeComboBox.setVisible(false);
			lblPaymentTypeIcon.setVisible(false); 
			lblTypeOfPayment.setVisible(false);
		}else if (e.getSource() == billTypeComboBox && billTypeComboBox.getSelectedItem().equals("regular bill")) {
			paymentTypeComboBox.setVisible(true);
			lblPaymentTypeIcon.setVisible(true); 
			lblTypeOfPayment.setVisible(true);
		}
		
		if(e.getSource() == tablesForBillsComboBox) {
			String table = (String) tablesForBillsComboBox.getSelectedItem();
			if(!table.isEmpty()) {
				tableOfBills.remove(billTablePanel);
				billTablePanel = new BillTablePanel(user, tableOfBills, pathToImage, restaurantTable, null, table, null, null);
				tableOfBills.add(billTablePanel, "billTablePanel");
				CardLayout card = (CardLayout)tableOfBills.getLayout();
				card.show(tableOfBills, "arcticlesForBillTable");
			}
		}
		if(e.getSource() == billsShiftComboBox) {
			String shift = (String) billsShiftComboBox.getSelectedItem();
			if(!shift.isEmpty()) {
				tableOfBills.remove(billTablePanel);
				billTablePanel = new BillTablePanel(user, tableOfBills, pathToImage, restaurantTable, null, null, shift, null);
				tableOfBills.add(billTablePanel, "billTablePanel");
				CardLayout card = (CardLayout)tableOfBills.getLayout();
				card.show(tableOfBills, "arcticlesForBillTable");
			}
		}
		
		if(e.getSource() == lblPaymentTypeComboBox) {
			String paymentType = (String) lblPaymentTypeComboBox.getSelectedItem();
			if(!paymentType.isEmpty()) {
				tableOfBills.remove(billTablePanel);
				billTablePanel = new BillTablePanel(user, tableOfBills, pathToImage, restaurantTable, null, null, null, paymentType);
				tableOfBills.add(billTablePanel, "billTablePanel");
				CardLayout card = (CardLayout)tableOfBills.getLayout();
				card.show(tableOfBills, "arcticlesForBillTable");
			}
		}
	}
}
