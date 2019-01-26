package client;

import java.net.*;

import input.InputThread;
import interfaces.*;
import networking.*;
import model.*;

public class ClientDatabase implements Database {
	//***************************************************
	//---------------------ATTRIBUTES--------------------
	//***************************************************
	private Peer peer;
	private InputThread inputThread;
	private Game game;
	private Player player;
	
	//***************************************************
	//---------------------CONSTRUCTORS--------------------
	//***************************************************
	public ClientDatabase() {
		
	}

	//***************************************************
	//---------------------GETTERS/SETTERS---------------
	//***************************************************
	public Peer getPeer() {
		return peer;
	}

	public void setPeer(Peer peer) {
		this.peer = peer;
	}

	public InputThread getInputThread() {
		return inputThread;
	}

	public void setInputThread(InputThread inputThread) {
		this.inputThread = inputThread;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
}
