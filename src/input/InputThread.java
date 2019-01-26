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
		ConsoleInputReadTask input = new ConsoleInputReadTask();
		try {
			while(this.running) {
				msg = input.call();
				if(msg != null) {
					this.peer.write(msg);
				}
			}
		
		} catch (IOException e) {
			e.printStackTrace();
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
