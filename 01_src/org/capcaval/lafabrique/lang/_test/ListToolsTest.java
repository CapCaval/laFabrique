package org.capcaval.lafabrique.lang._test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.capcaval.lafabrique.lang.ArrayTools;
import org.capcaval.lafabrique.lang.ListTools;
import org.capcaval.lafabrique.lang.listProcessor.ListProcessor;
import org.capcaval.lafabrique.lang.listProcessor.RemoveContainsFileListProcessor;
import org.capcaval.lafabrique.lang.listProcessor.RemoveContainsListProcessor;
import org.capcaval.lafabrique.lang.listProcessor.RemoveInstanceListProcessor;
import org.junit.Assert;
import org.junit.Test;

public class ListToolsTest {

	@Test
	public void ListProcessorTest() {
		List<String> list = ArrayTools.newArrayList(
				"titi",
				"toto",
				"tata",
				"totogt");
		ListTools.compute(list, new ListProcessor<String>(){
			@Override
			public boolean compute(String obj) {
				boolean returnValue = true;
				
				if(obj.contains("toto"))
					returnValue = false;
							
				return returnValue;
			}
		});
		
		Assert.assertTrue(Arrays.equals(
				list.toArray(new String[0]), ArrayTools.newArray("titi","tata")));
		
		
	}

	
	@Test
	public void RemoveListProcessorTest() {
		List<String> list = ArrayTools.newArrayList(
				"titi",
				"toto",
				"tata",
				"totogt",
				"ttutuo");
		
		ListTools.compute(list, new RemoveInstanceListProcessor<String>("toto", "tutu"));
		
		Assert.assertTrue(Arrays.equals(
				list.toArray(new String[0]), ArrayTools.newArray("titi","tata", "totogt", "ttutuo")));
	}
	
	@Test
	public void RemoveContainsListProcessorTest() {
		List<String> list = ArrayTools.newArrayList(
				"titi",
				"toto",
				"tata",
				"totogt",
				"ttutuo");
		
		ListTools.compute(list, new RemoveContainsListProcessor("toto", "tutu"));
		
		Assert.assertTrue(Arrays.equals(
				list.toArray(new String[0]), ArrayTools.newArray("titi","tata")));
	}	
	
	@Test
	public void RemoveContainsFileListProcessorTest() {
		List<File> list = ArrayTools.newArrayList(
				new File("./titi/a"),
				new File("toto"),
				new File("tata"),
				new File("totogt"),
				new File("ttutuo"));
		
		File[] fileArray = ListTools.compute(list.toArray(new File[0]), new RemoveContainsFileListProcessor("toto", "tutu"));
		
		Assert.assertEquals(new File("./titi/a"), fileArray[0]);
		Assert.assertEquals(new File("tata"), fileArray[1]);
		Assert.assertEquals(2, fileArray.length);
	}
}
