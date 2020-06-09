package com.comtrade.communicator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.comtrade.connection.ConnectionClass;
import com.comtrade.constants.HttpConstraints;
import com.comtrade.database.entities.Arcticle;
import com.comtrade.database.entities.Bill;
import com.comtrade.database.entities.CommonCrud;
import com.comtrade.database.entities.Menu;
import com.comtrade.database.entities.Restaurant;
import com.comtrade.database.entities.Recipe;
import com.comtrade.database.entities.RestaurantTable;
import com.comtrade.database.entities.User;
import com.comtrade.database.entities.Warehouse;
import com.comtrade.exception.SqlException;

public class DbCommunicator {
	
	
	public List<CommonCrud> getAll(CommonCrud dbEntity){
		StringBuilder sqlQuery = new StringBuilder("select * from ");
		sqlQuery.append(dbEntity.getTableName());
		
		if(!(dbEntity instanceof Restaurant)) {
			
			RestaurantTable restaurantTable = null;
			Bill bill = null;
			sqlQuery.append(" INNER JOIN restaurant ON ");
			sqlQuery.append(dbEntity.getTableName());
			sqlQuery.append(".restaurant_fk");
			sqlQuery.append(" = restaurant.restaurant_id");
			if(dbEntity instanceof Arcticle) {
				sqlQuery.append(" INNER JOIN menu ON ");
				sqlQuery.append(dbEntity.getTableName());
				sqlQuery.append(".menu_fk");
				sqlQuery.append(" = menu.menu_id");
			}
			if(dbEntity instanceof Recipe) {
				sqlQuery.append(" INNER JOIN arcticle ON ");
				sqlQuery.append(dbEntity.getTableName());
				sqlQuery.append(".arcticle_fk ");
				sqlQuery.append(" = arcticle.arcticle_id");
				
				sqlQuery.append(" INNER JOIN warehouse ON ");
				sqlQuery.append(dbEntity.getTableName());
				sqlQuery.append(".warehouse_fk ");
				sqlQuery.append(" = warehouse.warehouse_id");
			}
			if(dbEntity instanceof RestaurantTable) {
				restaurantTable = (RestaurantTable)dbEntity;
				sqlQuery.append(" INNER JOIN user ON ");
				sqlQuery.append(dbEntity.getTableName());
				sqlQuery.append(".user_fk ");
				sqlQuery.append(" = user.user_id");
				
			}
			if(dbEntity instanceof Bill) {
				bill = (Bill)dbEntity;
				sqlQuery.append(" INNER JOIN restaurant_table ON ");
				sqlQuery.append(dbEntity.getTableName());
				sqlQuery.append(".table_fk ");
				sqlQuery.append(" = restaurant_table.restaurant_table_id");
				
				sqlQuery.append(" inner join bill_arcticle on ");
				sqlQuery.append(dbEntity.getTableName());
				sqlQuery.append(".");
				sqlQuery.append(dbEntity.getIdColumnName());
				sqlQuery.append(" = bill_arcticle.bill_fk");
				sqlQuery.append(" INNER join arcticle on bill_arcticle.arcticle_fk = arcticle.arcticle_id ");
			}
			sqlQuery.append(" where ");
			sqlQuery.append(dbEntity.getTableName());
			sqlQuery.append(".restaurant_fk = ?");
			if(bill != null) {
				if(bill.getTable() != null) {
					sqlQuery.append(" and ");
					sqlQuery.append(dbEntity.getTableName());
					sqlQuery.append(".table_fk = ");
					sqlQuery.append(bill.getTable().getRestaurantTableId());
				}
			}
			if(restaurantTable != null) {
				if(!restaurantTable.getUser().isAdmin()) {
					sqlQuery.append(" and ");
					sqlQuery.append(dbEntity.getTableName());
					sqlQuery.append(".user_fk = ");
					sqlQuery.append(restaurantTable.getUser().getUserId());
				}
			}
		}
		
		List<CommonCrud> resultList = null;
		try {
			PreparedStatement preparedStatement =
					ConnectionClass.getConnection().getSqlConnection()
					.prepareStatement(sqlQuery.toString());
			if(!(dbEntity instanceof Restaurant)) {
				preparedStatement = dbEntity.setRestaurantId(preparedStatement);
			}
			ResultSet resultSet = preparedStatement.executeQuery();
			resultList = dbEntity.selectAll(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultList;
	}
	
	public User getUser(User user) {
		StringBuilder sqlQuery = new StringBuilder("SELECT * FROM user ");
		sqlQuery.append("INNER JOIN restaurant ON restaurant.restaurant_id = user.restaurant_fk ");
		sqlQuery.append("WHERE user.user_name = ? AND user.user_surname = ? ");
		sqlQuery.append( "AND user.password = ?");
		try {
			PreparedStatement preparedStatement = ConnectionClass.getConnection().getSqlConnection()
					.prepareStatement(sqlQuery.toString());
			preparedStatement.setString(1, user.getUserName());
			preparedStatement.setString(2, user.getUserSurname());
			preparedStatement.setString(3, user.getPassword());
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.first()) {
				
				Restaurant restaurant = new Restaurant();
				
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
				
				restaurant.setRestaurantId(restaurantId);
				restaurant.setRestaurantName(restaurantName);
				restaurant.setRestaurantTelephone(restaurantTelephone);
				restaurant.setEmail(email);
				restaurant.setRestaurantTIN(restaurantTIN);
				
				user.setUserId(id);
				user.setUserName(userName);
				user.setUserSurname(userSurname);
				user.setPassword(password);
				user.setAdmin(isAdmin);
				user.setRestaurant(restaurant);
				
			}else {
				user = null;
			}			
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void insert(CommonCrud dbEntity) throws SqlException {
		StringBuilder sqlQuery = new StringBuilder("insert into ");
		sqlQuery.append(dbEntity.getTableName());
		sqlQuery.append(" ");
		sqlQuery.append(dbEntity.getColumnsName());
		sqlQuery.append(" values ");
		sqlQuery.append(dbEntity.getCharacters());
		queryExecution(sqlQuery.toString(), dbEntity, HttpConstraints.POST);	
	}
	
	public void update(CommonCrud dbEntity) throws SqlException {
		StringBuilder sqlQuery = new StringBuilder("update ");
		sqlQuery.append(dbEntity.getTableName());
		sqlQuery.append(" set ");
		sqlQuery.append(dbEntity.getColumnsForUpdate());
		sqlQuery.append(" where ");
		sqlQuery.append(dbEntity.getIdColumnName());
		sqlQuery.append(" = ?");
		queryExecution(sqlQuery.toString(), dbEntity, HttpConstraints.PUT);	
	}
	
	public void delete(CommonCrud dbEntity) throws SqlException {
		
		StringBuilder sqlQuery = new StringBuilder("delete from ");
		sqlQuery.append(dbEntity.getTableName());
		sqlQuery.append(" where ");
		sqlQuery.append(dbEntity.getIdColumnName());
		sqlQuery.append(" = ?");
		queryExecution(sqlQuery.toString(), dbEntity, HttpConstraints.DELETE);	
	}
	
	private void queryExecution(String sqlQuery, CommonCrud dbEntity, HttpConstraints constant) throws SqlException {
		try {
			PreparedStatement preparedStatement =
					ConnectionClass.getConnection().getSqlConnection()
					.prepareStatement(sqlQuery);
			switch (constant) {
			case POST:
				preparedStatement = dbEntity.getInsert(preparedStatement);
				break;
			case PUT:
				preparedStatement = dbEntity.getUpdate(preparedStatement);
				break;
			case DELETE:
				preparedStatement = dbEntity.delete(preparedStatement);
				break;
			default:
				break;
			}
			preparedStatement.execute();
			
			if(dbEntity instanceof Bill && constant.equals(HttpConstraints.PUT)) {
				Bill bill = (Bill) dbEntity;
				for(Arcticle arcticle: bill.getArcticles()) {
					StringBuilder billArcticlesQuery = new StringBuilder("UPDATE warehouse w ");
					billArcticlesQuery.append("INNER join recipe r on w.warehouse_id = r.warehouse_fk inner join arcticle a on r.arcticle_fk = a.arcticle_id ");
					billArcticlesQuery.append("set w.good_quantity = w.good_quantity - r.good_quantity ");
					billArcticlesQuery.append("where w.restaurant_fk = ");
					billArcticlesQuery.append(bill.getRestaurant().getRestaurantId());
					billArcticlesQuery.append(" and a.arcticle_id = ");
					billArcticlesQuery.append(arcticle.getArcticleId());
					PreparedStatement manyToManyStatement =
							ConnectionClass.getConnection().getSqlConnection()
							.prepareStatement(billArcticlesQuery.toString());
					manyToManyStatement.execute();
				}
			}
		} catch (SQLException e) {
			switch (constant) {
			case POST:
				throw new SqlException("Data is not added for table "+dbEntity.getTableName()+"\n"+ e.getMessage());
			case PUT:
				throw new SqlException("Data is not updated for table "+dbEntity.getTableName()+"\n"+ e.getMessage());
			case DELETE:
				throw new SqlException("Data is not deleted for table "+dbEntity.getTableName()+"\n"+ e.getMessage());
			default:
				break;
			}
		}
		
	}

	public Restaurant getRestaurant(Restaurant restaurant) {
		
		
		return null;
	}

	public Warehouse getWarehouse(Warehouse request) {


		return null;
	}
	
	public List<CommonCrud> getArcticle(Arcticle request) {
		List<CommonCrud> resultList = new ArrayList<CommonCrud>();
		Set<Arcticle> arcticles = new HashSet<Arcticle>();
		StringBuilder sqlQuery = new StringBuilder("SELECT * FROM arcticle ");
		sqlQuery.append("INNER JOIN restaurant ON arcticle.restaurant_fk = restaurant.restaurant_id ");
		sqlQuery.append("INNER JOIN recipe ON arcticle.arcticle_id = recipe.arcticle_fk ");
		sqlQuery.append("INNER JOIN warehouse ON recipe.warehouse_fk = warehouse.warehouse_id ");
		sqlQuery.append("WHERE warehouse.good_quantity > 0 and restaurant.restaurant_id = ");
		sqlQuery.append(request.getRestaurant().getRestaurantId());
		try {
			PreparedStatement preparedStatement =
					ConnectionClass.getConnection().getSqlConnection()
					.prepareStatement(sqlQuery.toString());
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				
				Restaurant restaurant = new Restaurant();
				
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
				
				int menuId = resultSet.getInt("menu_fk");
				
				restaurant.setRestaurantId(restaurantId);
				restaurant.setRestaurantName(restaurantName);
				restaurant.setRestaurantTelephone(restaurantTelephone);
				restaurant.setEmail(email);
				restaurant.setRestaurantTIN(restaurantTIN);
				
				Menu menu = new Menu(menuId, "", restaurant);
				
				Arcticle arcticle = new Arcticle(id, arcticleName, quantity, quantityUnitMeasure, purchasePrice, priceWithVAT, restaurant, menu, null);
				
				arcticles.add(arcticle);
			}
			Iterator<Arcticle> iterator = arcticles.iterator();
			while(iterator.hasNext()) {
				resultList.add(iterator.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultList;
	}

	public Menu getMenu(Menu request) {
		
		
		return null;
	}

	public Recipe getRecipe(Recipe request) {
		
		return null;
	}

	public RestaurantTable getRestaurantTable(RestaurantTable request) {

		
		return null;
	}
	/*select * from bill inner join restaurant on bill.restaurant_fk = restaurant.restaurant_id inner join restaurant_table on 
	bill.table_fk = restaurant_table.restaurant_table_id inner join bill_arcticle on bill.bill_id = bill_arcticle.bill_fk INNER join arcticle on 
	bill_arcticle.arcticle_fk = arcticle.arcticle_id where restaurant.restaurant_id = 1 and bill.bill_id = 1*/
	public Bill getBill(Bill request) {
		Bill selectBill = new Bill();
		selectBill.setRestaurant(request.getRestaurant());
		String selectQuery = "SELECT * FROM bill ORDER BY bill_id DESC LIMIT 1";
		try {
			PreparedStatement selectStatement =
					ConnectionClass.getConnection().getSqlConnection()
					.prepareStatement(selectQuery);
			ResultSet rs = selectStatement.executeQuery();
			
			if(rs.first()) {
				int id = rs.getInt("bill_id");
				selectBill.setBillId(id);
			}
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		try {
			for(Arcticle arcticle: request.getArcticles()) {
				
				
				StringBuilder billArcticlesQuery = new StringBuilder("insert into ");
				billArcticlesQuery.append("bill_arcticle (bill_fk, arcticle_fk) values");
				billArcticlesQuery.append(" (");
				billArcticlesQuery.append(selectBill.getBillId());
				billArcticlesQuery.append(", ");
				billArcticlesQuery.append(arcticle.getArcticleId());
				billArcticlesQuery.append(" )");
				PreparedStatement manyToManyStatement =
						ConnectionClass.getConnection().getSqlConnection()
						.prepareStatement(billArcticlesQuery.toString());
				manyToManyStatement.execute();
			}
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		try {
			if(!request.isProFormaInvoice()) {
				for(Arcticle arcticle: request.getArcticles()) {
					StringBuilder billArcticlesQuery = new StringBuilder("UPDATE warehouse w ");
					billArcticlesQuery.append("INNER join recipe r on w.warehouse_id = r.warehouse_fk inner join arcticle a on r.arcticle_fk = a.arcticle_id ");
					billArcticlesQuery.append("set w.good_quantity = w.good_quantity - r.good_quantity ");
					billArcticlesQuery.append("where w.restaurant_fk = ");
					billArcticlesQuery.append(selectBill.getRestaurant().getRestaurantId());
					billArcticlesQuery.append(" and a.arcticle_id = ");
					billArcticlesQuery.append(arcticle.getArcticleId());
					PreparedStatement manyToManyStatement =
							ConnectionClass.getConnection().getSqlConnection()
							.prepareStatement(billArcticlesQuery.toString());
					manyToManyStatement.execute();
				}
			}
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
}
