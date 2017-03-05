package org.capcaval.lafabrique.file._test;

import junit.framework.Assert;

import org.capcaval.lafabrique.file.LocalRessourceLoader;
import org.junit.Test;

public class LocalRessourceLoaderTest {
	
	public class TestClass implements LocalRessourceLoader{}
	
	@Test
	public void localRessourceTestText(){
		TestClass tc = new TestClass();
		String str = tc.getLocalTextFile("dir2/File1.txt");
		
		Assert.assertEquals("file1", str);
	}
}
