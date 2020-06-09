package com.comtrade.service.impl;

import java.util.List;
import java.util.Set;

import com.comtrade.communicator.DbCommunicator;
import com.comtrade.database.entities.Bill;
import com.comtrade.database.entities.CommonCrud;
import com.comtrade.exception.SqlException;
import com.comtrade.system.operation.GeneralSystemOperation;
import com.comtrade.transfer.TransferClass;

public class BillServiceImpl extends GeneralSystemOperation {

	@Override
	public void doConcreateSystemOperation(TransferClass object) throws SqlException {
		switch (object.getOperation()) {
		case GET:
			billCrudOperation(object);
			break;
		case GET_ONE:
			billCrudOperation(object);
			break;
		case POST:
			billCrudOperation(object);
			break;
		case PUT:
			billCrudOperation(object);
			break;
		case DELETE:
			billCrudOperation(object);
			break;
		default:
			break;
		}
	}
	
	private void billCrudOperation(TransferClass object) throws SqlException {
		
		Bill request = (Bill) object.getRequest();
		DbCommunicator communicator = new DbCommunicator();
		
		Bill response;
		List<CommonCrud> responseList;
		
		switch (object.getOperation()) {
		case GET:
			responseList = communicator.getAll(request);
			object.setResponse(responseList);
			break;
		case GET_ONE:
			response = (Bill) communicator.getBill(request);
			object.setMessage("All operation went great!");
			break;
		case POST:
			communicator.insert(request);
			object.setMessage("Successfully added bill!");
			break;
		case PUT:
			communicator.update(request);
			object.setMessage("Successfully updated bill!");
			break;
		case DELETE:
			communicator.delete(request);
			object.setMessage("Successfully deleted bill!");
			break;
		default:
			break;
		}
	}

}
