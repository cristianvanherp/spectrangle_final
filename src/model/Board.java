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
	
	//@ requires tile!=null && index>=0 && index<=this.LENGTH;
	//@ ensures tile==null ==> \result== -1;
	//@ ensures !this.canBePlaced(tile) ==> \result== -1;
	public int placeTile(Tile tile, int index) {
		Slot slot = null;
		Integer points = 0;
		Integer edges = 0;
		
		try {
			slot = this.slots.get(index);
		} catch(IndexOutOfBoundsException e) {
			return -1;
		}
		
		if(slot.getTile() != null) {
			return -1;
		}
		
		edges = this.canBePlaced(slot, tile);
		
		if(edges == 0) {
			tile.invert();
			edges = this.canBePlaced(slot, tile);
		}
		
		if(edges == 0) {
			tile.invert();
			return -1;
		}
		
		points = tile.getPoints() * edges * slot.getBonus();
		slot.setTile(tile);
		
		return points;
	}
	//@ requires slot!=null && tile !=null;
	//@ ensures \result>=0 && \result<=3 ;
	public int canBePlaced(Slot slot, Tile tile) {
		int edges = 0;
		int matching_edges = 0;
		
		if(this.isEmpty()) {
			if(!tile.getOrientation().equals(slot.getOrientation())) {
				tile.invert();
			}
			return 1;
		}
		else {
			if(!tile.getOrientation().equals(slot.getOrientation())) {
				return 0;
			}
			
			Slot left;
			if((left = slot.getLeft()) != null) {
				if(left.getTile() != null) {
					edges++;
					if(left.getTile().getColorRight() == tile.getColorLeft() || tile.getColorLeft() == 'W' || left.getTile().getColorRight() == 'W') {
						matching_edges++;
					}
				}
			}
			
			Slot right;
			if((right = slot.getRight()) != null) {
				if(right.getTile() != null) {
					edges++;
					if(right.getTile().getColorLeft() == tile.getColorRight() || tile.getColorRight() == 'W' || right.getTile().getColorLeft() == 'W') {
						matching_edges++;
					}
				}
			}
			
			
			Slot vertical;
			if((vertical = slot.getVertical()) != null) {
				if(vertical.getTile() != null) {
					edges++;
					if(vertical.getTile().getColorVertical() == tile.getColorVertical()  || tile.getColorVertical() == 'W' || vertical.getTile().getColorVertical() == 'W') {
						matching_edges++;
					}
				}
			}
	
		}
		
		if(edges > matching_edges) {
			return 0;
		}
				
		return matching_edges;
	}
	//@ requires tile!=null;
	//@ ensures (\exists Slot s; this.getSlots().contains(s) && this.canBePlaced(s,tile)!=0; \result== true ); 
	/*@ pure*/public boolean canBePlaced(Tile tile) {
		if(this.isEmpty()) {
			return true;
		}
		
		for(Slot slot: this.slots) {
			if(this.canBePlaced(slot, tile) != 0) {
				return true;
			}
		}
		
		return false;
	}

	//@ ensures \result!=null;
	/*@ pure*/public String toString() {
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
	
	//@ ensures  this.getSlots()!=null && this.getSlots().size()==this.LENGTH;
    //@ ensures (\forall Slot s; this.getSlots().contains(s) ; s.getOrientation()!=null);
	//@ ensures (\forall Slot s; this.getSlots().contains(s) && (s.getIndex() == 11 || s.getIndex() == 13 || s.getIndex() == 20  || s.getIndex() == 2 || s.getIndex() == 26 || s.getIndex() == 34 || s.getIndex() == 10 || s.getIndex() == 14 || s.getIndex() == 30) ; s.getBonus()!=1);
	//@ ensures  (\forall Slot s; this.getSlots().contains(s) && (columnOfIndex(s.getIndex())!=(-1)*rowOfIndex(s.getIndex())); s.getLeft().getIndex()==-1);
	//@ ensures  (\forall Slot s; this.getSlots().contains(s) && (columnOfIndex(s.getIndex())==rowOfIndex(s.getIndex())); s.getRight().getIndex()==-1);
	//@ ensures  (\forall Slot s; this.getSlots().contains(s) && 5==rowOfIndex(s.getIndex()) && s.getOrientation()==Orientation.UP; s.getVertical().getIndex()==-1);
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
	
	//@ requires 0 <= row && row<=5 && -5<=col && col<= 5;
	//@ ensures \result >=0 && \result <= this.LENGTH;
	/*@ pure*/public int coordToIndex(int row, int col) {
		return (row*row) + row + col;
	}

	//@requires x<=this.LENGTH && x>=0;
	//@ensures  0 <= \result && \result<=5;
	/*@ pure*/public int rowOfIndex(int x) {

		for(int i=0;i<=5;i++) {
			if ( x>=i*i && x<(i+1)*(i+1) )
				return i;
		}
		return -1;
	}

	//@ requires x<=this.LENGTH && x>=0;
	//@ ensures -5<=\result && \result<= 5;
	/*@ pure*/public int columnOfIndex(int x) {
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

	//@ requires 0 <= r && r<=5 && -5<=c && c<= 5;
	//@ ensures \result !=null;
	/*@ pure*/public Slot getSlotOfCoord(int r, int c) {

		for(Slot s: this.slots) {
			if(columnOfIndex(s.getIndex())==c && rowOfIndex(s.getIndex())==r)
				return  s;
		}
		return null;
	}
	
	/*
	 * @pure; ensures (\exists Slot s; s.getSlots().contains(s) && s.getTile()!=null; \result==false);
	 * 
	 */
	//@ ensures (\exists Slot s; this.getSlots().contains(s) && s.getTile()!=null; \result==false);
	/*@ pure*/public boolean isEmpty() {
		for(Slot slot: this.slots) {
			if(slot.getTile() != null) {
				return false;
			}
		}
		return true;
	}
	
	// @ensures \result.equals(this.slots);
	/*@ pure*/public List<Slot> getSlots() {
		return slots;
	}
}

