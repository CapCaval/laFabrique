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
import org.capcaval.ccoutils.file.FileTools;
import org.capcaval.ccoutils.lang.JDKInstallationInfo;
import org.capcaval.ccoutils.lang.StringMultiLine;
import org.capcaval.ccoutils.lang.SystemClassLoader;
import org.capcaval.ccoutils.lang.SystemTools;

public class CompilerTools {
	
	public static<T> T compile(Class<? extends T> type, String root, String source, String outputDir) throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException{
		compile(root, source, outputDir);

		File binDir = new File(outputDir);
		
		Class<?> c = null;
		
		SystemClassLoader scm = new SystemClassLoader();
		scm.addURL(binDir.toString(),"./10_bin");
		String name = source.replace('/', '.').replace(".java", "");
		
		c = scm.loadClass(name);
		
		T instance = null;
		if(c!=null){
			instance = (T)c.newInstance();}
		
		return instance;
	}


	public static<T> T compile(Class<? extends T> type, String sourceFileName)throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException{
		
		String fullClassName = getFullClassNameFromFile(sourceFileName);
		
		String classFileName = fullClassName.replace('.', '/') + ".java";
		
		String rootDir = sourceFileName.replace(classFileName, "");
		
		T obj = compile( type, rootDir, classFileName, "ccBinTemp");
		
		return obj;
	}
	

	
	public static void compile( String root, String source, String outputDir) throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException{
		
		File rootDir = new File(root);
		File binDir = new File(outputDir);
		File sourceFile = new File(rootDir, source);
		
		// check if the bin directory exist
		if(binDir.exists() == false){
			// if not create it
			binDir.mkdirs();
		}
		
		// Compile source file.
		int i = 1;
		try{
			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
			
			if(compiler == null){
				// jdk is not accessible with PATH
				JDKInstallationInfo jdk = SystemTools.getJDKInstallationInfo();
				System.setProperty("java.home", jdk.path.toString());
				compiler = ToolProvider.getSystemJavaCompiler();
			}
			
			i = compiler.run(null, null, null, 
					"-g",
					"-classpath", "../10_bin;10_bin;02_lib/ccOutils.jar",
					"-d", outputDir, 
					sourceFile.getPath());
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	
	public static String getFullClassNameFromFile(String sourceFileName) throws IOException{
		String fileStr = FileTools.readStringfromFile(sourceFileName);
		String[] lineArray = fileStr.split("\n");
		
		String packageName = "";
		String className = "";
		
		for(String line : lineArray){
			if(line.contains("package")){
				packageName = line.replaceAll("package", "");
				packageName = packageName.replaceAll(" ", "");
				packageName = packageName.replaceAll("\t", "");
				packageName = packageName.replaceAll(";", "");
			}
			if(line.contains("class")){
				String temp  = line.split("class")[1];
				for(String str : temp.split(" ")){
					if(str.equals("") == false){
						className = str;
						break;
					}
				}
				
				break;
			}
		}
		
		String fullName = packageName + "." + className;
		System.out.println(fullName);

		return fullName;
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		// Prepare source somehow.
//		String source = "package test; public class Test2 extends org.capcaval.ccoutils.compiler.test.AbstractClass{ static { System.out.println(\"hello\"); } public Test2() { System.out.println(\"world\"); } public void init() { System.out.println(\"new one\"); }}";

		//TODO simplify le code;
		
		
		StringMultiLine source = new StringMultiLine(
				"package test;",
				"",
				"public class Test2 extends org.capcaval.ccoutils.compiler._test.AbstractClass{",
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
		File outputDir = new File(root, "10_bin");
		sourceFile.getParentFile().mkdirs();
		outputDir.mkdirs();
		
		new FileWriter(sourceFile).append(source.toString()).close();

		
		AbstractClass o = compile(AbstractClass.class, "java2", "test/Test2.java", "11_bin");
		o.init();
		
		System.out.println(o.i);
	}

}
