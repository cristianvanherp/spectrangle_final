package client;

import interfaces.*;
import networking.*;

public class ClientPeerSetup implements Setup {
	//***************************************************
	//---------------------ATTRIBUTES--------------------
	//***************************************************
	private ClientDatabase database;
	private Peer peer;
	
	//***************************************************
	//---------------------CONSTRUCTORS------------------
	//***************************************************
	public ClientPeerSetup(ClientDatabase database) {
		this.database = database;
	}
	
	//***************************************************
	//---------------------THREAD------------------------
	//***************************************************
	@Override
	public void run() {
		
	}
	
	//***************************************************
	//---------------------GETTERS/SETTERS---------------
	//***************************************************
	public void setPeer(Peer peer) {
		this.peer = peer;
	}
	
}
