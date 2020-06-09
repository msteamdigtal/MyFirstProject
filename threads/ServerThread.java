package com.comtrade.threads;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.comtrade.constants.ConnectionConstraints;

public class ServerThread extends Thread{
	
	private ServerSocket serverSocket;

	public void run() {
		startServer();
	}

	private void startServer() {
		try {
			System.out.println("U Server 1 tredu SAM!");
			serverSocket = new ServerSocket(ConnectionConstraints.PORT.getPort());
			while (true) {
				System.out.println("U Server 2 tredu SAM!");
				Socket socket = serverSocket.accept();
				System.out.println("U Server 3 tredu SAM!");
				ClientThread clientThread = new ClientThread(socket);
				System.out.println("U Server 4 tredu SAM!");
				clientThread.start();
				System.out.println("U Server 5 tredu SAM!");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
