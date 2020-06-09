package com.comtrade.threads;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.comtrade.controller.ArcticleController;
import com.comtrade.controller.BillController;
import com.comtrade.controller.CommandBase;
import com.comtrade.controller.MenuController;
import com.comtrade.controller.RecipeController;
import com.comtrade.controller.RestaurantController;
import com.comtrade.controller.RestaurantTableController;
import com.comtrade.controller.UserController;
import com.comtrade.controller.WarehouseController;
import com.comtrade.exception.SqlException;
import com.comtrade.transfer.TransferClass;

public class ClientThread extends Thread{
	
	private Socket socket;

	public ClientThread(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		
		System.out.println("U Klijent 1 tredu SAM!");
		while(true) {
			try {
				System.out.println("U Klijent 2 tredu SAM!");
				ObjectInputStream objectInputStream = new
						ObjectInputStream(socket.getInputStream());
				TransferClass transfer = null;
				System.out.println("U Klijent 3 tredu SAM!");
				try {
					transfer = (TransferClass) objectInputStream.readObject();
					System.out.println("U Klijent 4 tredu SAM!");
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				processRequest(transfer);
				System.out.println("U Klijent 5 tredu SAM!");
			} catch (IOException | SqlException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void processRequest(TransferClass transfer) throws SqlException {
		System.out.println("processRequest() 1");
		CommandBase commandBase = null;
		System.out.println("processRequest() 2");
		switch (transfer.getDbConstant()) {
		case USER:
			System.out.println("processRequest() 3");
			commandBase = new UserController();
			break;
		case RESTAURANT:
			System.out.println("processRequest() 4");
			commandBase = new RestaurantController();
			break;
		case WAREHOUSE:
			System.out.println("processRequest() 5");
			commandBase = new WarehouseController();
			break;
		case ARCTICLE:
			System.out.println("processRequest() 6");
			commandBase = new ArcticleController();
			break;
		case MENU:
			System.out.println("processRequest() 6");
			commandBase = new MenuController();
			break;
		case RECIPE:
			System.out.println("processRequest() 7");
			commandBase = new RecipeController();
			break;
		case TABLE:
			System.out.println("processRequest() 8");
			commandBase = new RestaurantTableController();
			break;
		case BILL:
			System.out.println("processRequest() 9");
			commandBase = new BillController();
			break;
		default:
			System.out.println("processRequest() 10");
			break;
		}
		System.out.println("processRequest() 11");
		commandBase.execute(transfer);
		send(transfer);
	}
	
	private void send(TransferClass transfer) {
		try {
			System.out.println("send() 1");
			ObjectOutputStream 	objectOutputStream = new 
					ObjectOutputStream(socket.getOutputStream());
			System.out.println("send() 2");
			objectOutputStream.writeObject(transfer);
			System.out.println("send() 3");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
