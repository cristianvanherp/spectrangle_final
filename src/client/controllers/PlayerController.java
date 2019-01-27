package client.controllers;

import java.util.*;

import abstract_classes.Controller;
import input.Message;
import networking.Peer;
import client.*;
import model.*;

public class PlayerController extends Controller {
	//***************************************************
	//------------------CONSTRUCTORS---------------------
	//***************************************************
	public PlayerController(ClientDatabase database) {
		super(database);
	}

	//***************************************************
	//------------------PUBLIC METHODS-------------------
	//***************************************************
	@Override
	public void forward(Peer peer, Message msg) {
		switch(msg.getCommand()) {
		case "nicknamed":
			this.nicknamed(msg.getStringArgs());
			break;
		default:
			break;
		}
	}
	
	//***************************************************
	//------------------PRIVATE METHODS------------------
	//***************************************************
	public void serverMessage(String msg) {
		System.out.println("Server: " + msg);
		System.out.print("> ");
	}
	
	public void nicknamed(String args) {
		Player player = new Player(args);
		((ClientDatabase)this.getDatabase()).setPlayer(player);
	}
}
