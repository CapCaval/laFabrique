package org.capcaval.lafabrique.factory._sample.generic;

import java.util.ArrayList;
import java.util.List;


public class GreeterMain {

	public static void main(String[] args) {
		// Generic factory can be used to create instance without parameter
		// it only need having constructor without parameters
		Greeter g = Greeter.factory.newInstance();
		System.out.println(g.sayHello("world"));
		
		int poolSize = 10;
		Greeter.factory.setObjectPoolSize(poolSize);
		List<Greeter> list1 = new ArrayList<Greeter>();
		List<Greeter> list2 = new ArrayList<Greeter>();
		
		for(int i=0; i<poolSize; i++){
			Greeter greeter = Greeter.factory.newInstance();
			list1.add(greeter);
		}
		// release all of them
		for(int i=0; i<poolSize; i++){
			Greeter greeter = list1.get(i);
			Greeter.factory.releaseInstance(greeter);
		}
		
		// allocate a new set of object
		for(int i=0; i<poolSize; i++){
			Greeter greeter = Greeter.factory.newInstance();
			list2.add(greeter);
			
		}
		System.out.println("finish");
		// 
		
	}
}
