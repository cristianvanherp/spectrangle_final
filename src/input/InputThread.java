package input;

import java.io.IOException;
import java.util.Scanner;

import networking.*;

import input.*;

public class InputThread implements Runnable {
	//***************************************************
	//---------------------ATTRIBUTES--------------------
	//***************************************************
	private Peer peer;
	private Thread thread;
	private boolean running;
	private Messenger messenger;

	//***************************************************
	//---------------------CONSTRUCTORS------------------
	//***************************************************
	public InputThread(Peer peer, Messenger messenger)  {
		this.peer = peer;
		this.running = true;
		this.messenger = messenger;
	}
	
	//***************************************************
	//---------------------THREAD------------------------
	//***************************************************
	@Override
	public void run() {
		String input;
		Scanner scanner = new Scanner(System.in);
		
		while(this.running) {
			System.out.print("> ");
			input = scanner.nextLine();
			this.messenger.forward(null, input);
			
			if(input != null) {
				this.peer.write(input);
			}
		}
		
	}
	
	//***************************************************
	//---------------------PUBLIC METHODS----------------
	//***************************************************
	public void begin() {
		this.running = true;
		this.thread = new Thread(this);
		this.thread.start();
	}
	
	public void end() {
		this.running = false;
		this.thread.interrupt();
	}
	
}
