package com.comtrade.service.impl;

import java.util.List;
import java.util.Set;

import com.comtrade.communicator.DbCommunicator;
import com.comtrade.database.entities.Arcticle;
import com.comtrade.database.entities.CommonCrud;
import com.comtrade.exception.SqlException;
import com.comtrade.system.operation.GeneralSystemOperation;
import com.comtrade.transfer.TransferClass;

public class ArcticleServiceImpl extends GeneralSystemOperation {

	@Override
	public void doConcreateSystemOperation(TransferClass object) throws SqlException {
		switch (object.getOperation()) {
		case GET:
			arcticleCrudOperation(object);
			break;
		case GET_ONE:
			arcticleCrudOperation(object);
			break;
		case POST:
			arcticleCrudOperation(object);
			break;
		case PUT:
			arcticleCrudOperation(object);
			break;
		case DELETE:
			arcticleCrudOperation(object);
			break;
		default:
			break;
		}
	}
	
	private void arcticleCrudOperation(TransferClass object) throws SqlException {
		
		Arcticle request = (Arcticle) object.getRequest();
		DbCommunicator communicator = new DbCommunicator();
		
		Arcticle response;
		List<CommonCrud> responseList;
		
		switch (object.getOperation()) {
		case GET:
			responseList = communicator.getAll(request);
			object.setResponse(responseList);
			break;
		case GET_ONE:
			responseList = communicator.getArcticle(request);
			object.setResponse(responseList);
			break;
		case POST:
			communicator.insert(request);
			object.setMessage("Successfully added Arcticle!");
			break;
		case PUT:
			communicator.update(request);
			object.setMessage("Successfully updated Arcticle!");
			break;
		case DELETE:
			communicator.delete(request);
			object.setMessage("Successfully deleted Arcticle!");
			break;
		default:
			break;
		}
	}

}
