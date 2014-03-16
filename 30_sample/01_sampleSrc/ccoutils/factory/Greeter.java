package ccoutils.factory;

import org.capcaval.ccoutils.factory.FactoryTools;
import org.capcaval.ccoutils.factory.GenericFactory;


public interface Greeter{

	GenericFactory<Greeter> factory = FactoryTools.newGenericFactory( // allocate a generic factory
			EnglishGreeterImpl.class); // default factory implementation instance
	
	String sayHelloTo(String name);
	
	
	public class EnglishGreeterImpl implements Greeter{

		@Override
		public String sayHelloTo(String name) {
			return "Hello " + name;
		}
		
	}
	public class AussieGreeterImpl implements Greeter{

		@Override
		public String sayHelloTo(String name) {
			return "G'day " + name;
		}
		
	}

}
