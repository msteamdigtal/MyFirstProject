package com.comtrade.controller;

import com.comtrade.exception.SqlException;
import com.comtrade.service.impl.ArcticleServiceImpl;
import com.comtrade.service.impl.MenuServiceImpl;
import com.comtrade.system.operation.GeneralSystemOperation;
import com.comtrade.transfer.TransferClass;

public class MenuController implements CommandBase {

	@Override
	public void execute(TransferClass transfer) throws SqlException {
		GeneralSystemOperation operation = null;
		
		switch (transfer.getOperation()) {
		case GET:
			operation = new MenuServiceImpl();
			break;
		case GET_ONE:
			operation = new MenuServiceImpl();
			break;
		case POST:
			operation = new MenuServiceImpl();
			break;
		case PUT:
			operation = new MenuServiceImpl();
			break;
		case DELETE:
			operation = new MenuServiceImpl();
			break;

		default:
			break;
		}
		
		operation.doSystemOperation(transfer);
	}

}
