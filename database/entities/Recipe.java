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

public class Recipe implements CommonCrud, Serializable {
	
	private int recipeId;
	private Double goodQuantity;
	private Restaurant restaurant;
	private Arcticle arcticle;
	private Warehouse warehouse;
	
	public Recipe() {
		super();
	}

	public Recipe(int recipeId, Double goodQuantity, Restaurant restaurant,
			Arcticle arcticle, Warehouse warehouse) {
		super();
		this.recipeId = recipeId;
		this.goodQuantity = goodQuantity;
		this.restaurant = restaurant;
		this.arcticle = arcticle;
		this.warehouse = warehouse;
	}

	public int getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(int recipeId) {
		this.recipeId = recipeId;
	}

	public Double getGoodQuantity() {
		return goodQuantity;
	}

	public void setGoodQuantity(Double goodQuantity) {
		this.goodQuantity = goodQuantity;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public Arcticle getArcticle() {
		return arcticle;
	}

	public void setArcticle(Arcticle arcticle) {
		this.arcticle = arcticle;
	}

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	@Override
	public String getColumnsForUpdate() {
		StringBuilder columsForUpdate = new StringBuilder();
		
		if(goodQuantity != null)
			if(columsForUpdate.length() != 0)
				columsForUpdate.append(", good_quantity = "+goodQuantity+" ");
			else
				columsForUpdate.append(" good_quantity = "+goodQuantity+" ");
		if(restaurant != null)
			if(columsForUpdate.length() != 0)
				columsForUpdate.append(", restaurant_fk = "+restaurant.getRestaurantId()+" ");
			else
				columsForUpdate.append(" restaurant_fk = "+restaurant.getRestaurantId()+" ");
		if(arcticle != null)
			if(columsForUpdate.length() != 0)
				columsForUpdate.append(", arcticle_fk = "+arcticle.getArcticleId()+" ");
			else
				columsForUpdate.append(" arcticle_fk = "+arcticle.getArcticleId()+" ");
		if(warehouse != null)
			if(columsForUpdate.length() != 0)
				columsForUpdate.append(", warehouse_fk = "+warehouse.getWarehouseId()+" ");
			else
				columsForUpdate.append(" warehouse_fk = "+warehouse.getWarehouseId()+" ");
		return columsForUpdate.toString();
	}

	@Override
	public String getTableName() {
		return "recipe";
	}

	@Override
	public String getColumnsName() {
		return "( good_quantity, restaurant_fk,"
				+ " arcticle_fk, warehouse_fk )";
	}

	@Override
	public PreparedStatement getInsert(PreparedStatement preparedStatement) {
		try {
			preparedStatement.setDouble(1, goodQuantity);
			preparedStatement.setInt(2, restaurant.getRestaurantId());
			preparedStatement.setInt(3, arcticle.getArcticleId());
			preparedStatement.setInt(4, warehouse.getWarehouseId());
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
				
				int id = resultSet.getInt("recipe_id");
				Double goodQuantity = Double.parseDouble(resultSet.getString("good_quantity"));
				
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
				
				int arcticleId = resultSet.getInt("arcticle_id");
				String arcticleName = resultSet.getString("arcticle_name");
				Double quantity = Double.parseDouble(resultSet.getString("quantity"));
				String quantityUnitMeasure = resultSet.getString("quantity_unit_measure");
				Double purchasePrice = Double.parseDouble(resultSet.getString("purchase_price"));
				Double priceWithVAT = Double.parseDouble(resultSet.getString("price_with_VAT"));
				
				int warehoudId = resultSet.getInt("warehouse_id");
				String warehouseGoodName = resultSet.getString("good_name");
				Double warehouseGoodQuantity = resultSet.getDouble("good_quantity");
				String warehouseQuantityUnitMeasure  = resultSet.getString("quantity_unit_measure");
				
				Arcticle arcticle = new Arcticle(arcticleId, arcticleName, quantity, quantityUnitMeasure, purchasePrice, priceWithVAT, restaurant, null, null);				
				
				Warehouse warehouse = new Warehouse(warehoudId, warehouseGoodName, warehouseGoodQuantity, warehouseQuantityUnitMeasure, restaurant);
				
				Recipe recipe = new Recipe(id, goodQuantity, restaurant, arcticle, warehouse);
				
				resultList.add(recipe);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return resultList;
	}

	@Override
	public String getIdColumnName() {
		return "recipe_id";
	}

	@Override
	public PreparedStatement getUpdate(PreparedStatement preparedStatement) {
		try {
			preparedStatement.setInt(1, recipeId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return preparedStatement;
	}

	@Override
	public PreparedStatement delete(PreparedStatement preparedStatement) {
		try {
			preparedStatement.setInt(1, recipeId);
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
