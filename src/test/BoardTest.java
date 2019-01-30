package test;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import enums.*;
import model.*;
public class BoardTest {
	
	
	@Test
	public void testCoordToIndex() {
		Board board=new Board();
		assertEquals(board.coordToIndex(0, 0),0);
		assertEquals(board.coordToIndex(1, -1),1);
		assertEquals(board.coordToIndex(5, -4),26);
		assertEquals(board.coordToIndex(5, 1),31);
		assertEquals(board.coordToIndex(2, 0),6);
		assertEquals(board.coordToIndex(5, 5),35);
	}
	
	@Test
	public void testIndexToCoord() {
		Board board=new Board();
		assertEquals(board.columnOfIndex(0),0);
		assertEquals(board.rowOfIndex(0),0);
		
		assertEquals(board.columnOfIndex(1),-1);
		assertEquals(board.rowOfIndex(1),1);
		
		
		assertEquals(board.columnOfIndex(26),-4);
		assertEquals(board.rowOfIndex(26),5);
		
		assertEquals(board.columnOfIndex(31),1);
		assertEquals(board.rowOfIndex(31),5);
		
		assertEquals(board.columnOfIndex(6),0);
		assertEquals(board.rowOfIndex(6),2);
		
		assertEquals(board.columnOfIndex(35),5);
		assertEquals(board.rowOfIndex(35),5);
	}
	
	@Test
	public void testEmptySlots() {
		Board board=new Board();
		boolean ok=true;
		for(Slot s : board.getSlots())
			if(s.getTile()!=null)
				ok=false;
		assertTrue(ok);
	}
	@Test
	public void testSlotOfCoord() {
		Board board=new Board();
		assertTrue(board.getSlots().get(0).equalsIgnoreCase(board.getSlotOfCoord(0, 0)));
		assertTrue(board.getSlots().get(1).equalsIgnoreCase(board.getSlotOfCoord(1, -1)));
		assertTrue(board.getSlots().get(26).equalsIgnoreCase(board.getSlotOfCoord(5, -4)));
		assertTrue(board.getSlots().get(31).equalsIgnoreCase(board.getSlotOfCoord(5, 1)));
		
		
	}
	
	@Test
	public void testNeighbours() {
		Board board=new Board();
		assertTrue( board.getSlots().get(12).getLeft().equalsIgnoreCase(board.getSlots().get(11)) );
		assertTrue( board.getSlots().get(23).getLeft().equalsIgnoreCase(board.getSlots().get(22)) );
		assertTrue( board.getSlots().get(34).getLeft().equalsIgnoreCase(board.getSlots().get(33)) );
		
				
    	assertTrue( board.getSlots().get(10).getRight().equalsIgnoreCase(board.getSlots().get(11)) );
		assertTrue( board.getSlots().get(22).getRight().equalsIgnoreCase(board.getSlots().get(23)) );
		assertTrue( board.getSlots().get(25).getRight().equalsIgnoreCase(board.getSlots().get(26)) );

		assertTrue( board.getSlots().get(28).getVertical().equalsIgnoreCase(board.getSlots().get(18)) );
		assertTrue( board.getSlots().get(3).getVertical().equalsIgnoreCase(board.getSlots().get(7)) );
		assertTrue( board.getSlots().get(4).getVertical().equalsIgnoreCase(board.getSlots().get(10)) );
	   
	}
	
	@Test
	public void testPlaceTile() {
		Board board=new Board();
	    Tile tile=new Tile(Orientation.UP,'R','R','R',6);
		
	    board.placeTile(tile,0);
		assertTrue(board.getSlotOfCoord(0,0).getTile().equalsIgnoreCase(tile));
		
		
		assertEquals(board.placeTile(tile, 50),-1);
	}
	
	
	
	@Test
	public void testCanBePlaced() {
		Board board=new Board();
	    Tile tile=new Tile(Orientation.UP,'R','R','R',6);
	    
	    board.placeTile(tile, 1);
	    board.placeTile(tile, 0);
	    assertEquals(board.canBePlaced(board.getSlotOfCoord(1, 0), tile),2);
	    
	    Tile tile2= new Tile(Orientation.UP,'G', 'G','G',6);
	    board.placeTile(tile2, 3);
	    assertEquals(board.canBePlaced(board.getSlotOfCoord(1, 0), tile2),0);

	    
	    
	    
	    
	}
	
}