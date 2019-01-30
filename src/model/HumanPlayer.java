package model;

import networking.*;

public class HumanPlayer extends Player {
	public HumanPlayer(String nickname, Peer peer) {
		super(nickname, peer);
	}
	
	public HumanPlayer(String nickname) {
		super(nickname);
	}
	
	public void requestMove() {
		this.getPeer().write("requestMove");
	}
}
