package org.capcaval.lafabrique.lang;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.capcaval.lafabrique.lang._test.TestClass;

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
		Type type = method.getGenericReturnType();
		
		return getClassFromType(type);
	}
	
	
	public static Class<?> getClassFromType(Type type){
		Class<?> classType = null;
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
	
	public static Class<?> getGenericFieldType(Field field){
		
	    Type type = field.getGenericType();
	
	    return getClassFromType(type);
	}

	
	public static Class<?> getGenericFieldType(Class<?> type, String fieldName){
		Field field = null;
		try {
			field = type.getClass().getDeclaredField(fieldName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ReflectionTools.getGenericFieldType(field);
	}

	
	public static Method getMethodFromGenericInheritance(String methodName, Class<?> originSearch){
		// 
		Method returnedMethod = null;
		
		for(Method method : originSearch.getDeclaredMethods()){
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

	
	public static Class<?>[] getMemberGenericTypeListWithAnotation(
			Class<?> classToBeIntrospected, Class<? extends Annotation> annotationType) {

		// allocate the return value
		List<Class<?>> memberTypeList = new ArrayList<Class<?>>();
		
		// get all the members elements
		Field[] fieldList =  classToBeIntrospected.getDeclaredFields();
		
		for(Field field : fieldList){
			// for each check the annotation
			Annotation type = field.getAnnotation(annotationType);
			//Annotation[] annotationList = field.getDeclaredAnnotations();
			//if( annotationList.length > 0)
			if(type!=null)
				memberTypeList.add(field.getType());
		}
		
		return memberTypeList.toArray(new Class[0]);
	}
	
	
	public static Field[] getMemberFieldListWithAnnotation(
			Class<?> classToBeIntrospected, Class<? extends Annotation> annotationType) {

		// allocate the return value
		List<Field> memberTypeList = new ArrayList<Field>();
		
		// get all the members elements
		Field[] fieldList =  classToBeIntrospected.getDeclaredFields();
		
		for(Field field : fieldList){
			// for each check the annotation
			Annotation type = field.getAnnotation(annotationType);

			
			if(type!=null)
				memberTypeList.add(field);
		}
		
		return memberTypeList.toArray(new Field[0]);
	}
	
	public static Class<?>[] getMemberTypeListWithAnnotation(
			Class<?> classToBeIntrospected, Class<? extends Annotation> annotationType) {

		// allocate the return value
		List<Class<?>> memberTypeList = new ArrayList<Class<?>>();
		
		// get all the members elements
		Field[] fieldList =  classToBeIntrospected.getDeclaredFields();
		
		for(Field field : fieldList){
			// for each check the annotation
			Annotation type = field.getAnnotation(annotationType);
			//Annotation[] annotationList = field.getDeclaredAnnotations();
			//if( annotationList.length > 0)
			if(type!=null)
				memberTypeList.add(field.getType());
		}
		
		return memberTypeList.toArray(new Class[0]);
	}
	
	public static Method[] getMemberMethodListWithAnotation(
			Class<?> classToBeIntrospected, Class<? extends Annotation> annotationType) {

		// allocate the return value
		List<Method> memberTypeList = new ArrayList<Method>();
		
		// get all the members elements
		Method[] methodList =  classToBeIntrospected.getDeclaredMethods();
		
		for(Method method : methodList){
			// for each check the annotation
			Annotation type = method.getAnnotation(annotationType);
			//Annotation[] annotationList = field.getDeclaredAnnotations();
			//if( annotationList.length > 0)
			if(type!=null)
				memberTypeList.add(method );
		}
		
		return memberTypeList.toArray(new Method[0]);
	}


	public static Method[] getMethod(Class<?> type, String methodName) {
		Method[] methodArray = type.getMethods();
		List<Method> methodList = new ArrayList<>();
		
		for(Method m : methodArray){
			if(m.getName().equals(methodName)){
				methodList.add(m);
			}
		}
		return methodList.toArray(new Method[0]);
	}


	
}