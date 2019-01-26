package server;

import exceptions.PeerNotFoundException;
import interfaces.*;
import networking.*;
import model.*;

public class ServerPeerSetup implements Setup {
	
	//***************************************************
	//---------------------ATTRIBUTES--------------------
	//***************************************************
	private ServerDatabase database;
	private Peer peer;
	
	//***************************************************
	//---------------------CONSTRUCTORS------------------
	//***************************************************
	public ServerPeerSetup(ServerDatabase database) {
		this.database = database;
	}
	
	//***************************************************
	//---------------------THREAD------------------------
	//***************************************************
	@Override
	public void run() {
		Player player = new Player(null, this.peer);
		
		try {
			this.database.insertUser(peer, player);
		} catch (PeerNotFoundException e) {
			System.out.println(e.getMessage());
			return;
		}
	}
	
	//***************************************************
	//---------------------GETTERS/SETTERS---------------
	//***************************************************
	public void setPeer(Peer peer) {
		this.peer = peer;
	}
	
}
