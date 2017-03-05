package org.capcaval.lafabrique.ccdepend;

import java.util.Arrays;
import java.util.List;

import org.capcaval.lafabrique.ccdepend.meta.Item;

public class DependencyError {

	protected Item item;
	protected List<Item> dependencyList;

	public DependencyError(Item item, List<Item> arrayList){
		this.item = item;
		this.dependencyList = arrayList;
	}
	
	public Item getItem(){
		return this.item;
	}
	
	public List<Item> getDependencyList(){
		return this.dependencyList;
	}
	
	@Override
	public String toString(){
		return "root : " + item + Arrays.toString(this.dependencyList.toArray(new Item[0]));
	}
}
