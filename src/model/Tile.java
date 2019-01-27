package model;

import enums.Orientation;

public class Tile {
	//***************************************************
	//------------------RELATIONSHIPS---------------------
	//***************************************************
	private Player player;
	
	//***************************************************
	//------------------ATTRIBUTES---------------------
	//***************************************************
	private char colorVertical, colorLeft, colorRight;
	private Orientation orientation; //TODO: change to an enum type
	private Integer points;

	//***************************************************
	//------------------CONSTRUCTORS---------------------
	//***************************************************
	public Tile(Orientation orientation, char colorLeft, char colorRight, char colorVertical, int points) {
		this.orientation = orientation;
		this.colorLeft = colorLeft;
		this.colorRight = colorRight;
		this.colorVertical = colorVertical;
		this.points = points;
	}
	
	//***************************************************
	//------------------PUBLIC METHODS-------------------
	//***************************************************
	@Override
	public String toString() {
		if(this.orientation.equals(Orientation.UP)) {
			return String.valueOf(this.colorRight) + String.valueOf(this.colorVertical) + String.valueOf(this.colorLeft) + this.points.toString();
		}
		else {
			return String.valueOf(this.colorVertical) + String.valueOf(this.colorRight) + String.valueOf(this.colorLeft) + this.points.toString();		
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
	
	
}
