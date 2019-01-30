package client.controllers;

import java.util.*;

import abstract_classes.Controller;
import input.Message;
import networking.Peer;
import client.*;
import model.*;
import view.*;

public class GameController extends Controller {
	//***************************************************
	//------------------ATTRIBUTES-----------------------
	//***************************************************
	private GameView view;
	
	//***************************************************
	//------------------CONSTRUCTORS---------------------
	//***************************************************
	public GameController(ClientDatabase database) {
		super(database);
		this.view = new GameView();
	}

	//***************************************************
	//------------------PUBLIC METHODS-------------------
	//***************************************************
	@Override
	public void forward(Peer peer, Message msg) {
		switch(msg.getCommand().toLowerCase()) {
		case "start":
			if(msg.getArgs().size() < Game.MIN_PLAYERS) return;
			this.start(msg.getArgs());
			break;
		case "drawntile":
			if(msg.getArgs().size() < 2) return;
			this.drawnTile(msg.getArgs().get(0), msg.getArgs().get(1));
			break;
		case "placedtile":
			if(msg.getArgs().size() < 3) return;
			this.placedTile(msg.getArgs().get(0), msg.getArgs().get(1), msg.getArgs().get(2));
			break;
		case "switchedtile":
			if(msg.getArgs().size() < 3) return;
			this.switchedTile(msg.getArgs().get(0), msg.getArgs().get(1), msg.getArgs().get(2));
			break;
		case "skippedmove":
			if(msg.getArgs().size() < 1) return;
			this.skippedMove(msg.getArgs().get(0));
			break;
		case "requestmove":
			this.requestMove();
			break;
		case "rotate":
			this.rotate(msg.getArgs().get(0));
			break;
		case "players":
			this.players(msg.getArgs());
			break;
		case "end":
			this.end();
			break;
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
	
	public void start(List<String> args) {
		ClientDatabase database = (ClientDatabase)this.getDatabase();
		
		List<Player> players = new ArrayList<Player>();
		
		for(String nickname: args) {
			if(nickname.equals(database.getPlayer().getNickname())) {
				players.add(database.getPlayer());
			}
			else {
				players.add(new HumanPlayer(nickname));
			}
		}
		
		Game game = new Game(players, null);
		database.setGame(game);
		this.view.setGame(game);
		this.view.draw(true);
	}
	
	public void drawnTile(String nickname, String tileStr) {
		ClientDatabase database = (ClientDatabase)this.getDatabase();
		List<Player> players = database.getGame().getPlayers();
		
		for(Player player: players) {
			if(nickname.equals(player.getNickname())) {
				player.drawTile(tileStr);
			}
		}
		
		this.view.draw(true);
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
		this.view.draw(true);
	}
	
	public void switchedTile(String nickname, String oldTileStr, String newTileStr) {
		ClientDatabase database = (ClientDatabase)this.getDatabase();
		Game game = database.getGame();
		Player player = null;
		
		for(Player p: game.getPlayers()) {
			if(nickname.equals(p.getNickname())) {
				player = p;
				break;
			}
		}
		
		if(player == null) {
			return;
		}
		
		player.switchTile(oldTileStr, newTileStr);
		this.view.draw(true);
	}
	
	public void requestMove() {
		this.serverMessage("It's your turn. Enter move.");
	}
	
	public void skippedMove(String nickname) {
		ClientDatabase database = (ClientDatabase)this.getDatabase();
		Game game = database.getGame();
		Player player = null;
		
		for(Player p: game.getPlayers()) {
			if(nickname.equals(p.getNickname())) {
				player = p;
				break;
			}
		}
		
		if(player == null) {
			return;
		}
		
		player.skipMove();
		this.view.draw(true);
	}
	
	public void end() {
		this.serverMessage("Game over!");
	}
	
	public void players(List<String> args) {
		ClientDatabase database = (ClientDatabase)this.getDatabase();
		Game game = database.getGame();
		
		for(Player player: game.getPlayers()) {
			if(!args.contains(player.getNickname())) {
				player.leaveGame();
			}
		}
	}
	
	public void rotate(String tileStr) {
		ClientDatabase database = (ClientDatabase)this.getDatabase();
		Player player = database.getPlayer();
		
		for(Tile tile: player.getTiles()) {
			if(tile.isEquivalent(tileStr)) {
				tile.rotate();
			}
		}
		this.view.draw(false);
	}

}
