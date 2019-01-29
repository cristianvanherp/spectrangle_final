package test;

import org.junit.*;
import enums.*;
import model.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class TileTest {

	private Tile tile1, tile2, tile3, tile4, tile5, tile6, tile7;

	@Before
	public void setUp() {
		tile1 = new Tile(Orientation.UP, 'A', 'B', 'C', 3);
		tile2 = new Tile(Orientation.UP, 'A', 'B', 'C', 3);
		
		tile3 = new Tile(Orientation.UP, 'A', 'C', 'B', 3);
		tile4 = new Tile(Orientation.UP, 'C', 'B', 'A', 3);
	}
	
	@Test
	public void rotateTest() {
		tile1.rotate();
		assertEquals("ABC3", tile1.toString());
		assertEquals(Orientation.DOWN, tile1.getOrientation());
		tile1.rotate();
		assertEquals("ABC3", tile1.toString());
		assertEquals(Orientation.UP, tile1.getOrientation());
		tile1.rotate();
		assertEquals("CAB3", tile1.toString());
		assertEquals(Orientation.DOWN, tile1.getOrientation());
		tile1.rotate();
		assertEquals("CAB3", tile1.toString());
		assertEquals(Orientation.UP, tile1.getOrientation());
		tile1.rotate();
		assertEquals("BCA3", tile1.toString());
		assertEquals(Orientation.DOWN, tile1.getOrientation());
		tile1.rotate();
		assertEquals("BCA3", tile1.toString());
		assertEquals(Orientation.UP, tile1.getOrientation());
	}
	
	@Test
	public void isEquivalentTest() {

		for(int i = 0 ; i < 6 ; i++) {
			tile1.rotate();
			assertTrue(tile2.isEquivalent(tile1.toString()));
		}
		
		for(int i = 0 ; i < 6 ; i++) {
			tile1.rotate();
			assertFalse(tile3.isEquivalent(tile1.toString()));
		}
		
		for(int i = 0 ; i < 6 ; i++) {
			tile1.rotate();
			assertFalse(tile4.isEquivalent(tile1.toString()));
		}
		
	}
	
}