package org.capcaval.ccoutils.lang._test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.capcaval.ccoutils.lang.ReflectionTools;
import org.junit.Assert;
import org.junit.Test;

public class ReflectionToolsTest {

	@Target(ElementType.PARAMETER)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface TestingAnnotation {
	}

	public class TestingClass {
		public void testingMethod(@TestingAnnotation String test) {
		};

		@Test
		public void GenricReturningMethod() throws NoSuchMethodException,
				SecurityException {
			Method method = TestClass.class.getMethod("getValueList", null);

			Class<?> type = ReflectionTools.getGenericReturnedType(method);

			Assert.assertEquals(Integer.class, type);
		}

		@Test
		public void gettingMethodFromAbstractInheritance() {
			Method m = ReflectionTools.getMethodFromGenericInheritance(
					"getInstance", TestClass.class);

			Assert.assertEquals(m.getReturnType(), Double.class);
		}
	}
}
