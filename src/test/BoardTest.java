package test;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import enums.*;
import model.*;

public class BoardTest {

	@Test
	public void testCoordToIndex() {
		// creating a new board
		Board board = new Board();

		// testing correct mathematical behaviour
		assertEquals(board.coordToIndex(0, 0), 0);
		assertEquals(board.coordToIndex(1, -1), 1);
		assertEquals(board.coordToIndex(5, -4), 26);
		assertEquals(board.coordToIndex(5, 1), 31);
		assertEquals(board.coordToIndex(2, 0), 6);
		assertEquals(board.coordToIndex(5, 5), 35);
	}

	@Test
	public void testIndexToCoord() {
		// creating a new board
		Board board = new Board();

		// testing correct mathematical behaviour
		assertEquals(board.columnOfIndex(0), 0);
		assertEquals(board.rowOfIndex(0), 0);

		assertEquals(board.columnOfIndex(1), -1);
		assertEquals(board.rowOfIndex(1), 1);

		assertEquals(board.columnOfIndex(26), -4);
		assertEquals(board.rowOfIndex(26), 5);

		assertEquals(board.columnOfIndex(31), 1);
		assertEquals(board.rowOfIndex(31), 5);

		assertEquals(board.columnOfIndex(6), 0);
		assertEquals(board.rowOfIndex(6), 2);

		assertEquals(board.columnOfIndex(35), 5);
		assertEquals(board.rowOfIndex(35), 5);
	}

	@Test
	public void testEmptySlots() {
		// creating a new board
		Board board = new Board();

		// checking if there is any occupied slot
		boolean ok = true;
		for (Slot s : board.getSlots()) {
			if (s.getTile() != null) {
				ok = false;
			}
		}
		// expected to be true
		assertTrue(ok);
	}

	@Test
	public void testSlotOfCoord() {
		// creating a new board
		Board board = new Board();

		// testing correct mathematical behaviour
		assertTrue(board.getSlots().get(0).equals(board.getSlotOfCoord(0, 0)));
		assertTrue(board.getSlots().get(1).equals(board.getSlotOfCoord(1, -1)));
		assertTrue(board.getSlots().get(26).equals(board.getSlotOfCoord(5, -4)));
		assertTrue(board.getSlots().get(31).equals(board.getSlotOfCoord(5, 1)));

	}

	@Test
	public void testNeighbours() {
		// creating a new board
		Board board = new Board();

		// testing if the neighbours are correct;
		assertTrue(board.getSlots().get(12).getLeft().equals(board.getSlots().get(11)));
		assertTrue(board.getSlots().get(23).getLeft().equals(board.getSlots().get(22)));
		assertTrue(board.getSlots().get(34).getLeft().equals(board.getSlots().get(33)));

		assertTrue(board.getSlots().get(10).getRight().equals(board.getSlots().get(11)));
		assertTrue(board.getSlots().get(22).getRight().equals(board.getSlots().get(23)));
		assertTrue(board.getSlots().get(25).getRight().equals(board.getSlots().get(26)));

		assertTrue(board.getSlots().get(28).getVertical().equals(board.getSlots().get(18)));
		assertTrue(board.getSlots().get(3).getVertical().equals(board.getSlots().get(7)));
		assertTrue(board.getSlots().get(4).getVertical().equals(board.getSlots().get(10)));

	}

	@Test
	public void testPlaceTile() {
		// creating a new board
		Board board = new Board();

		// creating a tile
		Tile tile = new Tile(Orientation.UP, 'R', 'R', 'R', 6);

		// test correct behaviour
		board.placeTile(tile, 0);
		assertTrue(board.getSlotOfCoord(0, 0).getTile().equals(tile));

		// test correct behaviour
		assertEquals(board.placeTile(tile, 50), -1);
	}

	@Test
	public void testCanBePlaced() {
		// creating a new board
		Board board = new Board();

		// creating a new tile
		Tile tile = new Tile(Orientation.UP, 'R', 'R', 'R', 6);

		// placing the tiles in such a way that that they leave an empty slot with two
		// red neighbours
		board.placeTile(tile, 2);
		board.placeTile(tile, 1);
		board.placeTile(tile, 5);
		board.placeTile(tile, 6);
		board.placeTile(tile, 7);

		// testing correct behaviour
		assertEquals(board.canBePlaced(board.getSlotOfCoord(1, 1), tile), 1);

		Tile tile5 = new Tile(Orientation.UP, 'G', 'G', 'G', 6);
		board.placeTile(tile5, 3);
		assertEquals(board.canBePlaced(board.getSlotOfCoord(1, 1), tile5), 0);

	}

}