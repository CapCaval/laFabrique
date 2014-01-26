package org.capcaval.ccoutils.lang;

public class VersionImpl implements Version {
	private String versionStr = null;
	private int[] versionIntArray = null;

	public VersionImpl(String versionStr) {
		// first check the validity
		if (this.isStringValid(versionStr) == false) {
			String message = "[ccOutils] Errors : the string \"" + versionStr
					+ "\" contains unssorted characters ";
			throw new RuntimeException(message);
		} else {
			this.versionStr = versionStr;
			this.versionIntArray = this.convert(versionStr);
		}
	}

	public VersionImpl(int... versionIntArray) {
		if ((versionIntArray == null) || (versionIntArray.length == 0)) {
			throw new RuntimeException(
					"[ccOutils] Errors : The int array shall contains some values");
		} else {
			this.versionIntArray = versionIntArray;
			this.versionStr = this.convert(versionIntArray);
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
	public boolean isMoreRecentThan(Version version) {
		boolean returnValue = false;
		int[] outArray = version.getVersionIntArray();
		int[] inArray = this.versionIntArray;

		// use the shortest array
		int longestLength = outArray.length>inArray.length?outArray.length:inArray.length;

		int[] tempArray = new int[longestLength];
		if(inArray.length < longestLength){
			System.arraycopy(inArray, 0, tempArray, 0, inArray.length);
			inArray = tempArray;
		}
		else if(outArray.length < longestLength){
			System.arraycopy(outArray, 0, tempArray, 0, outArray.length);
			outArray = tempArray;
		}
		
			
		for(int i =0; i< longestLength; i++){
			if(outArray[i] < inArray[i]){
				returnValue=true;
				break;
			}
		}
		return returnValue;
	}

	@Override
	public String toString() {
		return this.versionStr;
	}

}
