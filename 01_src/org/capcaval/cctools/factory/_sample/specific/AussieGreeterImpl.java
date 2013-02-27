package org.capcaval.cctools.factory._sample.specific;

public class AussieGreeterImpl implements Greeter{

	private String preMessage;
	private String postMesage;

	public AussieGreeterImpl(String preMessage, String postMessage){
		this.init( preMessage, postMessage);
	}
	
	public AussieGreeterImpl(String postMessage) {
		this.init( "", postMessage);
	}

	protected void init(String preMessage, String postMessage){
		this.preMessage = preMessage;
		this.postMesage = postMessage;
	}

	@Override
	public String sayHelloTo(String name) {
		return this.preMessage + "G'day " + name + this.postMesage;
	}

	static public class AussieGreeterFactoryImpl implements GreeterFactory{
		@Override
		public Greeter newGreteer() {
			return new AussieGreeterImpl("", "");
		}

		@Override
		public Greeter newGreteer(String postMessage) {
			return new AussieGreeterImpl("", postMessage);
		}

		@Override
		public Greeter newGreteer(String preMessage, String postMessage) {
			return new AussieGreeterImpl( preMessage, postMessage);
		}
	}

}
