package org.capcaval.lafabrique.recycling._test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.capcaval.lafabrique.recycling.Flush;
import org.capcaval.lafabrique.recycling.ObjectPool;


public class RecyclingTest {

	/**
	 * @
	 */
	@org.junit.Test
	public void normalPoolUseTest(){

		int poolSize = 1000;
		
		ObjectPool<MutableInteger> integerPool = new ObjectPool<MutableInteger>(MutableInteger.class, poolSize);

		// check that all instance are free and none are used
		Assert.assertEquals(integerPool.getUsedInstanceNumber(), 0);
		Assert.assertEquals(integerPool.getFreeInstanceNumber(), poolSize);
		
		List<MutableInteger> list = new ArrayList<MutableInteger>();
		
		for(int i=0; i<poolSize; i++){
			MutableInteger intValue = integerPool.getNewFreeInstance();
			intValue.setValue(i);
			list.add(intValue);
		}

		// check that all instance are used and none are freed
		Assert.assertEquals(integerPool.getUsedInstanceNumber(), poolSize);
		Assert.assertEquals(integerPool.getFreeInstanceNumber(), 0);

		// release all instances
		for(MutableInteger instance: list){
			integerPool.releaseInstance(instance);
		}
		// check that all instance are free and none are used
		Assert.assertEquals(integerPool.getUsedInstanceNumber(), 0);
		Assert.assertEquals(integerPool.getFreeInstanceNumber(), poolSize);
	}

	@org.junit.Test
	public void poolUseOverSizeTest(){
		int poolSize = 1000;
		
		ObjectPool<MutableInteger> integerPool = new ObjectPool<MutableInteger>(MutableInteger.class, poolSize);

		// check that all instance are free and none are used
		Assert.assertEquals(integerPool.getUsedInstanceNumber(), 0);
		Assert.assertEquals(integerPool.getFreeInstanceNumber(), poolSize);
		
		for(int i=0; i<poolSize*2; i++){
			MutableInteger intValue = integerPool.getNewFreeInstance();
			intValue.setValue(i);
		}
	}
	
	
	/**
	 * @
	 */
	@org.junit.Test
	public void poolWithFlushTest(){

		int poolSize = 1000;
		
		// create a flush feature
		Flush<MutableInteger> flush = new Flush<MutableInteger>(){
			@Override
			public void flushInstance(MutableInteger obj) {
				obj.setValue(-1);
			}
		};
		
		ObjectPool<MutableInteger> integerPool = new ObjectPool<MutableInteger>(
				MutableInteger.class, poolSize, flush);

		// check that all instance are free and none are used
		Assert.assertEquals(integerPool.getUsedInstanceNumber(), 0);
		Assert.assertEquals(integerPool.getFreeInstanceNumber(), poolSize);
		
		List<MutableInteger> list = new ArrayList<MutableInteger>();
		
		for(int i=0; i<poolSize; i++){
			MutableInteger intValue = integerPool.getNewFreeInstance();
			intValue.setValue(i);
			list.add(intValue);
		}

		// check that all instance are used and none are freed
		Assert.assertEquals(integerPool.getUsedInstanceNumber(), poolSize);
		Assert.assertEquals(integerPool.getFreeInstanceNumber(), 0);

		// release all instances
		for(MutableInteger instance: list){
			integerPool.releaseInstance(instance);
			Assert.assertEquals(instance.getValue(), -1);
		}
		// check that all instance are free and none are used
		Assert.assertEquals(integerPool.getUsedInstanceNumber(), 0);
		Assert.assertEquals(integerPool.getFreeInstanceNumber(), poolSize);
	}
}
