package org.capcaval.ccoutils.data._test;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

import org.capcaval.ccoutils.data.DataList;
import org.capcaval.ccoutils.data.DataListEvent;
import org.capcaval.ccoutils.data._impl.DataListImpl;
import org.junit.Assert;
import org.junit.Test;

public class DataListTest {
	
	@Test
	public void dataListTestAdd1(){
		DataList<Integer> dlist = new DataListImpl<>();
		
		final AtomicReference<Integer[]> resultList = new AtomicReference<>();
		dlist.subscribeToData(new DataListEvent<Integer>() {
			@Override
			public void notifyObjectCreated(Integer... objectList) {
				System.out.println(Arrays.toString(objectList));
				resultList.set(objectList);
			}
			@Override public void notifyObjectDeleted(Integer... objectList) {}
			@Override public void notifyObjectUpdated(Integer... objectList) {}
		});
		
		Integer[] list = new Integer[]{1,2,3,4,5};
		dlist.addObject(list);
		Assert.assertArrayEquals(list, resultList.get());
	}
	@Test
	public void dataListTestAdd2(){
		DataList<Integer> dlist = new DataListImpl<Integer>();

		Integer[] list = new Integer[]{1,2,3,4,5};
		dlist.addObject(list);
		
		final AtomicReference<Integer[]> resultList = new AtomicReference<>();
		dlist.subscribeToData(new DataListEvent<Integer>() {
			@Override
			public void notifyObjectCreated(Integer... objectList) {
				System.out.println(Arrays.toString(objectList));
				resultList.set(objectList);
			}
			@Override public void notifyObjectDeleted(Integer... objectList) {}
			@Override public void notifyObjectUpdated(Integer... objectList) {}
		});
		dlist.addObject(2, 12);
		Assert.assertArrayEquals(new Integer[]{12}, resultList.get());
		Assert.assertArrayEquals(new Integer[]{1,2,12, 3,4,5}, dlist.getAllObject());
	}

	
}
