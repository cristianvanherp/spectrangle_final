package model;

import java.io.IOException;
import java.util.*;

import enums.Status;
import input.Messenger;
import networking.*;
import observer.ActionAttribute;

public class Game implements Runnable, Observer {
	//***************************************************
	//------------------CONSTANTS------------------------
	//***************************************************
	public static final int MIN_PLAYERS = 2;
	public static final int MAX_PLAYERS = 4;
	
	//***************************************************
	//------------------RELATIONSHIPS--------------------
	//***************************************************
	private List<Player> players;
	private Board board;
	private Bag bag;
	
	//***************************************************
	//------------------ATTRIBUTES-----------------------
	//***************************************************
	private Thread thread;
	private Status status;
	private Player turn;
	
	//***************************************************
	//------------------CONSTRUCTORS---------------------
	//***************************************************
	
	public Game(List<Player> players, Player host) {
		if(players.size() < Game.MIN_PLAYERS || players.size() > Game.MAX_PLAYERS) {
			//Throw an exception
		}
		
		if(!players.contains(host)) {
			//Throw an exception
		}
		
		this.players = players;
		this.init();
	}
	
	//***************************************************
	//------------------PUBLIC METHODS-------------------
	//***************************************************	
	public void start() {
		this.thread = new Thread(this);
		this.thread.start();
		this.status = Status.RUNNING;
	}
	
	//@ensures !\result.equals(null);
	/*@ pure*/public String getPlayersStr() {
		String str = "";
		for(Player player: this.players) {
			str += player.getNickname() + " ";
		}
		
		return str.trim();
	}
	//@ requires this.getPlayers().contains(player) && player.getTiles().contains(tile) && index>=0 && index <=this.getBoard().LENGTH;
	//@ ensures !this.getTurn().equals(player) ==> \result==403;
	//@ ensures this.getBoard().placeTile(tile, index)==-1 ==> \result == 404;
	//@ ensures player.getScore()>=\old(player.getScore());
	//@ ensures  this.getTurn().equals(this.nextPlayer());
	//@ ensures player.getTiles().size()<=\old(player.getTiles().size());
	public int placeTile(Player player, int index, Tile tile) {
		Integer points;
		
		if(!this.getTurn().equals(player)) {
			return 403;
		}
		
		points = this.board.placeTile(tile, index);
		if(points == -1) {
			return 404;
		}
		
		player.getTiles().remove(tile);
		player.addPoints(points);
		this.turn = this.nextPlayer();
		
		return 0;
	}
	
	//@ requires this.getPlayers().contains(player) && player.getTiles().contains(tile);
	//@ ensures !this.getTurn().equals(player) || this.canMakeMove(player) ==> \result==403;
	//@ ensures  this.getTurn().equals(this.nextPlayer());
	public int switchTile(Player player, Tile tile) {
		
		if(!this.getTurn().equals(player)) {
			return 403;
		}
		
		if(this.canMakeMove(player)) {
			return 403;
		}
		
		player.getTiles().remove(tile);
		this.bag.getTiles().add(tile);
		player.drawTile();
		this.turn = this.nextPlayer();
		return 0;
	}
	
	//@ requires this.getPlayers().contains(player) && player.getTiles().contains(tile) && !newTileStr.equals(null);
	//@ ensures !this.getTurn().equals(player) || this.canMakeMove(player) ==> \result==403;
	//@ ensures  this.getTurn().equals(this.nextPlayer());
	public int switchTile(Player player, Tile tile, String newTileStr) {
		
		if(!this.getTurn().equals(player)) {
			return 403;
		}
		
		if(this.canMakeMove(player)) {
			return 403;
		}
		
		player.getTiles().remove(tile);
		this.bag.getTiles().add(tile);
		player.drawTile(newTileStr);
		this.turn = this.nextPlayer();
		return 0;
	}
	
	//@ requires player!=null && this.getPlayers().contains(player);
	//@ ensures this.canMakeMove(player) ==> \result==403; 
	public int skipMove(Player player) {
		if(this.canMakeMove(player)) {
			return 403;
		}
		
		this.turn = this.nextPlayer();
		return 0;
	}
	
	//@ requires player!=null && this.getPlayers().contains(player);
	//@ ensures (\exists Tile t; player.getTiles().contains(t) && this.getBoard().canBePlaced(t);\result==true);
	/*@ pure*/public boolean canMakeMove(Player player) {
		boolean result = false;
		for(Tile tile: player.getTiles()) {
			for(int i = 0 ; i < 6 ; i++) {
				tile.rotate();
				if(this.board.canBePlaced(tile)) {
					result = true;
				}
			}
			
			if(result) break;
		}
		return result;
	}
	
	//@ ensures (this.getPlayers().contains(\result));
	//@ ensures this.getPlayers().indexOf(this.getTurn())<this.getPlayers().size() ==> \result.equals(this.getPlayers().get(1+this.getPlayers().indexOf(this.getTurn())));
	/*@ pure*/public Player nextPlayer() {	
		Integer index = this.players.indexOf(this.turn) + 1;
		Player next = this.players.get(0);
		
		if(index < this.players.size()) {
			next = this.players.get(index);
		}
		
		return next;
	}
	//@ requires this.getPlayers().contains(player);
	//@ ensures !(this.getPlayers().contains(player)) && player.getScore()==0;
	//@ensures this.getBag().getTiles().size()==4+\old(this.getBag().getTiles().size());
	public void leaveGame(Player player) {
		this.bag.getTiles().addAll(player.getTiles());
		player.setTiles(new ArrayList<Tile>());
		player.setScore(0);
		this.players.remove(player);
	}
	
	//***************************************************
	//------------------PRIVATE METHODS------------------
	//***************************************************
	
	//@ ensures this.getBoard()!=null && this.getBag()!=null && this.status==Status.NOT_STARTED;
	//@ ensures (\forall Player p; this.getPlayers().contains(p); p.getGame().equals(this) && p.getScore()==0 );
	private void init() {
		this.board = new Board();
		this.bag = new Bag();
		this.status = Status.NOT_STARTED;
		
		for(Player player: this.players) {
			player.setGame(this);
			player.setScore(0);
		}
		
		this.turn = players.get(0);
	}
	
	//***************************************************
	//------------------GETTERS/SETTERS------------------
	//***************************************************
	//@ensures !(\result.equals(null));
	/*@ pure*/public Bag getBag() {
		return bag;
	}

	//@requires bag!=null;
	//@ensures this.getBag().equals(bag);
	public void setBag(Bag bag) {
		this.bag = bag;
	}
	
	//@ensures !(\result.equals(null));
	/*@ pure*/public List<Player> getPlayers() {
		return players;
	}

	//@ requires players!=null;
	//@ ensures this.getPlayers().equals(players);
	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	//@ensures \result!=null;
	/*@ pure*/public Board getBoard() {
		return board;
	}

	//@ requires board!=null;
	//@ ensures this.getBoard()==board;
	public void setBoard(Board board) {
		this.board = board;
	}

	//@ensures \result!=null;
	/*@ pure*/public Player getTurn() {
		return turn;
	}

	//@requires this.getPlayers().contains(turn);
	//@ensures this.getTurn().equals(turn);
	public void setTurn(Player turn) {
		this.turn = turn;
	}

	//***************************************************
	//------------------THREAD---------------------------
	//***************************************************
	@Override
	public void run() {
		
		//FIRST STEP - TELL ALL PLAYERS THAT THE GAME STARTED AND IN WHICH ORDER THE PLAYERS ARE
		Messenger.broadcast(this.players, "start " + this.getPlayersStr());
		
		//SECOND STEP - DRAW TILES FOR ALL OF THE PLAYERS
		for(Player player: this.players) {
			List<Tile> tiles = player.drawMaxTiles();
			for(Tile tile: tiles) {
				Messenger.broadcast(this.players, "drawnTile " + player.getNickname() + " " + tile.toString());
			}
		}
		
		while(this.status.equals(Status.RUNNING)) {
			for(Player player: this.players) {
				player.requestMove();
				while(this.turn.equals(player)) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} 
				}
				
				if(this.getBag().getTiles().isEmpty()) {
					this.status = Status.FINISHED;
					Messenger.broadcast(this.players, "end");
					break;
				}
				else {
					player.drawTile();		
				}
			}
		}
	}
	
	//***************************************************
	//------------------OBSERVER METHODS-----------------
	//***************************************************
	
	//@ requires arg0!=null && arg1!=null;
	//@ requires ((ActionAttribute)arg1).getPlayers().get(0).getPeer()!=null;
	@Override
	public void update(Observable arg0, Object arg1) {
		ActionAttribute attr = (ActionAttribute)arg1;
		Player player = attr.getPlayers().get(0);
		Tile tile;
		
		if(player.getPeer() == null) {
			return;
		}
		
		switch(attr.getAction()) {
		case "placedTile":
			Integer index = attr.getIndex().get(0);
			tile = attr.getTiles().get(0);
			Messenger.broadcast(this.players, "placedTile " + player.getNickname() + " " + index + " " + tile.toString());
			break;
		case "drawnTile":
			tile = attr.getTiles().get(0);
			Messenger.broadcast(this.players, "drawnTile " + player.getNickname() + " " + tile.toString());
			break;
		case "switchedTile":
			Tile oldTile = attr.getTiles().get(0);
			Tile newTile = attr.getTiles().get(1);
			Messenger.broadcast(this.players, "switchedTile " + player.getNickname() + " " + oldTile + " " + newTile);
		case "skippedMove":
			Messenger.broadcast(this.players, "skippedMove " + player.getNickname());
			break;
		case "playerLeft":
			Messenger.broadcast(this.players, "players " + this.getPlayersStr());
			break;
		default:
			break;
		}
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
