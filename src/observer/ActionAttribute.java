package observer;

import java.util.*;
import model.*;

public class ActionAttribute {
	private List<Tile> tiles;
	private List<Player> players;
	private List<Integer> indexes;
	private String action;

	public ActionAttribute(String action) {
		this.action = action;
		this.tiles = new ArrayList<Tile>();
		this.players = new ArrayList<Player>();
		this.indexes = new ArrayList<Integer>();
	}

	public void addTile(Tile tile) {
		this.tiles.add(tile);
	}

	public void addPlayer(Player player) {
		this.players.add(player);
	}

	public void addIndex(Integer index) {
		this.indexes.add(index);
	}

	public List<Tile> getTiles() {
		return tiles;
	}

	public void setTiles(List<Tile> tiles) {
		this.tiles = tiles;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public List<Integer> getIndex() {
		return indexes;
	}

	public void setIndex(List<Integer> index) {
		this.indexes = index;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
}
