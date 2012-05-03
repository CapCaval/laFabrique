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
package org.capcaval.c3.componentmanager.tools;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.capcaval.c3.component.Component;
import org.capcaval.c3.component.ComponentEvent;
import org.capcaval.c3.component.ComponentEventSubscribe;
import org.capcaval.c3.component.ComponentService;
import org.capcaval.c3.component.annotation.ComponentItem;
import org.capcaval.c3.component.annotation.ConsumedEvent;
import org.capcaval.c3.component.annotation.EventSubcribe;
import org.capcaval.c3.component.annotation.ProducedEvent;
import org.capcaval.c3.component.annotation.SubComponent;
import org.capcaval.c3.component.annotation.UsedService;
import org.capcaval.c3.componentmanager._impl.tools.ComponentItemDescription;
import org.capcaval.c3.componentmanager._impl.tools.SubComponentDescription;
import org.capcaval.c3.componentmanager._impl.tools.UsedEventSubscribeDescription;
import org.capcaval.c3.componentmanager._impl.tools.UsedServicesDescription;

public class ComponentAnalyserTool {

	
	static public Class<?> getComponentType(Class<?> implClass){
		Class<?>[] interfacesList = implClass.getInterfaces();
		
		boolean over = false;
		Class<?> componentType = null;
		int index = 0;
		Class<?> itf = null;
		
		while( over== false){
			
			if(index>=interfacesList.length){
				over = Boolean.TRUE;
			}else{
				itf = interfacesList[index++];
				if(Component.class.isAssignableFrom(itf))
					over = true;
				else
					itf = null;
			}
		};
		
		componentType=itf;
		
		return componentType;
	}

	@SuppressWarnings("unchecked")
	public static Class<? extends ComponentService>[] getProvidedServiceList(Class<?> cmpnClass) {

		return (Class<? extends ComponentService>[])getInterfaceList(cmpnClass, ComponentService.class);
	}
	
	
	/**
	 * @param implClass
	 * @param type
	 * @return
	 */
	static public Class<?>[] getInterfaceList(Class<?> implClass, Class<?> type){
		
		Class<?>[] interfacesList = implClass.getInterfaces();
		List <Class<?>> resultInterfacesList = new ArrayList<Class<?>>();

		for(Class<?>itf : interfacesList){
			if(type.isAssignableFrom(itf)){
				resultInterfacesList.add(itf);
			}
		}
		
		return resultInterfacesList.toArray(new Class<?>[0]);
	}

	@SuppressWarnings("unchecked")
	public static Class<? extends ComponentEvent>[] getProvidedEventList(
			Class<?> cmpnClass) {
		// allocate the return value
		List<Class<? extends ComponentEvent>> eventList = new ArrayList<Class<? extends ComponentEvent>>();
		
		// retrieve all the consumed event from factories 
		Class<? extends ComponentEvent>[] eventListFromFactory = (Class<? extends ComponentEvent>[])getMemberTypeListWithAnnotation(cmpnClass, ProducedEvent.class);
		for(Class<? extends ComponentEvent> eventType : eventListFromFactory){
			eventList.add(eventType);
		}
		return eventList.toArray(new Class[0]);
	}

	private static Class<?>[] getMemberTypeListWithAnnotation(
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

	private static Field[] getMemberFieldListWithAnnotation(
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
	
	private static Class<?>[] getMemberGenericTypeListWithAnotation(
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

	private static Method[] getMemberMethodListWithAnotation(
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

	
	public static UsedEventSubscribeDescription[] getUsedEventSubscribeList( Class<?> cmpnClass) {
		Field[] fieldList = getMemberFieldListWithAnnotation(cmpnClass, EventSubcribe.class);
		// allocate the eventSubscribeList with the same number of element
		UsedEventSubscribeDescription[] returnList = new UsedEventSubscribeDescription[fieldList.length];
		
		// fill the array of service description
		for(int i = 0; i< fieldList.length; i++){
			returnList[i] = new UsedEventSubscribeDescription( fieldList[i], cmpnClass);
		}
		
		return returnList;
	}

	public static Method[] getConsumeEventList( Class<?> cmpnClass) {

//		// allocate the return value
//		List<Class<? extends ComponentEvent>> eventList = new ArrayList<Class<? extends ComponentEvent>>();
//
//		
//		// retrieve secondly all the consumed event from the implementation
//		Class<?>[] EventImplementedList = getInterfaceList(cmpnClass, ComponentEvent.class);
//		for(Class<?> eventType : EventImplementedList){
//			eventList.add((Class<? extends ComponentEvent>)eventType);
//		}
		

		
		return getMemberMethodListWithAnotation(cmpnClass, ConsumedEvent.class);
	}


	public static UsedServicesDescription[] getUsedServiceFieldList( Class<?> cmpnClass) {
		Field[] fieldList = getMemberFieldListWithAnnotation(cmpnClass, UsedService.class);
		
		// allocate service description with the same size as the field
		UsedServicesDescription[] returnedDescList = new UsedServicesDescription[fieldList.length];
		
		// fill the array of service description
		for(int i = 0; i< fieldList.length; i++){
			returnedDescList[i] = new UsedServicesDescription( fieldList[i], cmpnClass);
		}
		
		return returnedDescList;
	}

	public static SubComponentDescription[] getSubComponentList(Class<?> cmpnClass) {
		Field[] fieldList = getMemberFieldListWithAnnotation(cmpnClass, SubComponent.class);
		
		// allocate the sub component description
		SubComponentDescription[] returnSubComponentDesc = new SubComponentDescription[fieldList.length];
		
		// fill the array of sub component description
		for(int i = 0; i< fieldList.length; i++){
			returnSubComponentDesc[i] = new SubComponentDescription( fieldList[i], cmpnClass);
		}
		
		return returnSubComponentDesc;
	}
	
	public static ComponentItemDescription[] getComponentItemList(Class<?> cmpnClass) {
		Field[] fieldList = getMemberFieldListWithAnnotation(cmpnClass, ComponentItem.class);
		
		// allocate the sub component item description
		ComponentItemDescription[] returnSubComponentDesc = new ComponentItemDescription[fieldList.length];
		
		// fill the array of sub component description
		for(int i = 0; i< fieldList.length; i++){
			returnSubComponentDesc[i] = new ComponentItemDescription( fieldList[i], fieldList[i].getType(), cmpnClass);
		}
		
		return returnSubComponentDesc;
	}
}
