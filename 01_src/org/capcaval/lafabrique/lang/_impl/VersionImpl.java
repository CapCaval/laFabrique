package org.capcaval.lafabrique.lang._impl;

import org.capcaval.lafabrique.lang.StringTools;
import org.capcaval.lafabrique.lang.Version;

public class VersionImpl implements Version {
	protected String versionStr = null;
	protected int[] versionIntArray = new int[]{0,0,0,0,0,0};
	protected long versionValue; 

	public VersionImpl(String versionStr) {
		// remove all characters
		versionStr = versionStr.replaceAll("[abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ]", "0");
		
		// first check the validity
		if (this.isStringValid(versionStr) == false) {
			String message = "[laFabrique] Errors : the string \"" + versionStr
					+ "\" contains unssorted characters ";
			throw new RuntimeException(message);
		} else {
			this.versionStr = versionStr;
			this.versionIntArray = this.convert(versionStr, this.versionIntArray);
			// compute a long value for  
			this.versionValue = this.computeVersionValue(this.versionIntArray);
		}
	}

	public VersionImpl(int... versionIntArray) {
		if ((versionIntArray == null) || (versionIntArray.length == 0)) {
			throw new RuntimeException(
					"[laFabrique] Errors : The int array shall contains some values");
		} else {
			// copy inputs values into the int array
			for(int i = 0; i< this.versionIntArray.length; i++){
				// check if input array has element at current index
				if(i<versionIntArray.length){
					this.versionIntArray[i]=versionIntArray[i];
				}
				else{
					// no input value put 0
					this.versionIntArray[i]=0;
				}
			}
			// compute string value
			this.versionStr = this.convert(versionIntArray);
			// compute a long value for  
			this.versionValue = this.computeVersionValue(this.versionIntArray);
		}
	}
	
	protected long computeVersionValue(int[] versionIntArray) {
		
		long factor = 1;
		long value = 0;
		
		
		int index = versionIntArray.length;
		for(int i = 0; i < index ; i++){
			value = value + (versionIntArray[index -1 - i] * factor);
			// update the factor for the next values
			factor = factor * 1_000;
		}
		
		return value;
	}

	@Override
	public String getVersionString() {
		return this.versionStr;
	}

	@Override
	public int[] getVersionIntArray() {
		return this.versionIntArray;
	}

	protected String convert(int[] versionArray) {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < versionArray.length; i++) {
			str.append(versionArray[i]);
			if (i < versionArray.length-1) {
				str.append(".");
			}
		}
		return str.toString();
	}

	public boolean isStringValid(String versionStr) {
		return StringTools.isExclusiveMadeOf(versionStr, "0123456789._-");
	}

	protected int[] convert(String versionStr, int[] inArray) {
		// first convert all most common delimiter if any to point
		versionStr = versionStr.replace("-", ".");
		versionStr = versionStr.replace("_", ".");

		// secondly split the string
		String[] splittedStrArray = versionStr.split("\\.");


		int i = 0;
		for (String str : splittedStrArray) {
			inArray[i++] = Integer.parseInt(str);
		}

		return inArray;
	}

	@Override
	public boolean isThisVersionValueHigherThan(Version version) {
		return this.versionValue>version.getLongValue();
	}
	
	@Override
	public String toString() {
		return this.versionStr;
	}


	@Override
	public boolean isThisVersionValueLowerThan(Version version) {
		return this.versionValue<version.getLongValue();
	}

	@Override
	public long getLongValue() {
		return this.versionValue;
	}

	@Override
	public boolean isThisVersionLowerThan(Version version) {
		// get the longest item version
		int thisSize = this.versionIntArray.length;
		int otherSize = version.getVersionIntArray().length;
		
		//get the smallest
		int size = thisSize;
		
		if(otherSize<thisSize){
			size = thisSize;
		}
		
		for(int i=0; i<size;i++){
			int thisValue = this.versionIntArray[i];
			int otherValue = version.getVersionIntArray()[i];
			
			if( thisValue<otherValue){
				return true;
			}else if( otherValue<thisValue){
				return false;
			}
			// else go to next 
		}
		
		return false;
	}

	@Override
	public boolean isThisVersionHigherThan(Version version) {
		// get the longest item version
		int thisSize = this.versionIntArray.length;
		int otherSize = version.getVersionIntArray().length;
		
		//get the smallest
		int size = thisSize;
		
		if(otherSize<thisSize){
			size = thisSize;
		}
		
		for(int i=0; i<size;i++){
			int thisValue = this.versionIntArray[i];
			int otherValue = version.getVersionIntArray()[i];
			
			if( thisValue>otherValue){
				return true;
			}else if( otherValue>thisValue){
				return false;
			}
			// else go to next 
		}
		
		return false;
	}

	@Override
	public boolean isThisVersionSameThan(Version version) {
		// get the two size
		int thisSize = this.versionIntArray.length;
		int otherSize = version.getVersionIntArray().length;

		if(thisSize != otherSize){
			return false;
		}

		for(int i=0; i<thisSize;i++){
			int thisValue = this.versionIntArray[i];
			int otherValue = version.getVersionIntArray()[i];
			
			if( thisValue!=otherValue){
				return false;
			}
			// else go to next 
		}

		
		
		return true;
	}

}
