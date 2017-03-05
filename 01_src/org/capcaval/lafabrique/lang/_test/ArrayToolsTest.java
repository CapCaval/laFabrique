/*
Copyright (C) 2012 by CapCaval.org

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/
package org.capcaval.lafabrique.lang._test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.capcaval.lafabrique.lang.ArrayTools;
import org.junit.Assert;

public class ArrayToolsTest {
	
	@org.junit.Test
	public void testFindChar(){
		char[] charList = new char[]{'_','a','8','B'};
		ArrayTools.contains(charList, '8');
		boolean result = ArrayTools.contains(charList, '8');
		Assert.assertEquals(result, true);
	}

	@org.junit.Test
	public void testCreateArray(){
		List<String> list = ArrayTools.newArrayList("a", "b", "c", "d");
		
		List<String> list2 = new ArrayList<String>();
		list2.add("a");
		list2.add("b");
		list2.add("c");
		list2.add("d");		
		
		Assert.assertTrue(list2.equals(list));
	}

	@org.junit.Test
	public void testCreateArray2(){
		List<String> list = ArrayTools.newArrayList(new String[]{"a", "b", "c", "d"});
		
		List<String> list2 = new ArrayList<String>();
		list2.add("a");
		list2.add("b");
		list2.add("c");
		list2.add("d");		
		
		Assert.assertTrue(list2.equals(list));
	}

	
	@org.junit.Test
	public void testCreateArrayArray(){
		List<String> list = ArrayTools.newArrayList(new String[][]{
				{ "a", "b"},
				{ "c", "d"}});
		
		List<String> list2 = new ArrayList<String>();
		list2.add("a");
		list2.add("b");
		list2.add("c");
		list2.add("d");		
		
		Assert.assertTrue(list2.equals(list));
	}

	
	@org.junit.Test
	public void testCreateMap(){
		Map<String, String> map = ArrayTools.newMap("un", "1", "deux", "2", "trois", "3");
		
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("un", "1");
		map2.put("deux", "2");
		map2.put("trois", "3");
		
		Assert.assertEquals(map, map2);
	}
	
	@org.junit.Test
	public void testMultiLine(){
		String multiLine = ArrayTools.toMultiLine("line1", "line2");
		
		System.out.println(multiLine);
		Assert.assertEquals("line1\nline2\n", multiLine);
		
	}

	@org.junit.Test
	public void testPrefix(){
		String[] result = ArrayTools.toStringWithPrefix("A", new Integer[]{1,2,3,4});
		Assert.assertArrayEquals(new String[]{"A1","A2","A3","A4"}, result);
	}

	@org.junit.Test
	public void testPosfix(){
		String[] result = ArrayTools.toStringWithPosfix("B", new Integer[]{1,2,3,4});
		Assert.assertArrayEquals(new String[]{"1B","2B","3B","4B"}, result);
	}

	@org.junit.Test
	public void testArrayConcat(){
		String[] array1 = new String[]{"1","2","3","4"};
		String[] array2 = new String[]{"5","6"};
		String[] arrayResult = new String[]{"1","2","3","4","5","6"};
		
		String[] result = ArrayTools.concat(array1, array2);
		Assert.assertArrayEquals(arrayResult, result);
		
		result = ArrayTools.concat(array1, new String[]{});
		Assert.assertArrayEquals(result, array1);
		
		result = ArrayTools.concat(array1, null);
		Assert.assertArrayEquals(result, array1);
		
		result = ArrayTools.concat(array1, new String[]{});
		Assert.assertArrayEquals(result, array1);
		
		result = ArrayTools.concat(null, array2);
		Assert.assertArrayEquals(result, array2);
		
		result = ArrayTools.concat(new String[]{}, array2);
		Assert.assertArrayEquals(result, array2);

	}
	
	@org.junit.Test
	public void testAddArray(){
		Integer[] array = new Integer[]{1,2,3,4,5};
		array = ArrayTools.add(6, array);
		// chack out tha the 6 value has been added
		Assert.assertArrayEquals(new Integer[]{1,2,3,4,5,6}, array);
		
	}

	@org.junit.Test
	public void testRemoveElementArray(){
		Integer[] array = new Integer[]{1,2,3,4,5};
		array = ArrayTools.removeElement(2, array);
		// chack out tha the 6 value has been added
		Assert.assertArrayEquals(new Integer[]{1,3,4,5}, array);
	}
	
	@org.junit.Test
	public void testRemoveElementArrayAt(){
		Integer[] array = new Integer[]{1,2,3,4,5};
		
		Integer[] resultArray = ArrayTools.removeElementAt(array, 2);
		// check out that the 2 value has been removed
		Assert.assertArrayEquals(new Integer[]{1,3,4,5}, resultArray);
		
		// assert first element
		resultArray = ArrayTools.removeElementAt(array, 1);
		// check out that the first value has been removed
		Assert.assertArrayEquals(new Integer[]{2,3,4,5}, resultArray);		

		// assert last element
		resultArray = ArrayTools.removeElementAt(array, 5);
		// check out that the last value has been removed
		Assert.assertArrayEquals(new Integer[]{1,2,3,4}, resultArray);	
		
		// assert error element
		try{
			resultArray = ArrayTools.removeElementAt(array, 6);
			Assert.assertTrue(false);
		}catch(Exception e){
			// exception is excepted
			e.printStackTrace();
			Assert.assertTrue(true);
		}
	}
	
	@org.junit.Test
	public void subArrayTest(){
		String[] array = ArrayTools.newArray("a", "b", "c", "d", "e");
		String[] subArray = ArrayTools.getSubArray(array, 1, 3);
		
		Assert.assertTrue(Arrays.equals(new String[]{"b","c","d"}, subArray));
	}
	
	@org.junit.Test
	public void byteArrayToHexaStringTest(){
		byte[]array = new byte[]{(byte)0xCA, (byte)0xFE, (byte)0xBA, (byte)0xBE};
		String str = ArrayTools.byteArrayToHexaString(array);
		
		Assert.assertEquals("CA FE BA BE", str);
	}	

	
	@org.junit.Test
	public void byteArrayToCompactHexaStringTest(){
		byte[]array = new byte[]{(byte)0xCA, (byte)0xFE, (byte)0xBA, (byte)0xBE};
		String str = ArrayTools.byteArrayToCompactHexaString(array);
		
		Assert.assertEquals("CAFEBABE", str);
	}
	
	@org.junit.Test
	public void compactHexaStringTobyteArrayTest(){
		byte[]resultArray = ArrayTools.compactHexaStringToByteArray("CAFEBABE");
		
		Assert.assertArrayEquals(new byte[]{(byte)0xCA, (byte)0xFE, (byte)0xBA, (byte)0xBE}, resultArray);
	}
	
}
