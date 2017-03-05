package org.capcaval.lafabrique.lafab;

public class Pack extends LaFabTaskDesc{

	public String name = null;
	public boolean packSource = false;
	public boolean packBin = false;
	public boolean packProj = false;
	public String[] extraPathToPackArray = null;

	public void name(String name) {
		this.name =name;
	}

	public void source(boolean isPacked) {
		this.packSource = isPacked;
	}

	public void bin(boolean isPacked) {
		this.packBin = isPacked;
	}
	
	public void extraPathToPack(String... dirToPackArray) {
		this.extraPathToPackArray = dirToPackArray;
	}

	public void proj(boolean isPacked) {
		this.packProj  = isPacked;
	}

}
