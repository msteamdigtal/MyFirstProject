package com.comtrade.system.operation;

import com.comtrade.connection.ConnectionClass;
import com.comtrade.exception.SqlException;
import com.comtrade.transfer.TransferClass;

public abstract class GeneralSystemOperation {
	
	public void doSystemOperation(TransferClass object) throws SqlException {
		try {
			startTransaction();
			doConcreateSystemOperation(object);
			commitTransaction();
			
		}catch (Exception e) {
			rollbackTransaction();
			object.setMessage(e.getMessage());
			
		}finally {
			closeConnection();
		}
	}
	
	public void closeConnection() {
		ConnectionClass.getInstance().closeConnection();
	}
	
	public void rollbackTransaction() {
		ConnectionClass.getInstance().rollbackTransaction();
	}
	
	public void startTransaction() {
		ConnectionClass.getInstance().startTransaction();
	}
	
	public void commitTransaction() {
		ConnectionClass.getInstance().commitTransaction();
	}
	
	public abstract void doConcreateSystemOperation(TransferClass object) throws SqlException;

	

}
