package org.capcaval.lafabrique.lang;

import java.util.ArrayList;
import java.util.List;

import org.capcaval.lafabrique.lang.listProcessor.ListProcessor;

public class ListTools {

	public static <T>void compute(List<T> list, ListProcessor<T> listProcessor) {
		List<Object> toBeRemovedList = new ArrayList<>();
		for(T obj: list){
			// element to be kept
			boolean keep = listProcessor.compute(obj);
			
			// add to the to be removed list is matched
			if(keep == false){
				toBeRemovedList.add(obj);}
		}
		//delete all requested elements
		for(Object o : toBeRemovedList){
			list.remove(o);
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public static <T> T[] compute(T[] list, ListProcessor<T> listProcessor) {
		List<T> returnedList = ArrayTools.newArrayList(list);
		
		ListTools.compute(returnedList, listProcessor);
		
		return (T[])ArrayTools.convertToArray((List<Object>)returnedList, list.getClass().getComponentType());
	}


	public static <T> void concat(List<T> origin, T[] newList) {
		for(T o : newList){
			origin.add(o);
		}
	}

	public static <T> void concat(List<T> origin, List<T> newList) {
		for(T o : newList){
			origin.add(o);
		}
	}


	static public <T> List<T> newArrayList(T... objectList){
		//allocate the array at the correct size
		List<T> list = new ArrayList<T>(objectList.length);
		
		// add all element inside
		for(T obj : objectList){
			list.add(obj);
		}
		// this is it bye
		return list;
	}
}
