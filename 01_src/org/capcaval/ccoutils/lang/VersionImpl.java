package org.capcaval.ccoutils.lang;

public class VersionImpl implements Version {
	protected String versionStr = null;
	protected int[] versionIntArray = null;
	protected long versionValue; 

	public VersionImpl(String versionStr) {
		// first check the validity
		if (this.isStringValid(versionStr) == false) {
			String message = "[ccOutils] Errors : the string \"" + versionStr
					+ "\" contains unssorted characters ";
			throw new RuntimeException(message);
		} else {
			this.versionStr = versionStr;
			this.versionIntArray = this.convert(versionStr);
			// compute a long value for  
			this.versionValue = this.computeVersionValue(this.versionIntArray);
		}
	}

	private long computeVersionValue(int[] versionIntArray) {
		
		long factor = 1;
		long value = 0;
		
		
		int index = versionIntArray.length;
		for(int i = 0; i < index ; i++){
			value = value + (versionIntArray[index -1 - i] * factor);
			// update the factor for the next values
			factor = factor * 10_000;
		}
		
		return value;
	}

	public VersionImpl(int... versionIntArray) {
		if ((versionIntArray == null) || (versionIntArray.length == 0)) {
			throw new RuntimeException(
					"[ccOutils] Errors : The int array shall contains some values");
		} else {
			this.versionIntArray = versionIntArray;
			this.versionStr = this.convert(versionIntArray);
			// compute a long value for  
			this.versionValue = this.computeVersionValue(this.versionIntArray);
		}
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

	protected int[] convert(String versionStr) {
		// first convert all most common delimiter if any to point
		versionStr = versionStr.replace("-", ".");
		versionStr = versionStr.replace("_", ".");

		// secondly split the string
		String[] splittedStrArray = versionStr.split("\\.");

		// convert all string to int
		int[] intArray = new int[splittedStrArray.length];

		int i = 0;
		for (String str : splittedStrArray) {
			intArray[i++] = Integer.parseInt(str);
		}

		return intArray;
	}

	@Override
	public boolean isHigherVersionThan(Version version) {
		return this.versionValue>version.getLongValue();
	}

	@Override
	public String toString() {
		return this.versionStr;
	}


	@Override
	public boolean isLowerVersionThan(Version version) {
		return this.versionValue<version.getLongValue();
	}

	@Override
	public long getLongValue() {
		return this.versionValue;
	}

}
