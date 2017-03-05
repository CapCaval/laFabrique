package org.capcaval.lafabrique.lang.object._test;


import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.capcaval.lafabrique.lang.object.ObjectTools;
import org.capcaval.lafabrique.lang.object._impl.ObjectToolsImpl;


public class ObjectToolsTest {

	
	@org.junit.Test
	public void copyToTest(){
		ObjectTools objectTools = ObjectTools.factory.newInstance();
		
		Parent parent = new Parent();
		parent.val = 456.456f;
		parent.namesArray = new String[]{"joe", "paul"};
		parent.child = new Child();
		parent.child.age = 18;
		parent.child.size = 1.78;
		
		Parent sameParent = new Parent();
		objectTools.copyObject(parent, sameParent);
		
		Assert.assertEquals(parent.val, sameParent.val);
		Assert.assertEquals(parent.child.age, sameParent.child.age);
		Assert.assertEquals(parent.child.size, sameParent.child.size);
		Assert.assertEquals(parent.namesArray[0], sameParent.namesArray[0]);
		Assert.assertEquals(parent.namesArray[1], sameParent.namesArray[1]);
		Assert.assertEquals(parent.namesArray.length, sameParent.namesArray.length);
	}

	@org.junit.Test
	public void cloneToTest(){
		Parent parent = new Parent();
		parent.val = 456.456f;
		parent.namesArray = new String[]{"joe", "paul"};
		parent.child = new Child();
		parent.child.age = 18;
		parent.child.size = 1.78;
		
		ObjectTools objectTools = ObjectTools.factory.newInstance();
		Parent sameParent = objectTools.cloneObject(parent);
		
		Assert.assertEquals(parent.val, sameParent.val);
		Assert.assertEquals(parent.child.age, sameParent.child.age);
		Assert.assertEquals(parent.child.size, sameParent.child.size);
		Assert.assertEquals(parent.namesArray[0], sameParent.namesArray[0]);
		Assert.assertEquals(parent.namesArray[1], sameParent.namesArray[1]);
		Assert.assertEquals(parent.namesArray.length, sameParent.namesArray.length);
	}
	
	@org.junit.Test
	public void binaryTest(){
		ObjectTools objectTools = ObjectTools.factory.newInstance();

		byte[] array = objectTools.objectToBinary(1414);
		int value = objectTools.binaryToObject(int.class, array);
		Assert.assertEquals(1414, value);
		
		array = objectTools.objectToBinary(1414.14);
		double doubleValue = objectTools.binaryToObject(double.class, array);
		Assert.assertEquals(1414.14, doubleValue);

		array = objectTools.objectToBinary(1414.14f);
		float floatValue = objectTools.binaryToObject(float.class, array);
		Assert.assertEquals(1414.14f, floatValue);
		
	}
	
	@org.junit.Test
	public void binaryListAndArrayTest(){
		ObjectTools objectTools = ObjectTools.factory.newInstance();
		
		Parent parent = new Parent();
		parent.val = 456.456f; // size 4
		parent.child = new Child();
		parent.child.age = 18; // size int 4
		parent.child.size = 1.78; // size double 8
		parent.namesArray = new String[]{"joe", "paul"}; // size 30 => 4(list size); 4(string size); 8("joe\00"); 4(string size); 10("paul")
		// total 46
		
		byte[] array = objectTools.objectToBinary(parent);
		
		Assert.assertEquals(46, array.length);
		
		ByteBuffer b = ByteBuffer.allocate(46);
		b.putFloat(parent.val);
		b.putDouble(parent.child.size);
		b.putInt(parent.child.age);
		b.putInt(parent.namesArray.length);
		b.putInt(parent.namesArray[0].length());
		b.put(parent.namesArray[0].getBytes(Charset.forName("UTF-16")));
		b.putInt(parent.namesArray[1].length());
		b.put(parent.namesArray[1].getBytes(Charset.forName("UTF-16")));
		
		byte[] ref = b.array();
		Assert.assertTrue(Arrays.equals(ref, array));
		
		Parent sameParent = objectTools.binaryToObject(Parent.class, array);
		
		Assert.assertEquals(parent.val, sameParent.val);
		Assert.assertEquals(parent.child.age, sameParent.child.age);
		Assert.assertEquals(parent.child.size, sameParent.child.size);
		Assert.assertEquals(parent.namesArray[0], sameParent.namesArray[0]);
		Assert.assertEquals(parent.namesArray[1], sameParent.namesArray[1]);
		Assert.assertEquals(parent.namesArray.length, sameParent.namesArray.length);
		
	}
	
	@org.junit.Test
	public void sizeofTest(){
		ObjectToolsImpl objectTools = new ObjectToolsImpl();
		
		Assert.assertEquals(4, objectTools.sizeOf(Integer.valueOf(5)));
		Assert.assertEquals(8, objectTools.sizeOf(Long.valueOf(5)));
		Assert.assertEquals(2, objectTools.sizeOf(Short.valueOf((short)5)));
		Assert.assertEquals(4, objectTools.sizeOf(Float.valueOf(5f)));
		Assert.assertEquals(8, objectTools.sizeOf(Double.valueOf(5.0)));
		Assert.assertEquals(2, objectTools.sizeOf(Byte.valueOf((byte)5)));
		Assert.assertEquals(2, objectTools.sizeOf(Character.valueOf((char)5)));
		Assert.assertEquals(1, objectTools.sizeOf(Boolean.valueOf(true)));
		
		Assert.assertEquals(24, objectTools.sizeOf(String.valueOf("0123456789"))); // 10 * 2 + 4
		
	
	}	
	
	@org.junit.Test
	public void sizeofListAndArrayTest(){
		ObjectToolsImpl objectTools = new ObjectToolsImpl();
	
		int[] array = new int[]{0,1,2,3,4,5,6,7,8,9};
		Assert.assertEquals(44, objectTools.sizeOf(array)); // 10 * 4 + 4
		
		List<Double> list = new ArrayList<>();
		list.add(0.0);
		list.add(1.0);
		list.add(2.0);
		list.add(3.0);
		list.add(4.0);
		list.add(5.0);
		list.add(6.0);
		list.add(7.0);
		list.add(8.0);
		list.add(9.0);
		Assert.assertEquals(84, objectTools.sizeOf(list)); // 10 * 4 + 4

	}
	
	@org.junit.Test
	public void sizeofAggregateTest(){
		Parent parent = new Parent(); // 4
		parent.child = new Child(); // 12
		parent.namesArray=new String[]{"012", "3456789"}; // (3*2 + 4) + (7*2 + 4) + 4 = 10 + 18 + 4 = 32
		
		ObjectToolsImpl objectTools = new ObjectToolsImpl();
		int size  = objectTools.sizeOf(parent);
		
		Assert.assertEquals(48, size);
	}
	
	
	@org.junit.Test
	public void objectToPropertyFileTest(){
		Child child = new Child();
		
		ObjectTools objectTools = ObjectTools.factory.newInstance();
		
		objectTools.objectToPropertyFile(child);
		
	}
}
