package com.comtrade.database.entities;

import java.io.Serializable;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Restaurant implements CommonCrud, Serializable {
	
	private int restaurantId;
	private String restaurantName;
	private String restaurantTelephone;
	private String email;
	private String restaurantTIN;
	private List<User> users;
	private List<Arcticle> arcticles;
	private List<Bill> bills;
	private List<Menu> menus;
	private List<Recipe> recipes;
	private List<RestaurantTable> restaurantTables;
	private List<Warehouse> warehouses;
	
	public Restaurant() {
		super();
	}
	
	public Restaurant(int restaurantId, String restaurantName, String restaurantTelephone, String email, String restaurantTIN,
			List<User> users, List<Arcticle> arcticles, List<Bill> bills, List<Menu> menus, List<Recipe> recipes,
			List<RestaurantTable> restaurantTables, List<Warehouse> warehouses) {
		super();
		this.restaurantId = restaurantId;
		this.restaurantName = restaurantName;
		this.restaurantTelephone = restaurantTelephone;
		this.email = email;
		this.restaurantTIN = restaurantTIN;
		this.users = users;
		this.arcticles = arcticles;
		this.bills = bills;
		this.menus = menus;
		this.recipes = recipes;
		this.restaurantTables = restaurantTables;
		this.warehouses = warehouses;
	}

	public int getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public String getRestaurantTelephone() {
		return restaurantTelephone;
	}

	public void setRestaurantTelephone(String restaurantTelephone) {
		this.restaurantTelephone = restaurantTelephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRestaurantTIN() {
		return restaurantTIN;
	}

	public void setRestaurantTIN(String restaurantTIN) {
		this.restaurantTIN = restaurantTIN;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Arcticle> getArcticles() {
		return arcticles;
	}

	public void setArcticles(List<Arcticle> arcticles) {
		this.arcticles = arcticles;
	}

	public List<Bill> getBills() {
		return bills;
	}

	public void setBills(List<Bill> bills) {
		this.bills = bills;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public List<Recipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(List<Recipe> recipes) {
		this.recipes = recipes;
	}

	public List<RestaurantTable> getRestaurantTables() {
		return restaurantTables;
	}

	public void setRestaurantTables(List<RestaurantTable> restaurantTables) {
		this.restaurantTables = restaurantTables;
	}

	public List<Warehouse> getWarehouses() {
		return warehouses;
	}

	public void setWarehouses(List<Warehouse> warehouses) {
		this.warehouses = warehouses;
	}

	@Override
	public String getColumnsForUpdate() {
		StringBuilder columsForUpdate = new StringBuilder();
		
		if(restaurantName != null)
			if(columsForUpdate.length() != 0)
				columsForUpdate.append(", restaurant_name = '"+restaurantName+"' ");
			else
				columsForUpdate.append(" restaurant_name = '"+restaurantName+"' ");
		if(restaurantTelephone != null)
			if(columsForUpdate.length() != 0)
				columsForUpdate.append(", restaurant_telephone = "+restaurantTelephone+" ");
			else
				columsForUpdate.append(" restaurant_telephone = "+restaurantTelephone+" ");
		if(email != null)
			if(columsForUpdate.length() != 0)
				columsForUpdate.append(", restaurant_email = '"+email+"' ");
			else
				columsForUpdate.append(" restaurant_email = '"+email+"' ");
		if(restaurantTIN != null)
			if(columsForUpdate.length() != 0)
				columsForUpdate.append(", restaurant_TIN = "+restaurantTIN+" ");
			else
				columsForUpdate.append(" restaurant_TIN = "+restaurantTIN+" ");
		return columsForUpdate.toString();
	}

	@Override
	public String getTableName() {
		return "restaurant";
	}

	@Override
	public String getColumnsName() {
		return "( restaurant_name, restaurant_telephone, restaurant_email,"
				+ " restaurant_TIN )";
	}

	@Override
	public PreparedStatement getInsert(PreparedStatement preparedStatement) {
		try {
			preparedStatement.setString(1, restaurantName);
			preparedStatement.setString(2, restaurantTelephone);
			preparedStatement.setString(3, email);
			preparedStatement.setString(4, restaurantTIN);
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
				
				resultList.add(restaurant);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return resultList;
	}

	@Override
	public String getIdColumnName() {
		return "restaurant_id";
	}

	@Override
	public PreparedStatement getUpdate(PreparedStatement preparedStatement) {
		try {
			preparedStatement.setInt(1, restaurantId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return preparedStatement;
	}

	@Override
	public PreparedStatement delete(PreparedStatement preparedStatement) {
		try {
			preparedStatement.setInt(1, restaurantId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return preparedStatement;
	}

	@Override
	public PreparedStatement setRestaurantId(PreparedStatement preparedStatement) {
		// TODO Auto-generated method stub
		return null;
	}

}
