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
		switch(msg.getCommand().toLowerCase()) {
		case "nickname":
			if(msg.getArgs().size() < 1) return;
			this.nickname(peer, msg.getStringArgs());
			break;
		case "features":
			this.features(peer, msg.getArgs());
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
			if(nickname.equalsIgnoreCase(p.getNickname())) {
				peer.write("403 That nickname has already been chosen. Pick another one");
				return;
			}
		}
		
		player.setNickname(nickname);
		peer.write("200 " + "Waiting for more players...");
	}
	
	public void features(Peer peer, List<String> args) {
	
	}

}
