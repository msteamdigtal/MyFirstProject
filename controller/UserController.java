package com.comtrade.controller;

import com.comtrade.exception.SqlException;
import com.comtrade.service.impl.UserServiceImpl;
import com.comtrade.system.operation.GeneralSystemOperation;
import com.comtrade.transfer.TransferClass;

public class UserController implements CommandBase {

	@Override
	public void execute(TransferClass transfer) throws SqlException {

		GeneralSystemOperation operation = null;
		
		switch (transfer.getOperation()) {
		case GET:
			operation = new UserServiceImpl();
			break;
		case GET_ONE:
			operation = new UserServiceImpl();
			break;
		case POST:
			operation = new UserServiceImpl();
			break;
		case PUT:
			operation = new UserServiceImpl();
			break;
		case DELETE:
			operation = new UserServiceImpl();
			break;

		default:
			break;
		}
		
		operation.doSystemOperation(transfer);
	}

}
