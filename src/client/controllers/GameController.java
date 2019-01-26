package client.controllers;

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
		case "colour":
			this.colour(msg.getArgs().get(0), msg.getArgs().get(1));
			break;
		case "drawnTile":
			this.drawnTile(msg.getArgs().get(0), msg.getArgs().get(1));
			break;
		default:
			break;
		}
	}
	
	//***************************************************
	//------------------PRIVATE METHODS------------------
	//***************************************************
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
	}
	
	public void colour(String nickname, String colour) {
		ClientDatabase database = (ClientDatabase)this.getDatabase();
		List<Player> players = database.getGame().getPlayers();
		
		for(Player player: players) {
			if(nickname.equals(player.getNickname())) {
				player.getScoreBoard().setColor(colour);
			}
		}
	}
	
	public void drawnTile(String nickname, String tile) {
		ClientDatabase database = (ClientDatabase)this.getDatabase();
		List<Player> players = database.getGame().getPlayers();
		
		for(Player player: players) {
			if(nickname.equals(player.getNickname())) {
				player.drawTile(tile);
			}
		}
	}

}
