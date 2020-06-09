package com.comtrade.controller;

import com.comtrade.exception.SqlException;
import com.comtrade.transfer.TransferClass;

public interface CommandBase {
	
	void execute(TransferClass transfer) throws SqlException;

}
