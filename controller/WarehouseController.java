package com.comtrade.controller;

import com.comtrade.exception.SqlException;
import com.comtrade.service.impl.WarehouseServiceImpl;
import com.comtrade.system.operation.GeneralSystemOperation;
import com.comtrade.transfer.TransferClass;

public class WarehouseController implements CommandBase {

	@Override
	public void execute(TransferClass transfer) throws SqlException {
		GeneralSystemOperation operation = null;
		
		switch (transfer.getOperation()) {
		case GET:
			operation = new WarehouseServiceImpl();
			break;
		case GET_ONE:
			operation = new WarehouseServiceImpl();
			break;
		case POST:
			operation = new WarehouseServiceImpl();
			break;
		case PUT:
			operation = new WarehouseServiceImpl();
			break;
		case DELETE:
			operation = new WarehouseServiceImpl();
			break;

		default:
			break;
		}
		
		operation.doSystemOperation(transfer);
	}

}
