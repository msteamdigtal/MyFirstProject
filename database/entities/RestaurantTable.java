package com.comtrade.database.entities;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class RestaurantTable implements CommonCrud, Serializable {
	
	private int restaurantTableId;
	private String restaurantTableName;
	private Restaurant restaurant;
	private User user;
	private List<Bill> bills;
	
	public RestaurantTable() {
		super();
	}

	public RestaurantTable(int restaurantTableId, String restaurantTableName, Restaurant restaurant,
			User user, List<Bill> bills) {
		super();
		this.restaurantTableId = restaurantTableId;
		this.restaurantTableName = restaurantTableName;
		this.restaurant = restaurant;
		this.user = user;
		this.bills = bills;
	}

	public int getRestaurantTableId() {
		return restaurantTableId;
	}

	public void setRestaurantTableId(int restaurantTableId) {
		this.restaurantTableId = restaurantTableId;
	}

	public String getRestaurantTableName() {
		return restaurantTableName;
	}

	public void setRestaurantTableName(String restaurantTableName) {
		this.restaurantTableName = restaurantTableName;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Bill> getBills() {
		return bills;
	}

	public void setBills(List<Bill> bills) {
		this.bills = bills;
	}

	@Override
	public String getColumnsForUpdate() {
		StringBuilder returnString = new StringBuilder();
		if(restaurantTableName != null)
			if(returnString.length() != 0)
				returnString.append(", table_name = '"+restaurantTableName+"' ");
			else
				returnString.append(" table_name = '"+restaurantTableName+"' ");
		if(user != null)
			if(returnString.length() != 0)
				returnString.append(", user_fk = "+user.getUserId()+" ");
			else
				returnString.append(" user_fk = "+user.getUserId()+" ");
		if(restaurant != null)
			if(returnString.length() != 0)
				returnString.append(", restaurant_fk = "+restaurant.getRestaurantId()+" ");
			else
				returnString.append(" restaurant_fk = "+restaurant.getRestaurantId()+" ");
		return returnString.toString();
	}

	@Override
	public String getTableName() {
		return "restaurant_table";
	}

	@Override
	public String getColumnsName() {
		return "( table_name, restaurant_fk, user_fk )";
	}

	@Override
	public PreparedStatement getInsert(PreparedStatement preparedStatement) {
		try {
			preparedStatement.setString(1, restaurantTableName);
			preparedStatement.setInt(2, restaurant.getRestaurantId());
			preparedStatement.setInt(3, user.getUserId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return preparedStatement;
	}

	@Override
	public String getCharacters() {
		return "( ?, ?, ? )";
	}

	@Override
	public List<CommonCrud> selectAll(ResultSet resultSet) {
		List<CommonCrud> resultList = new LinkedList<CommonCrud>();
		try {
			while (resultSet.next()) {
				
				int restaurantId = resultSet.getInt("restaurant_id");
				String restaurantName = resultSet.getString("restauraurant_name");
				String restaurantTelephone = resultSet.getString("restaurant_telephone");
				String email = resultSet.getString("restaurant_email");
				String restaurantTIN = resultSet.getString("restaurant_TIN");
				
				int tableId = resultSet.getInt("restaurant_table_id");
				String tableName = resultSet.getString("table_name");
				
				int userId = resultSet.getInt("user_id");
				String userName = resultSet.getString("user_name");
				String userSurname = resultSet.getString("user_surname");
				String password  = resultSet.getString("password");
				boolean isAdmin = resultSet.getBoolean("is_admin");
				
				Restaurant restaurant = new Restaurant();
				restaurant.setRestaurantId(restaurantId);
				restaurant.setRestaurantName(restaurantName);
				restaurant.setRestaurantTelephone(restaurantTelephone);
				restaurant.setEmail(email);
				restaurant.setRestaurantTIN(restaurantTIN);
				
				User user = new User(userId, userName, userSurname, password, isAdmin, restaurant);
				
				RestaurantTable table = new RestaurantTable(tableId, tableName, restaurant, user, null);
				
				resultList.add(table);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return resultList;
	}

	@Override
	public String getIdColumnName() {
		return "restaurant_table_id";
	}

	@Override
	public PreparedStatement getUpdate(PreparedStatement preparedStatement) {
		try {
			preparedStatement.setInt(1, restaurantTableId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return preparedStatement;
	}

	@Override
	public PreparedStatement delete(PreparedStatement preparedStatement) {
		try {
			preparedStatement.setInt(1, restaurantTableId);
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
