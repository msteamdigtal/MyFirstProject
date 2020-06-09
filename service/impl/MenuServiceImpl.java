package com.comtrade.service.impl;

import java.util.List;
import java.util.Set;

import com.comtrade.communicator.DbCommunicator;
import com.comtrade.database.entities.Arcticle;
import com.comtrade.database.entities.CommonCrud;
import com.comtrade.database.entities.Menu;
import com.comtrade.exception.SqlException;
import com.comtrade.system.operation.GeneralSystemOperation;
import com.comtrade.transfer.TransferClass;

public class MenuServiceImpl extends GeneralSystemOperation {

	@Override
	public void doConcreateSystemOperation(TransferClass object) throws SqlException {
		switch (object.getOperation()) {
		case GET:
			menuCrudOperation(object);
			break;
		case GET_ONE:
			menuCrudOperation(object);
			break;
		case POST:
			menuCrudOperation(object);
			break;
		case PUT:
			menuCrudOperation(object);
			break;
		case DELETE:
			menuCrudOperation(object);
			break;
		default:
			break;
		}
	}
	
	private void menuCrudOperation(TransferClass object) throws SqlException {
		
		Menu request = (Menu) object.getRequest();
		DbCommunicator communicator = new DbCommunicator();
		
		Menu response;
		List<CommonCrud> responseList;
		
		switch (object.getOperation()) {
		case GET:
			responseList = communicator.getAll(request);
			object.setResponse(responseList);
			break;
		case GET_ONE:
			response = (Menu) communicator.getMenu(request);
			object.setResponse(response);
			break;
		case POST:
			communicator.insert(request);
			object.setMessage("Successfully added Menu!");
			break;
		case PUT:
			communicator.update(request);
			object.setMessage("Successfully updated Menu!");
			break;
		case DELETE:
			communicator.delete(request);
			object.setMessage("Successfully deleted Menu!");
			break;
		default:
			break;
		}
	}

}
