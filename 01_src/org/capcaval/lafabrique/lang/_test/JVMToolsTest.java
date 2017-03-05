package org.capcaval.lafabrique.lang._test;

import java.nio.file.Paths;

import junit.framework.Assert;

import org.capcaval.lafabrique.file.FileTools;
import org.capcaval.lafabrique.lang.ClassTools;
import org.capcaval.lafabrique.lang.JVM;
import org.capcaval.lafabrique.lang.JVM.JVMTypeEnum;
import org.capcaval.lafabrique.lang.JVMTools;
import org.capcaval.lafabrique.lang.Version;
import org.junit.Test;

public class JVMToolsTest {

	@Test
	public void seekJava(){
		
		FileTools.deleteFile("temp/java");
		
		// create all file to test
		FileTools.writeFile("temp/java/jdk1.5.2_22/bin/java", "fake linux java bin");
		FileTools.writeFile("temp/java/jdk1.5.2_22/bin/java.exe", "fake windows java bin");
		
		FileTools.writeFile("temp/java/jre1.6.2_26/bin/java", "fake linux java bin");
		FileTools.writeFile("temp/java/jre1.6.2_26/bin/java.exe", "fake windows bin");
		
		FileTools.writeFile("temp/java/jre1.7/bin/java", "fake linux java bin");
		FileTools.writeFile("temp/java/jre1.7/bin/java.exe", "fake windows java bin");
		
		FileTools.writeFile("temp/java/jdk1.8.0_25/bin/java", "fake linux java bin");
		FileTools.writeFile("temp/java/jdk1.8.0_25/bin/java.exe", "fake windows java bin");
		
		
		JVM[] jvmArray = JVMTools.findJDK(Paths.get("temp"));
		
		JVM jvm1 = jvmArray[0];
		JVM jvm2 = jvmArray[1];
		JVM jvm3 = jvmArray[2];
		JVM jvm4 = jvmArray[3];
		
		Assert.assertEquals("jdk1.5.2_22", jvm3.getName());
		Assert.assertEquals(JVMTypeEnum.JDK, jvm3.getJvmType());
		Assert.assertEquals("temp/java/jdk1.5.2_22", jvm3.getPath().toString());
		Assert.assertTrue( Version.factory.newVersion("1.5.2_22").isThisVersionSameThan(jvm3.getVersion()));

		Assert.assertEquals("jre1.6.2_26", jvm1.getName());
		Assert.assertEquals(JVMTypeEnum.JRE, jvm1.getJvmType());
		Assert.assertEquals("temp/java/jre1.6.2_26", jvm1.getPath().toString());
		Assert.assertTrue( Version.factory.newVersion("1.6.2_26").isThisVersionSameThan(jvm1.getVersion()));

		Assert.assertEquals("jre1.7", jvm4.getName());
		Assert.assertEquals(JVMTypeEnum.JRE, jvm4.getJvmType());
		Assert.assertEquals("temp/java/jre1.7", jvm4.getPath().toString());
		Assert.assertTrue( Version.factory.newVersion("1.7").isThisVersionSameThan(jvm4.getVersion()));

		Assert.assertEquals("jdk1.8.0_25", jvm2.getName());
		Assert.assertEquals(JVMTypeEnum.JDK, jvm2.getJvmType());
		Assert.assertEquals("temp/java/jdk1.8.0_25", jvm2.getPath().toString());
		Assert.assertTrue( Version.factory.newVersion("1.8.0_25").isThisVersionSameThan(jvm2.getVersion()));
		
		FileTools.deleteFile("temp/java");
	}
	
}
