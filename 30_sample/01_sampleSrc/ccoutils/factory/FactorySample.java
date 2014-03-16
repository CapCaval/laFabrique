package ccoutils.factory;

import org.capcaval.ccoutils.commandline.Command;
import org.capcaval.ccoutils.common.TextFileDisplayFrame;
import org.capcaval.ccoutils.factory.FactoryTools;
import org.capcaval.ccoutils.factory.GenericFactory;
import org.capcaval.ccoutils.lang.StringMultiLine;
import org.capcaval.ccoutils.lang.SystemTools;
import ccoutils.SampleCommons;

public class FactorySample {
	@Command(desc = "Show how to change instance type by using a generic factory.")
	public String mutableFactory(){
		System.out.println( SampleCommons.SAMPLE_SOURCE_CODE_MESSAGE 
				+ SystemTools.getCurrentFullMethodName());
		
		TextFileDisplayFrame.
			factory.
			newTextFileDisplayFrame( this.getClass()).
			display(800, 0, 600, 800);		
		
		StringMultiLine returnStr = new StringMultiLine();
		
		// the default instance is in english just get it
		Greeter greeter = Greeter.factory.newInstance();
		returnStr.addLine(greeter.sayHelloTo("John"));
		
		// change default type
		Greeter.factory.setObjectImplementationType(AussieGreeterImpl.class);
		
		// say hello again
		Greeter greeterAussie = Greeter.factory.newInstance();
		returnStr.addLine(greeterAussie.sayHelloTo("John"));
		
		return returnStr.toString();
	}
	
	
	public interface Greeter{
		// allocate a generic factory
		GenericFactory<Greeter> factory = FactoryTools.newGenericFactory( 
				EnglishGreeterImpl.class); // default factory implementation
		
		String sayHelloTo(String name);
	}	
	public static class EnglishGreeterImpl implements Greeter{
		@Override
		public String sayHelloTo(String name) {
			return "Hello " + name;
		}
		
	}
	public static class AussieGreeterImpl implements Greeter{
		@Override
		public String sayHelloTo(String name) {
			return "G'day " + name;
		}
	}
}
