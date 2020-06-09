package com.comtrade.service.impl;

import java.util.List;
import java.util.Set;

import com.comtrade.communicator.DbCommunicator;
import com.comtrade.database.entities.CommonCrud;
import com.comtrade.database.entities.Warehouse;
import com.comtrade.exception.SqlException;
import com.comtrade.system.operation.GeneralSystemOperation;
import com.comtrade.transfer.TransferClass;

public class WarehouseServiceImpl extends GeneralSystemOperation {

	@Override
	public void doConcreateSystemOperation(TransferClass object) throws SqlException {
		switch (object.getOperation()) {
		case GET:
			warehouseCrudOperation(object);
			break;
		case GET_ONE:
			warehouseCrudOperation(object);
			break;
		case POST:
			warehouseCrudOperation(object);
			break;
		case PUT:
			warehouseCrudOperation(object);
			break;
		case DELETE:
			warehouseCrudOperation(object);
			break;
		default:
			break;
		}
	}
	
	private void warehouseCrudOperation(TransferClass object) throws SqlException {
		
		Warehouse request = (Warehouse) object.getRequest();
		DbCommunicator communicator = new DbCommunicator();
		
		Warehouse response;
		List<CommonCrud> responseList;
		
		switch (object.getOperation()) {
		case GET:
			responseList = communicator.getAll(request);
			object.setResponse(responseList);
			break;
		case GET_ONE:
			response = (Warehouse) communicator.getWarehouse(request);
			object.setResponse(response);
			break;
		case POST:
			communicator.insert(request);
			object.setMessage("Successfully added good!");
			break;
		case PUT:
			communicator.update(request);
			object.setMessage("Successfully updated good!");
			break;
		case DELETE:
			communicator.delete(request);
			object.setMessage("Successfully deleted good!");
			break;
		default:
			break;
		}
	}
}
