package org.capcaval.lafabrique.ccdepend.meta;

public interface Item {
	enum ItemType{internal, external}
	String getName();
	Item getParent();
	ItemType getType();
	void addDependency(Item item);
	Item[] getDependecies();
}
