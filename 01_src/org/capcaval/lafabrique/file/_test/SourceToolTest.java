package org.capcaval.lafabrique.file._test;

import java.util.Arrays;

import junit.framework.Assert;

import org.capcaval.lafabrique.file.FileTools;
import org.capcaval.lafabrique.file.SourceInfo;
import org.capcaval.lafabrique.file.SourceInfo.SourceTypeEnum;
import org.capcaval.lafabrique.file.SourceTool;
import org.capcaval.lafabrique.lang.StringMultiLine;
import org.capcaval.lafabrique.lang.StringTools;
import org.junit.Test;

public class SourceToolTest extends Object{
	
	@Test
	public void headerCommentTest(){
		StringMultiLine header1 = new StringMultiLine(
				"/*",
				"* hello",
				"*", 
				"*/"
				);
		
		StringMultiLine header2 = new StringMultiLine(
				"//",
				"// hello",
				"//", 
				"//\n"
			);

		FileTools.createAndCleanDir("newDir");
		
		// write the two files
		FileTools.writeFile("newDir/file1.txt", header1.toString()+ "Toto rides his bike!");
		FileTools.writeFile("newDir/file2.txt", header2.toString()+ "Titi rides his bike!");
		
		String str = SourceTool.getHeaderComment("newDir/file1.txt");
		
		System.out.println(str);
		
		// get the comment
		Assert.assertEquals(header1.toString(), str);
		
		str = SourceTool.getHeaderComment("newDir/file2.txt");
		System.out.println(str);
		
		// get the comment
		Assert.assertEquals(header2.toString(), str);
		
		FileTools.deleteFile("newDir");
	}

	@Test
	public void removeAllCComment(){
		StringMultiLine text = new StringMultiLine(
				"/*",
				"* comment",
				"*", 
				"*/",
				"// jojo",
				"/**/",
				"Hello",
				"Bob/*  */"
				);
		
		String result = SourceTool.removeAllComment(text.toString());
		System.out.println(result);
		
		Assert.assertEquals("\n\nHello\nBob", result);
	}
	
	@Test
	public void removeAllComment2(){
		StringMultiLine source = new StringMultiLine(
		"package org.capcaval.lafabrique.ccdepend._test.sub;",
		"import java.awt.image.BufferedImage;",
		"import java.net.Socket;",
		"import javax.swing.JPanel;",
		"	// import toto",
		"	/*",
		"	 import titi;", 
		"	 import tutu;",
		"	 */",
		"import org.capcaval.lafabrique.ccdepend._test.sub.child2.child21.ClassChild211;",
		"public class TestClass {",
		"	BufferedImage bi;",
		"	JPanel pane;",
		"	Socket socket;",
		"	ClassChild211 child;",
		"}");
		
		StringMultiLine result = new StringMultiLine(
		"package org.capcaval.lafabrique.ccdepend._test.sub;",
		"import java.awt.image.BufferedImage;",
		"import java.net.Socket;",
		"import javax.swing.JPanel;",
		"\t\t",
		"import org.capcaval.lafabrique.ccdepend._test.sub.child2.child21.ClassChild211;",
		"public class TestClass {",
		"	BufferedImage bi;",
		"	JPanel pane;",
		"	Socket socket;",
		"	ClassChild211 child;",
		"}");
		
		
		String str = SourceTool.removeAllComment(source.toString());
		String resultStr = result.toString();
		
		System.out.println(str);
		System.out.println(resultStr);
		
		StringTools.compareStringForDebug(resultStr, str);
		
		Assert.assertEquals( resultStr, str);
	}

	@Test
	public void removeAllCPlusPLusComment(){
		StringMultiLine text = new StringMultiLine(
				"//",
				"// comment",
				"//", 
				"//",
				"//        ",
				"Hello",
				"Bob// comment2"
				);
		
		String result = SourceTool.removeAllComment(text.toString());
		System.out.println(result);
		
		Assert.assertEquals("Hello\nBob", result);
	}	
	@Test
	public void importListTest(){
		StringMultiLine fileStr = new StringMultiLine(
				"/*",
				"* comment",
				"*/", 
				"// other comment ",
				"",
				"",
				"  import org.capcaval.lafabrique.file.FileTools;",
				"	import org.capcaval.lafabrique.file.SourceTool;",
				"	 import org.capcaval.lafabrique.lang.StringMultiLine;",
				"  \nlaClasse",
				"",
				"this is text");

		String[] importArray = SourceTool.getImportListFromString(fileStr.toString());
		
		String[] expectedResult = new String[]{
				"org.capcaval.lafabrique.file.FileTools",
				"org.capcaval.lafabrique.file.SourceTool",
				"org.capcaval.lafabrique.lang.StringMultiLine"};
		
		System.out.println(Arrays.toString(expectedResult));
		System.out.println(Arrays.toString(importArray));
		
		Assert.assertTrue(Arrays.equals(expectedResult, importArray));

		
	}
	@Test
	public void packageTest(){
		StringMultiLine fileStr = new StringMultiLine(
				"",
				"	  ",
				"/*",
				"* comment",
				"*/", 
				"package toto.titi;",
				"// other comment ",
				"",
				"",
				"  import org.capcaval.lafabrique.file.FileTools;",
				"	import org.capcaval.lafabrique.file.SourceTool;",
				"	 import org.capcaval.lafabrique.lang.StringMultiLine;",
				"  \nlaClasse",
				"",
				"this is text");

		String packageStr = SourceTool.getPackageName(fileStr.toString());
		
		
		System.out.println(fileStr.toString());
		System.out.println("=>"+packageStr);
		
		Assert.assertEquals("toto.titi", packageStr);
	}
	
	@Test
	public void getAllParentPackageTest(){
		String pkg = "titi.tata.tutu.toto";
		
		String[] allParentArrayExpetedResult = new String[]{
				"titi",
				"titi.tata",
				"titi.tata.tutu",
				"titi.tata.tutu.toto"
		};
		
		String[] resultArray = SourceTool.getAllParentPackage(pkg);
		
		Assert.assertEquals(Arrays.equals(allParentArrayExpetedResult, resultArray), true);
	}
	
	@Test
	public void getSourceInfoTest(){
		String source = new StringMultiLine(
		"package org.capcaval.lafabrique;",
		"	",
		"import java.awt.image.BufferedImage;",
		"import java.net.Socket;",
		"import javax.swing.JPanel;",
		"	// import toto",
		"	/*",
		"	 import titi;", 
		"	 import tutu;",
		"	 */",
		"import org.capcaval.lafabrique.ccdepend._test.sub.child2.child21.ClassChild211;",
		"\t public static class	HypeClass {",
		"	BufferedImage bi;",
		"	JPanel pane;",
		"	Socket socket;",
		"	ClassChild211 child;",
		"}").toString();
		
		SourceInfo result = SourceTool.getSourceInfo(source);
		
		System.out.println(result);
		
		Assert.assertEquals("HypeClass", result.getName());
		Assert.assertEquals(SourceTypeEnum.classType, result.getType());
		Assert.assertEquals("org.capcaval.lafabrique", result.getPackageName());
	}
}
