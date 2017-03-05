package org.capcaval.lafabrique.compiler._test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import junit.framework.Assert;

import org.capcaval.lafabrique.common.CommandResult;
import org.capcaval.lafabrique.compiler.CompilerTools;
import org.capcaval.lafabrique.compiler.JavaBuilder;
import org.capcaval.lafabrique.compiler._impl.CompileResult;
import org.capcaval.lafabrique.compiler._impl.CompileResult.CompilationResultEnum;
import org.capcaval.lafabrique.file.FileSeekerResult;
import org.capcaval.lafabrique.file.FileTools;
import org.capcaval.lafabrique.lang.ClassTools;
import org.capcaval.lafabrique.lang.JVM;
import org.junit.Test;

public class CompilerToolsTest {

//	@Test
//	public void compilerSimpleType() {
//		
//		try {
//			String binDir = "./temp/10_bin";
//			FileTools.createAndCleanDir(binDir);
//			
//			Type type = CompilerTools.compileClass(Type.class, binDir, "01_src");
//			Assert.assertEquals("Hello, it is working!", type.test);
//		} catch (Exception e) {
//			e.printStackTrace();
//			Assert.fail();
//		} 
//	}
	
//	@Test
//	public void compilerSimpleTest() {
//		
//		try {
//			Type type = CompilerTools.compileClass(
//					//Type.class, 
//					"01_src", 
//					"org/capcaval/ccoutils/compiler/_test/Type.java", 
//					"01_src");
//			Assert.assertEquals("Hello, it is working!", type.test);
//		} catch (Exception e) {
//			e.printStackTrace();
//			Assert.fail();
//		} 
//	}
	
//	@Test
//	public void compilerInterfaceTest(){
//		try {
//			Path rootSourceDir = Paths.get("01_src");
//			Path sourceFile = Paths.get("org/capcaval/ccoutils/compiler/_test/MyClass.java_");
//			Path destDir = Paths.get("./temp/01_src");
//			Path binDir = Paths.get("./temp/10_bin");
//			
//			FileTools.createAndCleanDir(destDir);
//			FileTools.createAndCleanDir(binDir);
//			
//			FileTools.copy(sourceFile, destDir, rootSourceDir);
//			
//			FileTools.renameFile(
//					destDir.resolve(sourceFile),
//					"MyClass.java");
//			
//			sourceFile = Paths.get("org/capcaval/ccoutils/compiler/_test/MyClass.java");
//			
//			MyInterface instance= CompilerTools.compileClass(
//					//MyInterface.class, 
//					destDir.toString(), 
//					sourceFile.toString(), 
//					binDir.toString());
//			
//			Assert.assertEquals("Cool this class has been compiled!", instance.getValue());
//			System.out.println("val : " + instance.getValue());
//			
//			FileTools.deleteFile("temp");
//		} catch (Exception e) {
//			e.printStackTrace();
//			Assert.fail();
//		}
//	}
	
	

	
	@Test
	public void compilerAllTest(){
		// prepare all files
		Path path = FileTools.getLocalDirPath();
		path = path.resolve("pack");
		
		Path srcPath = Paths.get("./temp/01_src");
		Path binPath = Paths.get("./temp/10_bin");
		
		FileTools.createAndCleanDir(srcPath);
		FileTools.createAndCleanDir(binPath);
		
		FileTools.copy(path, srcPath);
		
		FileSeekerResult r = null;
		r = FileTools.seekFiles("*.java_", srcPath);
		
		for(Path p : r.getPathList()){
			String newName = p.toFile().getName().replace(".java_", ".java");
			FileTools.renameFile(p, newName);
		}
		
		CommandResult cr = CompilerTools.compileAll(binPath.toString(), srcPath.toString());
		System.out.println(cr);
		
		
	}	
	
	@Test
	public void compileWithGivenJDKTest(){
		// prepare all files
		Path path = FileTools.getLocalDirPath();
		path = path.resolve("pack2");
		
		Path srcPath = Paths.get("./temp/01_src");
		Path binPath = Paths.get("./temp/10_bin");
		
		FileTools.createAndCleanDir(srcPath);
		FileTools.createAndCleanDir(binPath);
		
		FileTools.copy(path, srcPath);
		
		FileSeekerResult r = null;
		r = FileTools.seekFiles("*.java_", srcPath);
		
		for(Path p : r.getPathList()){
			String newName = p.toFile().getName().replace(".java_", ".java");
			FileTools.renameFile(p, newName);
		}
		
		CommandResult cr = CompilerTools.compileAll(binPath.toString(), srcPath.toString());
		System.out.println(cr);
	}	
	
	@Test
	public void compileOneClass(){
		JavaBuilder compiler = CompilerTools.newJavaBuilder(Paths.get("/home/mik/prg/java/jdk1.7.0_02"), Paths.get("testbin"));
		CompileResult result = compiler.compile("01_src/org/capcaval/lafabrique/compiler/_test/MyClass.java");
		System.out.println(result.getCompilationLog());
		System.out.println("--------------------------------------");
		Assert.assertEquals(1, result.getWarningNumber());
		Assert.assertEquals(0, result.getErrorNumber());
		Assert.assertEquals(CompilationResultEnum.warningNoError, result.getCompilationResultEnum());
		
		//ClassTools.get
		
		// check compilation error
		FileTools.copy("01_src/org/capcaval/lafabrique/compiler/_test/ErrorClass.java_", "01_src/org/capcaval/lafabrique/compiler/_test/ErrorClass.java");
		result = compiler.compile("01_src/org/capcaval/lafabrique/compiler/_test/ErrorClass.java");
		System.out.println(result.getCompilationLog());
		System.out.println("--------------------------------------");
		Assert.assertEquals(0, result.getWarningNumber());
		Assert.assertEquals(1, result.getErrorNumber());
		Assert.assertEquals(CompilationResultEnum.error, result.getCompilationResultEnum());
		FileTools.deleteFile("01_src/org/capcaval/lafabrique/compiler/_test/ErrorClass.java");
		
	}

	@Test
	public void compileAndGetClass(){
		//Toto toto = CompilerTools.compile(Toto.class, "temp/01_src", "temp/10/bin");
		
	}
	
	@Test
	public void compileDir(){
		//CompilerTools.compile("temp/01_src/", "temp/10/bin");
		
	}

	
	
}
