package com.comtrade.database.entities;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class User implements Serializable, CommonCrud{
	private int	userId;
	private String userName;
	private String userSurname;
	private String password;
	private Boolean isAdmin;
	private Restaurant restaurant;
	
	public User() {
		super();
	}

	public User(int userId, String userName, String userSurname, String password,
			boolean isAdmin, Restaurant restaurant) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userSurname = userSurname;
		this.password = password;
		this.isAdmin = isAdmin;
		this.restaurant = restaurant;
	}
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserSurname() {
		return userSurname;
	}

	public void setUserSurname(String userSurname) {
		this.userSurname = userSurname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	@Override
	public String getTableName() {
		return "user";
	}
	
	@Override
	public String getColumnsName() {
		return " (user_name, user_surname, password, is_admin, restaurant_fk) ";
	}
	
	@Override
	public PreparedStatement getInsert(PreparedStatement preparedStatement) {
		try {
			preparedStatement.setString(1, userName);
			preparedStatement.setString(2, userSurname);
			preparedStatement.setString(3, password);
			preparedStatement.setBoolean(4, isAdmin);
			preparedStatement.setInt(5, restaurant.getRestaurantId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return preparedStatement;
	}
	
	@Override
	public String getCharacters() {
		return " (?,?,?,?,?) ";
	}
	
	@Override
	public List<CommonCrud> selectAll(ResultSet resultSet) {
		
		List<CommonCrud> resultList = new LinkedList<CommonCrud>();
		try {
			while (resultSet.next()) {
				
				int id = resultSet.getInt("user_id");
				String userName = resultSet.getString("user_name");
				String userSurname = resultSet.getString("user_surname");
				String password  = resultSet.getString("password");
				boolean isAdmin = resultSet.getBoolean("is_admin");
				
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

				User user = new User(id, userName, userSurname, password, isAdmin, restaurant);
				
				resultList.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return resultList;
	}
	
	@Override
	public String getColumnsForUpdate() {
		
		StringBuilder columsForUpdate = new StringBuilder();
		
		if(userName != null)
			if(columsForUpdate.length() != 0)
				columsForUpdate.append(", user_name = '"+userName+"' ");
			else
				columsForUpdate.append(" user_name = '"+userName+"' ");
		if(userSurname != null)
			if(columsForUpdate.length() != 0)
				columsForUpdate.append(", user_surname = '"+userSurname+"' ");
			else
				columsForUpdate.append(" user_surname = '"+userSurname+"' ");
		if(password != null)
			if(columsForUpdate.length() != 0)
				columsForUpdate.append(", password = '"+password+"' ");
			else
				columsForUpdate.append(" password = '"+password+"' ");
		if(isAdmin != null)
			if(isAdmin)
				if(columsForUpdate.length() != 0)
					columsForUpdate.append(", is_admin = 1 ");
				else
					columsForUpdate.append(" is_admin = 1 ");
			else
				if(columsForUpdate.length() != 0)
					columsForUpdate.append(", is_admin = 0 ");
				else
					columsForUpdate.append(" is_admin = 0 ");
		if(restaurant != null)
			if(columsForUpdate.length() != 0)
				columsForUpdate.append(", restaurant_fk = "+restaurant.getRestaurantId()+" ");
			else
				columsForUpdate.append(" restaurant_fk = "+restaurant.getRestaurantId()+" ");
		return columsForUpdate.toString();
	}
	
	@Override
	public String getIdColumnName() {
		return "user_id";
	}
	
	@Override
	public PreparedStatement getUpdate(PreparedStatement preparedStatement) {
		try {
			preparedStatement.setInt(1, userId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return preparedStatement;
	}
	
	@Override
	public PreparedStatement delete(PreparedStatement preparedStatement) {
		try {
			preparedStatement.setInt(1, userId);
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
