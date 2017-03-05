package org.capcaval.lafabrique.lang._test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.capcaval.lafabrique.lang.ReflectionTools;
import org.junit.Assert;
import org.junit.Test;
import org.junit.internal.runners.TestMethod;

public class ReflectionToolsTest {
	
	@Deprecated
	String test;
	
	@Deprecated
	public void testMethod(){
		// do nothing it is just for test
	}
	
	@Test
	public void genricReturningMethodTest() throws NoSuchMethodException,
			SecurityException {
		Method method = TestClass.class.getMethod("getValueList");

		Class<?> type = ReflectionTools.getGenericReturnedType(method);

		Assert.assertEquals(Integer.class, type);
	}

	@Test
	public void gettingMethodFromAbstractInheritanceTest() {
		Method m = ReflectionTools.getMethodFromGenericInheritance(
				"getInstance", TestClass.class);

		Assert.assertEquals(m.getReturnType(), Double.class);
	}
	
	@Test
	public void getGenericFieldtypeTest(){
		try{
			Class<?> type = ReflectionTools.getGenericFieldType(TestClass.class, "list");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		// Does not work
		//Assert.assertEquals(Boolean.class, type);
		
	}
	
	@Test
	public void memberFieldListWithAnnotationTest(){
		Field[] fieldArray = ReflectionTools.getMemberFieldListWithAnnotation(
				this.getClass(), Deprecated.class);
		
		Assert.assertEquals(1, fieldArray.length);
		Assert.assertEquals( "test", fieldArray[0].getName());
		
	}

	@Test
	public void memberTypeListWithAnnotationTest(){
		Class<?>[] classArray = ReflectionTools.getMemberTypeListWithAnnotation(
				this.getClass(), Deprecated.class);
		
		Assert.assertEquals(1, classArray.length);
		Assert.assertEquals( String.class, classArray[0]);
	}

	@Test
	public void memberMethodListWithAnotationTest(){
		Method[] methodArray = ReflectionTools.getMemberMethodListWithAnotation(
				this.getClass(), Deprecated.class);
		
		Assert.assertEquals(1, methodArray.length);
		Assert.assertEquals( "testMethod", methodArray[0].getName());
	}
	
	
	public class TestMethodClass{
		public String getValue(int val1, String val2){ return "method1";}
		public String getValue(Float val1, String val2){ return "method2";}
		public String getValues(Float val1, String val2){ return "method3";}
	}
	
	@Test
	public void getMethodTest(){
		Method[] resultArray = ReflectionTools.getMethod(TestMethodClass.class, "getValue");
		
		Assert.assertEquals("getValue", resultArray[0].getName());
		Assert.assertEquals("getValue", resultArray[1].getName());
		Assert.assertEquals(2, resultArray.length);
		
	}
}
