package org.capcaval.lafabrique.ccdepend.meta;

import java.util.ArrayList;
import java.util.List;

import org.capcaval.lafabrique.lang.StringMultiLine;
import org.capcaval.lafabrique.lang.StringTools;

public abstract class ItemImpl implements Item{

	protected String name;
	protected Item parent;
	protected List<Item> dependencyList = new ArrayList<>();
	protected ItemType itemType;

	public ItemImpl(String name, Item parent, ItemType type) {
		this.name = name;
		this.parent = parent;
		this.itemType = type;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Item getParent() {
		return this.parent;
	}

	@Override
	public void addDependency(Item item) {
		if(this.dependencyList.contains(item) == false){
			this.dependencyList.add(item);}
	}
	
	@Override
	public String toString(){
		StringMultiLine strb = new StringMultiLine();
		strb.addLine("name : " + this.name + "  " + this.itemType);
		if(this.dependencyList.size()>0){
			strb.addLine(" dependencies : "+ this.dependencyList.size());
			for(Item dep: this.dependencyList){
				// add a tab on each lines
				strb.addLine(StringTools.shiftTextWithTab(dep.getName()));
			} 
		}
		return strb.toString();
	}

	@Override
	public ItemType getType() {
		return this.itemType;
	}

	@Override
	public Item[] getDependecies() {
		return this.dependencyList.toArray(new Item[0]);
	}
}
