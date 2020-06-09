package com.comtrade.database.entities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public interface CommonCrud {
	
	String getColumnsForUpdate();
	String getTableName();
	String getColumnsName();
	PreparedStatement getInsert(PreparedStatement preparedStatement);
    String getCharacters();
	List<CommonCrud> selectAll(ResultSet resultSet);
	String getIdColumnName();
	PreparedStatement getUpdate(PreparedStatement preparedStatement);
	PreparedStatement delete(PreparedStatement preparedStatement);
	PreparedStatement setRestaurantId(PreparedStatement preparedStatement);
}
