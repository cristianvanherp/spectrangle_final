package test;

import enums.Orientation;
import model.*;

public class Main {
	
	public static void main(String[] args) {
		Tile tile = new Tile(Orientation.UP, 'L', 'R', 'V', 6);
		Tile impostor = new Tile(Orientation.UP, 'L', 'R', 'V', 6);
	
		for(int i = 0 ; i < 6 ; i++) {
			impostor.rotate();
			System.out.println(tile.isEquivalent(impostor.toString()));
		}
	
	}
}
