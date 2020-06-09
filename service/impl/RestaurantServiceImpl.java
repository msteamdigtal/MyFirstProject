package com.comtrade.service.impl;

import java.util.List;
import java.util.Set;

import com.comtrade.communicator.DbCommunicator;
import com.comtrade.database.entities.CommonCrud;
import com.comtrade.database.entities.Restaurant;
import com.comtrade.exception.SqlException;
import com.comtrade.system.operation.GeneralSystemOperation;
import com.comtrade.transfer.TransferClass;

public class RestaurantServiceImpl extends GeneralSystemOperation {

	@Override
	public void doConcreateSystemOperation(TransferClass object) throws SqlException {
		
		switch (object.getOperation()) {
		case GET:
			restaurantCrudOperation(object);
			break;
		case GET_ONE:
			restaurantCrudOperation(object);
			break;
		case POST:
			restaurantCrudOperation(object);
			break;
		case PUT:
			restaurantCrudOperation(object);
			break;
		case DELETE:
			restaurantCrudOperation(object);
			break;
		default:
			break;
		}
	}
	
	private void restaurantCrudOperation(TransferClass object) throws SqlException {
		
		Restaurant request = (Restaurant) object.getRequest();
		DbCommunicator communicator = new DbCommunicator();
		
		Restaurant response;
		List<CommonCrud> responseList;
		
		switch (object.getOperation()) {
		case GET:
			responseList = communicator.getAll(request);
			object.setResponse(responseList);
			break;
		case GET_ONE:
			response = (Restaurant) communicator.getRestaurant(request);
			object.setResponse(response);
			break;
		case POST:
			communicator.insert(request);
			object.setMessage("Successfully added restaurant!");
			break;
		case PUT:
			communicator.update(request);
			object.setMessage("Successfully updated restaurant!");
			break;
		case DELETE:
			communicator.delete(request);
			object.setMessage("Successfully deleted restaurant!");
			break;
		default:
			break;
		}
	}

}
