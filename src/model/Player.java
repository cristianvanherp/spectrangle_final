package model;

import java.util.*;

import input.Messenger;
import networking.*;
import server.ServerDatabase;
import observer.ActionAttribute;

public abstract class Player extends Observable {
	//***************************************************
	//------------------RELATIONSHIPS---------------------
	//***************************************************
	private Game game;
	private Lobby lobby;
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
		this.score = 0;
		this.tiles = new ArrayList<Tile>();
	}
	
	public Player(String nickname, Peer peer) {
		this.nickname = nickname;
		this.score = 0;
		this.peer = peer;
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
		
		ActionAttribute attr = new ActionAttribute("drawnTile");
		attr.addPlayer(this);
		attr.addTile(tile);
		this.warnObservers(attr);
		
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
	
	public int placeTile(int index, String tileStr) {
		Tile tile = null;
		int status;
		
		for(Tile t: this.tiles) {
			if(t.isEquivalent(tileStr)) {
				tile = t;
				break;
			}
		}
		
		if(tile == null) {
			return 404;
		}
		
		for(int i = 0 ; i < 6 ; i++) {
			if(tileStr.equalsIgnoreCase(tile.toString())) {
				break;
			}
			tile.rotate();
		}
		
		status = this.game.placeTile(this, index, tile);
		
		if(status == 0) {
			ActionAttribute attr = new ActionAttribute("placedTile");
			attr.addPlayer(this);
			attr.addIndex(index);
			attr.addTile(tile);
			this.warnObservers(attr);
		}
		
		return status;
	}
	
	public int switchTile(String tileStr) {
		Tile tile = null;
		int status;
		
		for(Tile t: this.tiles) {
			if(t.isEquivalent(tileStr)) {
				tile = t;
				break;
			}
		}
		
		if(tile == null) {
			return 404;
		}
		
		status = this.game.switchTile(this, tile);
		
		if(status == 0) {
			ActionAttribute attr = new ActionAttribute("switchedTile");
			attr.addTile(tile);
			attr.addTile(this.lastTile());
			attr.addPlayer(this);
			this.warnObservers(attr);
		}
		
		return status;
	}
	
	public int switchTile(String oldTileStr, String newTileStr) {
		Tile tile = null;
	
		for(Tile t: this.tiles) {
			if(t.isEquivalent(oldTileStr)) {
				tile = t;
				break;
			}
		}
		
		if(tile == null) {
			return 404;
		}
		
		return this.game.switchTile(this, tile, newTileStr);
	}
	
	public void addPoints(int points) {
		this.score += points;
	}
	
	public int skipMove() {
		int status;
		status = this.game.skipMove(this);
	
		if(status == 0) {
			ActionAttribute attr = new ActionAttribute("skippedMove");
			attr.addPlayer(this);
			this.warnObservers(attr);
		}
		
		return status;
	}
	
	public void leaveGame() {
		this.game.leaveGame(this);
		ActionAttribute attr = new ActionAttribute("playerLeft");
		attr.addPlayer(this);
		this.warnObservers(attr);
	}
	
	public abstract void requestMove();
	
	//***************************************************
	//------------------GETTERS/SETTERS------------------
	//***************************************************
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
		this.addObserver(game);
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

	public List<Tile> getTiles() {
		return tiles;
	}

	public void setTiles(List<Tile> tiles) {
		this.tiles = tiles;
	}
	
	//***************************************************
	//------------------OBSERVABLE METHODS---------------
	//***************************************************
	public void warnObservers(ActionAttribute attr) {
		this.setChanged();
		this.notifyObservers(attr);
	}
}
