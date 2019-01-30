package abstract_classes;

import java.lang.reflect.Method;
import java.util.List;

import model.*;
import networking.*;
import interfaces.*;
import input.*;
import test.Main;

public abstract class Controller {
	//***************************************************
	//---------------------ATTRIBUTES--------------------
	//***************************************************
	private Database database;
	
	//***************************************************
	//---------------------CONSTRUCTORS------------------
	//***************************************************
	public Controller(Database database) {
		this.database = database;
	}
	
	//***************************************************
	//---------------------ABSTRACT METHODS--------------
	//***************************************************
	public abstract void forward(Peer peer, Message msg);
	
	//***************************************************
	//---------------------PUBLIC METHODS----------------
	//***************************************************
	public boolean hasMethod(String method) {
		for(Method m: this.getClass().getMethods()) {
			if(m.getName().equalsIgnoreCase(method)) {
				return true;
			}
		}
		return false;
	}
	
	public void writeErrorMessage(Peer peer, int status, String msg) {
		peer.write(status + " " + msg);
	}
	
	//***************************************************
	//---------------------GETTERS/SETTERS---------------
	//***************************************************
	public Database getDatabase() {
		return database;
	}

	public void setDatabase(Database database) {
		this.database = database;
	}	
	
}
