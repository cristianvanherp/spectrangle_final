package test;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import enums.*;
import model.*;
import java.util.*;

public class GameTest {

	@Test
	public void testInitialConditions() {
		// initialising a new game with only one player
		HumanPlayer player = new HumanPlayer("RandomName");
		HumanPlayer host = new HumanPlayer("Host");
		List<Player> jucatori = new ArrayList<Player>();
		jucatori.add(player);
		Game game = new Game(jucatori, host);

		// testing if the constructor does its job
		assertTrue(game.getBag() != null);
		assertTrue(game.getBoard() != null);
		assertTrue(game.getTurn() == player);

	}

	@Test
	public void testGetPlayerStr() {
		// initialising a game with two players
		HumanPlayer player1 = new HumanPlayer("RandomName");
		HumanPlayer player2 = new HumanPlayer("RandomName2");
		HumanPlayer host = new HumanPlayer("Host");
		List<Player> jucatori = new ArrayList<Player>();
		jucatori.add(player1);
		jucatori.add(player2);
		Game game = new Game(jucatori, host);

		// checking if the expected string matches the one that the method generates
		assertTrue("RandomName RandomName2".equals(game.getPlayersStr()));

	}

	@Test
	public void testPlaceTile() {
		// initialising a game
		HumanPlayer player = new HumanPlayer("name");
		HumanPlayer host = new HumanPlayer("Host");
		List<Player> jucatori = new ArrayList<Player>();
		jucatori.add(player);
		Game game = new Game(jucatori, host);

		// initialising a tile
		Tile tile = new Tile(Orientation.UP, 'R', 'R', 'R', 6);

		// placing the tile on the slot with the index 0
		game.placeTile(player, 0, tile);

		// checking for the correct behaviour
		assertTrue(game.getBoard().getSlotOfCoord(0, 0).getTile().equals(tile));

	}

	@Test
	public void testLeaveGame() {
		// initialising players
		HumanPlayer player = new HumanPlayer("RandomName");
		HumanPlayer player2 = new HumanPlayer("RandomName2");
		HumanPlayer player3 = new HumanPlayer("RandomName3");
		HumanPlayer host = new HumanPlayer("Host");
		List<Player> jucatori = new ArrayList<Player>();
		jucatori.add(player);
		jucatori.add(player2);
		jucatori.add(player3);
		// initialising a game with the players
		Game game = new Game(jucatori, host);
		// testing the correct behaviour
		game.leaveGame(player);
		assertEquals(2, game.getPlayers().size());

	}

	@Test
	public void testCanMakeMove() {
		// initialising a new game
		HumanPlayer player = new HumanPlayer("name");
		HumanPlayer host = new HumanPlayer("Host");
		List<Player> jucatori = new ArrayList<Player>();
		jucatori.add(player);
		Game game = new Game(jucatori, host);

		// initialising a new tile
		Tile tile = new Tile(Orientation.UP, 'R', 'R', 'R', 6);

		// creating a list of tiles
		List<Tile> playersTile = new ArrayList<Tile>();

		// adding the created tile to the list
		playersTile.add(tile);

		// assigning the tiles to the player
		player.setTiles(playersTile);

		// testing correct behaviour
		assertTrue(game.canMakeMove(player));

	}

	@Test
	public void testNextPlayer() {
		// creating 3 different players
		HumanPlayer player = new HumanPlayer("RandomName");
		HumanPlayer player2 = new HumanPlayer("RandomName2");
		HumanPlayer player3 = new HumanPlayer("RandomName3");
		HumanPlayer host = new HumanPlayer("Host");
		List<Player> jucatori = new ArrayList<Player>();
		jucatori.add(player);
		jucatori.add(player2);
		jucatori.add(player3);

		// creating a game with the players
		Game game = new Game(jucatori, host);

		// testing correct behaviour
		game.setTurn(player);
		assertTrue(player2.equals(game.nextPlayer()));

		game.setTurn(player3);
		assertTrue(player.equals(game.nextPlayer()));
	}

	@Test
	public void testSwitchTile() {
		// creating a new game
		HumanPlayer player = new HumanPlayer("name");
		HumanPlayer host = new HumanPlayer("Host");
		List<Player> jucatori = new ArrayList<Player>();
		jucatori.add(player);
		Game game = new Game(jucatori, host);

		// creating 2 different tiles
		Tile tile = new Tile(Orientation.UP, 'R', 'R', 'R', 6);
		Tile tile2 = new Tile(Orientation.UP, 'G', 'G', 'G', 6);

		// creating 2 lists of tiles
		List<Tile> playersTile = new ArrayList<Tile>();
		List<Tile> playersTileCopy = new ArrayList<Tile>();

		// adding the red tile to both of them
		playersTile.add(tile);
		playersTileCopy.add(tile);

		// assigning the first one as the player's tiles
		player.setTiles(playersTile);

		// putting the green tile on the index 0
		game.getBoard().getSlots().get(0).setTile(tile2);

		// testing correct behaviour
		game.switchTile(player, tile);
		assertFalse(player.getTiles().equals(playersTileCopy));

	}

	@Test
	public void testSwitchTile2() {

		// creating a new game
		HumanPlayer player = new HumanPlayer("name");
		HumanPlayer host = new HumanPlayer("Host");
		List<Player> jucatori = new ArrayList<Player>();
		jucatori.add(player);
		Game game = new Game(jucatori, host);

		// creating 2 different tiles
		Tile tile = new Tile(Orientation.UP, 'R', 'R', 'R', 6);
		Tile tile2 = new Tile(Orientation.UP, 'G', 'G', 'G', 6);

		// creating 2 lists of tiles
		List<Tile> playersTile = new ArrayList<Tile>();
		List<Tile> playersTileCopy = new ArrayList<Tile>();

		// adding the red tile to both of them
		playersTile.add(tile);
		playersTileCopy.add(tile);

		// assigning the first one as the player's tiles
		player.setTiles(playersTile);

		// putting the green tile on the index 0
		game.getBoard().getSlots().get(0).setTile(tile2);

		// testing correct behaviour
		game.switchTile(player, tile, "PPP6");
		assertFalse(player.getTiles().equals(playersTileCopy));

	}

	@Test
	public void testSkipMove() {
		// creating 3 different players
		HumanPlayer player = new HumanPlayer("RandomName");
		HumanPlayer player2 = new HumanPlayer("RandomName2");
		HumanPlayer player3 = new HumanPlayer("RandomName3");
		HumanPlayer host = new HumanPlayer("Host");
		List<Player> jucatori = new ArrayList<Player>();
		jucatori.add(player);
		jucatori.add(player2);
		jucatori.add(player3);

		// creating a new game with the players
		Game game = new Game(jucatori, host);

		// creating 2 different tiles
		Tile tile = new Tile(Orientation.UP, 'R', 'R', 'R', 6);
		Tile tile2 = new Tile(Orientation.UP, 'G', 'G', 'G', 6);

		// giving the red tile to the first player
		List<Tile> playersTile = new ArrayList<Tile>();
		playersTile.add(tile);
		player.setTiles(playersTile);

		// giving the turn to first player
		game.setTurn(player);

		// testing correct behaviour
		assertEquals(game.skipMove(player), 403);

		// placing the green tile on the board
		game.getBoard().getSlots().get(0).setTile(tile2);

		// testing for correct behaviour
		game.skipMove(player);
		assertTrue(game.getTurn().equals(player2));

	}

}
