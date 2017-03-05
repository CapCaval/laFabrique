package org.capcaval.lafabrique.compiler._test;

import java.io.IOException;
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
import org.capcaval.lafabrique.lang.Version;
import org.junit.Test;

public class JavaBuilderToolsTest {

	@Test
	public void getJDKTest(){
		JavaBuilder compiler = CompilerTools.newJavaBuilder(Paths.get("/home/mik/prg/java/jdk1.8.0_25"), Paths.get("testbin"));
		JVM jdk = compiler.getJVM();
		
		Assert.assertTrue(Version.factory.newVersion("1.7.0_02").isThisVersionSameThan( jdk.getVersion()));
	}

	@Test
	public void compileAndGetClassTest(){
		// root test dir
		Path rootSrcPath = Paths.get("tempCompilation");
		Path binPath = rootSrcPath.resolve("bin");
		Path srcPath = Paths.get("01_src");
		Path filePath = Paths.get("org/capcaval/lafabrique/compiler/_test/ChildClass.java");
		
		// clean the root dir
		FileTools.createAndCleanDir(rootSrcPath);
		//FileTools.createDir(rootPath.resolve(filePath.getParent()));
		
		// create a compilator
		JavaBuilder compiler = CompilerTools.newJavaBuilder(binPath);
		
		// copy to testCompilation and keep packages directory
//		FileTools.copyFile(
//				srcPath.resolve(filePath.toString()+"_"), rootPath, srcPath);
		String src = srcPath.resolve(filePath).toString()+"_";
		String dest = rootSrcPath.resolve(filePath).toString();
		System.out.println( src);
		System.out.println( dest);
		
		
		FileTools.copy( src, dest);

		// compile the copied file
		ObjectAndResult<MyInterface> result = compiler.compileAndGet(MyInterface.class, rootSrcPath, filePath);
		
		Assert.assertEquals(result.getObject().getValue(), "Cool this class has been compiled!");
		
		System.out.println(result.getResult().getCompilationLog());
		System.out.println("---------------------------------------");
		Assert.assertEquals( 1, result.getResult().getWarningNumber());
		Assert.assertEquals(0, result.getResult().getErrorNumber());
		Assert.assertEquals(CompilationResultEnum.warningNoError, result.getResult().getCompilationResultEnum());
		Assert.assertTrue(FileTools.isFileExist(binPath.resolve("org/capcaval/lafabrique/compiler/_test/ChildClass.class")));
		
		Assert.assertNotNull(result.getObject());
		Assert.assertEquals("ChildClass", result.getObject().getClass().getSimpleName());
	}

	
	
	@Test
	public void compileClassTest(){
		// root test dir
		Path rootPath = Paths.get("tempCompilation");
		Path binPath = rootPath.resolve("bin");
		Path srcPath = Paths.get("01_src");
		Path filePath = Paths.get("org/capcaval/lafabrique/compiler/_test/MyClass.java");
		Path errorFilePath = Paths.get("org/capcaval/lafabrique/compiler/_test/ErrorClass.java");
		
		// clean the root dir
		FileTools.createAndCleanDir(rootPath);
		
		// create a compiler
		JavaBuilder compiler = CompilerTools.newJavaBuilder(binPath);
		
		// copy to testCompilation and keep packages directory
		FileTools.copyFile(
				srcPath.resolve(filePath), rootPath, srcPath);

		// compile the copied file
		CompileResult result = compiler.compile(rootPath.resolve(filePath));
		
		System.out.println(result.getCompilationLog());
		System.out.println("---------------------------------------");
		Assert.assertEquals(1, result.getWarningNumber());
		Assert.assertEquals(0, result.getErrorNumber());
		Assert.assertEquals(CompilationResultEnum.warningNoError, result.getCompilationResultEnum());
		Assert.assertTrue(FileTools.isFileExist(binPath.resolve("org/capcaval/lafabrique/compiler/_test/MyClass.class")));
		
		Object obj = ClassTools.getFileNewInstance("org/capcaval/lafabrique/compiler/_test/MyClass.java", "testbin");
		Assert.assertNotNull(obj);
		Assert.assertEquals("MyClass", obj.getClass().getSimpleName());
				
		// check compilation error
		// copy the error file and change the name
		FileTools.copy( srcPath.resolve(errorFilePath).toString()+"_", rootPath.resolve(errorFilePath).toString());
		result = compiler.compile(rootPath.resolve(errorFilePath));
		System.out.println(result.getCompilationLog());
		System.out.println("--------------------------------------");
		Assert.assertEquals(0, result.getWarningNumber());
		Assert.assertEquals(1, result.getErrorNumber());
		Assert.assertEquals(CompilationResultEnum.error, result.getCompilationResultEnum());
	}

	@Test
	public void compileSpecificJDKClassTest(){
		// root test dir
		Path rootPath = Paths.get("tempCompilation");
		Path binPath = rootPath.resolve("bin");
		Path srcPath = Paths.get("01_src");
		Path filePath = Paths.get("org/capcaval/lafabrique/compiler/_test/MyClass.java");
		Path classPath = Paths.get("org/capcaval/lafabrique/compiler/_test/MyClass.class");
		Path jdkPath = Paths.get("/home/mik/prg/java/jdk1.8.0_25");
		
		// clean the root dir
		FileTools.createAndCleanDir(rootPath);
		
		// create a compiler
		JavaBuilder compiler = CompilerTools.newJavaBuilder(jdkPath, binPath);
		
		// copy to testCompilation and keep packages directory
		FileTools.copyFile(
				srcPath.resolve(filePath), rootPath, srcPath);

		// compile the copied file
		CompileResult result = compiler.compile(rootPath.resolve(filePath));
		
		System.out.println(result.getCompilationLog());
		System.out.println("---------------------------------------");
		Assert.assertEquals(0, result.getWarningNumber());
		Assert.assertEquals(0, result.getErrorNumber());
		Assert.assertEquals(CompilationResultEnum.noErrorNoWarning, result.getCompilationResultEnum());
		Assert.assertTrue(FileTools.isFileExist(binPath.resolve("org/capcaval/lafabrique/compiler/_test/MyClass.class")));
		
		Object obj = ClassTools.getFileNewInstance("org/capcaval/lafabrique/compiler/_test/MyClass.java", "testbin");
		Assert.assertNotNull(obj);
		Assert.assertEquals("MyClass", obj.getClass().getSimpleName());
		
		// load the class and check version
		Version classVersion = ClassTools.getClassVersion(binPath.resolve(classPath).toString());
		System.out.println("The binary has been compiled in "+ classVersion.getVersionString()); 
		Assert.assertEquals(Version.factory.newVersion("1.8.0_00").getLongValue(), classVersion.getLongValue());
	}
	
	
	@Test
	public void compilerAllTest(){
		// prepare all files
		Path path = FileTools.getLocalDirPath();
		path = path.resolve("pack");
		
		Path srcPath = Paths.get("./tempCompilation/01_src");
		Path binPath = Paths.get("./tempCompilation/10_bin");
		
		FileTools.createAndCleanDir(srcPath);
		FileTools.createAndCleanDir(binPath);
		
		FileTools.copy(path, srcPath);
		
		FileSeekerResult r = FileTools.seekFiles("*.java_", srcPath);
		
		for(Path p : r.getPathList()){
			String newName = p.toFile().getName().replace(".java_", ".java");
			FileTools.renameFile(p, newName);
		}
		
		JavaBuilder compiler = CompilerTools.newJavaBuilder(binPath);
		CommandResult cr = compiler.compileAll(binPath.toString(), srcPath.toString());
		System.out.println(cr);
		
		Assert.assertTrue(FileTools.isFileExist("tempCompilation/10_bin/pack/Class1.class"));
		Assert.assertTrue(FileTools.isFileExist("tempCompilation/10_bin/pack/Class2.class"));
		Assert.assertTrue(FileTools.isFileExist("tempCompilation/10_bin/pack/subpack/Class3.class"));
	}	
}
