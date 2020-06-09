package com.comtrade.controller;

import com.comtrade.exception.SqlException;
import com.comtrade.service.impl.ArcticleServiceImpl;
import com.comtrade.system.operation.GeneralSystemOperation;
import com.comtrade.transfer.TransferClass;

public class ArcticleController implements CommandBase {

	@Override
	public void execute(TransferClass transfer) throws SqlException {
		GeneralSystemOperation operation = null;
		
		switch (transfer.getOperation()) {
		case GET:
			operation = new ArcticleServiceImpl();
			break;
		case GET_ONE:
			operation = new ArcticleServiceImpl();
			break;
		case POST:
			operation = new ArcticleServiceImpl();
			break;
		case PUT:
			operation = new ArcticleServiceImpl();
			break;
		case DELETE:
			operation = new ArcticleServiceImpl();
			break;

		default:
			break;
		}
		
		operation.doSystemOperation(transfer);
	}

}
