package server;

import java.net.*;
import java.io.*;
import java.util.*;

import abstract_classes.Controller;
import networking.*;
import input.*;
import server.controllers.*;

public class Server implements Observer {
	// ***************************************************
	// ---------------------ATTRIBUTES--------------------
	// ***************************************************
	private Integer port;
	private ServerSocket socket;
	private ServerDatabase database;
	private Messenger messenger;

	// ***************************************************
	// ---------------------CONSTRUCTORS------------------
	// ***************************************************
	public Server(Integer port) throws IOException {
		this.port = port;
		this.database = new ServerDatabase();
		this.socket = new ServerSocket(this.port);

		// Controller list
		List<Controller> controllers = new ArrayList<Controller>();
		controllers.add(new PlayerController(this.database));
		controllers.add(new GameController(this.database));

		// Message handler
		this.messenger = new Messenger(controllers);
	}

	// ***************************************************
	// ---------------------THREAD------------------------
	// ***************************************************
	public static void main(String[] args) {
		ServerManager manager;
		Server server;
		Peer peer;
		
		Integer port;
		try {
			port = Integer.parseInt(args[0]);
		} catch(NumberFormatException e) {
			System.out.println("Invalid port. Terminating!");
			return;
		} catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("Port missing!");
			return;
		}
		

		try {
			server = new Server(port);
		} catch (IOException e) {
			System.out.println("Unable to start the server. Terminating!");
			return;
		}

		manager = new ServerManager(server.database);
		new Thread(manager).start();

		while (true) {
			try {
				peer = new Peer(server.socket.accept(), server.messenger,
						new ServerPeerSetup(server.database));
				peer.addObserver(server);

				server.database.insertPeer(peer);
			} catch (IOException e) {
				System.out.println("Client failed to connect. Terminating!");
				return;
			}
		}

	}

	// ***************************************************
	// ---------------------OBSERVER METHODS--------------
	// ***************************************************
	@Override
	public void update(Observable arg0, Object arg1) {
		Peer peer = (Peer) arg0;
		String msg = (String) arg1;

		if (msg.equalsIgnoreCase("disconnected")) {
			this.database.removePeer(peer);
			System.out.print("Client at " + peer.getAddress() + " has disconnected. Clients left: "
					+ this.database.getPeers().size() + "\n");
		}
	}
}
