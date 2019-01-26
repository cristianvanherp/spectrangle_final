package server.controllers;

import java.util.List;

import abstract_classes.Controller;
import input.*;
import server.*;
import networking.*;
import model.*;

public class PlayerController extends Controller {
	//***************************************************
	//---------------------CONSTRUCTORS------------------
	//***************************************************
	public PlayerController(ServerDatabase database) {
		super(database);
	}

	//***************************************************
	//---------------------PUBLIC METHODS----------------
	//***************************************************
	@Override
	public void forward(Peer peer, Message msg) {
		switch(msg.getCommand()) {
		case "nickname":
			this.nickname(peer, msg.getStringArgs());
			break;
		case "colour":
			this.colour(peer, msg.getStringArgs());
			break;
		default:
			break;
		}
	}
	
	//***************************************************
	//---------------------CLIENT COMMANDS---------------
	//***************************************************
	public void nickname(Peer peer, String nickname) {
		Player player = ((ServerDatabase)this.getDatabase()).getPlayer(peer);
		ServerDatabase database = (ServerDatabase)this.getDatabase();
		
		if(player == null) {
			return;
		}
		
		if(player.getGame() != null) {
			peer.write("403 You're not allowed to change your nickname during the game.");
			return;
		}
		
		for(Player p: database.getPlayers()) {
			if(nickname.equals(p.getNickname())) {
				peer.write("403 That nickname has already been chosen. Pick another one");
				return;
			}
		}
		
		player.setNickname(nickname);
		peer.write("nicknamed " + nickname);
	}
	
	public void colour(Peer peer, String colour) {
		ServerDatabase database = (ServerDatabase)this.getDatabase();
		Player player = database.getPlayer(peer);
		List<Player> players = player.getGame().getPlayers();
		
		for(Player p: players) {
			if(colour.equals(p.getScoreBoard().getColor())) {
				peer.write("403 This colour has already been chosen. Pick another one");
				return;
			}
		}
		
		player.getScoreBoard().setColor(colour);
	}

}
