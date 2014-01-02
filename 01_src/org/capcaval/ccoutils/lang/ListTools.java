package org.capcaval.ccoutils.lang;

import java.util.ArrayList;
import java.util.List;

import org.capcaval.ccoutils.lang.listProcessor.ListProcessor;

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
}
