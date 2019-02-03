package model;

import java.util.*;

public class Lobby {
	// ***************************************************
	// ------------------RELATIONSHIPS--------------------
	// ***************************************************
	private List<Player> players;
	private Player host;
	private Game game;

	// ***************************************************
	// ------------------CONSTRUCTORS---------------------
	// ***************************************************
	public Lobby(Player player) {
		if (player == null) {
			// Throw an exception
		}

		this.players = new ArrayList<Player>();
		this.host = player;
		this.addPlayer(player);
	}

	// ***************************************************
	// ------------------PUBLIC METHODS-------------------
	// ***************************************************

	public Game start() {

		if (this.players.size() < 2) {
			// Throw an exception
		}

//		this.game = new Game(players);
//		this.game.setLobby(this);
//		
		return this.game;
	}

	public boolean addPlayer(Player player) {
		if (player == null) {
			return false;
		}

		player.setLobby(this);
		this.players.add(player);

		return true;
	}

	public void removePlayer(Player player) {
		this.players.remove(player);
	}

	public boolean available() {
		return this.players.size() < 4 && this.game == null;
	}

	// ***************************************************
	// ------------------GETTERS/SETTERS------------------
	// ***************************************************
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

}
