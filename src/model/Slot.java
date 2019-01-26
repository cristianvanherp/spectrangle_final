package model;

import enums.Orientation;

public class Slot {
	//***************************************************
	//------------------RELATIONSHIPS---------------------
	//***************************************************
	private Tile tile;
	private Slot left, right, vertical;

	//***************************************************
	//------------------ATTRIBUTES---------------------
	//***************************************************
	private int bonus;
	private Orientation orientation; //TODO: change to an enum type

	//***************************************************
	//------------------CONSTRUCTORS---------------------
	//***************************************************
	public Slot() {
		
	}
	
	//***************************************************
	//------------------GETTERS/SETTERS---------------------
	//***************************************************
	public Tile getTile() {
		return tile;
	}

	public void setTile(Tile tile) {
		this.tile = tile;
	}
	
	public int getBonus() {
		return this.bonus;
	}
	
	public void setBonus(int bonus) {
		this.bonus=bonus;
	}
	
	public Slot getLeft() {
		return  left;
	}

	public void setLeft(Slot left) {
		this.left = left;
	}

	public Slot getRight() {
		return right;
	}

	public void setRight(Slot right) {
		this.right = right;
	}

	public Slot getVertical() {
		return vertical;
	}

	public void setVertical(Slot vertical) {
		this.vertical = vertical;
	}
	
}
