package org.capcaval.ccoutils.lang;

import java.lang.annotation.Annotation;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectionTools {

		//TODO rewrite
	  static Type getActualTypeForFirstTypeVariable(Type type) {
		    if (type instanceof Class) {
		      return Object.class;
		    } else if (type instanceof ParameterizedType) {
		      return ((ParameterizedType)type).getActualTypeArguments()[0];
		    } else if (type instanceof GenericArrayType) {
		      return getActualTypeForFirstTypeVariable(((GenericArrayType)type).getGenericComponentType());
		    } else {
		      throw new IllegalArgumentException("Type \'" + type + "\' is not a Class, "
		          + "ParameterizedType, or GenericArrayType. Can't extract class.");
		    }
		  }
	
	
	public static Class<?> getGenericReturnedType(Method method){

		Class<?> classType = null;
		
		Type type = method.getGenericReturnType();
		
		type = getActualTypeForFirstTypeVariable(type);

		if(type instanceof ParameterizedType){
		    ParameterizedType ptype = (ParameterizedType) type;
		    Type rawType = ptype.getRawType();
		    Type[] typeArgumentList = ptype.getActualTypeArguments();
		    classType = (Class<?>) typeArgumentList[0];
		}else if (type instanceof GenericArrayType) {
			GenericArrayType gtype = (GenericArrayType)type;
			try{
			classType = (Class<?>) gtype.getGenericComponentType();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}else if (type instanceof Class) {
			classType = (Class<?>) type;
		}
		
		return classType;
	}
	
	public static Method getMethodFromGenericInheritance(String methodName, Class<?> originSearch){
		// 
		Method returnedMethod = null;
		
		for(Method method : originSearch.getMethods()){
			if((method.getName().equals(methodName))&&(method.getReturnType()!= Object.class)){
				returnedMethod = method;
				break;
			}
		}		
		return returnedMethod;
	}
	
	public static <T extends Annotation> List<T> getAnnotationInstance(Method method, Class<T>type){
		List<T> returnList = new ArrayList<>();
		Annotation[][] anList = method.getParameterAnnotations();
		for(Annotation[] annotationList : anList){
			for(Annotation ann : annotationList){
				if(ann.annotationType() == type){
					returnList.add((T)ann);
				}
			}
		}
		return returnList;
	}
}
