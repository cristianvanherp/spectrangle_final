package model;

import java.util.*;

import enums.Orientation;

public class Bag {
	//***************************************************
	//------------------RELATIONSHIPS---------------------
	//***************************************************
	private List<Tile> tiles;
	
	//***************************************************
	//------------------CONSTRUCTORS---------------------
	//***************************************************
	public Bag() {
		this.tiles = new ArrayList<Tile>();
		this.init();
	}
	
	//***************************************************
	//------------------PUBLIC METHODS---------------------
	//***************************************************
	public Tile drawTile() {
		int tilesLength, index;
		
		tilesLength = this.tiles.size();
		if(tilesLength == 0) {
			return null;
		}
		
		index = new Random().nextInt(this.tiles.size());
		return this.tiles.remove(index);
	}
	
	public Tile drawTile(String tile) {
		for(Tile t: this.tiles) {
			if(t.toString().equalsIgnoreCase(tile)) {
				this.tiles.remove(t);
				return t;
			}
		}
		
		return null;
	}
	
	
	//***************************************************
	//------------------PRIVATE METHODS-------------------
	//***************************************************
	private void init() {
		//6 POINTS
		this.tiles.add(new Tile(Orientation.UP, 'R', 'R', 'R', 6));
		this.tiles.add(new Tile(Orientation.UP, 'G', 'G', 'G', 6));
		this.tiles.add(new Tile(Orientation.UP, 'B', 'B', 'B', 6));
		this.tiles.add(new Tile(Orientation.UP, 'Y', 'Y', 'Y', 6));
		this.tiles.add(new Tile(Orientation.UP, 'P', 'P', 'P', 6));
		
		//5 POINTS
		this.tiles.add(new Tile(Orientation.UP, 'R', 'R', 'Y', 5));
		this.tiles.add(new Tile(Orientation.UP, 'R', 'R', 'P', 5));
		this.tiles.add(new Tile(Orientation.UP, 'B', 'B', 'R', 5));
		this.tiles.add(new Tile(Orientation.UP, 'B', 'B', 'P', 5));
		this.tiles.add(new Tile(Orientation.UP, 'G', 'G', 'R', 5));
		this.tiles.add(new Tile(Orientation.UP, 'G', 'G', 'B', 5));
		this.tiles.add(new Tile(Orientation.UP, 'Y', 'Y', 'G', 5));
		this.tiles.add(new Tile(Orientation.UP, 'Y', 'Y', 'B', 5));
		this.tiles.add(new Tile(Orientation.UP, 'P', 'P', 'Y', 5));
		this.tiles.add(new Tile(Orientation.UP, 'P', 'P', 'G', 5));
		
		//4 POINTS
		this.tiles.add(new Tile(Orientation.UP, 'R', 'R', 'B', 4));
		this.tiles.add(new Tile(Orientation.UP, 'R', 'R', 'G', 4));
		this.tiles.add(new Tile(Orientation.UP, 'B', 'B', 'G', 4));
		this.tiles.add(new Tile(Orientation.UP, 'B', 'B', 'Y', 4));
		this.tiles.add(new Tile(Orientation.UP, 'G', 'G', 'Y', 4));
		this.tiles.add(new Tile(Orientation.UP, 'G', 'G', 'P', 4));
		this.tiles.add(new Tile(Orientation.UP, 'Y', 'Y', 'R', 4));
		this.tiles.add(new Tile(Orientation.UP, 'Y', 'Y', 'P', 4));
		this.tiles.add(new Tile(Orientation.UP, 'P', 'P', 'R', 4));
		this.tiles.add(new Tile(Orientation.UP, 'P', 'P', 'B', 4));
		
		//3 POINTS
		this.tiles.add(new Tile(Orientation.UP, 'Y', 'B', 'P', 3));
		this.tiles.add(new Tile(Orientation.UP, 'R', 'G', 'Y', 3));
		this.tiles.add(new Tile(Orientation.UP, 'B', 'G', 'P', 3));
		this.tiles.add(new Tile(Orientation.UP, 'G', 'R', 'B', 3));
	
		//2 POINTS
		this.tiles.add(new Tile(Orientation.UP, 'B', 'R', 'P', 2));
		this.tiles.add(new Tile(Orientation.UP, 'Y', 'P', 'R', 2));
		this.tiles.add(new Tile(Orientation.UP, 'Y', 'P', 'G', 2));
		
		//1 POINT
		this.tiles.add(new Tile(Orientation.UP, 'G', 'R', 'P', 1));
		this.tiles.add(new Tile(Orientation.UP, 'B', 'Y', 'G', 1));
		this.tiles.add(new Tile(Orientation.UP, 'R', 'Y', 'B', 1));
		this.tiles.add(new Tile(Orientation.UP, 'W', 'W', 'W', 1));
	}
	
	//***************************************************
	//------------------GETTERS/SETTERS------------------
	//***************************************************
	public List<Tile> getTiles() {
		return tiles;
	}

	public void setTiles(List<Tile> tiles) {
		this.tiles = tiles;
	}
	
}
