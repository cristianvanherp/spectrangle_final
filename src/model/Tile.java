package model;

import enums.Orientation;
import java.util.*;

public class Tile {
	//***************************************************
	//------------------RELATIONSHIPS---------------------
	//***************************************************
	private Player player;
	
	//***************************************************
	//------------------ATTRIBUTES---------------------
	//***************************************************
	private char colorVertical, colorLeft, colorRight;
	private Orientation orientation;
	private Integer points;
	public List<String> drawingLines;

	//***************************************************
	//------------------CONSTRUCTORS---------------------
	//***************************************************
	public Tile(Orientation orientation, char colorLeft, char colorRight, char colorVertical, int points) {
		this.orientation = orientation;
		this.colorLeft = colorLeft;
		this.colorRight = colorRight;
		this.colorVertical = colorVertical;
		this.points = points;
		this.init();
	}
	
	//***************************************************
	//------------------PUBLIC METHODS-------------------
	//***************************************************
	public void rotate() {
		char aux_left = this.colorLeft, aux_right = this.colorRight, aux_vertical = this.colorVertical;
		
		if(this.orientation.equals(Orientation.UP)) {
			this.orientation = Orientation.DOWN;
			this.colorLeft = aux_vertical;
			this.colorRight = aux_right;
			this.colorVertical = aux_left;	
		}
		else {
			this.orientation = Orientation.UP;
			this.colorLeft = aux_left;
			this.colorRight = aux_vertical;
			this.colorVertical = aux_right;	
		}
		
		this.updateDrawing();
	}
	
	@Override
	public String toString() {
		if(this.orientation.equals(Orientation.UP)) {
			return String.valueOf(this.colorRight) + String.valueOf(this.colorVertical) + String.valueOf(this.colorLeft) + this.points.toString();
		}
		else {
			return String.valueOf(this.colorVertical) + String.valueOf(this.colorRight) + String.valueOf(this.colorLeft) + this.points.toString();		
		}	
	}
	
	public boolean isEquivalent(String tileStr) {
		boolean equivalent = false;
		
		for(int i = 0 ; i < 6 ; i++) {
			this.rotate();
			if(this.toString().equals(tileStr)) {
				equivalent = true;
			}
		}
		
		return equivalent;
	}
	
	public static void draw(List<Tile> tiles) {
		for(int i = 0 ; i < 6 ; i++) {
			for(Tile tile: tiles) {
				System.out.print(tile.drawingLines.get(i));
			}
			System.out.print("\n");
		}
	}
	
	//***************************************************
	//------------------PRIVATE METHODS-------------------
	//***************************************************
	private void init() {
		this.drawingLines = new ArrayList<String>();
		this.updateDrawing();
	}
	
	private void updateDrawing() {
		if(this.orientation.equals(Orientation.UP)) {
			this.drawingLines.add("     ^      ");
			this.drawingLines.add("    / \\     ");
			this.drawingLines.add("   /   \\    ");
			this.drawingLines.add("  /"+String.valueOf(this.colorLeft)+" "+this.points+" "+String.valueOf(this.colorRight)+"\\   ");
			this.drawingLines.add(" /   "+String.valueOf(this.colorVertical)+"   \\  ");
			this.drawingLines.add("----------- ");
		}
		else {
			this.drawingLines.add("----------- ");
			this.drawingLines.add(" \\   "+String.valueOf(this.colorVertical)+"   /  ");
			this.drawingLines.add("  \\"+String.valueOf(this.colorLeft)+" " + String.valueOf(this.colorVertical) + " "+String.valueOf(this.colorRight)+"/   ");
			this.drawingLines.add("   \\   /    ");
			this.drawingLines.add("    \\ /     ");
			this.drawingLines.add("     X      ");	
		}
	}
	
	//***************************************************
	//------------------GETTERS/SETTERS------------------
	//***************************************************
	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public char getColorVertical() {
		return colorVertical;
	}

	public void setColorVertical(char colorVertical) {
		this.colorVertical = colorVertical;
	}

	public char getColorLeft() {
		return colorLeft;
	}

	public void setColorLeft(char colorLeft) {
		this.colorLeft = colorLeft;
	}

	public char getColorRight() {
		return colorRight;
	}

	public void setColorRight(char colorRight) {
		this.colorRight = colorRight;
	}

	public Orientation getOrientation() {
		return orientation;
	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}
	
	
}
