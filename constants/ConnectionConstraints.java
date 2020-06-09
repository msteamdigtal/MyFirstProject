package com.comtrade.constants;

public enum ConnectionConstraints {
	
	ADDRESS("127.0.0.1"),
	PORT(9000);
	
	private String address;
	private int port;
	
	ConnectionConstraints(String address) {
		this.address = address;
	}
	
	ConnectionConstraints(int port) {
		this.port = port;
	}
	
	public String getAddress() {
		return address;
	}
	
	public int getPort() {
		return port;
	}

}
