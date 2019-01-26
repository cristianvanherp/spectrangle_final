package model;

import java.util.*;

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

