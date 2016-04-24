package ccoutils.compiler;
import org.capcaval.ccoutils.commandline.Command;
import org.capcaval.ccoutils.common.TextFileDisplayFrame;
import org.capcaval.ccoutils.compiler.CompilerTools;
import org.capcaval.ccoutils.lang.SystemTools;
import ccoutils.SampleCommons;

public class CompilerSample {
	
	@Command(desc = "Show how to compile a class on the fly and to use it.")
	public String compileClass(){
		System.out.println( SampleCommons.SAMPLE_SOURCE_CODE_MESSAGE + SystemTools.getCurrentFullMethodName());
		TextFileDisplayFrame.factory.newTextFileDisplayFrame(this.getClass()).display(800, 0, 600, 550);
		TextFileDisplayFrame.factory.newTextFileDisplayFrame(TestClass.class).display(800, 600, 600, 200);
		
		TestClass object = null;
		try {
			object = CompilerTools.compile(TestClass.class, "01_sampleSrc/ccoutils/compiler/TestClass.java");
		} catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println(object.getString());
		
		SystemTools.readConsoleInput("\nPlease, press return key to quit.");
		System.exit(0);
		
		return "bye";
	}
}
