package com.comtrade.communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.comtrade.constants.ConnectionConstraints;
import com.comtrade.transfer.TransferClass;

public class Communication {
	private static Communication communication;
	private Socket socket;
	
	private Communication() {
		try {
			socket = new Socket(ConnectionConstraints.ADDRESS.getAddress(),
					ConnectionConstraints.PORT.getPort());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static Communication getInstance() {
		if(communication == null) {
			communication = new Communication();
			
		}
		return communication;
	}

	public void send(TransferClass transfer) {
		try {
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectOutputStream.writeObject(transfer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public TransferClass read () throws IOException, ClassNotFoundException {
			ObjectInputStream objectInputStream = new ObjectInputStream(
					socket.getInputStream());
			return (TransferClass) objectInputStream.readObject();
		
		
	}
}
