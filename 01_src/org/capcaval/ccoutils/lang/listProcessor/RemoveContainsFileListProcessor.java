package org.capcaval.ccoutils.lang.listProcessor;

import java.io.File;
import java.util.List;

import org.capcaval.ccoutils.lang.ArrayTools;


public class RemoveContainsFileListProcessor implements ListProcessor<File>{ 

	List<String> removeElementList;
	
	@SafeVarargs
	public RemoveContainsFileListProcessor(String... removeElementArray) {
		this.removeElementList = ArrayTools.newArrayList(removeElementArray);
	}

	@Override
	public boolean compute(File file) {
		boolean toBeKept = true;
		
		for(String removeStr : this.removeElementList){
			String absPathStr = file.getAbsolutePath();
			if(absPathStr.contains(removeStr)){
				toBeKept = false;
				// bye bye
				break;
			}
		}
		return toBeKept;
	}
}
