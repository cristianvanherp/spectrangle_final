package server.controllers;

import java.util.List;

import abstract_classes.Controller;
import input.*;
import networking.Peer;
import server.ServerDatabase;
import model.*;

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
		switch(msg.getCommand()) {
		case "placeTile":
			this.placeTile(peer, msg.getArgs().get(0), msg.getArgs().get(1));
			break;
		case "switchTile":
			this.switchTile(peer, msg.getArgs().get(0));
			break;
		case "skipMove":
			this.skipMove(peer);
			break;
		case "leave":
			this.leave(peer);
			break;
		default:
			break;
		}
	}
	
	//***************************************************
	//---------------------PRIVATE METHODS---------------
	//***************************************************
	public void placeTile(Peer peer, String indexStr, String tileStr) {
		Integer index;

		try {
			index = Integer.parseInt(indexStr);
		} catch(NumberFormatException e) {
			peer.write("404 Invalid index. Try again.");
			return;
		}
		
		ServerDatabase database = (ServerDatabase)this.getDatabase();
		Player player = database.getPlayer(peer);
		int status = player.placeTile(index, tileStr);
		
		switch(status) {
		case 403:
			peer.write("403 It's not your turn.");
			break;
		case 404:
			peer.write("404 You don't have that tile or the index is not valid. Try again.");
			break;
		default:
			Messenger.broadcast(player.getGame().getPlayers(), "placedTile " + player.getNickname() + " " + index + " " + tileStr);
			break;
		}
	}
	
	public void switchTile(Peer peer, String tileStr) {
		ServerDatabase database = (ServerDatabase)this.getDatabase();
		Player player = database.getPlayer(peer);
		
		int status = player.switchTile(tileStr);
	
		switch(status) {
		case 403:
			peer.write("403 It's either not your turn or you can place a tile.");
			break;
		case 404:
			peer.write("404 You don't have that tile or it can be played. Try again.");
			break;
		default:
			Messenger.broadcast(player.getGame().getPlayers(), "switchedTile " + player.getNickname() + " " + tileStr + " " + player.lastTile().toString());
			break;
		}
	}
	
	public void skipMove(Peer peer) {
		ServerDatabase database = (ServerDatabase)this.getDatabase();
		Player player = database.getPlayer(peer);
	
		int status = player.skipMove();
		
		switch(status) {
		case 403:
			peer.write("403 It's either not your turn or you can place a tile.");
			break;
		default:
			Messenger.broadcast(player.getGame().getPlayers(), "skippedMove " + player.getNickname());
			break;
		}
	}
	
	public void leave(Peer peer) {
		ServerDatabase database = (ServerDatabase)this.getDatabase();
		Player player = database.getPlayer(peer);
		Game game = player.getGame();
		
		if(game == null) {
			peer.write("403 You're not currently in a game.");
		}
		
		player.leaveGame();
		Messenger.broadcast(game.getPlayers(), "players " + game.getPlayersStr());
		
	}

}
