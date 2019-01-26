package model;

import java.util.*;

import networking.*;

public class Player {
	//***************************************************
	//------------------RELATIONSHIPS---------------------
	//***************************************************
	private Game game;
	private Lobby lobby;
	private ScoreBoard scoreBoard;
	private List<Tile> tiles;
	
	//***************************************************
	//------------------ATTRIBUTES---------------------
	//***************************************************
	private String nickname;
	private Integer score;
	private Peer peer;

	//***************************************************
	//------------------CONSTRUCTORS---------------------
	//***************************************************
	public Player(String nickname) {
		this.nickname = nickname;
		this.scoreBoard = new ScoreBoard();
		this.tiles = new ArrayList<Tile>();
	}
	
	public Player(String nickname, Peer peer) {
		this.nickname = nickname;
		this.peer = peer;
		this.scoreBoard = new ScoreBoard();
		this.tiles = new ArrayList<Tile>();
	}
	
	//***************************************************
	//------------------PUBLIC METHODS---------------------
	//***************************************************
	public List<Tile> drawMaxTiles() {
		List<Tile> tiles = new ArrayList<Tile>();
		while(this.tiles.size() < 4) {
			tiles.add(this.drawTile());
		}
		return tiles;
	}
	
	public Tile drawTile() {
		if(this.tiles.size() >= 4) {
			return null;
		}
		
		Tile tile = this.game.getBag().drawTile();
		this.tiles.add(tile);
		
		return tile;
	}
	
	public Tile drawTile(String tile) {
		if(this.tiles.size() >= 4) {
			return null;
		}
		
		Tile t = this.game.getBag().drawTile(tile);
		this.tiles.add(t);
		return t;
	}
	
	public Tile lastTile() {
		return this.tiles.get(this.tiles.size()-1);
	}
	
	//***************************************************
	//------------------GETTERS/SETTERS---------------------
	//***************************************************
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}
	
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Lobby getLobby() {
		return lobby;
	}

	public void setLobby(Lobby lobby) {
		this.lobby = lobby;
	}

	public Peer getPeer() {
		return peer;
	}

	public void setPeer(Peer peer) {
		this.peer = peer;
	}

	public ScoreBoard getScoreBoard() {
		return scoreBoard;
	}

	public void setScoreBoard(ScoreBoard scoreBoard) {
		this.scoreBoard = scoreBoard;
	}

	public List<Tile> getTiles() {
		return tiles;
	}

	public void setTiles(List<Tile> tiles) {
		this.tiles = tiles;
	}
	
}
