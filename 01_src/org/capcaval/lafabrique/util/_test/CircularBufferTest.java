package org.capcaval.lafabrique.util._test;

import org.capcaval.lafabrique.util.CircularBuffer;
import org.junit.Assert;

public class CircularBufferTest {

	
	@org.junit.Test
	public void testCircularBuffer(){
		CircularBuffer<String> cb = new CircularBuffer<String>(3);
		
		cb.add("A");
		cb.add("B");
		Assert.assertArrayEquals(new String[]{"A","B"}, cb.getArray());
		
		cb.add("C");
		Assert.assertArrayEquals(new String[]{"A","B","C"}, cb.getArray());
		
		cb.add("D");
		Assert.assertArrayEquals(new String[]{"B","C","D"}, cb.getArray());

		cb.add("E");
		Assert.assertArrayEquals(new String[]{"C","D","E"}, cb.getArray());

		
	}

}
