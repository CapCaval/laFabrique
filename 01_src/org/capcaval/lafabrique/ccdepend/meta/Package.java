package org.capcaval.lafabrique.ccdepend.meta;

import java.util.ArrayList;
import java.util.List;

import org.capcaval.lafabrique.lang.StringMultiLine;
import org.capcaval.lafabrique.lang.StringTools;

public class Package extends ItemImpl{
	protected List<Item> childList = new ArrayList<>();
	
	public Package(String name, Item parent, ItemType type) {
		super(name, parent, type);
	}
	
	public void addChildren(Item item) {
		if(this.childList.contains(item)==false){
			this.childList.add(item);}
	}
	
	public Item[] getChildren(){
		return childList.toArray(new Item[0]);
	}
	
	@Override
	public String toString(){
		StringMultiLine strb = new StringMultiLine();
		// first get the dependencies
		strb.addLine(super.toString());

		// add all children if any
		int childNb = this.childList.size();
		if(childNb>0){
			strb.addLine(" Children : "+ childNb);
			for(Item child: this.childList){
				// add a tab on each lines
				String childStr = StringTools.shiftTextWithTab(child.toString());
				strb.addLine(childStr);
			}
		} 
		return strb.toString();
	}
}
