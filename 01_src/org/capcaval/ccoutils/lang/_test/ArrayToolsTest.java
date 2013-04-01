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
package org.capcaval.ccoutils.lang._test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.capcaval.ccoutils.lang.ArrayTools;

import junit.framework.Assert;

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

	
}
