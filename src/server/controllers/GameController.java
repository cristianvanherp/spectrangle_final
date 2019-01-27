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
		default:
			break;
		}
	}
	
	//***************************************************
	//---------------------PRIVATE METHODS---------------
	//***************************************************
	private void placeTile(Peer peer, String indexStr, String tileStr) {
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
			player.getGame().moveMade(player);
			break;
		}
	}

}
