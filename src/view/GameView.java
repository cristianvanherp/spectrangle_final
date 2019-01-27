package view;

import model.*;

public class GameView {
	Game game;
	
	public GameView() {
		
	}
	
	public void draw() {
		this.clear();
		
		Board board = this.game.getBoard();
		System.out.println(board.toString());
		System.out.println("\n");
		
		for(Player player: this.game.getPlayers()) {
			System.out.print(player.getNickname() + " -> Points: " + 0 + ", Tiles: ");
			for(Tile tile: player.getTiles()) {
				System.out.print(tile.toString() + " ");
			}
			System.out.println("\n");
		}
		
		
		System.out.print("> ");
	}
	
	private void clear() {  
	    for(int i = 0 ; i < 100 ; i++) {
	    	System.out.print("\n");
	    }
	} 

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}
	
}
