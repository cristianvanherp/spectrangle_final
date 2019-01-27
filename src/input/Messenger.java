package input;

import java.util.*;

import abstract_classes.Controller;
import model.Player;
import networking.*;

public class Messenger {
	//***************************************************
	//---------------------ATTRIBUTES--------------------
	//***************************************************
	private List<Controller> controllers;
	private List<Integer> statusCodes;
	
	//***************************************************
	//---------------------CONSTRUCTORS------------------
	//***************************************************
	public Messenger(List<Controller> controllers) {
		this.controllers = controllers;
		this.statusCodes = new ArrayList<Integer>();
		this.statusCodes.add(400);
		this.statusCodes.add(403);
		this.statusCodes.add(404);
	}
	
	//***************************************************
	//---------------------PUBLIC METHODS----------------
	//***************************************************	
	public void forward(Peer peer, String message) {
		Message msg = new Message(message);
		
		if(msg.getStatusCode() != null) {
			this.printStatusCode(peer, msg);
			return;
		}
		
		for(Controller controller: this.controllers) {
			if(controller.hasMethod(msg.getCommand())) {
				controller.forward(peer, msg);
			}
		}
	}
	
	//***************************************************
	//---------------------PRIVATE METHODS---------------
	//***************************************************
	private void printStatusCode(Peer peer, Message msg) {
		System.out.println(msg.getStatusCode() + ": "+ msg.getStringArgs());
		System.out.print("> ");
	}
	
	//***************************************************
	//---------------------STATIC METHODS----------------
	//***************************************************	
	public static void broadcast(List<Player> players, String msg) {
		for(Player player: players) {
			player.getPeer().write(msg);
		}
	}

}
