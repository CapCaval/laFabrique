package org.capcaval.ccoutils.lang.listProcessor;

import java.util.List;

import org.capcaval.ccoutils.lang.ArrayTools;


public class RemoveInstanceListProcessor<T> implements ListProcessor<T>{ 

	List<T> removeElementList;
	
	public RemoveInstanceListProcessor(T... removeElementArray) {
		this.removeElementList = ArrayTools.newArrayList(removeElementArray);
	}

	@Override
	public boolean compute(T obj) {
		boolean toBeKept = true;
		
		if(this.removeElementList.contains(obj)){
			toBeKept = false;
		}
		
		return toBeKept;
	}
}
