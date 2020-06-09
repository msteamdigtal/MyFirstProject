package com.comtrade.view.common;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.comtrade.constants.DbConstraints;
import com.comtrade.constants.HttpConstraints;
import com.comtrade.controller.FrontController;
import com.comtrade.database.entities.Bill;
import com.comtrade.database.entities.RestaurantTable;
import com.comtrade.database.entities.User;
import com.comtrade.helpers.ButtonColumn;
import com.comtrade.transfer.TransferClass;

public class BillTablePanel extends JPanel {
	
	private JTable tableOfBills;
	private Bill bill;
	private JScrollPane scroll;
	private List<Bill> billList;
	String[] columnNames;
	private List<Bill> proFormaInvoiceList;
	
	/**
	 * Create the panel.
	 */
	public BillTablePanel(User user, JPanel panel, String pathToImage, RestaurantTable restaurantTable, String date, String billsTable, String billsShift, String paymentTypeOfBills) {
		
		setBounds(new Rectangle(0, 0, 782, 150));
		setBackground(Color.GRAY);
		setLayout(null);
		
		bill = new Bill();
		bill.setTable(restaurantTable);
		bill.setRestaurant(user.getRestaurant());
		
		if(user.isAdmin()){
			columnNames = new String[] {"Payment Type","Sum", "Date and Time", "Bill Type", "Shift", "Table", "", ""};
		}else {
			columnNames = new String[] {"Payment Type","Sum", "Date and Time", "Bill Type", "Shift", "Table", ""};
		}
		
		Object[][] data = null;
		try {
			bill.setUser(user);
			TransferClass transfer = FrontController.getInstance().execute(
					TransferClass.create(bill, HttpConstraints.GET, DbConstraints.BILL));
			billList = (List<Bill>) transfer.getResponse();
			if(user.isAdmin()) {
				if(date != null) {
					DateFormat fromatter = new SimpleDateFormat("dd.MM.yyyy.");
					
					List<Bill> dateBills = new ArrayList<Bill>();
					for(Bill b: billList) {
						Date bDate = new Date(b.getDateTime().getTime());
						String gettedDate = fromatter.format(bDate);
						if(gettedDate.equals(date)) {
							dateBills.add(b);
						}
					}
					data = new Object[dateBills.size()][columnNames.length];
					for(int i = 0; i < dateBills.size(); i++) {
						String paymentType = dateBills.get(i).getTypeOfPayment();
						double billSum = dateBills.get(i).getBillSum();
						Timestamp dateTime = dateBills.get(i).getDateTime();
						String billType = dateBills.get(i).isProFormaInvoice()? "Pro forma inv.": "Regular";
						String shift = dateBills.get(i).getShift();
						String table = dateBills.get(i).getTable().getRestaurantTableName();
						
						
						for(int j = 0; j < columnNames.length; j++) {
							if(j == 0)
								data[i][j] = paymentType;
							if(j == 1)
								data[i][j] = billSum;
							if(j == 2)
								data[i][j] = dateTime;
							if(j == 3)
								data[i][j] = billType;
							if(j == 4)
								data[i][j] = shift;
							if(j == 5)
								data[i][j] = table;
							if(j == 6 && billList.get(i).isProFormaInvoice())
								data[i][j] = "charge";
							else if(j == 6 && !billList.get(i).isProFormaInvoice())
								data[i][j] = "details";
							if(j == 7)
								data[i][j] = "delete";
						}
					}
				}else if(billsTable != null) {
					List<Bill> dateBills = new ArrayList<Bill>();
					for(Bill b: billList) {
						String table = b.getTable().getRestaurantTableName();
						if(table.equals(billsTable)) {
							dateBills.add(b);
						}
					}
					data = new Object[dateBills.size()][columnNames.length];
					for(int i = 0; i < dateBills.size(); i++) {
						String paymentType = dateBills.get(i).getTypeOfPayment();
						double billSum = dateBills.get(i).getBillSum();
						Timestamp dateTime = dateBills.get(i).getDateTime();
						String billType = dateBills.get(i).isProFormaInvoice()? "Pro forma inv.": "Regular";
						String shift = dateBills.get(i).getShift();
						String table = dateBills.get(i).getTable().getRestaurantTableName();
						
						
						for(int j = 0; j < columnNames.length; j++) {
							if(j == 0)
								data[i][j] = paymentType;
							if(j == 1)
								data[i][j] = billSum;
							if(j == 2)
								data[i][j] = dateTime;
							if(j == 3)
								data[i][j] = billType;
							if(j == 4)
								data[i][j] = shift;
							if(j == 5)
								data[i][j] = table;
							if(j == 6 && billList.get(i).isProFormaInvoice())
								data[i][j] = "charge";
							else if(j == 6 && !billList.get(i).isProFormaInvoice())
								data[i][j] = "details";
							if(j == 7)
								data[i][j] = "delete";
						}
					}
				}else if(billsShift != null) {
					List<Bill> dateBills = new ArrayList<Bill>();
					for(Bill b: billList) {
						String shift = b.getShift();
						if(shift.equals(billsShift)) {
							dateBills.add(b);
						}
					}
					data = new Object[dateBills.size()][columnNames.length];
					for(int i = 0; i < dateBills.size(); i++) {
						String paymentType = dateBills.get(i).getTypeOfPayment();
						double billSum = dateBills.get(i).getBillSum();
						Timestamp dateTime = dateBills.get(i).getDateTime();
						String billType = dateBills.get(i).isProFormaInvoice()? "Pro forma inv.": "Regular";
						String shift = dateBills.get(i).getShift();
						String table = dateBills.get(i).getTable().getRestaurantTableName();
						
						
						for(int j = 0; j < columnNames.length; j++) {
							if(j == 0)
								data[i][j] = paymentType;
							if(j == 1)
								data[i][j] = billSum;
							if(j == 2)
								data[i][j] = dateTime;
							if(j == 3)
								data[i][j] = billType;
							if(j == 4)
								data[i][j] = shift;
							if(j == 5)
								data[i][j] = table;
							if(j == 6 && billList.get(i).isProFormaInvoice())
								data[i][j] = "charge";
							else if(j == 6 && !billList.get(i).isProFormaInvoice())
								data[i][j] = "details";
							if(j == 7)
								data[i][j] = "delete";
						}
					}
				}else if(paymentTypeOfBills != null) {
					List<Bill> dateBills = new ArrayList<Bill>();
					for(Bill b: billList) {
						String paymentType = b.getTypeOfPayment();
						if(paymentType.equals(paymentTypeOfBills)) {
							dateBills.add(b);
						}
					}
					data = new Object[dateBills.size()][columnNames.length];
					for(int i = 0; i < dateBills.size(); i++) {
						String paymentType = dateBills.get(i).getTypeOfPayment();
						double billSum = dateBills.get(i).getBillSum();
						Timestamp dateTime = dateBills.get(i).getDateTime();
						String billType = dateBills.get(i).isProFormaInvoice()? "Pro forma inv.": "Regular";
						String shift = dateBills.get(i).getShift();
						String table = dateBills.get(i).getTable().getRestaurantTableName();
						
						
						for(int j = 0; j < columnNames.length; j++) {
							if(j == 0)
								data[i][j] = paymentType;
							if(j == 1)
								data[i][j] = billSum;
							if(j == 2)
								data[i][j] = dateTime;
							if(j == 3)
								data[i][j] = billType;
							if(j == 4)
								data[i][j] = shift;
							if(j == 5)
								data[i][j] = table;
							if(j == 6 && billList.get(i).isProFormaInvoice())
								data[i][j] = "charge";
							else if(j == 6 && !billList.get(i).isProFormaInvoice())
								data[i][j] = "details";
							if(j == 7)
								data[i][j] = "delete";
						}
					}
				}else {
					data = new Object[billList.size()][columnNames.length];
					for(int i = 0; i < billList.size(); i++) {
						String paymentType = billList.get(i).getTypeOfPayment();
						double billSum = billList.get(i).getBillSum();
						Timestamp dateTime = billList.get(i).getDateTime();
						String billType = billList.get(i).isProFormaInvoice()? "Pro forma inv.": "Regular";
						String shift = billList.get(i).getShift();
						String table = billList.get(i).getTable().getRestaurantTableName();
						
						
						for(int j = 0; j < columnNames.length; j++) {
							if(j == 0)
								data[i][j] = paymentType;
							if(j == 1)
								data[i][j] = billSum;
							if(j == 2)
								data[i][j] = dateTime;
							if(j == 3)
								data[i][j] = billType;
							if(j == 4)
								data[i][j] = shift;
							if(j == 5)
								data[i][j] = table;
							if(j == 6 && billList.get(i).isProFormaInvoice())
								data[i][j] = "charge";
							else if(j == 6 && !billList.get(i).isProFormaInvoice())
								data[i][j] = "details";
							if(j == 7)
								data[i][j] = "delete";
						}
					}
				}
			}else {
				
				proFormaInvoiceList = new LinkedList<Bill>();
				for(Bill bill: billList) {
					if(bill.isProFormaInvoice()) {
						proFormaInvoiceList.add(bill);
					}
				}
				
				if(date != null) {
					DateFormat fromatter = new SimpleDateFormat("dd.MM.yyyy.");
					
					List<Bill> dateBills = new ArrayList<Bill>();
					for(Bill b: proFormaInvoiceList) {
						Date bDate = new Date(b.getDateTime().getTime());
						String gettedDate = fromatter.format(bDate);
						if(gettedDate.equals(date)) {
							dateBills.add(b);
						}
					}
					data = new Object[dateBills.size()][columnNames.length];
					for(int i = 0; i < dateBills.size(); i++) {
						String paymentType = dateBills.get(i).getTypeOfPayment();
						double billSum = dateBills.get(i).getBillSum();
						Timestamp dateTime = dateBills.get(i).getDateTime();
						String billType = dateBills.get(i).isProFormaInvoice()? "Regular":"Pro forma inv.";
						String shift = dateBills.get(i).getShift();
						String table = dateBills.get(i).getTable().getRestaurantTableName();
						
						
						for(int j = 0; j < columnNames.length; j++) {
							if(j == 0)
								data[i][j] = paymentType;
							if(j == 1)
								data[i][j] = billSum;
							if(j == 2)
								data[i][j] = dateTime;
							if(j == 3)
								data[i][j] = billType;
							if(j == 4)
								data[i][j] = shift;
							if(j == 5)
								data[i][j] = table;
							if(j == 6)
								data[i][j] = "charge";
						}
					}
				}else if(billsTable != null) {
					
					List<Bill> dateBills = new ArrayList<Bill>();
					for(Bill b: billList) {
						String table = b.getTable().getRestaurantTableName();
						if(table.equals(billsTable)) {
							dateBills.add(b);
						}
					}
					data = new Object[dateBills.size()][columnNames.length];
					for(int i = 0; i < dateBills.size(); i++) {
						String paymentType = dateBills.get(i).getTypeOfPayment();
						double billSum = dateBills.get(i).getBillSum();
						Timestamp dateTime = dateBills.get(i).getDateTime();
						String billType = dateBills.get(i).isProFormaInvoice()? "Regular":"Pro forma inv.";
						String shift = dateBills.get(i).getShift();
						String table = dateBills.get(i).getTable().getRestaurantTableName();
						
						
						for(int j = 0; j < columnNames.length; j++) {
							if(j == 0)
								data[i][j] = paymentType;
							if(j == 1)
								data[i][j] = billSum;
							if(j == 2)
								data[i][j] = dateTime;
							if(j == 3)
								data[i][j] = billType;
							if(j == 4)
								data[i][j] = shift;
							if(j == 5)
								data[i][j] = table;
							if(j == 6)
								data[i][j] = "charge";
						}
					}
				}else if(billsShift != null) {
					
					List<Bill> dateBills = new ArrayList<Bill>();
					for(Bill b: billList) {
						String shift = b.getShift();
						if(shift.equals(billsShift)) {
							dateBills.add(b);
						}
					}
					data = new Object[dateBills.size()][columnNames.length];
					for(int i = 0; i < dateBills.size(); i++) {
						String paymentType = dateBills.get(i).getTypeOfPayment();
						double billSum = dateBills.get(i).getBillSum();
						Timestamp dateTime = dateBills.get(i).getDateTime();
						String billType = dateBills.get(i).isProFormaInvoice()? "Regular":"Pro forma inv.";
						String shift = dateBills.get(i).getShift();
						String table = dateBills.get(i).getTable().getRestaurantTableName();
						
						
						for(int j = 0; j < columnNames.length; j++) {
							if(j == 0)
								data[i][j] = paymentType;
							if(j == 1)
								data[i][j] = billSum;
							if(j == 2)
								data[i][j] = dateTime;
							if(j == 3)
								data[i][j] = billType;
							if(j == 4)
								data[i][j] = shift;
							if(j == 5)
								data[i][j] = table;
							if(j == 6)
								data[i][j] = "charge";
						}
					}
				}else if(paymentTypeOfBills != null) {
					
					List<Bill> dateBills = new ArrayList<Bill>();
					for(Bill b: billList) {
						String payment = b.getTypeOfPayment();
						if(payment.equals(paymentTypeOfBills)) {
							dateBills.add(b);
						}
					}
					data = new Object[dateBills.size()][columnNames.length];
					for(int i = 0; i < dateBills.size(); i++) {
						String paymentType = dateBills.get(i).getTypeOfPayment();
						double billSum = dateBills.get(i).getBillSum();
						Timestamp dateTime = dateBills.get(i).getDateTime();
						String billType = dateBills.get(i).isProFormaInvoice()? "Regular":"Pro forma inv.";
						String shift = dateBills.get(i).getShift();
						String table = dateBills.get(i).getTable().getRestaurantTableName();
						
						
						for(int j = 0; j < columnNames.length; j++) {
							if(j == 0)
								data[i][j] = paymentType;
							if(j == 1)
								data[i][j] = billSum;
							if(j == 2)
								data[i][j] = dateTime;
							if(j == 3)
								data[i][j] = billType;
							if(j == 4)
								data[i][j] = shift;
							if(j == 5)
								data[i][j] = table;
							if(j == 6)
								data[i][j] = "charge";
						}
					}
				}else {
				
					data = new Object[proFormaInvoiceList.size()][columnNames.length];
					for(int i = 0; i < proFormaInvoiceList.size(); i++) {
						String paymentType = proFormaInvoiceList.get(i).getTypeOfPayment();
						double billSum = proFormaInvoiceList.get(i).getBillSum();
						Timestamp dateTime = proFormaInvoiceList.get(i).getDateTime();
						String billType = proFormaInvoiceList.get(i).isProFormaInvoice()? "Regular":"Pro forma inv.";
						String shift = proFormaInvoiceList.get(i).getShift();
						String table = proFormaInvoiceList.get(i).getTable().getRestaurantTableName();
						
						
						for(int j = 0; j < columnNames.length; j++) {
							if(j == 0)
								data[i][j] = paymentType;
							if(j == 1)
								data[i][j] = billSum;
							if(j == 2)
								data[i][j] = dateTime;
							if(j == 3)
								data[i][j] = billType;
							if(j == 4)
								data[i][j] = shift;
							if(j == 5)
								data[i][j] = table;
							if(j == 6)
								data[i][j] = "charge";
						}
					}
					
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    DefaultTableModel model = new DefaultTableModel(data, columnNames);
	    tableOfBills = new JTable(model);
	    tableOfBills.setColumnSelectionAllowed(true);
	    tableOfBills.setRowSelectionAllowed(true);
		
		Action edit = new AbstractAction()
	    {
	        public void actionPerformed(ActionEvent arg0)
	        {
	            JTable table = (JTable)arg0.getSource();
	            int modelRow = Integer.valueOf(arg0.getActionCommand());
	            if(user.isAdmin()) {
		            Bill billForEdit = billList.get(modelRow);
		            if(billForEdit.isProFormaInvoice()) {
			            EditBillPanel editBillPanel = new EditBillPanel(user, billForEdit, pathToImage, tableOfBills, model, panel, modelRow, billList);
			            panel.add(editBillPanel, "editBillPanel");
		            }else {
		            	ViewBillPanel editBillPanel = new ViewBillPanel(user, billForEdit, pathToImage, tableOfBills, model, panel, modelRow, billList);
			            panel.add(editBillPanel, "editBillPanel");
		            }
	            }else {
	            	Bill billForEdit = proFormaInvoiceList.get(modelRow);
		            EditBillPanel editBillPanel = new EditBillPanel(user, billForEdit, pathToImage, tableOfBills, model, panel, modelRow, proFormaInvoiceList);
		            panel.add(editBillPanel, "editBillPanel");
	            }
	    	    CardLayout card = (CardLayout)panel.getLayout();
	    		card.show(panel, "editBillPanel");
	        }
	    };
	    
	    ButtonColumn billsButton = new ButtonColumn(tableOfBills, edit, 6);
	    billsButton.setMnemonic(KeyEvent.VK_D);
		
	    if(user.isAdmin()) {
		    Action delete = new AbstractAction()
		    {
		        public void actionPerformed(ActionEvent arg0)
		        {
		            JTable table = (JTable)arg0.getSource();
		            int modelRow = Integer.valueOf(arg0.getActionCommand());
		            Bill billForDelete = billList.get(modelRow);
		            DeleteBillPanel deleteBillPanel = new DeleteBillPanel(billForDelete, pathToImage, panel, tableOfBills, modelRow, billList);
		            panel.add(deleteBillPanel, "deleteBillPanel");
		            
		            CardLayout card = (CardLayout)panel.getLayout();
		    		card.show(panel, "deleteBillPanel");
		        }
		    };
		     
		    ButtonColumn deleteButton = new ButtonColumn(tableOfBills, delete, 7);
		    deleteButton.setMnemonic(KeyEvent.VK_D);
	    }
	    
	    
	    scroll = new JScrollPane(tableOfBills);
	    scroll.setBounds(0, 0, 782, 150);
	    add(scroll);
	}

}
