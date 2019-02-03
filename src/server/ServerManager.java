package server;

import java.util.*;

import exceptions.NotEnoughPlayers;
import model.*;

public class ServerManager implements Runnable {
	// ***************************************************
	// ---------------------ATTRIBUTES--------------------
	// ***************************************************
	private ServerDatabase database;

	// ***************************************************
	// ---------------------CONSTRUCTORS------------------
	// ***************************************************
	public ServerManager(ServerDatabase database) {
		this.database = database;
	}

	// ***************************************************
	// ---------------------THREAD------------------------
	// ***************************************************
	@Override
	public void run() {
		while (true) {

			this.createGames();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println("Error in server manager");
			}
		}
	}

	// ***************************************************
	// ---------------------PRIVATE METHODS---------------
	// ***************************************************
	private void createGames() {
		List<Player> players = this.database.getIdlePlayers();

		if (players.size() >= Game.MIN_PLAYERS) {
			Player host = players.get(0);
			Game game = null;
			
			try {
				game = new Game(players, host);
			} catch (NotEnoughPlayers e) {
				e.printStackTrace();
				return;
			}

			game.start();
			this.database.insertGame(game);
		}
	}

}
