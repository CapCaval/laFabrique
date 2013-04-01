package org.capcaval.ccoutils.factory._sample.generic;


public class GreeterImpl implements Greeter{

	protected String postMessage;

	public GreeterImpl(){
		this.postMessage="";
	}
	public GreeterImpl(String postMessage){
		this.postMessage = postMessage;
		
	}
	
	@Override
	public String sayHello(String name) {
		return "Hello " + name + " " + this.postMessage;
	}
	
}
