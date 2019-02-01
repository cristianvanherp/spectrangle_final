package test;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;
import enums.*;
import model.*;
import java.util.*;
public class GameTest {
	
	@Test
	public void testInitialConditions() {
		HumanPlayer player=new HumanPlayer("RandomName");
		HumanPlayer host=new HumanPlayer("Host");
		List<Player>jucatori=new ArrayList<Player>();
		jucatori.add(player);
		Game game=new Game(jucatori,host);
		
		
		assertTrue(game.getBag()!=null);
		assertTrue(game.getBoard()!=null);
		assertTrue(game.getTurn()==player);
		
	}
	
	@Test
	public void testGetPlayerStr() {
		HumanPlayer player1=new HumanPlayer("RandomName");
		HumanPlayer player2=new HumanPlayer("RandomName2");
		HumanPlayer host=new HumanPlayer("Host");
		List<Player>jucatori=new ArrayList<Player>();
		jucatori.add(player1);
		jucatori.add(player2);
		Game game=new Game(jucatori,host);
		assertTrue("RandomName RandomName2".equals(game.getPlayersStr()));
		
	}
	
	@Test
	public void testNextPlayer() {
		HumanPlayer player1=new HumanPlayer("RandomName");
		HumanPlayer player2=new HumanPlayer("RandomName2");
		HumanPlayer host=new HumanPlayer("Host");
		List<Player>jucatori=new ArrayList<Player>();
		jucatori.add(player1);
		jucatori.add(player2);
		
		Game game=new Game(jucatori,host);
		
		assertTrue(player2.equals(game.nextPlayer()));
		game.setTurn(game.nextPlayer());
		assertTrue(player1.equals(game.nextPlayer()));
			
	}
	
	@Test
	public void testPlaceTile() {
		
		HumanPlayer player=new HumanPlayer("name");
		HumanPlayer host=new HumanPlayer("Host");
		List<Player>jucatori=new ArrayList<Player>();
		jucatori.add(player);
		Game game=new Game(jucatori,host);
		
		Tile tile=new Tile(Orientation.UP,'R','R','R',6);
		
		game.placeTile(player, 0, tile);
		
		assertTrue(game.getBoard().getSlotOfCoord(0, 0).getTile().equals(tile));
		 
		 
	}
	
	@Test
	public void testLeaveGame() {
		
		
		HumanPlayer player=new HumanPlayer("RandomName");
		HumanPlayer player2=new HumanPlayer("RandomName2");
		HumanPlayer player3=new HumanPlayer("RandomName3");
		HumanPlayer host=new HumanPlayer("Host");
		List<Player>jucatori=new ArrayList<Player>();
		jucatori.add(player);
		jucatori.add(player2);
		jucatori.add(player3);
		Game game=new Game(jucatori,host);
		
		game.leaveGame(player);
		assertEquals(2,game.getPlayers().size());

	}
	
	@Test
	public void testCanMakeMove() {
		HumanPlayer player=new HumanPlayer("name");
		HumanPlayer host=new HumanPlayer("Host");
		List<Player>jucatori=new ArrayList<Player>();
		jucatori.add(player);
		Game game=new Game(jucatori,host);
		
		Tile tile=new Tile(Orientation.UP,'R','R','R',6);
		List<Tile>playersTile=new ArrayList<Tile>();
		
		playersTile.add(tile);
		player.setTiles(playersTile);
		
		assertTrue(game.canMakeMove(player));
		
		
	}
	
	@Test
	public void testSkipMove() {
		HumanPlayer player=new HumanPlayer("RandomName");
		HumanPlayer player2=new HumanPlayer("RandomName2");
		HumanPlayer player3=new HumanPlayer("RandomName3");
		HumanPlayer host=new HumanPlayer("Host");
		List<Player>jucatori=new ArrayList<Player>();
		jucatori.add(player);
		jucatori.add(player2);
		jucatori.add(player3);
		Game game=new Game(jucatori,host);
		
		game.setTurn(player);
		assertTrue(player2.equals(game.nextPlayer()));
		
		game.setTurn(player3);
		assertTrue(player.equals(game.nextPlayer()));
	}

}
