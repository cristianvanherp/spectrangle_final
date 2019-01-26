package client;

import java.io.IOException;
import java.net.*;
import java.util.*;

import abstract_classes.Controller;
import input.*;
import networking.*;
import client.controllers.*;

public class Client {
	//***************************************************
	//---------------------ATTRIBUTES--------------------
	//***************************************************
	private Socket socket;
	private Integer port;
	private String host;
	private ClientDatabase database;
	private Messenger messenger;
	
	//***************************************************
	//---------------------CONSTRUCTORS------------------
	//***************************************************
	public Client(String host, Integer port) throws UnknownHostException, IOException {
		this.host = host;
		this.port = port;
		this.database = new ClientDatabase();
		
		//Controller list
		List<Controller> controllers = new ArrayList<Controller>();
		controllers.add(new PlayerController(this.database));
		controllers.add(new GameController(this.database));
		
		
		this.messenger = new Messenger(controllers);
		this.socket = new Socket(this.host, this.port);
		this.database.setPeer(new Peer(this.socket, this.messenger, new ClientPeerSetup(this.database)));
		this.database.setInputThread(new InputThread(this.database.getPeer()));
	}
	
	//***************************************************
	//---------------------THREAD------------------------
	//***************************************************
	public static void main(String args[]) throws InterruptedException {
		try {
			Client client = new Client("127.0.0.1", 9091);
			client.database.getInputThread().begin();
			
		} catch (UnknownHostException e) {
			System.out.println("Could not find host. Terminating!");
		} catch (IOException e) {
			System.out.println("An error ocurred. Terminating!");
		}
	}
}
