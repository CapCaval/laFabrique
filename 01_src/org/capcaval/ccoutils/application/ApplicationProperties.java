package org.capcaval.ccoutils.application;

import java.util.ArrayList;
import java.util.List;

public class ApplicationProperties {

	protected List<AppPropertyInfo> propList;
	protected List<AppPropertyInfo> persistentPropList = new ArrayList<>();
	
	public ApplicationProperties(List<AppPropertyInfo> propList) {
		this.propList = propList;
		
		// check out all of them to find the persistent ones.
		for(AppPropertyInfo prop :  propList){
			if(prop.isPersistent){
				this.persistentPropList.add(prop);
			}
		}
	}
	
	AppPropertyInfo[] getAppPropertyInfoList(){
		return this.propList.toArray(new AppPropertyInfo[0]);
	}

	public AppPropertyInfo[] getPersistentAppPropertyInfoList() {
		return this.persistentPropList.toArray(new AppPropertyInfo[0]);
	}
}
