package input;

import java.io.*;
import java.util.concurrent.Callable;
import java.util.*;

public class ConsoleInputReadTask implements Callable<String> {

	//***************************************************
	//---------------------PUBLIC FUNCTIONS--------------
	//***************************************************
	public String call() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		this.emptyInputStream(br);
		
		String input;
		do {
			System.out.print("> ");
			try {
				while (!br.ready()) {
					Thread.sleep(50);
				}
				input = br.readLine();
			} catch (InterruptedException e) {
				return null;
			}
		} while ("".equals(input));
		
		return input;
	}
	
	//***************************************************
	//---------------------PRIVATE FUNCTIONS-------------
	//***************************************************
	private void emptyInputStream(BufferedReader br) throws IOException {
		while(br.ready()) {
			br.readLine();
		}
	}
}