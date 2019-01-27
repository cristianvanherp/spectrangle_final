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
		case "requestMove":
			this.requestMove();
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
	
	public void updateView() {
		try {
			Runtime.getRuntime().exec("cls");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ClientDatabase database = (ClientDatabase)this.getDatabase();
		Board board = database.getGame().getBoard();
		System.out.println(board.toString());
		System.out.print("> ");
	}
	
	public void start(List<String> args) {
		ClientDatabase database = (ClientDatabase)this.getDatabase();
		
		args.remove(database.getPlayer().getNickname());
		List<Player> players = new ArrayList<Player>();
		
		for(String nickname: args) {
			players.add(new Player(nickname));
		}
		players.add(database.getPlayer());
		
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
	
	public void requestMove() {
		this.serverMessage("It's your turn. Enter move.");
	}

}
