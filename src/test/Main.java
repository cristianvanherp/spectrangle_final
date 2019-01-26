package test;

import java.lang.reflect.Method;

public class Main {
	
	public void testMethod(String arg1) {
	}
	
	public static void main(String[] args) {
		Main m = new Main();
		for(Method method: m.getClass().getMethods()) {
			System.out.println(method.getName());
		}
	}
}
