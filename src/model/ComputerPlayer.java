package model;

public class ComputerPlayer extends Player {

	public ComputerPlayer() {
		super("CPU");
	}

	@Override
	public void requestMove() {
		Board board = this.getGame().getBoard();
		Slot target_slot;
		Tile target_tile;
		int max_points = 0;
		
		
		for(Slot slot: board.getSlots()) {
			for(Tile tile: this.getTiles()) {
				int points = board.canBePlaced(slot, tile);
				points = points * tile.getPoints() * slot.getBonus();
				
				if(points > max_points) {
					max_points = points;
					target_slot = slot;
					target_tile = tile;
				}
			}
		}
		
		
	}
	
}
