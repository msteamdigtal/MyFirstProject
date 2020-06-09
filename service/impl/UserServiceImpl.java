package com.comtrade.service.impl;

import java.util.List;
import java.util.Set;

import com.comtrade.communicator.DbCommunicator;
import com.comtrade.database.entities.CommonCrud;
import com.comtrade.database.entities.User;
import com.comtrade.exception.SqlException;
import com.comtrade.system.operation.GeneralSystemOperation;
import com.comtrade.transfer.TransferClass;

public class UserServiceImpl extends GeneralSystemOperation {

	@Override
	public void doConcreateSystemOperation(TransferClass object) throws SqlException {
		switch (object.getOperation()) {
		case GET:
			userCrudOperation(object);
			break;
		case GET_ONE:
			userCrudOperation(object);
			break;
		case POST:
			userCrudOperation(object);
			break;
		case PUT:
			userCrudOperation(object);
			break;
		case DELETE:
			userCrudOperation(object);
			break;
		default:
			break;
		}
	}
	
private void userCrudOperation(TransferClass object) throws SqlException {
		
		User request = (User) object.getRequest();
		DbCommunicator communicator = new DbCommunicator();
		
		User response;
		List<CommonCrud> responseList;
		
		switch (object.getOperation()) {
		case GET:
			responseList = communicator.getAll(request);
			object.setResponse(responseList);
			break;
		case GET_ONE:
			response = (User) communicator.getUser(request);
			object.setResponse(response);
			break;
		case POST:
			communicator.insert(request);
			object.setMessage("Successfully registered user!");
			break;
		case PUT:
			communicator.update(request);
			object.setMessage("Successfully updated user!");
			break;
		case DELETE:
			communicator.delete(request);
			object.setMessage("Successfully deactivated user!");
			break;
		default:
			break;
		}
	}

}
