package model;

import java.util.*;
import third_party.*;
import enums.*;

public class Board {
	//***************************************************
	//------------------RELATIONSHIPS---------------------
	//***************************************************
	private List<Slot> slots;

	//***************************************************
	//------------------ATTRIBUTES-----------------------
	//***************************************************
	public static final int LENGTH = 36;

	//***************************************************
	//------------------CONSTRUCTORS---------------------
	//***************************************************
	public Board() {
		this.slots = new ArrayList<Slot>();
		this.init();
	}

	//***************************************************
	//------------------PUBLIC METHODS------------------
	//***************************************************
	public int placeTile(Tile tile, int index) {
		Slot slot = null;

		try {
			slot = this.slots.get(index);
		} catch(IndexOutOfBoundsException e) {
			return 404;
		}

		//TODO: PERFORM CHECKS HERE

		//-------------------------

		slot.setTile(tile);
		return 0;
	}

	public String toString() {
		List<Integer> values = new ArrayList<Integer>();
		List<Character> vertical = new ArrayList<Character>();
		List<Character> left = new ArrayList<Character>();
		List<Character> right = new ArrayList<Character>();

		Integer value;
		Character chr_vertical, chr_left, chr_right;
		for(Slot slot: this.slots) {
			value = null;
			chr_vertical = chr_left = chr_right = null;

			if(slot.getTile() != null) {
				value = slot.getTile().getPoints();
				chr_vertical = slot.getTile().getColorVertical();
				chr_left = slot.getTile().getColorLeft();
				chr_right = slot.getTile().getColorRight();
			}

			values.add(value);
			vertical.add(chr_vertical);
			left.add(chr_left);
			right.add(chr_right);
		}


		return SpectrangleBoardPrinter.getBoardString(values, vertical, left, right);
	}

	//***************************************************
	//------------------PRIVATE METHODS------------------
	//***************************************************
	private void init() {
		Slot slot;
		for(int i = 0 ; i < Board.LENGTH ; i++) {
			
			//Initialize a new slot
			slot = new Slot();
			slot.setTile(null);
			slot.setIndex(i);

			//Set the orientation
			if((rowOfIndex(i) + columnOfIndex(i)) % 2 == 1) 
				slot.setOrientation(Orientation.DOWN);
			else slot.setOrientation(Orientation.UP);

			//Setting the bonus
			if(i == 11 || i == 13 || i == 20) {
				slot.setBonus(4);	
			}
			else if(i == 2 || i == 26 || i == 34) {
				slot.setBonus(3);	
			}
			else if(i==10 || i==14 || i==30) {
				slot.setBonus(2);
			}
			else {
				slot.setBonus(1);
			}
			this.slots.add(slot);
		}

		//Setting the neighbors
		slot = new Slot();
		for(Slot s : slots) {
			int x = s.getIndex();
			int r = rowOfIndex(x), c = columnOfIndex(x);
			
			if(r == (-1)*c ) {
				s.setLeft(slot);	
			}
			if(r == c) {
				s.setRight(slot);	
			}
			if(r == (int)Math.sqrt(LENGTH-1) && s.getOrientation() == Orientation.UP) {
				s.setVertical(slot);				
			}
			if(s.getLeft() != slot) {
				s.setLeft(getSlotOfCoord(r, c-1));	
			}
			if(s.getRight() != slot) {
				s.setRight(getSlotOfCoord(r, c+1));	
			}
			if(s.getVertical() != slot && s.getOrientation() == Orientation.UP) {
				s.setVertical(getSlotOfCoord(r+1, c));	
			}
			if(s.getVertical() != slot && s.getOrientation() == Orientation.DOWN) {
				s.setVertical(getSlotOfCoord(r-1, c));				
			}
		}
	}

	public int coordToIndex(int row, int col) {
		return (row*row) + row + col;
	}

	public int rowOfIndex(int x) {

		for(int i=0;i<=5;i++) {
			if ( x>=i*i && x<(i+1)*(i+1) )
				return i;
		}
		return -1;
	}

	public int columnOfIndex(int x) {
		int r=rowOfIndex(x);
		if(x<r*(r+1)) {
			int i=0;
			while (i>=(-r)) {
				if(coordToIndex(r,i)==x)
					return i;
				i--;
			}
		}
		else {
			int i=0;
			while (i<=r) {
				if(coordToIndex(r,i)==x)
					return i;
				i++;
			}
		}
		return -15;
	}

	public Slot getSlotOfCoord(int r, int c) {

		for(Slot s: this.slots) {
			if(columnOfIndex(s.getIndex())==c && rowOfIndex(s.getIndex())==r)
				return  s;
		}
		return null;
	}
}

