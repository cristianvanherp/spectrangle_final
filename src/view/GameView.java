package view;

import model.*;

public class GameView {
	// ***************************************************
	// ---------------------ATTRIBUTES--------------------
	// ***************************************************
	Game game;

	// ***************************************************
	// ---------------------CONSTRUCTORS------------------
	// ***************************************************
	public GameView() {

	}

	// ***************************************************
	// ---------------------PUBLIC METHODS----------------
	// ***************************************************
	public void draw(boolean showEntry) {
		this.clear();

		Board board = this.game.getBoard();
		System.out.println(board.toString());
		System.out.println("\n");

		for (Player player : this.game.getPlayers()) {
			System.out.println(player.getNickname() + " - Score: " + player.getScore());
			Tile.draw(player.getTiles());
		}

		if (showEntry) {
			System.out.print("> ");
		}
	}

	// ***************************************************
	// ---------------------PRIVATE METHODS---------------
	// ***************************************************
	private void clear() {
		for (int i = 0; i < 100; i++) {
			System.out.print("\n");
		}
	}

	// ***************************************************
	// ---------------------GETTERS/SETTERS---------------
	// ***************************************************
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

}
