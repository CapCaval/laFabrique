package org.capcaval.ccoutils.compiler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import org.capcaval.ccoutils.compiler._test.AbstractClass;
import org.capcaval.ccoutils.compiler._test.MyInterface;
import org.capcaval.ccoutils.file.FileSeekerResult;
import org.capcaval.ccoutils.file.FileTool;
import org.capcaval.ccoutils.lang.StringMultiLine;
import org.capcaval.ccoutils.lang.SystemClassLoader;

public class Compiler {
	
	public static<T> T compile(Class<? extends T> type, String root, String source, String outputDir) throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException{
		
		File rootDir = new File(root);
		File binDir = new File(outputDir);
		File sourceFile = new File(rootDir, source);
		
		// check if the bin directory exist
		if(binDir.exists() == false){
			// if not create it
			binDir.mkdirs();
		}
		
		System.out.println(" rootDir : " + rootDir.exists() + " SourceFile : " + sourceFile.exists() + "  binDir : " + binDir.exists());
		System.out.println( "outDir : " + outputDir + " rootDir : " + rootDir +  " sourceFile : " + sourceFile.getPath() );
		System.out.println( "cp : " + System.getProperty("java.class.path"));
		// Compile source file.
		
		int i = 1;
		try{
			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
			i = compiler.run(null, null, null, 
					"-g",
					"-classpath", "02_lib/ccOutils.jar:junit-4.8.2.jar:10_bin",
					"-d", outputDir, 
					sourceFile.getPath());
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("Success : " + (i==0?"Yes":"No"));

		Class<?> c = null;
		
		SystemClassLoader scm = new SystemClassLoader();
		//scm.addURL(binDir.toString(),"./02_lib/C3.jar");
		scm.addURL(binDir.toString(),"./10_bin");
		String name = source.replace('/', '.').replace(".java", "");
		
		System.out.println("Load class : " + name + "   SCM : " + Arrays.toString(scm.getURLs()));
		c = scm.loadClass(name);
		
		T instance = null;
		if(c!=null){
			instance = (T)c.newInstance();}
		
		System.out.println("instance : " + instance);
		
		return instance;
	}

	public static Object compile2() throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException{
		// Prepare source somehow.
		String source = "package test; public class Test { static { System.out.println(\"hello\"); } public Test() { System.out.println(\"world\"); } }";

		// Save source in .java file.
		File root = new File("java");
		File sourceFile = new File(root, "test/Test.java");

		sourceFile.getParentFile().mkdirs();
		new FileWriter(sourceFile).append(source).close();

		// Compile source file.
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		compiler.run(null, null, null, "-g", "-d", "java/bin", sourceFile.getPath());

		// Load and instantiate compiled class.
		URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { root.toURI().toURL() });
		Class<?> cls = Class.forName("test.Test", true, classLoader); // Should print "hello".
		Object instance = cls.newInstance(); // Should print "world".
		System.out.println(instance); // Should print "test.Test@hashcode".
		
		return instance;
	}


	public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		// Prepare source somehow.
//		String source = "package test; public class Test2 extends org.capcaval.ccoutils.compiler.test.AbstractClass{ static { System.out.println(\"hello\"); } public Test2() { System.out.println(\"world\"); } public void init() { System.out.println(\"new one\"); }}";

		StringMultiLine source = new StringMultiLine(
				"package test;",
				"",
				"public class Test2 extends org.capcaval.ccoutils.compiler.test.AbstractClass{",
				//"public class Test2 implements org.capcaval.ccoutils.compiler.test.MyInterface{",
				" public int i = 0;",
				"	public Test2() {",
				"		System.out.println(\"Test2\");",
				"	}",
				"	public void init() {",
				"		System.out.println(\"Test2\");",
				"	}",
				"}"
				);
		
		// Save source in .java file.
		File root = new File("java2");
		File sourceFile = new File(root, "test/Test2.java");
		File outputDir = new File(root, "02_bin");
		sourceFile.getParentFile().mkdirs();
		outputDir.mkdirs();
		
		new FileWriter(sourceFile).append(source.toString()).close();

		
		AbstractClass o = compile(AbstractClass.class, "java2", "test/Test2.java", "10_bin");
		o.init();
		
		System.out.println(o.i);
		
//		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
//
//		FileSeekerResult result = FileTool.seekFiles("*.java", Paths.get("compileTest"));
//
//		int compilationResult = compiler.run(null, null, null, result.getStringFileList());
//		if (compilationResult == 0) {
//			System.out.println(Arrays.toString(result.getFileList()));
//			System.out.println("Compilation is successful");
//		} else {
//			System.out.println("Compilation Failed");
//		}
	}
}
