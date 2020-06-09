package com.comtrade.transfer;

import java.io.Serializable;

import com.comtrade.constants.DbConstraints;
import com.comtrade.constants.HttpConstraints;

public final class TransferClass implements Serializable {
	
	private  HttpConstraints operation;
	private  Object request, response;
	private  String message;
	private  DbConstraints dbConstant;
	
	public HttpConstraints getOperation() {
		return operation;
	}

	public void setOperation(HttpConstraints operation) {
		this.operation = operation;
	}

	public Object getRequest() {
		return request;
	}

	public void setRequest(Object request) {
		this.request = request;
	}

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

	public DbConstraints getDbConstant() {
		return dbConstant;
	}

	public void setDbConstant(DbConstraints dbConstant) {
		this.dbConstant = dbConstant;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public static TransferClass create(Object request, HttpConstraints httpConstant, DbConstraints dbConstant) {
		TransferClass transfer = new TransferClass();
		transfer.request = request;
		transfer.operation = httpConstant;
		transfer.dbConstant = dbConstant;
		return transfer;
	}
}
