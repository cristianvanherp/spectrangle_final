package model;

import java.util.*;
import networking.*;
import observer.ActionAttribute;

public abstract class Player extends Observable {
	// ***************************************************
	// ------------------RELATIONSHIPS---------------------
	// ***************************************************
	private Game game;
	private List<Tile> tiles;

	// ***************************************************
	// ------------------ATTRIBUTES---------------------
	// ***************************************************
	private String nickname;
	private Integer score;
	private Peer peer;

	// ***************************************************
	// ------------------CONSTRUCTORS---------------------
	// ***************************************************
	
	//@ requires !nickname.equals(null);
	//@ ensures this.getNickname().equals(nickname);
	//@ ensures this.getScore() == 0 && !this.getTiles().equals(null);
	public Player(String nickname) {
		this.nickname = nickname;
		this.score = 0;
		this.tiles = new ArrayList<Tile>();
	}


	//@ requires !nickname.equals(null) && !peer.equals(null);
	//@ ensures this.getNickname().equals(nickname) && this.getPeer().equals(peer); 
	//@ ensures this.getScore() == 0 && !this.getTiles().equals(null);
	public Player(String nickname, Peer peer) {
		this.nickname = nickname;
		this.score = 0;
		this.peer = peer;
		this.tiles = new ArrayList<Tile>();
	}

	// ***************************************************
	// ------------------PUBLIC METHODS---------------------
	// ***************************************************
	
	//@ ensures !\result.equals(null);
	//@ ensures this.getTiles().size() == 4;
	public List<Tile> drawMaxTiles() {
		List<Tile> tiles1 = new ArrayList<Tile>();
		while (this.tiles.size() < 4) {
			tiles1.add(this.drawTile());
		}
		return tiles1;
	}
    
	//@ ensures !this.getTiles().equals(\old(this.getTiles()));
	//@ ensures \result != null;
	public Tile drawTile() {
		if (this.tiles.size() >= 4) {
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

	//@ requires !tile.equals(null) && this.getGame().getBag().getTiles().contains(tile);
	//@ ensures !this.getTiles().equals(\old(this.getTiles()));
	public Tile drawTile(String tile) {
		if (this.tiles.size() >= 4) {
			return null;
		}

		Tile t = this.game.getBag().drawTile(tile);
		this.tiles.add(t);
		return t;
	}

	// @ensures !\result.equals(null);
	public Tile lastTile() {
		return this.tiles.get(this.tiles.size() - 1);
	}

	//@ requires (index >= 0 && index <= this.getGame().getBoard().LENGTH && !( tileStr.equals(null)));
	//@ ensures (\exists Tile t; this.getTiles().contains(t) && t.isEquivalent(tileStr); \result != 404 );
	//@ ensures \result != 404 ==> !this.getGame().getBoard().equals(\old(this.getGame().getBoard()));
	public int placeTile(int index, String tileStr) {
		Tile tile = null;
		int status;

		for (Tile t : this.tiles) {
			if (t.isEquivalent(tileStr)) {
				tile = t;
				break;
			}
		}

		if (tile == null) {
			return 404;
		}

		for (int i = 0; i < 6; i++) {
			if (tileStr.equalsIgnoreCase(tile.toString())) {
				break;
			}
			tile.rotate();
		}

		status = this.game.placeTile(this, index, tile);

		if (status == 0) {
			ActionAttribute attr = new ActionAttribute("placedTile");
			attr.addPlayer(this);
			attr.addIndex(index);
			attr.addTile(tile);
			this.warnObservers(attr);
			
		}

		return status;
	}

	//@ requires !tileStr.equals(null);
	//@ ensures (\exists Tile t; this.getTiles().contains(t) && !t.isEquivalent(tileStr) ; \result == 404 );
	//@ ensures  \result!=404 ==> !this.getTiles().equals(\old(this.getTiles()));
	public int switchTile(String tileStr) {
		Tile tile = null;
		int status;

		for (Tile t : this.tiles) {
			if (t.isEquivalent(tileStr)) {
				tile = t;
				break;
			}
		}

		if (tile == null) {
			return 404;
		}

		status = this.game.switchTile(this, tile);

		if (status == 0) {
			ActionAttribute attr = new ActionAttribute("switchedTile");
			attr.addTile(tile);
			attr.addTile(this.lastTile());
			attr.addPlayer(this);
			this.warnObservers(attr);
		}

		return status;
	}

	//@ requires !oldTileStr.equals(null) && !newTileStr.equals(null);
	//@ ensures (\exists Tile t; this.getTiles().contains(t) && !t.isEquivalent(oldTileStr) ; \result == 404 ) ;
	//@ ensures  \result!=404 ==> !this.getTiles().equals(\old(this.getTiles()));
	public int switchTile(String oldTileStr, String newTileStr) {
		Tile tile = null;

		for (Tile t : this.tiles) {
			if (t.isEquivalent(oldTileStr)) {
				tile = t;
				break;
			}
		}

		if (tile == null) {
			return 404;
		}

		return this.game.switchTile(this, tile, newTileStr);
	}

	// @ requires points>0;
	// @ ensures this.getScore() > \old(this.getScore());
	public void addPoints(int points) {
		this.score += points;
	}

	// @ requires this.getGame().getTurn().equals(this);
	// @ ensures !this.getGame().getTurn().equals(this);
	public int skipMove() {
		int status;
		status = this.game.skipMove(this);

		if (status == 0) {
			ActionAttribute attr = new ActionAttribute("skippedMove");
			attr.addPlayer(this);
			this.warnObservers(attr);
		}

		return status;
	}

	// @ requires !this.getGame().equals(null);
	// @ ensures this.getGame().equals(null);
	public void leaveGame() {
		this.game.leaveGame(this);
		ActionAttribute attr = new ActionAttribute("playerLeft");
		attr.addPlayer(this);
		this.warnObservers(attr);
	}

	public abstract void requestMove();

	// ***************************************************
	// ------------------GETTERS/SETTERS------------------
	// ***************************************************

	// @ ensures !\result.equals(null);
	/* @ pure */public Game getGame() {
		return game;
	}

	// @ requires !game.equals(null);
	// @ ensures this.getGame().equals(game);
	public void setGame(Game game) {
		this.game = game;
		this.addObserver(game);
	}

	// @ ensures !\result.equals(null);
	/* @ pure */public Integer getScore() {
		return score;
	}

	// @ requires score != 0;
	// @ ensures getScore()==score;
	public void setScore(Integer score) {
		this.score = score;
	}

	// @ ensures !\result.equals(null);
	/* @ pure */public String getNickname() {
		return nickname;
	}

	// @ requires !nickname.equals(null);
	// @ ensures this.getNickname().equals(null);
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	// @ ensures !\result.equals(null);
	/* @ pure */public Peer getPeer() {
		return peer;
	}

	// @ requires !peer.equals(null);
	// @ ensures this.getPeer().equals(peer);
	public void setPeer(Peer peer) {
		this.peer = peer;
	}

	// @ ensures \result.size()>=0 && \result.size()<=4;
	/* @ pure */public List<Tile> getTiles() {
		return tiles;
	}

	// @ requires !tiles.equals(null);
	// @ensures this.getTiles().equals(tiles);
	public void setTiles(List<Tile> tiles) {
		this.tiles = tiles;
	}

	// ***************************************************
	// ------------------OBSERVABLE METHODS---------------
	// ***************************************************

	// @ requires !attr.equals(null);
	public void warnObservers(ActionAttribute attr) {
		this.setChanged();
		this.notifyObservers(attr);
	}
}
