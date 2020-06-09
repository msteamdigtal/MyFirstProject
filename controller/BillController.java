package com.comtrade.controller;

import com.comtrade.exception.SqlException;
import com.comtrade.service.impl.BillServiceImpl;
import com.comtrade.system.operation.GeneralSystemOperation;
import com.comtrade.transfer.TransferClass;

public class BillController implements CommandBase {

	@Override
	public void execute(TransferClass transfer) throws SqlException {
GeneralSystemOperation operation = null;
		
		switch (transfer.getOperation()) {
		case GET:
			operation = new BillServiceImpl();
			break;
		case GET_ONE:
			operation = new BillServiceImpl();
			break;
		case POST:
			operation = new BillServiceImpl();
			break;
		case PUT:
			operation = new BillServiceImpl();
			break;
		case DELETE:
			operation = new BillServiceImpl();
			break;

		default:
			break;
		}
		
		operation.doSystemOperation(transfer);
	}

}
