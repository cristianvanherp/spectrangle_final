package model;

import java.io.IOException;
import java.util.*;

import enums.Status;
import input.Messenger;
import networking.*;

public class Game implements Runnable {
	//***************************************************
	//------------------CONSTANTS------------------------
	//***************************************************
	public static final int MIN_PLAYERS = 2;
	public static final int MAX_PLAYERS = 4;
	
	//***************************************************
	//------------------RELATIONSHIPS--------------------
	//***************************************************
	private List<Player> players;
	private Player host;
	private Board board;
	private Bag bag;
	
	//***************************************************
	//------------------ATTRIBUTES-----------------------
	//***************************************************
	private Thread thread;
	private Status status;
	
	//***************************************************
	//------------------CONSTRUCTORS---------------------
	//***************************************************
	public Game(List<Player> players, Player host) {
		if(players.size() < Game.MIN_PLAYERS || players.size() > Game.MAX_PLAYERS) {
			//Throw an exception
		}
		
		if(!players.contains(host)) {
			//Throw an exception
		}
		
		this.players = players;
		this.init();
	}
	
	//***************************************************
	//------------------PUBLIC METHODS-------------------
	//***************************************************	
	public void start() {
		this.thread = new Thread(this);
		this.thread.start();
		this.status = Status.RUNNING;
	}
	
	public String getPlayersStr() {
		String str = "";
		for(Player player: this.players) {
			str += player.getNickname() + " ";
		}
		
		return str.trim();
	}
	
	//***************************************************
	//------------------PRIVATE METHODS------------------
	//***************************************************
	private void init() {
		this.board = new Board();
		this.bag = new Bag();
		this.status = Status.NOT_STARTED;
		
		for(Player player: this.players) {
			player.setGame(this);
			player.setScore(0);
		}
	}
	
	//***************************************************
	//------------------GETTERS/SETTERS------------------
	//***************************************************
	public Bag getBag() {
		return bag;
	}

	public void setBag(Bag bag) {
		this.bag = bag;
	}
	
	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	//***************************************************
	//------------------THREAD---------------------------
	//***************************************************
	@Override
	public void run() {
		
		//FIRST STEP - TELL ALL PLAYERS THAT THE GAME STARTED AND IN WHICH ORDER THE PLAYERS ARE
		Messenger.broadcast(this.players, "start " + this.getPlayersStr());
		
		//SECOND STEP - DRAW TILES FOR ALL OF THE PLAYERS
		for(Player player: this.players) {
			List<Tile> tiles = player.drawMaxTiles();
			for(Tile tile: tiles) {
				Messenger.broadcast(this.players, "drawnTile " + player.getNickname() + " " + tile.toString());
			}
		}
	}

}
