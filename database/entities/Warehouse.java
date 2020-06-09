package com.comtrade.database.entities;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Warehouse implements CommonCrud, Serializable {
	
	private int warehouseId;
	private String goodName;
	private Double goodQuantity;
	private String quantityUnitMeasure;
	private Restaurant restaurant;
	
	public Warehouse() {
		super();
	}

	public Warehouse(int warehouseId, String goodName, Double goodQuantity, 
			String quantityUnitMeasure, Restaurant restaurant) {
		super();
		this.warehouseId = warehouseId;
		this.goodName = goodName;
		this.goodQuantity = goodQuantity;
		this.quantityUnitMeasure = quantityUnitMeasure;
		this.restaurant = restaurant;
	}

	public int getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(int warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public Double getGoodQuantity() {
		return goodQuantity;
	}

	public void setGoodQuantity(Double goodQuantity) {
		this.goodQuantity = goodQuantity;
	}

	public String getQuantityUnitMeasure() {
		return quantityUnitMeasure;
	}

	public void setQuantityUnitMeasure(String quantityUnitMeasure) {
		this.quantityUnitMeasure = quantityUnitMeasure;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	@Override
	public String getColumnsForUpdate() {
		StringBuilder columsForUpdate = new StringBuilder();
		
		if(goodName != null)
			if(columsForUpdate.length() != 0)
				columsForUpdate.append(", good_name = '"+goodName+"' ");
			else
				columsForUpdate.append(" good_name = '"+goodName+"' ");
		if(goodQuantity != null)
			if(columsForUpdate.length() != 0)
				columsForUpdate.append(", good_quantity = "+goodQuantity+" ");
			else
				columsForUpdate.append(" good_quantity = "+goodQuantity+" ");
		if(quantityUnitMeasure != null)
			if(columsForUpdate.length() != 0)
				columsForUpdate.append(", quantity_unit_measure = '"+quantityUnitMeasure+"' ");
			else
				columsForUpdate.append(" quantity_unit_measure = '"+quantityUnitMeasure+"' ");
		if(restaurant != null)
			if(columsForUpdate.length() != 0)
				columsForUpdate.append(", restaurant_fk = "+restaurant.getRestaurantId()+" ");
			else
				columsForUpdate.append(" restaurant_fk = "+restaurant.getRestaurantId()+" ");
		return columsForUpdate.toString();
	}

	@Override
	public String getTableName() {
		return "warehouse";
	}

	@Override
	public String getColumnsName() {
		return "( good_name, good_quantity,"
				+ "quantity_unit_measure, restaurant_fk)";
	}

	@Override
	public PreparedStatement getInsert(PreparedStatement preparedStatement) {
		try {
			preparedStatement.setString(1, goodName);
			preparedStatement.setDouble(2, goodQuantity);
			preparedStatement.setString(3, quantityUnitMeasure);
			preparedStatement.setInt(4, restaurant.getRestaurantId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return preparedStatement;
	}

	@Override
	public String getCharacters() {
		return "( ?, ?, ?, ? )";
	}

	@Override
	public List<CommonCrud> selectAll(ResultSet resultSet) {
		List<CommonCrud> resultList = new LinkedList<CommonCrud>();
		try {
			while (resultSet.next()) {
				
				int id = resultSet.getInt("warehouse_id");
				String goodName = resultSet.getString("good_name");
				Double goodQuantity = resultSet.getDouble("good_quantity");
				String quantityUnitMeasure  = resultSet.getString("quantity_unit_measure");
				
				int restaurantId = resultSet.getInt("restaurant_id");
				String restaurantName = resultSet.getString("restauraurant_name");
				String restaurantTelephone = resultSet.getString("restaurant_telephone");
				String email = resultSet.getString("restaurant_email");
				String restaurantTIN = resultSet.getString("restaurant_TIN");
				
				Restaurant restaurant = new Restaurant();
				restaurant.setRestaurantId(restaurantId);
				restaurant.setRestaurantName(restaurantName);
				restaurant.setRestaurantTelephone(restaurantTelephone);
				restaurant.setEmail(email);
				restaurant.setRestaurantTIN(restaurantTIN);
				
				Warehouse warehouse = new Warehouse(id, goodName, goodQuantity, quantityUnitMeasure, restaurant);
				
				resultList.add(warehouse);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return resultList;
	}

	@Override
	public String getIdColumnName() {
		return "warehouse_id";
	}

	@Override
	public PreparedStatement getUpdate(PreparedStatement preparedStatement) {
		try {
			preparedStatement.setInt(1, warehouseId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return preparedStatement;
	}

	@Override
	public PreparedStatement delete(PreparedStatement preparedStatement) {
		try {
			preparedStatement.setInt(1, warehouseId);
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

}
