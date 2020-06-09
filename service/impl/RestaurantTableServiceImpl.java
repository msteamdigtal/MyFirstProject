package com.comtrade.service.impl;

import java.util.List;
import java.util.Set;

import com.comtrade.communicator.DbCommunicator;
import com.comtrade.database.entities.CommonCrud;
import com.comtrade.database.entities.RestaurantTable;
import com.comtrade.exception.SqlException;
import com.comtrade.system.operation.GeneralSystemOperation;
import com.comtrade.transfer.TransferClass;

public class RestaurantTableServiceImpl extends GeneralSystemOperation {

	@Override
	public void doConcreateSystemOperation(TransferClass object) throws SqlException {
		switch (object.getOperation()) {
		case GET:
			tableCrudOperation(object);
			break;
		case GET_ONE:
			tableCrudOperation(object);
			break;
		case POST:
			tableCrudOperation(object);
			break;
		case PUT:
			tableCrudOperation(object);
			break;
		case DELETE:
			tableCrudOperation(object);
			break;
		default:
			break;
		}
	}
	
	private void tableCrudOperation(TransferClass object) throws SqlException {
		
		RestaurantTable request = (RestaurantTable) object.getRequest();
		DbCommunicator communicator = new DbCommunicator();
		
		RestaurantTable response;
		List<CommonCrud> responseList;
		
		switch (object.getOperation()) {
		case GET:
			responseList = communicator.getAll(request);
			object.setResponse(responseList);
			break;
		case GET_ONE:
			response = (RestaurantTable) communicator.getRestaurantTable(request);
			object.setResponse(response);
			break;
		case POST:
			communicator.insert(request);
			object.setMessage("Successfully added table!");
			break;
		case PUT:
			communicator.update(request);
			object.setMessage("Successfully updated table!");
			break;
		case DELETE:
			communicator.delete(request);
			object.setMessage("Successfully deleted table!");
			break;
		default:
			break;
		}
	}

}
