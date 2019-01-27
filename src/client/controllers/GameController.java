package client.controllers;

import java.io.IOException;
import java.util.*;

import abstract_classes.Controller;
import input.Message;
import networking.Peer;
import client.*;
import model.*;

public class GameController extends Controller {
	//***************************************************
	//------------------CONSTRUCTORS---------------------
	//***************************************************
	public GameController(ClientDatabase database) {
		super(database);
	}

	//***************************************************
	//------------------PUBLIC METHODS-------------------
	//***************************************************
	@Override
	public void forward(Peer peer, Message msg) {
		switch(msg.getCommand()) {
		case "start":
			this.start(msg.getArgs());
			break;
		case "drawnTile":
			this.drawnTile(msg.getArgs().get(0), msg.getArgs().get(1));
			break;
		case "placedTile":
			this.placedTile(msg.getArgs().get(0), msg.getArgs().get(1), msg.getArgs().get(2));
			break;
		case "requestMove":
			this.requestMove();
		default:
			break;
		}
	}
	
	//***************************************************
	//------------------PRIVATE METHODS------------------
	//***************************************************
	private void serverMessage(String msg) {
		System.out.println("Server: " + msg);
		System.out.print("> ");
	}
	
	private void updateView() {
		this.clearScreen();
		
		ClientDatabase database = (ClientDatabase)this.getDatabase();
		Board board = database.getGame().getBoard();
		System.out.println(board.toString());
		System.out.print("> ");
	}
	
	private void clearScreen() {  
	    for(int i = 0 ; i < 100 ; i++) {
	    	System.out.print("\n");
	    }
	}  
	
	public void start(List<String> args) {
		ClientDatabase database = (ClientDatabase)this.getDatabase();
		
		List<Player> players = new ArrayList<Player>();
		
		for(String nickname: args) {
			if(nickname.equals(database.getPlayer().getNickname())) {
				players.add(database.getPlayer());
			}
			else {
				players.add(new Player(nickname));
			}
		}
		
		Game game = new Game(players, null);
		database.setGame(game);
		this.updateView();
	}
	
	public void drawnTile(String nickname, String tileStr) {
		ClientDatabase database = (ClientDatabase)this.getDatabase();
		List<Player> players = database.getGame().getPlayers();
		
		for(Player player: players) {
			if(nickname.equals(player.getNickname())) {
				player.drawTile(tileStr);
			}
		}
		
		this.updateView();
	}
	
	public void placedTile(String nickname, String indexStr, String tileStr) {
		ClientDatabase database = (ClientDatabase)this.getDatabase();
		Game game = database.getGame();
		Player player = null;
		Integer index;
		
		for(Player p: game.getPlayers()) {
			if(nickname.equals(p.getNickname())) {
				player = p;
				break;
			}
		}
		
		if(player == null) {
			return;
		}
		
		try {
			index = Integer.parseInt(indexStr);
		} catch(NumberFormatException e) {
			return;
		}
		
		player.placeTile(index, tileStr);
		
		this.updateView();
	}
	
	public void requestMove() {
		this.serverMessage("It's your turn. Enter move.");
	}

}
