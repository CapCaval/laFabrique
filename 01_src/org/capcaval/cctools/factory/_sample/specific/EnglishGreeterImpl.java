package org.capcaval.cctools.factory._sample.specific;

public class EnglishGreeterImpl implements Greeter{

	private String preMessage;
	private String postMesage;

	public EnglishGreeterImpl(String preMessage, String postMessage){
		this.init( preMessage, postMessage);
	}
	
	public EnglishGreeterImpl(String postMessage) {
		this.init( "", postMessage);
	}

	protected void init(String preMessage, String postMessage){
		this.preMessage = preMessage;
		this.postMesage = postMessage;
	}

	@Override
	public String sayHelloTo(String name) {
		return this.preMessage + "Hello " + name + ", " + this.postMesage;
	}

	static public class EnglishGreeterFactoryImpl implements GreeterFactory{
		@Override
		public Greeter newGreteer() {
			return new EnglishGreeterImpl( "Hello " , "");
		}
		
		@Override
		public Greeter newGreteer(String postMessage) {
			return new EnglishGreeterImpl( "" , postMessage);
		}

		@Override
		public Greeter newGreteer(String preMessage, String postMessage) {
			return new EnglishGreeterImpl( preMessage, postMessage);
		}

		
	}

}
