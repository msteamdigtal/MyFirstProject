package com.comtrade.database.entities;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Arcticle implements Serializable, CommonCrud {
	
	private int arcticleId;
	private String arcticleName;
	private Double quantity;
	private String quantityUntiMeasure;
	private Double purchasePrice;
	private Double priceWithVAT;
	private Restaurant restaurant;
	private Menu menu;
	private List<Bill> bills;
	
	public Arcticle() {
		super();
	}

	public Arcticle(int arcticleId, String arcticleName, Double quantity, String quantityUntiMeasure,
			Double purchasePrice, Double priceWithVAT,
			Restaurant restaurant, Menu menu, List<Bill> bills) {
		super();
		this.arcticleId = arcticleId;
		this.arcticleName = arcticleName;
		this.quantity = quantity;
		this.quantityUntiMeasure = quantityUntiMeasure;
		this.purchasePrice = purchasePrice;
		this.priceWithVAT = priceWithVAT;
		this.restaurant = restaurant;
		this.menu = menu;
		this.bills = bills;
	}

	public int getArcticleId() {
		return arcticleId;
	}

	public void setArcticleId(int arcticleId) {
		this.arcticleId = arcticleId;
	}

	public String getArcticleName() {
		return arcticleName;
	}

	public void setArcticleName(String arcticleName) {
		this.arcticleName = arcticleName;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public String getQuantityUntiMeasure() {
		return quantityUntiMeasure;
	}

	public void setQuantityUntiMeasure(String quantityUntiMeasure) {
		this.quantityUntiMeasure = quantityUntiMeasure;
	}

	public Double getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(Double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public Double getPriceWithVAT() {
		return priceWithVAT;
	}

	public void setPriceWithVAT(Double priceWithVAT) {
		this.priceWithVAT = priceWithVAT;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
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
		if(arcticleName != null)
			if(returnString.length() != 0)
				returnString.append(", arcticle_name = '"+arcticleName+"' ");
			else
				returnString.append(" arcticle_name = '"+arcticleName+"' ");
		if(quantity != null)
			if(returnString.length() != 0)
				returnString.append(", quantity = "+quantity+" ");
			else
				returnString.append(" quantity = "+quantity+" ");
		if(quantityUntiMeasure != null)
			if(returnString.length() != 0)
				returnString.append(", quantity_unit_measure = '"+quantityUntiMeasure+"' ");
			else
				returnString.append(" quantity_unit_measure = '"+quantityUntiMeasure+"' ");
		if(purchasePrice != null)
			if(returnString.length() != 0)
				returnString.append(", purchase_price = "+purchasePrice+" ");
			else
				returnString.append(" purchase_price = "+purchasePrice+" ");
		if(priceWithVAT != null)
			if(returnString.length() != 0)
				returnString.append(", price_with_VAT = "+priceWithVAT+" ");
			else
				returnString.append(" price_with_VAT = "+priceWithVAT+" ");
		if(restaurant != null)
			if(returnString.length() != 0)
				returnString.append(", restaurant_fk = "+restaurant.getRestaurantId()+" ");
			else
				returnString.append(" restaurant_fk = "+restaurant.getRestaurantId()+" ");
		if(menu != null)
			if(returnString.length() != 0)
				returnString.append(", restaurant_fk = "+menu.getMenuId()+" ");
			else
				returnString.append(" restaurant_fk = "+menu.getMenuId()+" ");
		return returnString.toString();
	}

	@Override
	public String getTableName() {
		return "arcticle";
	}

	@Override
	public String getColumnsName() {
		return "( arcticle_name, quantity, quantity_unit_measure, purchase_price,"
				+ " price_with_VAT, restaurant_fk, menu_fk)";
	}

	@Override
	public PreparedStatement getInsert(PreparedStatement preparedStatement) {
		try {
			preparedStatement.setString(1, arcticleName);
			preparedStatement.setDouble(2, quantity);
			preparedStatement.setString(3, quantityUntiMeasure);
			preparedStatement.setDouble(4, purchasePrice);
			preparedStatement.setDouble(5, priceWithVAT);
			preparedStatement.setInt(6, restaurant.getRestaurantId());
			preparedStatement.setInt(7, menu.getMenuId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return preparedStatement;
	}

	@Override
	public String getCharacters() {
		return "( ?, ?, ?, ?, ?, ?, ? )";
	}

	@Override
	public List<CommonCrud> selectAll(ResultSet resultSet) {

		List<CommonCrud> resultList = new LinkedList<CommonCrud>();
		try {
			while (resultSet.next()) {
				
				int id = resultSet.getInt("arcticle_id");
				String arcticleName = resultSet.getString("arcticle_name");
				Double quantity = Double.parseDouble(resultSet.getString("quantity"));
				String quantityUnitMeasure = resultSet.getString("quantity_unit_measure");
				Double purchasePrice = Double.parseDouble(resultSet.getString("purchase_price"));
				Double priceWithVAT = Double.parseDouble(resultSet.getString("price_with_VAT"));
				
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
				
				Arcticle arcticle = new Arcticle(id, arcticleName, quantity, quantityUnitMeasure, purchasePrice, priceWithVAT, restaurant, menu, null);				
				resultList.add(arcticle);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return resultList;
	}

	@Override
	public String getIdColumnName() {
		return "arcticle_id";
	}

	@Override
	public PreparedStatement getUpdate(PreparedStatement preparedStatement) {
		try {
			preparedStatement.setInt(1, arcticleId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return preparedStatement;
	}

	@Override
	public PreparedStatement delete(PreparedStatement preparedStatement) {
		try {
			preparedStatement.setInt(1, arcticleId);
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
        if (obj instanceof Arcticle)
            return (this.arcticleId == ((Arcticle) obj).arcticleId); 
        else
            return false;
    }
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int hashcode = 0;
        hashcode = (int) (priceWithVAT*20);
        hashcode += arcticleName.hashCode();
        return hashcode;
	}

}
