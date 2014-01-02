package org.capcaval.ccoutils.lang.listProcessor;

import java.util.List;

import org.capcaval.ccoutils.lang.ArrayTools;


public class RemoveContainsListProcessor implements ListProcessor<String>{ 

	List<String> removeElementList;
	
	@SafeVarargs
	public RemoveContainsListProcessor(String... removeElementArray) {
		this.removeElementList = ArrayTools.newArrayList(removeElementArray);
	}

	@Override
	public boolean compute(String str) {
		boolean toBeKept = true;
		
		for(String removeStr : this.removeElementList){
			if(str.contains(removeStr)){
				toBeKept = false;
				// bye bye
				break;
			}
		}
		
		return toBeKept;
	}
}
