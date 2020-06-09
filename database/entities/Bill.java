package com.comtrade.database.entities;

import java.awt.font.MultipleMaster;
import java.io.Serializable;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Bill implements CommonCrud, Serializable {
	
	private int billId;
	private String typeOfPayment;
	private Double billSum;
	private Timestamp dateTime;
	private Boolean isProFormaInvoice;
	private Restaurant restaurant;
	private String shift;
	private RestaurantTable table;
	private List<Arcticle> arcticles;
	private User user;
	private Arcticle arcticle;
	
	public Bill() {
		super();
	}

	public Bill(int billId, String typeOfPayment,Double billSum, Timestamp dateTime,
			Boolean isProFormaInvoice, Restaurant restaurant, String shift,
			RestaurantTable table, List<Arcticle> arcticles) {
		super();
		this.billId = billId;
		this.typeOfPayment = typeOfPayment;
		this.billSum = billSum;
		this.dateTime = dateTime;
		this.isProFormaInvoice = isProFormaInvoice;
		this.restaurant = restaurant;
		this.shift = shift;
		this.table = table;
		this.arcticles = arcticles;
	}

	public int getBillId() {
		return billId;
	}

	public void setBillId(int billId) {
		this.billId = billId;
	}

	public String getTypeOfPayment() {
		return typeOfPayment;
	}

	public void setTypeOfPayment(String typeOfPayment) {
		this.typeOfPayment = typeOfPayment;
	}

	public Timestamp getDateTime() {
		return dateTime;
	}

	public void setDateTime(Timestamp dateTime) {
		this.dateTime = dateTime;
	}

	public Boolean isProFormaInvoice() {
		return isProFormaInvoice;
	}

	public void setProFormaInvoice(Boolean isProFormaInvoice) {
		this.isProFormaInvoice = isProFormaInvoice;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public RestaurantTable getTable() {
		return table;
	}

	public void setTable(RestaurantTable table) {
		this.table = table;
	}

	public List<Arcticle> getArcticles() {
		return arcticles;
	}

	public void setArcticles(List<Arcticle> arcticles) {
		this.arcticles = arcticles;
	}

	public Double getBillSum() {
		return billSum;
	}

	public void setBillSum(Double billSum) {
		this.billSum = billSum;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Arcticle getArcticle() {
		return arcticle;
	}

	public void setArcticle(Arcticle arcticle) {
		this.arcticle = arcticle;
	}

	@Override
	public String getColumnsForUpdate() {
		StringBuilder columsForUpdate = new StringBuilder();
		
		if(typeOfPayment != null)
			if(columsForUpdate.length() != 0)
				columsForUpdate.append(", type_of_payment = '"+typeOfPayment+"' ");
			else
				columsForUpdate.append(" type_of_payment = '"+typeOfPayment+"' ");
		if(billSum != null)
			if(columsForUpdate.length() != 0)
				columsForUpdate.append(", bill_sum = "+billSum+" ");
			else
				columsForUpdate.append(" bill_sum = "+billSum+" ");
		if(dateTime != null)
			if(columsForUpdate.length() != 0)
				columsForUpdate.append(", date_time = '"+dateTime+"' ");
			else
				columsForUpdate.append(" date_time = '"+dateTime+"' ");
		if(isProFormaInvoice != null)
			if(columsForUpdate.length() != 0)
					columsForUpdate.append(", is_pro_forma_invoice = 0 ");
			else
				columsForUpdate.append(" is_pro_forma_invoice = 0 ");
		if(restaurant != null)
			if(columsForUpdate.length() != 0)
				columsForUpdate.append(", restaurant_fk = "+restaurant.getRestaurantId()+" ");
			else
				columsForUpdate.append(" restaurant_fk = "+restaurant.getRestaurantId()+" ");
		if(shift != null)
			if(columsForUpdate.length() != 0)
				columsForUpdate.append(", shift = '"+shift+"' ");
			else
				columsForUpdate.append(" shift = '"+shift+"' ");
		if(table != null)
			if(columsForUpdate.length() != 0)
				columsForUpdate.append(", table_fk = "+table.getRestaurantTableId()+" ");
			else
				columsForUpdate.append(" table_fk = "+table.getRestaurantTableId()+" ");
		return columsForUpdate.toString();
	}

	@Override
	public String getTableName() {
		return "bill";
	}

	@Override
	public String getColumnsName() {
		return "( type_of_payment, bill_sum, is_pro_forma_invoice,"
				+ " restaurant_fk, shift, table_fk)";
	}

	@Override
	public PreparedStatement getInsert(PreparedStatement preparedStatement) {
		try {
			preparedStatement.setString(1, typeOfPayment);
			preparedStatement.setDouble(2, billSum);
			preparedStatement.setBoolean(3, isProFormaInvoice);
			preparedStatement.setInt(4, restaurant.getRestaurantId());
			preparedStatement.setString(5, shift);
			preparedStatement.setInt(6, table.getRestaurantTableId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return preparedStatement;
	}

	@Override
	public String getCharacters() {
		return "( ?, ?, ?, ?, ?, ? )";
	}

	@Override
	public List<CommonCrud> selectAll(ResultSet resultSet) {
		List<CommonCrud> resultList = new LinkedList<CommonCrud>();
		Set<CommonCrud> result = new HashSet<CommonCrud>();
		try {
			List<Bill> bills = new ArrayList<Bill>();
			while (resultSet.next()) {
				
				int id = resultSet.getInt("bill_id");
				String typeOfPayment = "";
				if(resultSet.getString("type_of_payment") != null) {
					typeOfPayment = resultSet.getString("type_of_payment");
				}
				Double billSum = resultSet.getDouble("bill_sum");
				Timestamp dateTime = resultSet.getTimestamp("date_time");
				String shift = resultSet.getString("shift");
				boolean isProFormaInvoice = resultSet.getBoolean("is_pro_forma_invoice");
				
				int restaurantId = resultSet.getInt("restaurant_id");
				String restaurantName = resultSet.getString("restauraurant_name");
				String restaurantTelephone = resultSet.getString("restaurant_telephone");
				String email = resultSet.getString("restaurant_email");
				String restaurantTIN = resultSet.getString("restaurant_TIN");				
				
				int tableId = resultSet.getInt("restaurant_table_id");
				String tableName = resultSet.getString("table_name");
				int userFK = resultSet.getInt("user_fk");
				
				Restaurant restaurant = new Restaurant();
				restaurant.setRestaurantId(restaurantId);
				restaurant.setRestaurantName(restaurantName);
				restaurant.setRestaurantTelephone(restaurantTelephone);
				restaurant.setEmail(email);
				restaurant.setRestaurantTIN(restaurantTIN);

				User user = new User();
				user.setUserId(userFK);
				user.setRestaurant(restaurant);
				
				RestaurantTable restaurantTable = new RestaurantTable();
				restaurantTable.setRestaurantTableId(tableId);
				restaurantTable.setRestaurantTableName(tableName);
				restaurantTable.setUser(user);
				restaurantTable.setRestaurant(restaurant);
				
				int billFk = resultSet.getInt("bill_fk");
				int arcticleId = resultSet.getInt("arcticle_id");
				String arcticleName = resultSet.getString("arcticle_name");
				Double quantity = Double.parseDouble(resultSet.getString("quantity"));
				String quantityUnitMeasure = resultSet.getString("quantity_unit_measure");
				Double purchasePrice = Double.parseDouble(resultSet.getString("purchase_price"));
				Double priceWithVAT = Double.parseDouble(resultSet.getString("price_with_VAT"));
										
				Menu menu = new Menu();
				menu.setMenuId(resultSet.getInt("menu_fk"));
				menu.setRestaurant(restaurant);
				
				Arcticle arcticle = new Arcticle(arcticleId, arcticleName, quantity, quantityUnitMeasure, purchasePrice, priceWithVAT, restaurant, menu, null);
				
				Bill bill = new Bill(id, typeOfPayment, billSum, dateTime, isProFormaInvoice, restaurant, shift, restaurantTable, null);
				
				result.add(bill);
				bill.setArcticle(arcticle);

				bills.add(bill);
			}
			Iterator<CommonCrud> iterator = result.iterator();
			while (iterator.hasNext()) {
				Bill bill = (Bill) iterator.next();
				List<Arcticle> arcticles = new ArrayList<Arcticle>();
				for (Bill secondBill : bills) {
				    if(bill.getBillId() == secondBill.getBillId()) {
				    	arcticles.add(secondBill.getArcticle());
				    }
				}
				bill.setArcticles(arcticles);
			}
			Iterator<CommonCrud> iterator1 = result.iterator();
			while(iterator1.hasNext()) {
				resultList.add(iterator1.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultList;
	}

	@Override
	public String getIdColumnName() {
		return "bill_id";
	}

	@Override
	public PreparedStatement getUpdate(PreparedStatement preparedStatement) {
		try {
			preparedStatement.setInt(1, billId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return preparedStatement;
	}

	@Override
	public PreparedStatement delete(PreparedStatement preparedStatement) {
		try {
			preparedStatement.setInt(1, billId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return preparedStatement;
	}

	@Override
	public PreparedStatement setRestaurantId(PreparedStatement preparedStatement) {
		try {
			preparedStatement.setInt(1, restaurant.getRestaurantId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return preparedStatement;
	}
	
	@Override
    public boolean equals(Object obj) {
        if (obj instanceof Bill)
            return (this.billId == ((Bill) obj).billId); 
        else
            return false;
    }
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int hashcode = 0;
        hashcode = (int) (billSum*20);
        hashcode += typeOfPayment.hashCode();
        return hashcode;
	}

}
