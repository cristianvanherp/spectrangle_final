package server.controllers;

import java.util.List;

import abstract_classes.Controller;
import interfaces.*;
import input.*;
import networking.Peer;
import server.ServerDatabase;

public class GameController extends Controller {
	//***************************************************
	//---------------------CONSTRUCTORS------------------
	//***************************************************
	public GameController(ServerDatabase database) {
		super(database);
	}

	//***************************************************
	//---------------------PUBLIC METHODS----------------
	//***************************************************
	@Override
	public void forward(Peer peer, Message msg) {
		
	}
	
	//***************************************************
	//---------------------PRIVATE METHODS---------------
	//***************************************************


}
