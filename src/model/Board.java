package model;

import java.util.*;
import third_party.*;

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
		for(int i = 0 ; i < Board.LENGTH ; i++) {
			Slot s=new Slot();
			s.setTile(null);
			
			if(i==11 || i==13 || i== 20) 
				s.setBonus(4);
			else if(i==2 || i==26 || i==34)
				 s.setBonus(3);
				 else if(i==10 || i==14 || i==30)
					 s.setBonus(2);
				 	  else s.setBonus(1); 
			
			
			this.slots.add(s);
		}
	}
	
	public int coordToIndex(int row, int col) {
		return (row*row) + row + col;
	}
	
	public int indexRow(int x) {
		
		for(int i=0;i<=5;i++) {
			if ( x>=i*i && x<(i+1)*(i+1) )
				return i;
		}
	    return -1;
	}
	
	public int indexColumn(int x) {
		int r=indexRow(x);
	    if(x<r*(r+1)) {
			int i=0;
			while (i>=(-r)) {
				i--;
				if(coordToIndex(r,i)==x)
					return i;
			}
		}
		else {
			int i=0;
			while (i<=r) {
				i++;
				if(coordToIndex(r,i)==x)
					return i;
				}
			}
		return -15;
		}
		
	}

