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

	//***************************************************
	//---------------------CONSTRUCTORS------------------
	//***************************************************
	public InputThread(Peer peer)  {
		this.peer = peer;
		this.running = true;
	}
	
	//***************************************************
	//---------------------THREAD------------------------
	//***************************************************
	@Override
	public void run() {
		String msg;
		Scanner scanner = new Scanner(System.in);
		
		while(this.running) {
			System.out.print("> ");
			msg = scanner.nextLine();
			if(msg != null) {
				this.peer.write(msg);
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
