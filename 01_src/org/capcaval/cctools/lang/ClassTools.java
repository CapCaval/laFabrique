package org.capcaval.cctools.lang;

import java.lang.reflect.Constructor;
import java.util.Arrays;

public class ClassTools {
	public static boolean containNoArgumentConstructor(Class<?> type){
		Constructor<?> c = null;
		try {
			Constructor<?>[] ctorList = type.getConstructors();
			for(Constructor<?>ctor : ctorList){
				System.out.println(Arrays.toString(ctor.getParameterTypes()));
				// 1 means no parameter
				if(ctor.getParameterTypes().length==1){
					c=ctor;
					break;
				}
			}
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// return true if a ctor without parameter found
		return c==null?false:true;
	}
	
	public static Class<?>[] getAllType(final Object[] objList){
		// allocate the same size array
		Class<?>[] classTypeList = new Class<?>[objList.length];
		for(int i=0; i< objList.length; i++){
			classTypeList[i]=objList[i].getClass();
		}
		return classTypeList;
	}

}
