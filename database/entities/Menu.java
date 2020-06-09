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

public class Menu implements CommonCrud, Serializable {
	
	private int menuId;
	private String menuName;
	private Restaurant restaurant;
	
	public Menu() {
		super();
	}

	public Menu(int menuId, String menuName, Restaurant restaurant) {
		super();
		this.menuId = menuId;
		this.menuName = menuName;
		this.restaurant = restaurant;
	}

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	@Override
	public String getColumnsForUpdate() {
		
		StringBuilder returnString = new StringBuilder();
		if(menuName != null)
			if(returnString.length() != 0)
				returnString.append(", menu_name = '"+menuName+"' ");
			else
				returnString.append(" menu_name = '"+menuName+"' ");
		
		if(restaurant != null)
			if(returnString.length() != 0)
				returnString.append(", restaurant_fk = "+restaurant.getRestaurantId()+" ");
			else
				returnString.append(" restaurant_fk = "+restaurant.getRestaurantId()+" ");
		return returnString.toString();
	}

	@Override
	public String getTableName() {
		return "menu";
	}

	@Override
	public String getColumnsName() {
		return "( menu_name, restaurant_fk )";
	}

	@Override
	public PreparedStatement getInsert(PreparedStatement preparedStatement) {
		try {
			preparedStatement.setString(1, menuName);
			preparedStatement.setInt(2, restaurant.getRestaurantId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return preparedStatement;
	}

	@Override
	public String getCharacters() {
		return "( ?, ? )";
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
				
				int menuId = resultSet.getInt("menu_id");
				String menuName = resultSet.getString("menu_name");
				
				Restaurant restaurant = new Restaurant();
				restaurant.setRestaurantId(restaurantId);
				restaurant.setRestaurantName(restaurantName);
				restaurant.setRestaurantTelephone(restaurantTelephone);
				restaurant.setEmail(email);
				restaurant.setRestaurantTIN(restaurantTIN);
				
				Menu menu = new Menu(menuId, menuName, restaurant);
				
				resultList.add(menu);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return resultList;
	}

	@Override
	public String getIdColumnName() {
		return "menu_id";
	}

	@Override
	public PreparedStatement getUpdate(PreparedStatement preparedStatement) {
		try {
			preparedStatement.setInt(1, menuId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return preparedStatement;
	}

	@Override
	public PreparedStatement delete(PreparedStatement preparedStatement) {
		try {
			preparedStatement.setInt(1, menuId);
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
