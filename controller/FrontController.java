package com.comtrade.controller;

import java.io.IOException;

import com.comtrade.communication.Communication;
import com.comtrade.transfer.TransferClass;

public class FrontController {
	private static FrontController frontController;
	
	private FrontController() {
		
	}
	public static FrontController getInstance() {
		if(frontController == null) {
			frontController = new FrontController();
		}
		return frontController;
		
	}

	public TransferClass execute(TransferClass transfer) throws ClassNotFoundException, IOException {
		Communication.getInstance().send(transfer);
		return Communication.getInstance().read();
		
	}
}
