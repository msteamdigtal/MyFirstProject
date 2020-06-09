package com.comtrade.controller;

import com.comtrade.exception.SqlException;
import com.comtrade.service.impl.RestaurantServiceImpl;
import com.comtrade.system.operation.GeneralSystemOperation;
import com.comtrade.transfer.TransferClass;

public class RestaurantController implements CommandBase {

	@Override
	public void execute(TransferClass transfer) throws SqlException {
		
		GeneralSystemOperation operation = null;
		
		switch (transfer.getOperation()) {
		case GET:
			operation = new RestaurantServiceImpl();
			break;
		case GET_ONE:
			operation = new RestaurantServiceImpl();
			break;
		case POST:
			operation = new RestaurantServiceImpl();
			break;
		case PUT:
			operation = new RestaurantServiceImpl();
			break;
		case DELETE:
			operation = new RestaurantServiceImpl();
			break;

		default:
			break;
		}
		
		operation.doSystemOperation(transfer);
	}	 

}
