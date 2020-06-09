package com.comtrade.controller;

import com.comtrade.exception.SqlException;
import com.comtrade.service.impl.MenuServiceImpl;
import com.comtrade.service.impl.RestaurantTableServiceImpl;
import com.comtrade.system.operation.GeneralSystemOperation;
import com.comtrade.transfer.TransferClass;

public class RestaurantTableController implements CommandBase {

	@Override
	public void execute(TransferClass transfer) throws SqlException {
GeneralSystemOperation operation = null;
		
		switch (transfer.getOperation()) {
		case GET:
			operation = new RestaurantTableServiceImpl();
			break;
		case GET_ONE:
			operation = new RestaurantTableServiceImpl();
			break;
		case POST:
			operation = new RestaurantTableServiceImpl();
			break;
		case PUT:
			operation = new RestaurantTableServiceImpl();
			break;
		case DELETE:
			operation = new RestaurantTableServiceImpl();
			break;

		default:
			break;
		}
		
		operation.doSystemOperation(transfer);
	}

}
