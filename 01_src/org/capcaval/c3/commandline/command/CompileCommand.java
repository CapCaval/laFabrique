package org.capcaval.c3.commandline.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.capcaval.ccoutils.file.FileSeekerResult;
import org.capcaval.ccoutils.file.FileTool;
import org.capcaval.ccoutils.lang.ArrayTools;
import org.capcaval.ccproject.AbstractProject;
import org.capcaval.ccproject.command.CommandResult;


public class CompileCommand {

//	public CommandResult compile(AbstractProject proj){
//		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
//
//		FileSeekerResult result = null;
//		try {
//			result = FileTool.seekFiles("*.java", proj.sourceList);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		DiagnosticCollector<JavaFileObject> diagnosticList = new DiagnosticCollector<JavaFileObject>();
//		
//	    List<String> compileOptionList = ArrayTools.newArrayList(
//	    		"-d", 
//	    		proj.outputPathBin.toString(), 
//	    		"-classpath", 
//	    		System.getProperty("java.class.path"));
//	    
//		CommandResult commandResult = null;
//		//int compilationResult = compiler.run(null, null, null, compileOptionList.toArray(new String[0]));
//		StandardJavaFileManager stdFileManager = compiler.getStandardFileManager(null, Locale.getDefault(), null);
//		
//		
//		for(Path p : result.getFileList()){
//			new File(p.);
//		}
//		
//		Files[] files1 = result.getStringFileList();
//
//	       JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
//	       StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
//
//	       Iterable<? extends JavaFileObject> compilationUnits1 =
//	           fileManager.getJavaFileObjectsFromFiles(Arrays.asList(files1));
//	       compiler.getTask(null, fileManager, null, null, null, compilationUnits1).call();
//
//	       Iterable<? extends JavaFileObject> compilationUnits2 =
//	           fileManager.getJavaFileObjects(files2); // use alternative method
//	       // reuse the same file manager to allow caching of jar files
//	       compiler.getTask(null, fileManager, null, null, null, compilationUnits2).call();
//
//	       fileManager.close();
//		
//		
//		Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(javaFileObjects);
//		
//		Iterable<? extends JavaFileObject> filelist = ArrayTools.newArrayList(result.getStringFileList());
//		
//		CompilationTask task = compiler.getTask(null, null, diagnosticList, null, null, filelist);
//		
//		boolean isSuccessFull = task.call();
//		
//		if (isSuccessFull == true) {
//			String message = Arrays.toString(result.getFileList());
//			message = message + "\nCompilation is successful";
//			commandResult = new CommandResult(true, message);
//		}else{
//			commandResult = new CommandResult(false, "Error : ");
//		}
//		
//		return commandResult;
//	}

	
	public CommandResult compile(AbstractProject proj){
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

		FileSeekerResult result = null;
		try {
			result = FileTool.seekFiles("*.java", proj.sourceList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	
        String classpath=System.getProperty("java.class.path");
        String testpath=classpath+";.;" + ArrayTools.toStringWithDelimiter(';', proj.libPathList);

        List<String> optionList =  ArrayTools.newArrayList(
        		"-classpath",testpath,
        		"-d", proj.outputPathBin.toString());
        StandardJavaFileManager sfm = compiler.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> fileObjects = sfm.getJavaFileObjects(result.getStringFileList());
        JavaCompiler.CompilationTask task = compiler.getTask(
        		null, null, null,
        		optionList,null,fileObjects);

        try {
			sfm.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		boolean isSuccessFull = task.call();
		CommandResult commandResult = null;
		
		if (isSuccessFull == true) {
			String message = Arrays.toString(result.getFileList());
			message = message + "\nCompilation is successful";
			commandResult = new CommandResult(true, message);
		}else{
			commandResult = new CommandResult(false, "Error : ");
		}
		
		return commandResult;

        
        
    }
	
	

//	public class CompileSourceInMemory {
//		  public static void main(String args[]) throws IOException {
//		    JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
//		    DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
//
//		    StringWriter writer = new StringWriter();
//		    PrintWriter out = new PrintWriter(writer);
//		    out.println("public class HelloWorld {");
//		    out.println("  public static void main(String args[]) {");
//		    out.println("    System.out.println(\"This is in another java file\");");    
//		    out.println("  }");
//		    out.println("}");
//		    out.close();
//		    JavaFileObject file = new JavaSourceFromString("HelloWorld", writer.toString());
//
//		    Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(file);
//		    CompilationTask task = compiler.getTask(null, null, diagnostics, null, null, compilationUnits);
//
//		    boolean success = task.call();
//		    for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
//		      System.out.println(diagnostic.getCode());
//		      System.out.println(diagnostic.getKind());
//		      System.out.println(diagnostic.getPosition());
//		      System.out.println(diagnostic.getStartPosition());
//		      System.out.println(diagnostic.getEndPosition());
//		      System.out.println(diagnostic.getSource());
//		      System.out.println(diagnostic.getMessage(null));
//
//		    }
//		    System.out.println("Success: " + success);
//
//		    if (success) {
//		      try {
//		        Class.forName("HelloWorld").getDeclaredMethod("main", new Class[] { String[].class })
//		            .invoke(null, new Object[] { null });
//		      } catch (ClassNotFoundException e) {
//		        System.err.println("Class not found: " + e);
//		      } catch (NoSuchMethodException e) {
//		        System.err.println("No such method: " + e);
//		      } catch (IllegalAccessException e) {
//		        System.err.println("Illegal access: " + e);
//		      } catch (InvocationTargetException e) {
//		        System.err.println("Invocation target: " + e);
//		      }
//		    }
//		  }
//		}
//
//		class JavaSourceFromString extends SimpleJavaFileObject {
//		  final String code;
//
//		  JavaSourceFromString(String name, String code) {
//		    super(URI.create("string:///" + name.replace('.','/') + Kind.SOURCE.extension),Kind.SOURCE);
//		    this.code = code;
//		  }
//
//		  @Override
//		  public CharSequence getCharContent(boolean ignoreEncodingErrors) {
//		    return code;
//		  }
//		}
	
	// ****************************************************************************************
	
	
	
//	public void compile2(){
//		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
//
//	    StandardJavaFileManager stdFileManager = compiler.getStandardFileManager(null, Locale.getDefault(), null);
//
//	    Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(javaFileObjects);
//
//	    // creates a Temp BIN foler on the C: drive to add .class files for compiling      
//	    new File(directoryForBin).mkdirs();
//
//	    String[] compileOptions = new String[]{"-d", directoryForBin, "-classpath", System.getProperty("java.class.path")};
//	    Iterable<String> compilationOptions = Arrays.asList(compileOptions);
//
//	    DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
//
//	    CompilationTask compilerTask = compiler.getTask(null, stdFileManager, diagnostics, compilationOptions, null, compilationUnits);
//
//	    boolean status = compilerTask.call();
//
//	    if (!status) {//If compilation error occurs 
//	        // Iterate through each compilation problem and print it
//	        for (Diagnostic diagnostic : diagnostics.getDiagnostics()) { 
//	            compile.add(diagnostic.getKind().toString()+" on line  "+ diagnostic.getLineNumber() +"\nIn file:  \n"+ diagnostic.toString()+"\n\n");
//	        }
//	    }
//	    try {
//	        stdFileManager.close();//Close the file manager
//	    } catch (IOException e) {
//	        e.printStackTrace();
//	    }
//
//	}
}
