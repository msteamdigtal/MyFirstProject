package com.comtrade.controller;

import com.comtrade.exception.SqlException;
import com.comtrade.service.impl.RecipeServiceImpl;
import com.comtrade.system.operation.GeneralSystemOperation;
import com.comtrade.transfer.TransferClass;

public class RecipeController implements CommandBase {

	@Override
	public void execute(TransferClass transfer) throws SqlException {
		GeneralSystemOperation operation = null;
		
		switch (transfer.getOperation()) {
		case GET:
			operation = new RecipeServiceImpl();
			break;
		case GET_ONE:
			operation = new RecipeServiceImpl();
			break;
		case POST:
			operation = new RecipeServiceImpl();
			break;
		case PUT:
			operation = new RecipeServiceImpl();
			break;
		case DELETE:
			operation = new RecipeServiceImpl();
			break;

		default:
			break;
		}
		
		operation.doSystemOperation(transfer);
	}

}
